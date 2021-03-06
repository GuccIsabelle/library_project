/**
 * Sorry but I love adding that lil' "i" before
 * the interface's name, only because I heard
 * some professors don't like it. Sorry again.
 * - Marius Vallas.
 *
 * @author Jean-François Brette
 * @contributor Marius Vallas
 * @version 1.1
 */

package server.document;

import server.user.User;

public interface iDocument {
    /* books an item for someone */
    void booking(User ab) throws BookingException;

    /* set an item to borrowed */
    void borrowing(User ab) throws BookingException;

    /* return document or cancel booking */
    void returning(User user) throws ReturnException;

    /**
     * SubClass for the BookingException.
     * It just calls the super() constructor.
     */
    class BookingException extends Exception {
        public BookingException(String error) {
            super(error);
        }
    }

    /**
     * SubClass for the BookingException.
     * It just calls the super() constructor.
     */
    class ReturnException extends Exception {
        public ReturnException(String error) {
            super(error);
        }
    }
}
