package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.api.models.CorporatePaymentModel;
import com.turkcell.rentacar.api.models.CreateCardRequest;
import com.turkcell.rentacar.api.models.IndividualPaymentModel;
import com.turkcell.rentacar.api.models.SavedCreditCard;
import com.turkcell.rentacar.business.abstracts.CreditCardService;
import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.abstracts.PosService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.PaymentDto;
import com.turkcell.rentacar.business.requests.deleteRequests.DeletePaymentRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentacar.entities.concretes.Invoice;
import com.turkcell.rentacar.entities.concretes.Payment;
import com.turkcell.rentacar.entities.concretes.Rent;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentManager implements PaymentService {

  private final PaymentDao paymentDao;
  private final ModelMapperService modelMapperService;
  private final RentService rentService;
  private final OrderedAdditionalServiceService orderedAdditionalServiceService;
  private final InvoiceService invoiceService;
  private final PosService posService;
  private final CreditCardService creditCardService;
  private final CustomerService customerService;

  public PaymentManager(
      PaymentDao paymentDao,
      ModelMapperService modelMapperService,
      InvoiceService invoiceService,
      RentService rentService,
      OrderedAdditionalServiceService orderedAdditionalServiceService,
      PosService posService,
      CreditCardService creditCardService,
      CustomerService customerService) {

    this.paymentDao = paymentDao;
    this.modelMapperService = modelMapperService;
    this.rentService = rentService;
    this.orderedAdditionalServiceService = orderedAdditionalServiceService;
    this.invoiceService = invoiceService;
    this.posService = posService;
    this.creditCardService = creditCardService;
    this.customerService = customerService;
  }

  @Override
  public DataResult<List<PaymentDto>> getAll() {

    List<Payment> result = this.paymentDao.findAll();
    // TODO : Mappingde sorun var.
    List<PaymentDto> response =
        result.stream()
            .map(payment -> this.modelMapperService.forDto().map(payment, PaymentDto.class))
            .collect(Collectors.toList());

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Transactional(rollbackFor = BusinessException.class)
  @Override
  public Result makePaymentForIndividualCustomer(
      IndividualPaymentModel paymentModel, SavedCreditCard savedCreditCard)
      throws BusinessException {

    this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(
        paymentModel.createRentForIndividualRequest().individualCustomerId());

    Rent rent =
        this.rentService
            .addForIndividualCustomer(paymentModel.createRentForIndividualRequest())
            .getData();

    this.orderedAdditionalServiceService.addWithFields(
        rent.getRentId(), paymentModel.createRentForIndividualRequest().additionalServices());

    Invoice invoice = this.invoiceService.addInvoice(rent.getRentId()).getData();

    saveCreditCard(
        paymentModel.createCardRequest(),
        savedCreditCard,
        paymentModel.createRentForIndividualRequest().individualCustomerId());

    this.posService.pos(paymentModel.createCardRequest());

    Payment payment = manuelMappingPayment(rent, invoice);
    this.paymentDao.save(payment);

    return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
  }

  @Transactional(rollbackFor = BusinessException.class)
  @Override
  public Result makeAdditionalPaymentForIndividualCustomer(
      int rentId, IndividualPaymentModel paymentModel, SavedCreditCard savedCreditCard)
      throws BusinessException {

    this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(
        paymentModel.createRentForIndividualRequest().individualCustomerId());

    Rent rent =
        this.rentService
            .createRentForIndividualCustomer(paymentModel.createRentForIndividualRequest())
            .getData();
    rent.setRentId(rentId);
    rent.setOrderedAdditionalServices(
        this.orderedAdditionalServiceService.getEntityByRentId(rent.getRentId()));

    Invoice invoice = this.invoiceService.addInvoice(rent).getData();

    saveCreditCard(
        paymentModel.createCardRequest(),
        savedCreditCard,
        paymentModel.createRentForIndividualRequest().individualCustomerId());

    this.posService.pos(paymentModel.createCardRequest());

    Rent baseRent = this.rentService.getRentEntityById(rentId);
    baseRent.setFinishDate(rent.getFinishDate());
    baseRent.setBill(baseRent.getBill() + rent.getBill());
    this.rentService.saveRentEntity(baseRent);

    Payment payment = manuelMappingPayment(rent, invoice);
    this.paymentDao.save(payment);

    return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
  }

  @Transactional(rollbackFor = BusinessException.class)
  @Override
  public Result makePaymentForCorporateCustomer(
      CorporatePaymentModel paymentModel, SavedCreditCard savedCreditCard)
      throws BusinessException {

    this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(
        paymentModel.createRentForCorporateRequest().corporateCustomerId());

    Rent rent =
        this.rentService
            .addForCorporateCustomer(paymentModel.createRentForCorporateRequest())
            .getData();

    this.orderedAdditionalServiceService.addWithFields(
        rent.getRentId(), paymentModel.createRentForCorporateRequest().additionalServices());

    Invoice invoice = this.invoiceService.addInvoice(rent.getRentId()).getData();

    saveCreditCard(
        paymentModel.createCardRequest(),
        savedCreditCard,
        paymentModel.createRentForCorporateRequest().corporateCustomerId());

    this.posService.pos(paymentModel.createCardRequest());

    Payment payment = manuelMappingPayment(rent, invoice);

    this.paymentDao.save(payment);

    return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
  }

  @Transactional(rollbackFor = BusinessException.class)
  @Override
  public Result makeAdditionalPaymentForCorporateCustomer(
      int rentId, CorporatePaymentModel paymentModel, SavedCreditCard savedCreditCard)
      throws BusinessException {

    this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(
        paymentModel.createRentForCorporateRequest().corporateCustomerId());

    Rent rent =
        this.rentService
            .createRentForCorporateCustomer(paymentModel.createRentForCorporateRequest())
            .getData();
    rent.setRentId(rentId);
    rent.setOrderedAdditionalServices(
        this.orderedAdditionalServiceService.getEntityByRentId(rent.getRentId()));

    Invoice invoice = this.invoiceService.addInvoice(rent).getData();

    saveCreditCard(
        paymentModel.createCardRequest(),
        savedCreditCard,
        paymentModel.createRentForCorporateRequest().corporateCustomerId());

    this.posService.pos(paymentModel.createCardRequest());

    Rent baseRent = this.rentService.getRentEntityById(rentId);
    baseRent.setFinishDate(rent.getFinishDate());
    baseRent.setBill(baseRent.getBill() + rent.getBill());
    this.rentService.saveRentEntity(baseRent);

    Payment payment = manuelMappingPayment(rent, invoice);
    this.paymentDao.save(payment);

    return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
  }

  @Override
  public DataResult<PaymentDto> getById(int id) throws BusinessException {
    // TODO : replace getById
    Payment payment = this.paymentDao.getById(id);
    // TODO : mapping hatali
    PaymentDto paymentDto = this.modelMapperService.forDto().map(payment, PaymentDto.class);

    return new SuccessDataResult<>(paymentDto, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result delete(DeletePaymentRequest deletePaymentRequest) throws BusinessException {

    this.paymentDao.deleteById(deletePaymentRequest.paymentId());
    return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
  }

  private void saveCreditCard(
      CreateCardRequest createCardRequest, SavedCreditCard savedCreditCard, int customerId)
      throws BusinessException {

    if (savedCreditCard == SavedCreditCard.YES) {
      this.creditCardService.add(createCardRequest, customerId);
    }
  }

  private Payment manuelMappingPayment(Rent rent, Invoice invoice) {

    Payment payment = new Payment();
    payment.setCustomer(rent.getCustomer());
    payment.setInvoice(invoice);
    payment.setRent(invoice.getRent());
    payment.setTotalAmount(rent.getBill());
    return payment;
  }
}
