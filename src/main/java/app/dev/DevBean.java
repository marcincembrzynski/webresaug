/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dev;

import app.providers.AvailabilityPeriod;
import app.providers.Provider;
import app.services.Service;
import app.system.SystemProperty;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marcin
 */
@Singleton
@Startup
public class DevBean {
    
    
    private final static Logger logger = Logger.getLogger(DevBean.class.getName());
    
    @PersistenceContext
    EntityManager entityManager;
    
    
    
    @PostConstruct
    private void init(){
        
        
        SystemProperty s1 = new SystemProperty("CLIENT_ID", "");
        entityManager.persist(s1);
        
        SystemProperty s2 = new SystemProperty("CLIENT_SECRET", "");
        entityManager.persist(s2);
        
        logger.info("### devBean postconstruct");
        Service service1 = new Service("Service 1", Duration.ofMinutes(30), BigDecimal.TEN);
        Service service2 = new Service("Service 2", Duration.ofMinutes(40), BigDecimal.valueOf(4));
        
        Provider provider1 = new Provider("Provider 1");
        Provider provider2 = new Provider("Provider 2");
      
       
        
        AvailabilityPeriod availabilityPeriod1 = createAvailability(1, LocalTime.of(9, 0), LocalTime.of(17, 10));
        entityManager.persist(availabilityPeriod1);
        
        
        
        AvailabilityPeriod availabilityPeriod2 = createAvailability(2, LocalTime.of(9, 0), LocalTime.of(17, 10));
        entityManager.persist(availabilityPeriod2);
        
         AvailabilityPeriod availabilityPeriod3 = createAvailability(-1, LocalTime.of(9, 0), LocalTime.of(17, 10));
         entityManager.persist(availabilityPeriod3);
         
        AvailabilityPeriod availabilityPeriod4 = createAvailability(1, LocalTime.of(9, 5), LocalTime.of(17, 0));
        entityManager.persist(availabilityPeriod4);
        
        provider1.addAvailabilityPeriod(availabilityPeriod1);
        provider1.addAvailabilityPeriod(availabilityPeriod2);
        provider1.addAvailabilityPeriod(availabilityPeriod3);
        
        entityManager.persist(provider1);
        
        provider2.addAvailabilityPeriod(availabilityPeriod4);
        entityManager.persist(provider2);
        
        service1.addProvider(provider1);
        service1.addProvider(provider2);
        
        service2.addProvider(provider2);
        
        
        
        entityManager.persist(service1);
        entityManager.persist(service2);
                
    }
    
    
    private AvailabilityPeriod createAvailability(int days, LocalTime from, LocalTime to){
        
        LocalDate localDate;
        if(days > 0){
            localDate = LocalDate.now().plusDays(days);
        }else{
            localDate = LocalDate.now().minusDays(days * -1);
        }
        LocalDateTime dateTimeFrom = LocalDateTime.of(localDate, from);
        LocalDateTime dateTimeTo = LocalDateTime.of(localDate, to);
        
        AvailabilityPeriod availabilityPeriod = new AvailabilityPeriod(dateTimeFrom, dateTimeTo);
        return availabilityPeriod;
    }
}
