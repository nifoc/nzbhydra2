package org.nzbhydra.mapping.newznab;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;


//@XmlRootElement(name = "attr", namespace = "http://www.newznab.com/DTD/2010/feeds/attributes/")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class NewznabAttribute {

    public NewznabAttribute() {
    }

    public NewznabAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String value;


}
