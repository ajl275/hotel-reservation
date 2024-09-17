package model;

import java.util.Date;

/**
 * Reservation
 *
 * @author Amy Lanclos
 */
public class Reservation {

    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate, checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        this.customer = customer;
        this.room = room;
        checkInDate = checkIn;
        checkOutDate = checkOut;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Room: " + room + "\nReserved for " + customer + "\n" +
        "From: " + checkInDate + " to " + checkOutDate;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null) return false;
        if(o.getClass() == this.getClass()) {
            Reservation c = (Reservation) o;
            if((customer == null && c.getCustomer() == null) || (!(customer == null || c.getCustomer() == null) && customer.equals(c.getCustomer()))) {
                if(room == c.getRoom() || (room != null && room.equals(c.getRoom()))) {
                    if(checkInDate == c.getCheckInDate() || (checkInDate != null && checkInDate.equals(c.getCheckInDate()))) {
                        return checkOutDate == c.getCheckOutDate() || (checkOutDate != null && checkOutDate.equals(c.getCheckOutDate()));
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (customer == null ? 0 : customer.hashCode());
        hash = 31 * hash + (room == null ? 0 : room.hashCode());
        hash = 31 * hash + (checkInDate == null ? 0 : checkInDate.hashCode());
        hash = 31 * hash + (checkOutDate == null ? 0 : checkOutDate.hashCode());
        return hash;
    }
}
