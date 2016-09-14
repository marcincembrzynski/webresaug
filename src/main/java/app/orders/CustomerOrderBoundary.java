/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.orders;

import app.baskets.Basket;
import app.baskets.BasketAppointment;
import app.providers.AvailabilityPeriod;
import app.providers.ProviderAvailabilityPeriod;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marcin
 */
@Stateless
public class CustomerOrderBoundary {
    
    @PersistenceContext
    EntityManager em;
    
    public Optional<CustomerOrder> create(Basket basket){
        
        Set<BasketAppointment> appointments = basket.getAppointments();
        
        // check availability periods??
        // update availability periods...
        Optional<Set<ProviderAvailabilityPeriod>> allAvailabilityPeriods = ckeckAllAvailabilityPeriods(appointments);
        
        if(allValidBasketAppointment(appointments)){
        
        
            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setAppointments(appointments);
            em.persist(customerOrder);
            //add price to basket appointment
            //update provider availability period
            basket.setAppointments(new TreeSet<>());
            em.persist(basket);
        
            return Optional.of(customerOrder);
        
        }else{
            return Optional.empty();
        }
    }
    
    
    public Optional<ProviderAvailabilityPeriod> findAvailabilityPeriod(BasketAppointment basketAppointment){
        
        Set<AvailabilityPeriod> availabilityPeriods = basketAppointment.getProvider().getAvailabilityPeriods();
        LocalDateTime dateTime = basketAppointment.getDateTime();
        LocalDateTime appointmentEnd = basketAppointment.getAppointmentEnd();
        
        Predicate<AvailabilityPeriod> predicate = a -> a.getPeriodFrom().isBefore(dateTime) 
                || a.getPeriodFrom().isEqual(dateTime) && a.getPeriodTo().isAfter(appointmentEnd) 
                || a.getPeriodTo().isEqual(appointmentEnd);
      
        Optional<AvailabilityPeriod> findAny = availabilityPeriods.stream().filter(predicate::test).findAny();
        
        if(findAny.isPresent()){
            ProviderAvailabilityPeriod providerAvailabilityPeriod = new ProviderAvailabilityPeriod(basketAppointment.getProvider(), basketAppointment.getService(),findAny.get());
            return Optional.of(providerAvailabilityPeriod);
        }else{
            return Optional.empty();
        }
    }
    
    
    public Optional<Set<ProviderAvailabilityPeriod>> ckeckAllAvailabilityPeriods(Set<BasketAppointment> basketAppointments){
        
        if(allValidBasketAppointment(basketAppointments)){
            Set<ProviderAvailabilityPeriod> collect = basketAppointments.stream().map(a -> findAvailabilityPeriod(a).get()).collect(Collectors.toSet());
            return Optional.of(collect);
        
        }else{
            return  Optional.empty();
        }
        
       
    }
    
    /**
     * check basketAppoitnemtn exists
     * @param basketAppointment    
    */
    
    public boolean validBasketAppointment(BasketAppointment basketAppointment){
        return findAvailabilityPeriod(basketAppointment).isPresent();
    }
    
    /**
     * 
     * @param basketAppointments
     * @return 
     */
    
    public boolean allValidBasketAppointment(Set<BasketAppointment> basketAppointments){
       
        return basketAppointments.stream().allMatch(a -> validBasketAppointment(a));
       
    }
    
    
    public Set<ProviderAvailabilityPeriod> updateAvailabilityPeriods(ProviderAvailabilityPeriod providerAvailabilityPeriod){
        return null;
    }
    
    
    
    
    
    /**
     * update
     */
    
    
    
}
