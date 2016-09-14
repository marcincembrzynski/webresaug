
package app.services;

import app.baskets.BasketAppointment;
import app.providers.AvailabilityPeriod;
import app.providers.Provider;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static java.time.temporal.ChronoUnit.NANOS;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;



@Stateless
public class ServiceBoundary {
    
    private final static Logger logger = Logger.getLogger(ServiceBoundary.class.getName());
 
    @PersistenceContext
    EntityManager em;
    
    /**
     * create service
     * @param service
     * @return 
     */
    
    public Service create(Service service){
        em.persist(service);
        return service;
        
    }
    
    /**
     * final all services
     * @return 
     */
    
    public List<Service> findAll(){
       return em.createQuery("Select s From Service s").getResultList();
    }
    
    /***
     * find service by id
     * @param id
     * @return 
     */
    
    public Optional<Service> findById(Long id){
        Service service = em.find(Service.class, id);
        
        return Optional.of(service);
    }
    
    public Set<ServiceDate> getServiceDates(Long id){
        
        Optional<Service> findById = findById(id);
        logger.info("### getServiceDates findById: " + findById);
       
        Set<ServiceDate> set = new HashSet<>();
        
        if(findById.isPresent()){
            Service service = findById.get();
            set = service.getProviders().stream().flatMap(p ->  getServiceDatesInTheFuture(p, service).stream()).sorted().collect(Collectors.toSet());
            
            return set;
           
       
        }
        return set;
    }
    
    /**
     * 
     * @param provider
     * @param service
     * @return 
     */
    
    public Set<ServiceDate> getServiceDatesInTheFuture(Provider provider, Service service){
        /// bad
        //uriInfo.getPath();
       
        Set<ServiceDate> serviceDates = provider.getAvailabilityPeriods().stream()
                .filter(a -> a.getPeriodFrom().isAfter(LocalDateTime.now()) && a.getDuration().compareTo(service.getDuration()) >= 0)
                .map(a -> new ServiceDate(service.getId(), LocalDate.from(a.getPeriodFrom()) ))
                .collect(Collectors.toSet());
        
        logger.log(Level.INFO, "#### serviceDates: {0}", serviceDates);
        
        return serviceDates;
    }
    
    /**
     * 
     * @param serviceId
     * @param date
     * @return 
     */
    
    public List<Appointment> getAppointmentsForDate(Long serviceId, LocalDate date){
        
        List<Appointment> result = new ArrayList<>();
        
        Optional<Service> findById = findById(serviceId);
        
        if(findById.isPresent()){
            Service service = findById.get();
            result = service.getProviders().stream().flatMap(p ->  getAppointmentsForProviderAndDate(service, p, date).stream()).sorted().collect(Collectors.toList());
             
            return result;
       
        }
        
        
        return result;
    }
    
    /**
     * 
     * @param service
     * @param provider
     * @param date
     * @return 
     */
    
    public Set<Appointment> getAppointmentsForProviderAndDate(Service service, Provider provider, LocalDate date){
        
        Set<Appointment> collect = provider.getAvailabilityPeriods().stream()
                .filter(a -> a.getLocalDate().isEqual(date))
                .flatMap(a -> getAppointments(a, service, provider).stream())
                .collect(Collectors.toSet());
        
        return collect;
    }
    
    /***
     * 
     * @param availabilityPeriod
     * @param service
     * @param provider
     * @return 
     */
    
    public Set<Appointment> getAppointments(AvailabilityPeriod availabilityPeriod, Service service, Provider provider){
        
        Duration serviceDuration = service.getDuration();
        Duration availabilityDuration = availabilityPeriod.getDuration();
        Set<Appointment> result = new TreeSet<>();
        
        long a = availabilityDuration.getSeconds();
        long s = serviceDuration.getSeconds();
        logger.info("service: " + service);
        logger.info("duration: " + availabilityPeriod.getDuration().getSeconds());
        logger.info("availability period: " + availabilityPeriod);
        logger.info("## service duration " + s);
        logger.info("## availability duration " + a);
        
        
        
        long count = a / s;
        LocalDateTime periodFrom = availabilityPeriod.getPeriodFrom();
        
        LongStream.range(0, count).forEach((long i) -> {
            
            
                long seconds = s * i;
                
                LocalDateTime plus = periodFrom.plusSeconds(seconds);
                Appointment appointment = new Appointment(new Service(service), plus, new Provider(provider));
                result.add(appointment);
        
        });
        // produces
        
        return result;
    }
    
    
    /**
     * 
     * @param basketAppointment
     * @return 
     */
    
    
    public Optional<AvailabilityPeriod> findAvailabilityPeriod(BasketAppointment basketAppointment){
        
        Set<AvailabilityPeriod> availabilityPeriods = basketAppointment.getProvider().getAvailabilityPeriods();
        LocalDateTime dateTime = basketAppointment.getDateTime();
        LocalDateTime appointmentEnd = basketAppointment.getAppointmentEnd();
        
        Predicate<AvailabilityPeriod> predicate = a -> a.getPeriodFrom().isBefore(dateTime) 
                || a.getPeriodFrom().isEqual(dateTime) && a.getPeriodTo().isAfter(appointmentEnd) 
                || a.getPeriodTo().isEqual(appointmentEnd);
      
        
        return availabilityPeriods.stream().filter(predicate::test).findAny();
    }
    
    
    public boolean validBasketAppointment(BasketAppointment basketAppointment){
        return findAvailabilityPeriod(basketAppointment).isPresent();
    }
    
   
}
