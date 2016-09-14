/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.adapters;

import java.time.Duration;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author marcin
 */
public class DurationAdapter  extends XmlAdapter<String,Duration>{

    @Override
    public Duration unmarshal(String v) throws Exception {
        return Duration.parse(v);
    }

    @Override
    public String marshal(Duration v) throws Exception {
        return v.toString();
    }
}
