/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.checkout;

import app.orders.CustomerOrder;
import com.paypal.base.rest.PayPalRESTException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;


/**
 *
 * @author marcin
 */
@Named
@RequestScoped
public class PaypalBean {
    
    private final static Logger logger = Logger.getLogger(PaypalBean.class.getName());
    
    private String paymentId;
    private String token;
    private String payerId;
    
    @Inject
    PaypalCheckoutEndpoint paypalCheckoutEndpoint;
    
    
    
    
    public String pay() throws PayPalRESTException{
        
       
       
        // pay and create order
        Optional<CustomerOrder> customerOrder = paypalCheckoutEndpoint.executePayment(paymentId, payerId);
        
        if(customerOrder.isPresent()){
            
            return "order-confirmation?faces-redirect=true";
        }else{
            
            return "problemfdf";
        }
        
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

 
    
    
    
    
}
