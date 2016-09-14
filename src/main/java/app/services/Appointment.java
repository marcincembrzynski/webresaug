/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.services;

import app.adapters.LocalDateTimeAdapter;
import app.providers.Provider;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author marcin
 */
@XmlRootElement
public class Appointment implements Comparable<Appointment>, Serializable{
    
    @NotNull
    private Service service;
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime dateTime;
    
    @NotNull
    private Provider provider;
    
   

    public Appointment() {
    }

    public Appointment(Service service, LocalDateTime dateTime, Provider provider) {
        this.service = service;
        this.dateTime = dateTime;
        this.provider = provider;
    }

    


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.service);
        hash = 97 * hash + Objects.hashCode(this.dateTime);
        hash = 97 * hash + Objects.hashCode(this.provider);
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
        final Appointment other = (Appointment) obj;
        if (!Objects.equals(this.service, other.service)) {
            return false;
        }
        if (!Objects.equals(this.dateTime, other.dateTime)) {
            return false;
        }
        if (!Objects.equals(this.provider, other.provider)) {
            return false;
        }
        return true;
    }

   

    

    @Override
    public int compareTo(Appointment o) {
        return dateTime.compareTo(o.getDateTime());
    }
    
    
    
}
