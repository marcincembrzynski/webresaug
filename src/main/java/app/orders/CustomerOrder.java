/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.orders;

import app.adapters.LocalDateTimeAdapter;
import app.baskets.BasketAppointment;
import app.customers.Customer;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author marcin
 */
@Entity
public class CustomerOrder implements Serializable {
    
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   
    
    @ManyToOne
    private Customer customer;
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime created;
    
    @OneToMany
    private Set<BasketAppointment> appointments;

    public CustomerOrder() {
        this.created = LocalDateTime.now();
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        if (!(object instanceof CustomerOrder)) {
            return false;
        }
        CustomerOrder other = (CustomerOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.orders.CustomerOrder[ id=" + id + " ]";
    }
    
}
