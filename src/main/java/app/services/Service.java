
package app.services;

import app.adapters.DurationAdapter;
import app.providers.Provider;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author marcin
 */
@Entity
public class Service implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    
    @XmlJavaTypeAdapter(DurationAdapter.class)
    private Duration duration;
    
    private BigDecimal price;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Provider> providers = new HashSet<>();

    public Service() {
    }
    
    public Service(Service service){
        this.id = service.getId();
        this.name = service.getName();
        this.duration = service.getDuration();
        this.price = service.getPrice();
    }
    

    public Service(String name, Duration duration, BigDecimal price) {
        this.name = name;
        this.duration = duration;
        this.price = price;
    }
    
    public Set<Provider> addProvider(Provider provider){
        providers.add(provider);
        return providers;
    }
    

    public Service(String name) {
        this.name = name;
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Provider> getProviders() {
        return providers;
    }

    public void setProviders(Set<Provider> providers) {
        this.providers = providers;
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
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.services.Service[ id=" + id + " ]";
    }
    
}
