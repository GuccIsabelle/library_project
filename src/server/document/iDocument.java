package server.document;

import server.user.User;

public interface iDocument {
    void booking(User ab) throws BookingException;

    void borrowing(User ab) throws BookingException;

    /* return document or cancel booking */
    void returning() throws ReturnException;

    class BookingException extends Exception {
    }

    class ReturnException extends Exception {
    }
}
