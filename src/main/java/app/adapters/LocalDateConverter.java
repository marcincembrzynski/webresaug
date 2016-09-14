/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.adapters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.ws.rs.ext.ParamConverter;
import org.jboss.logging.Logger;


public class LocalDateConverter implements ParamConverter<LocalDate>{
    
    private final static Logger logger = Logger.getLogger(LocalDateConverter.class.getName());

    @Override
    public LocalDate fromString(String value) {
        logger.info("#### value" + value);
       return LocalDate.parse(value, DateTimeFormatter.ISO_DATE);
    }

    @Override
    public String toString(LocalDate value) {
       return DateTimeFormatter.ISO_DATE.format(value);
    }
    
}
