package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

/**
 * This service is the api for creating and retrieving rooms and reservations
 *
 * @author Amy Lanclos
 */
public class ReservationService {

    private static final Map<String, IRoom> rooms = new HashMap<>();
    private static final Map<Customer, List<Reservation>> customerReservations = new HashMap<>();
    private static final Map<IRoom, List<Reservation>> roomReservations = new HashMap<>();

    /**
     * adds a room to the hotel
     *
     * @param room the room to be added
     */
    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(),room);
    }

    /**
     * retrieves a room via its room number
     *
     * @param roomId the number of the room to be retrieved
     * @return the IRoom object representing this room number
     */
    public IRoom getARoom(String roomId) {
        return rooms.getOrDefault(roomId, null);
    }

    /**
     * Creates a new reservation for a customer to reserve a room for specified dates
     *
     * @param customer Customer reserving the room
     * @param room room to be reserved
     * @param checkInDate check-in time
     * @param checkOutDate check-out time
     * @return the newly created reservation
     */
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation r = new Reservation(customer, room, checkInDate, checkOutDate);
        List<Reservation> cr = customerReservations.getOrDefault(customer, new ArrayList<>());
        cr.add(r);
        customerReservations.put(customer,cr);
        List<Reservation> rr = roomReservations.getOrDefault(room, new ArrayList<>());
        rr.add(r);
        roomReservations.put(room,rr);
        return r;
    }

    /**
     * Finds the set of rooms, if any, that are available for the specified dates
     *
     * @param checkInDate specifies desired check-in date
     * @param checkOutDate specifies desired check-out date
     * @return a Collection of IRooms that are available for the date range
     */
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> available = new ArrayList<>();

        for(IRoom room : rooms.values()) {
            List<Reservation> reservations = roomReservations.get(room);
            if(reservations == null || reservations.isEmpty()) {
                available.add(room);
            } else {
                boolean conflict = false;
                for(Reservation r : reservations) {
                    if(!(checkInDate.before(r.getCheckInDate()) || checkInDate.after(r.getCheckOutDate())) || !(checkOutDate.before(r.getCheckInDate()) || checkOutDate.after(r.getCheckOutDate()))) {
                        conflict = true;
                    } else {
                        if(!(r.getCheckInDate().before(checkInDate) || r.getCheckInDate().after(checkOutDate))) {
                            conflict = true;
                        }
                    }
                }
                if(!conflict) {
                    available.add(room);
                }
            }
        }

        return available;
    }

    /**
     * retrieves all the reservations made by a particular customer
     * @param customer Customer to retrieve reservations for
     * @return Collection of all this customers reservations
     */
    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return customerReservations.getOrDefault(customer,null);
    }

    /**
     * displays all reservations currently in the system
     */
    public void printAllReservation() {
        for(IRoom rr : roomReservations.keySet()) {
            for(Reservation r : roomReservations.get(rr)) {
                System.out.println(r);
            }
        }
    }

    /**
     * retrieves all the rooms in the system
     *
     * @return collection of all the rooms in the system
     */
    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    private static final ReservationService instance = new ReservationService();

    /**
     * gets the singleton instance of this service
     * @return the reservation service instance
     */
    public static ReservationService getInstance() {
        return instance;
    }

}
