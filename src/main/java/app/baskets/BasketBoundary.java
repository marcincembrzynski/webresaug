
package app.baskets;

import app.customers.CustomerBean;
import app.services.Appointment;
import java.security.Principal;
import java.util.Optional;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marcin
 */
@Named
@Stateless
public class BasketBoundary {
    
    private final static Logger logger = Logger.getLogger(BasketBoundary.class.getName());
    
    @Resource
    SessionContext sessionContext;
    
    
    @PersistenceContext
    EntityManager em;
    
    @Inject CustomerBean customerBean;
    
    public Basket getCustomerBasket(){
        return customerBean.getBasket();
    }
    
    public Optional<Basket> getBasketById(Long id){
        logger.info("#### id: " + id);
        
        if(null == id){
            return Optional.empty();
        }
        
        Basket find = em.find(Basket.class, id);
        
        if(null != find){
            return Optional.of(find);
        }else{
            return Optional.empty();
        }
        
    }
    
    public Basket addAppointment(Appointment appointment){
        
        Principal callerPrincipal = sessionContext.getCallerPrincipal();
        //logger.log(Level.INFO, "callerPrincipal: {0}", callerPrincipal);
        
        Basket basket = customerBean.getBasket();
        
    
        if(null != basket.getId()){
            basket = em.find(Basket.class, basket.getId());
            
        }
        
        basket.addAppointment(appointment);
        em.persist(basket);
        customerBean.setBasket(basket);
        return basket;
        
       
    }
    
    
    public Optional<Basket> removeAppointment(Long basketAppointmentId){
        
       
        // check if session customer has this this basket item.
        Basket customerBasket = customerBean.getBasket();
        Optional<BasketAppointment> findAny = customerBasket.getAppointments()
                .stream()
                .filter(a -> a.getId().equals(basketAppointmentId))
                .findAny();
        
        if(!findAny.isPresent()){
            return Optional.empty();
        }
        
        
        
        Optional<Basket> basketById = getBasketById(customerBean.getBasket().getId());
        if(basketById.isPresent()){
            Basket basket = basketById.get();
            Optional<BasketAppointment> basketAppointment = basket.getAppointments().stream().filter(a -> a.getId().equals(basketAppointmentId)).findFirst();
            
            if(basketAppointment.isPresent()){
                basket.getAppointments().remove(basketAppointment.get());
                em.remove(basketAppointment.get());
                em.persist(basket);
                return Optional.of(basket);
            }
        }
        
        
        return Optional.empty();
    }
}
