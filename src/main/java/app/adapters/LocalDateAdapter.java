/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.adapters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author marcin
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate>{

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v, DateTimeFormatter.ISO_DATE);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return DateTimeFormatter.ISO_DATE.format(v);
    }
    
}
