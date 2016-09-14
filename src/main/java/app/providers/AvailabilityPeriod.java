/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.providers;

import app.adapters.DurationAdapter;
import app.adapters.LocalDateAdapter;
import app.adapters.LocalDateTimeAdapter;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author marcin
 */
@Entity
public class AvailabilityPeriod implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime periodFrom;
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime periodTo;
    
    @Transient
    @XmlJavaTypeAdapter(DurationAdapter.class)
    private Duration duration;
    
    
    
    @Transient
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate localDate;

    public AvailabilityPeriod() {
    }
    
    
    

    public AvailabilityPeriod(LocalDateTime periodFrom, LocalDateTime periodTo) {
        this.periodFrom = periodFrom;
        this.periodTo = periodTo;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(LocalDateTime periodFrom) {
        this.periodFrom = periodFrom;
    }

    public LocalDateTime getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(LocalDateTime periodTo) {
        this.periodTo = periodTo;
    }
    
    
    public Duration getDuration(){
        duration = Duration.between(periodFrom, periodTo);
        return duration;
    }
    
    
    public LocalDate getLocalDate(){
        return LocalDate.from(periodFrom);
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
        if (!(object instanceof AvailabilityPeriod)) {
            return false;
        }
        AvailabilityPeriod other = (AvailabilityPeriod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.providers.AvailabilityPeriod[ id=" + id + " ]";
    }
    
}
