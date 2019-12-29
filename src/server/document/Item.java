/**
 * Abstract Class used to easily add more things to
 * the server.document.book.library in the future.
 * This Class also have everything it needs to be
 * converted into an XML file.
 *
 * @author Marius Vallas, Gabriel Arbane, Antoine Dedieu
 * @version 1.4
 */

package server.document;

import server.user.User;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class Item implements iDocument {
    private String ID;
    private boolean available;
    private boolean reserved;
    private User user;

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

    /* Auto generated getter */
    public User getUser() {
        return user;
    }

    /* Auto generated setter */
    @XmlElement
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void booking(User user) throws BookingException {
        if (this.available && !this.reserved) { // check if Item's available and is not reserved
            this.setReserved(true); // if it's the case then reserves it
            this.setUser(user); // and set user
        } else {
            System.out.println("Item is either already reserved or unavailable."); // if not then display error
        }
    }

    @Override
    public void borrowing(User user) throws BookingException {
        if (this.reserved) // check if Item's reserved
            if (this.getUser() == user) // if reserved then check user
                this.setAvailable(false); // if users are the same then borrowing is ok
            else
                System.out.println("reserved"); // if not then display error
        else {
            this.setAvailable(false); // if not reserved then borrowing is ok
            this.setUser(user); // and set user
        }
    }

    @Override
    public void returning() throws ReturnException {
        this.setAvailable(true);
        this.setReserved(false);
        this.setUser(null);
    }
}
