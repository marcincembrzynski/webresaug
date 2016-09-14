/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.services;

import app.adapters.LocalDateAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class ServiceDate implements Serializable, Comparable<ServiceDate>{
    
    private Long serviceId;
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate serviceDate;
    private Link link;

    public ServiceDate() {
    }
    
    
    

    public ServiceDate(Long serviceId, LocalDate serviceDate) {
        this.serviceId = serviceId;
        this.serviceDate = serviceDate;
    }

    public ServiceDate(Long serviceId, LocalDate serviceDate, Link link) {
        this.serviceId = serviceId;
        this.serviceDate = serviceDate;
        this.link = link;
    }
    
    
    

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    

    @Override
    public int compareTo(ServiceDate o) {
        return this.serviceDate.compareTo(o.serviceDate);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.serviceDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ServiceDate other = (ServiceDate) obj;
        if (!Objects.equals(this.serviceDate, other.serviceDate)) {
            return false;
        }
        return true;
    }

   
    
    
    
}
