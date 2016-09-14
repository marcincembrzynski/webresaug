/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.baskets;

import app.customers.Customer;
import app.services.Appointment;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 *
 * @author marcin
 */
@Entity
public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BasketAppointment> appointments = new TreeSet<>();
    
    @OneToOne(mappedBy = "basket")
    private Customer customer;
    
    @Transient
    private BigDecimal totalValue;
    
    /***
     * 
     * @param appointment
     * @return 
     */
    
    public Basket addAppointment(Appointment appointment){
        
        
        Optional<BasketAppointment> findAny = appointments.stream().filter(a -> a.colide(new BasketAppointment(appointment))).findAny();
        
        if(!findAny.isPresent()){
            BasketAppointment basketAppointment = new BasketAppointment(appointment);
            appointments.add(basketAppointment);
        } 
       
        return this;
        
       
    }

    public BigDecimal getTotalValue() {
        
        BigDecimal total = appointments.stream()
                .map(a -> a.getService().getPrice())
                .reduce(BigDecimal.ZERO, (a,b) -> a.add(b));
        
        return total;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<BasketAppointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<BasketAppointment> appointments) {
        this.appointments = appointments;
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
        if (!(object instanceof Basket)) {
            return false;
        }
        Basket other = (Basket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.baskets.Basket[ id=" + id + " ]";
    }
    
}
