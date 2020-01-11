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

    /**
     * Books an item for someone if not
     * already booked by someone else.
     *
     * @param user User who wants to reserve the item
     * @throws BookingException
     */
    @Override
    public void booking(User user) throws BookingException {
        assert user != null : "User can't be null."; // panic if user not in database
        if (this.available && !this.reserved) { // check if Item's available and is not reserved
            this.setReserved(true); // if it's the case then reserves it
            this.setUser(user); // and set user
        } else {
            throw new BookingException("Item is either already reserved or unavailable.");
        }
    }

    /**
     * Set an item to "borrowed" if not already
     * and only if reserved by the right user.
     *
     * @param user User who wants to take the item
     * @throws BookingException
     */
    @Override
    public void borrowing(User user) throws BookingException {
        assert user != null : "User can't be null."; // panic if user not in database
        if (this.reserved) // check if Item's reserved
            if (this.getUser() == user) { // if reserved then check user
                this.setReserved(false);
                this.setAvailable(false); // if users are the same then borrowing is ok
            } else
                throw new BookingException("Can't borrow reserved item.");
        else if (!this.available)
            throw new BookingException("Can't borrow already borrowed item.");
        else {
            this.setAvailable(false); // if not reserved then borrowing is ok
            this.setUser(user); // and set user
        }
    }

    /**
     * Set an item to its original state, meaning
     * it's good to be reserved or borrowed again.
     *
     * @throws ReturnException
     */
    @Override
    public void returning(User user) throws ReturnException {
        if (this.available || this.user == null)
            throw new ReturnException("Can't return non-borrowed item.");
        else if (this.user != user)
            throw new ReturnException("Can't return someone's else book.");
        else {
            this.setAvailable(true);
            this.setReserved(false);
            this.setUser(null);
        }
    }
}
