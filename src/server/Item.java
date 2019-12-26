/**
 * Abstract Class used to easily add more things to
 * the library in the future.
 * This Class also have everything it needs to be
 * converted into an XML file.
 *
 * @author Marius Vallas, Gabriel Arbane, Antoine Dedieu
 * @version 1.4
 * @since 26-12-2019
 */

package server;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class Item {
    private String ID;
    private boolean available;

    /**
     * Empty constructor because of the XML integration.
     */
    public Item() {
    }

    /* Auto generated getter */
    public String getID() {
        return ID;
    }

    /* Auto generated setter */
    @XmlAttribute
    public void setID(String ID) {
        this.ID = ID;
    }

    /* Auto generated getter */
    public boolean isAvailable() {
        return available;
    }

    /* Auto generated setter */
    @XmlElement
    public void setAvailable(boolean available) {
        this.available = available;
    }
}
