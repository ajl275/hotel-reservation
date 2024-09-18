package menu;

import api.HotelResource;
import model.Reservation;
import model.IRoom;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * main menu and entry point of the hotel reservation application
 *
 * @author Amy Lanclos
 */
public class MainMenu {
    private static final String menuText = """
            -----------------------------------------
            1. Find and reserve a room
            2. See my reservations
            3. Create an account
            4. Admin
            5. Exit
            -----------------------------------------""";

    private static final Scanner scanner = new Scanner(System.in);
    private static final HotelResource hr = HotelResource.getInstance();
    private static final int searchRange = 7; //how far out to search for recommended rooms, could allow customer to specify
    private static String email = ""; //current email for a "logged-in" user, i.e. when booking a room the customer's account even if newly created
    private static final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy"); //allows users to enter mm/dd/yyyy style dates

    public static void main(String[] args) {
        while(true) {
            displayMenu();
            try {
                String input = scanner.nextLine();
                parseInput(input);
            } catch (Exception e) {
                System.out.println("Please choose a valid selection \"1-5\"");
            }
        }
    }

    private static void displayMenu() {
        System.out.println(menuText);
    }

    private static void parseInput(String in) {
        int selection = Integer.parseInt(in);
        switch(selection) {
            case 1-> findAndReserveRoom();
            case 2-> viewReservations();
            case 3-> createAccount();
            case 4-> AdminMenu.run();
            case 5-> System.exit(0);
            default-> throw new IllegalArgumentException();
        }
    }

    private static void findAndReserveRoom() {
        Date checkIn = null;
        Date checkOut = null;

        boolean check = false;

        while (!check) {
            try {
                System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/01/2020");
                checkIn = format.parse(scanner.nextLine());
                System.out.println(Calendar.getInstance().getTime());
                if(checkIn.before(Calendar.getInstance().getTime())) {
                    throw new IllegalArgumentException(); //make them enter a date after the check-in they specified!
                }
                check = true;
            } catch (Exception e) {
                //System.out.println(e.toString());
                System.out.println("Please enter a valid check-in-date in format: mm/dd/yyyy");
            }
        }
        check = false;
        while(!check) {
            try{
                System.out.println("Enter CheckOut Date month/day/year example 2/21/2020");
                checkOut = format.parse(scanner.nextLine());
                if(checkOut.before(checkIn)) {
                    throw new IllegalArgumentException(); //make them enter a date after the check-in they specified!
                }
                check = true;
            }catch (Exception e) {
                System.out.println("Please enter a valid check-out-date in format: mm/dd/yyyy");
            }
        }

        /* if dates are in the wrong order we could just swap them
        if(checkIn.after(checkOut)) {
            Date temp = checkIn;
            checkIn = checkOut;
            checkOut = temp;
        }
         */
        //check for available rooms in specified or alternate search range
        Collection<IRoom> available = hr.findARoom(checkIn,checkOut);
        if(available == null || available.isEmpty()) {
            //available = new ArrayList<>();
            Date newIn = addDays(checkIn,searchRange);
            Date newOut = addDays(checkOut,searchRange);
            System.out.println("No rooms found for desired range");
            System.out.println("Searching for rooms from: " + newIn + " to: " + newOut);
            checkIn = newIn;
            checkOut = newOut;
            available = hr.findARoom(newIn,newOut);
        }

        //no rooms in either range
        if(available.isEmpty()) {
            System.out.println("Sorry there are no rooms available");
            return;
        }

        //list available rooms
        for(IRoom r : available) {
            System.out.println(r);
        }

        System.out.println("Do you have an account?[Y/n]");
        if(scanner.nextLine().equals("Y")) {
            logIn();
        } else {
            System.out.println("Please create an account");
            createAccount();
        }
        System.out.println("What room number would you like to reserve?");
        String roomNumber = scanner.nextLine();
        while(hr.getRoom(roomNumber) == null) {
            //room does not exist
            System.out.println("Please enter a valid room number");
            roomNumber = scanner.nextLine();
        }
        if(!available.contains(hr.getRoom(roomNumber))) {
            //room is not available
            System.out.println("Sorry room number: " + roomNumber + " is not available for the requested dates, please try again");
            return;
        }
        System.out.println(hr.bookARoom(email,hr.getRoom(roomNumber),checkIn,checkOut));
    }

    private static void logIn() {
        System.out.println("Enter Email format: name@domain.com");
        email = scanner.nextLine();
        if(hr.getCustomer(email) == null) {
            System.out.println("No such account, please create one");
            createAccount();
        }
    }

    private static void viewReservations() {
        System.out.println("Please enter your email: ");
        String email = scanner.nextLine();
        if(hr.getCustomer(email) == null) {
            System.out.println("No customer with email: " + email + " found");
            return;
        }
        Collection<Reservation> results = hr.getCustomersReservations(email);
        if(results == null || results.isEmpty()) {
            System.out.println("No reservations found for customer: " + hr.getCustomer(email));
        } else {
            for(Reservation r : results) {
                System.out.println(r);
            }
        }
    }

    // takes an argument rather than using the constant it is being currently passed directly
    // so that it could be changed to allow the customer to specify the search offset
    private static Date addDays(Date date, int days)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
    //adapted from stackoverflow answer on how to increment days in java
    //https://stackoverflow.com/questions/428918/how-can-i-increment-a-date-by-one-day-in-java/20906602


    private static void createAccount() {
        boolean notCreated = true;
        while(notCreated) {
            try {
                System.out.println("Please enter a first name:");
                String firstName = scanner.nextLine();
                System.out.println("Please enter a last name:");
                String lastName = scanner.nextLine();
                System.out.println("Please enter an email:");
                email = scanner.nextLine();
                hr.createACustomer(email,firstName,lastName);
                notCreated = false;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
