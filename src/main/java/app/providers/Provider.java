
package app.providers;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author marcin
 */
@Entity
public class Provider implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name; 
    
    @OneToMany(fetch = FetchType.EAGER)
    private Set<AvailabilityPeriod> availabilityPeriods = new HashSet<>();

    public Provider() {
    }
    
    
    public Provider(Provider provider){
        this.name = provider.getName();
        this.id = provider.getId();
    }

    public Provider(String name) {
        this.name = name;
    }
    
    public Set<AvailabilityPeriod> addAvailabilityPeriod(AvailabilityPeriod availabilityPeriod){
        availabilityPeriods.add(availabilityPeriod);
        return availabilityPeriods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    

    public Provider(String name, Set<AvailabilityPeriod> availabilityPeriods) {
        this.name = name;
        this.availabilityPeriods = availabilityPeriods;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<AvailabilityPeriod> getAvailabilityPeriods() {
        return availabilityPeriods;
    }

    public void setAvailabilityPeriods(Set<AvailabilityPeriod> availabilityPeriods) {
        this.availabilityPeriods = availabilityPeriods;
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
        if (!(object instanceof Provider)) {
            return false;
        }
        Provider other = (Provider) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.providers.Provider[ id=" + id + " ]";
    }
    
}
