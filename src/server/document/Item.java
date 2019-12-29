/**
 * Abstract Class used to easily add more things to
 * the server.document.library in the future.
 * This Class also have everything it needs to be
 * converted into an XML file.
 *
 * @author Marius Vallas, Gabriel Arbane, Antoine Dedieu
 * @version 1.4
 */

package server.document;

import server.document.exception.BookingException;
import server.document.exception.ReturnException;
import server.user.User;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class Item implements iDocument {
    private String ID;
    private boolean available;
    private boolean reserved;

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

    /* Auto generated getter */
    public boolean isReserved() {
        return reserved;
    }

    /* Auto generated setter */
    @XmlElement
    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public void booking(User ab) throws BookingException {

    }

    @Override
    public void borrowing(User ab) throws BookingException {

    }

    @Override
    public void returning() throws ReturnException {

    }
}
