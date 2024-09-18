package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

/**
 * API for administrative functions
 *
 * @author Amy Lanclos
 */
public class AdminResource {

    //This is the static singleton instance, it was always here, but it is now
    // changed to lazy instantiation like the examples on the linked site rather than the eager/early
    // instantiation like shown in class
    private static AdminResource instance = null;

    private static final CustomerService cs = CustomerService.getInstance();
    private static final ReservationService rs = ReservationService.getInstance();

    /**
     * gets the instance of this resource
     * @return the AdminResource instance
     */
    public static AdminResource getInstance() {
        if(instance == null) {
            instance = new AdminResource();
        }
        return instance;
    }

    private AdminResource() {

    }

    /**
     * retrieves a customer via email
     * @param email email
     * @return customer with given email, or null if none exists
     */
    public Customer getCustomer(String email) {
        return cs.getCustomer(email);
    }

    /**
     * displays all reservations currently in the system
     */
    public void displayAllReservations() {
        rs.printAllReservation();
    }

    /**
     * adds a set of rooms to the hotel
     *
     * @param rooms the List of IRoom to be added
     */
    public void addRoom(List<IRoom> rooms) {
        if(rooms != null) {
            for (IRoom r : rooms) {
                rs.addRoom(r);
            }
        }
    }

    /**
     * retrieves all customer accounts
     * @return collection of all customers in the system
     */
    public Collection<Customer> getAllCustomers() {
        return cs.getAllCustomers();
    }

    /**
     * all the current rooms
     * @return collection of all IRoom
     */
    public Collection<IRoom> getAllRooms() {
        return rs.getAllRooms();
    }
}
