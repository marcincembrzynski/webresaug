/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.checkout;

import app.baskets.Basket;
import app.baskets.BasketAppointment;
import app.baskets.BasketBoundary;
import app.customers.Customer;
import app.customers.CustomerBean;
import app.orders.CustomerOrder;
import app.orders.CustomerOrderBoundary;
import app.system.SystemPropertyBoundary;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author marcin
 */
@Stateless
@Path("/checkout")
public class PaypalCheckoutEndpoint {

    private final static Logger logger = Logger.getLogger(PaypalCheckoutEndpoint.class.getName());

 
    private final String CLIENT_ID = "CLIENT_ID";
    private final String CLIENT_SECRET = "CLIENT_SECRET";
    private final String mode = "sandbox";
    private final String APPROVED = "approved";

    @Inject
    BasketBoundary basketBoundary;

    @Inject
    CustomerOrderBoundary customerOrderBoundary;
    
    
    @Inject 
    SystemPropertyBoundary systemPropertyBoundary;

    @Inject
    CustomerBean customerBean;

    public Optional<CustomerOrder> executePayment(String paymentId, String payerId) throws PayPalRESTException {

        APIContext apiContext = new APIContext(systemPropertyBoundary.getPropertyValue(CLIENT_ID), systemPropertyBoundary.getPropertyValue(CLIENT_SECRET), mode);

        Long basketId = customerBean.getBasket().getId();

        Optional<Basket> basketById = basketBoundary.getBasketById(basketId);
        //customerOrderBoundary.ckeckAllAvailabilityPeriods(null)
        
        
        if (basketById.isPresent()) {
            ///payment.sett
            Payment payment = new Payment();
            payment.setId(paymentId);
            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(payerId);

            Payment executedPayment = payment.execute(apiContext, paymentExecution);

            if (APPROVED.equals(executedPayment.getState())) {
                PayerInfo payerInfo = executedPayment.getPayer().getPayerInfo();
                Customer customer = new Customer(payerInfo.getEmail(), payerInfo.getFirstName(), payerInfo.getLastName());

             ///Optional<Basket> basketById = basketBoundary.getBasketById(basketId);
                Basket basket = basketById.get();
                Optional<CustomerOrder> optionalCustomerOrder = customerOrderBoundary.create(basket);
                return optionalCustomerOrder;

             //1. create order
                //2. create customer / check for customer with email address
                //3. authenticated customer ?log customer
                //
            }
            
            logger.log(Level.INFO, "payment: {0}", executedPayment.getState());

        }

        

        return Optional.empty();
    }

    @POST
    public Response checkout() throws URISyntaxException, PayPalRESTException {

        // get basket;
        Long basketId = customerBean.getBasket().getId();
        Optional<Basket> basketById = basketBoundary.getBasketById(basketId);

        if (!basketById.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // 1. call for token 
        // 2. create payment
         APIContext apiContext = new APIContext(systemPropertyBoundary.getPropertyValue(CLIENT_ID), systemPropertyBoundary.getPropertyValue(CLIENT_SECRET), mode);

        Payment payment = getPaymentFromBasket(basketById.get());
        //transaction.se

        ///payment.sett
        Payment createdPayment = payment.create(apiContext);

        List<Links> links = createdPayment.getLinks();

        Optional<Links> approvalUrl = links.stream().filter(e -> e.getRel().equals("approval_url")).findFirst();

        //RedirectUrls redirectUrls = createPayment.getRedirectUrls();
        //createPayment.g
        logger.info("##### approval url: " + approvalUrl.get().getHref());

        URI uri = new URI(approvalUrl.get().getHref());
        return Response.seeOther(uri).build();
    }

    private Payment getPaymentFromBasket(Basket basket) {

        RedirectUrls redirectUrls = new RedirectUrls();

        // move this to parameters
        redirectUrls.setReturnUrl("https://localhost:8443/webresaug/paypal-approval.jsf");
        redirectUrls.setCancelUrl("https://localhost:8443/webresaug/index.html?cancel=1");

        Payment payment = new Payment();
        payment.setIntent("sale");
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        payment.setPayer(payer);
        payment.setRedirectUrls(redirectUrls);
        Transaction transaction = new Transaction();

        Amount amount = new Amount();
        amount.setCurrency("GBP");
        amount.setTotal(basket.getTotalValue().toString());

        transaction.setAmount(amount);

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        for (BasketAppointment a : basket.getAppointments()) {
            // name of the item, include date and provider name
            Item item = new Item(a.getService().getName(), "1", a.getService().getPrice().toString(), "GBP");
            items.add(item);

        }

        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(transaction);
        payment.setTransactions(transactions);

        return payment;
    }

}
