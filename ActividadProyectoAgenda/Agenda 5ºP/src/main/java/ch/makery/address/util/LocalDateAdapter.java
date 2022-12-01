package ch.makery.address.util;

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter {

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}
}
