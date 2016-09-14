/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.baskets;

import app.adapters.LocalDateTimeAdapter;
import app.providers.Provider;
import app.services.Appointment;
import app.services.Service;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Predicate;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 *
 * @author marcin
 */
@Entity
public class BasketAppointment implements Serializable, Comparable<BasketAppointment> {
    
    private final static Logger logger = Logger.getLogger(BasketAppointment.class.getName());
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private Service service;
    
    @OneToOne
    private Provider provider;
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime dateTime;
    
    @Transient
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime appointmentEnd;
    
    private BigDecimal price;

    public BasketAppointment() {
    }

    public BasketAppointment(Appointment appointment) {
        this.service = appointment.getService();
        this.provider = appointment.getProvider();
        this.dateTime = appointment.getDateTime();
        this.price = appointment.getService().getPrice();
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

   
    
    
    public LocalDateTime getAppointmentEnd(){
      
        return this.dateTime.plusSeconds(this.service.getDuration().getSeconds());
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    

    public BasketAppointment(BigDecimal price) {
        this.price = price;
    }
    
   
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BasketAppointment)) {
            return false;
        }
        BasketAppointment other = (BasketAppointment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.baskets.BasketAppointment[ id=" + id + " ]";
    }

    @Override
    public int compareTo(BasketAppointment o) {
        return this.dateTime.compareTo(o.getDateTime());
    }
    
    /***
     * 
     * @param other
     * @return 
     */
    
    public boolean colide(BasketAppointment other){
        
        Predicate<BasketAppointment> sameStart = (e) -> this.dateTime.equals(e.getDateTime());
        Predicate<BasketAppointment> sameEnd = (e) -> this.getAppointmentEnd().equals(e.getAppointmentEnd());
        
        Predicate<BasketAppointment> startsAfter = (o) -> o.getDateTime().isAfter(this.dateTime) && o.getDateTime().isBefore(this.getAppointmentEnd());
        Predicate<BasketAppointment> endsBefore = (o) ->  o.getAppointmentEnd().isAfter(this.dateTime) &&   o.getAppointmentEnd().isBefore(this.dateTime);
        
        Predicate<BasketAppointment> test = sameStart.or(sameEnd).or(startsAfter).or(endsBefore);
     
        
        return test.test(other);
        
       
        
       
    }
    
}
