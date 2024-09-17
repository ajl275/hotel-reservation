package model;

import api.AdminResource;
import api.HotelResource;
import service.*;

import java.util.*;

/**
 * testing class
 *
 * @author Amy Lanclos
 */
public class Tester {

    public static void main(String[] args) {
        Customer customer = new Customer("first","second","j@domain.com");
        System.out.println(customer);
        Customer customer2 = new Customer("first","second","email@email.email");
        System.out.println(customer2);
        assert !customer.equals(customer2);
        Customer customer1 = customer;
        assert customer1.equals(customer);
        assert customer.equals(customer);
        assert customer.hashCode() == customer.hashCode();
        assert customer1.hashCode() == customer.hashCode();
        Customer customer3 = new Customer("first","second","j@domain.com");
        assert customer1.equals(customer3);
        assert customer3.equals(customer);
        assert customer3.equals(customer1);
        assert customer.hashCode() == customer1.hashCode() && customer.hashCode() == customer3.hashCode() && customer1.hashCode() == customer3.hashCode();

        CustomerService cs = CustomerService.getInstance();
        ReservationService rs = ReservationService.getInstance();
        HotelResource hr = HotelResource.getInstance();
        AdminResource ar = AdminResource.getInstance();

        cs.addCustomer("email@email.email", "John", "Smith");
        cs.addCustomer("e.m.ail@gmail.edu", "Joe", "Schmoe");

        for(Customer c : cs.getAllCustomers()) {
            System.out.println(c);
        }

        System.out.println(cs.getCustomer("e.m.ail@gmail.edu"));

        List<IRoom> rooms = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            rooms.add(new Room(Integer.toString(i),99.99, RoomType.SINGLE));
        }

        ar.addRoom(rooms);

        for(IRoom r : ar.getAllRooms()) {
            System.out.println(r);
        }

        hr.createACustomer("this@is.email", "Sarah", "Lance");
        System.out.println(hr.getRoom("3"));
        System.out.println(hr.getCustomer("this@is.email"));
        Date checkIn = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2031,2,2);
        Collection<IRoom> available = hr.findARoom(checkIn, calendar.getTime());
        for(IRoom r : available) {
            System.out.println(r);
        }
        hr.bookARoom("this@is.email",available.stream().iterator().next(),checkIn,calendar.getTime());
        for(Reservation r : hr.getCustomersReservations("this@is.email")) {
            System.out.println(r);
        }

        checkIn = new Date();
        available = hr.findARoom(checkIn,calendar.getTime());
        for(IRoom r : available) {
            System.out.println(r);
        }
        hr.bookARoom("e.m.ail@gmail.edu",available.stream().iterator().next(),checkIn,calendar.getTime());

        System.out.println(cs.getCustomer("bad email"));
        System.out.println(hr.getCustomer("email@email.com"));
        System.out.println(hr.getRoom("5"));

        for(Customer c : cs.getAllCustomers()) {
            System.out.println(c);
        }

        System.out.println(ar.getCustomer("email@email.email"));

        ar.displayAllReservations();

        for(Customer c : ar.getAllCustomers()) {
            System.out.println(c);
        }


    }
}
