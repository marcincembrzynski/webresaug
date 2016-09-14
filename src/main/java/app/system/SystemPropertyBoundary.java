/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.system;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marcin
 */
@Singleton
public class SystemPropertyBoundary {
    
    
    @PersistenceContext
    EntityManager em;
    
    
    public String getPropertyValue(String key){
        SystemProperty singleResult = (SystemProperty) em.createQuery("Select s From SystemProperty s WHERE s.propertyKey= :key").setParameter("key", key).getSingleResult();
        return singleResult.getPropertyValue();
    }
}
