/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.adapters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author marcin
 */
@Provider
public class LocalDateConverterProvider implements ParamConverterProvider{
    
    private final LocalDateConverter localDateConverter = new LocalDateConverter();

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
         if(!rawType.equals(LocalDate.class)){
            return null;
        }
        
        return (ParamConverter<T>) localDateConverter;
    }
    
}
