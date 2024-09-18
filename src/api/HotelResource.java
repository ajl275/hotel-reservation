package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

/**
 * The service for creating and retrieving customers and for customers booking reservations
 *
 * @author Amy Lanclos
 */
public class HotelResource {

    //This is the static singleton instance, it was always here, but it is now
    // changed to lazy instantiation like the examples on the linked site rather than the eager/early
    // instantiation like shown in class
    private static HotelResource instance = null;

    private static final CustomerService cs = CustomerService.getInstance();
    private static final ReservationService rs = ReservationService.getInstance();

    /**
     * the singleton instance of this api
     *
     * @return the HotelResource instance
     */
    public static HotelResource getInstance() {
        if(instance == null) {
            instance = new HotelResource();
        }
        return instance;
    }

    private HotelResource() { }

    /**
     * retrieves customer via their email
     * @param email customer's email
     * @return the customer with this email
     */
    public Customer getCustomer(String email) {
        return cs.getCustomer(email);
    }

    /**
     * creates a new customer
     * @param email email
     * @param firstName first name
     * @param lastName last name
     */
    public void createACustomer(String email, String firstName, String lastName) {
        cs.addCustomer(email,firstName,lastName);
    }

    /**
     * get room from room number
     * @param roomNumber room number
     * @return the room
     */
    public IRoom getRoom(String roomNumber) {
        return rs.getARoom(roomNumber);
    }

    /**
     * books a new reservation
     *
     * @param customerEmail email of customer booking room
     * @param room the room being booked
     * @param  checkInDate check In Date
     * @param checkOutDate check Out Date
     * @return The newly created Reservation
     */
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return rs.reserveARoom(cs.getCustomer(customerEmail),room,checkInDate,checkOutDate);
    }

    /**
     * retrieves all the reservations made by a particular customer
     * @param customerEmail email ofCustomer to retrieve reservations for
     * @return Collection of all this customer's reservations
     */
    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        return rs.getCustomersReservation(cs.getCustomer(customerEmail));
    }

    /**
     * find rooms available for specified dates
     * @param checkIn desired check-in date
     * @param checkOut desired check-out date
     * @return collection of available rooms
     */
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return rs.findRooms(checkIn,checkOut);
    }


}
