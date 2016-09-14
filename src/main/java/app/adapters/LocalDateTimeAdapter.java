/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.adapters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author marcin
 */
public class LocalDateTimeAdapter extends XmlAdapter<String,LocalDateTime>{

    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v, DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
       return DateTimeFormatter.ISO_DATE_TIME.format(v);
    }
    
}
