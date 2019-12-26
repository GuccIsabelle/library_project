package server;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class Item {
    private String ID;
    private boolean available;

    public Item() {
    }

    public String getID() {
        return ID;
    }

    @XmlAttribute
    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isAvailable() {
        return available;
    }

    @XmlElement
    public void setAvailable(boolean available) {
        this.available = available;
    }
}
