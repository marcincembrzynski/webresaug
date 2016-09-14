/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.providers;

import app.services.Service;
import java.util.Objects;

/**
 *
 * @author marcin
 */
public class ProviderAvailabilityPeriod {
    
    
    private final Provider provider;
    private final Service service;
    private final AvailabilityPeriod availabilityPeriod;

    public ProviderAvailabilityPeriod(Provider provider, Service service, AvailabilityPeriod availabilityPeriod) {
        this.provider = provider;
        this.service = service;
        this.availabilityPeriod = availabilityPeriod;
    }

    public Service getService() {
        return service;
    }

   

    public Provider getProvider() {
        return provider;
    }

    public AvailabilityPeriod getAvailabilityPeriod() {
        return availabilityPeriod;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.provider);
        hash = 29 * hash + Objects.hashCode(this.availabilityPeriod);
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
        final ProviderAvailabilityPeriod other = (ProviderAvailabilityPeriod) obj;
        if (!Objects.equals(this.provider, other.provider)) {
            return false;
        }
        if (!Objects.equals(this.availabilityPeriod, other.availabilityPeriod)) {
            return false;
        }
        return true;
    }
    
    
    
}
