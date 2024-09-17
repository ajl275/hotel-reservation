package menu;

import api.AdminResource;
import api.HotelResource;
import model.*;

import java.util.*;

/**
 * The administrative menu
 *
 * @author Amy Lanclos
 */
public class AdminMenu {
    private static final String menuText = """
            Admin Menu
            -----------------------------------------
            1. See all Customers
            2. See all Rooms
            3. See all Reservations
            4. Add a Room
            5. Add test data
            6. Back to Main Menu
            -----------------------------------------""";

    private static final HotelResource hr = HotelResource.getInstance();
    private static final AdminResource ar = AdminResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void run() {

        boolean exit = false;

        while(!exit) {
            displayMenu();
            try {
                String input = scanner.nextLine();
                switch (Integer.parseInt(input)) {
                    case 1-> seeCustomers();
                    case 2-> seeRooms();
                    case 3-> seeReservations();
                    case 4-> addRooms();
                    case 5-> addTestData();
                    case 6-> exit = true;
                    default -> throw new IllegalArgumentException();
                }
            } catch (Exception e) {
                System.out.println("Please choose a valid selection \"1-6\"");
            }
        }
    }

    // a small set of random data to start populating system for testing
    private static void addTestData() {

        hr.createACustomer("email@email.email", "John", "Smith");
        hr.createACustomer("e.m.ail@gmail.edu", "Joe", "Schmoe");
        hr.createACustomer("j@domain.tld", "first", "last");


        List<IRoom> rooms = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            rooms.add(new Room(Integer.toString(i),99.99, RoomType.SINGLE));
        }

        ar.addRoom(rooms);

        hr.createACustomer("this@is.email", "Sarah", "Lance");
        Date checkIn = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2031,Calendar.FEBRUARY,2);
        Collection<IRoom> available = hr.findARoom(checkIn, calendar.getTime());
        hr.bookARoom("this@is.email",available.stream().iterator().next(),checkIn,calendar.getTime());

        checkIn = new Date();
        available = hr.findARoom(checkIn,calendar.getTime());
        hr.bookARoom("e.m.ail@gmail.edu",available.stream().iterator().next(),checkIn,calendar.getTime());
        System.out.println();
        System.out.println("Test data added!\n");
    }

    private static void seeCustomers() {
        for(Customer c : ar.getAllCustomers()) {
            System.out.println(c);
        }
    }

    private static void seeRooms() {
        for(IRoom r : ar.getAllRooms()) {
            System.out.println(r);
        }
    }

    private static void seeReservations() {
        ar.displayAllReservations();
    }

    private static void addRooms() {
       List<IRoom> rooms = new ArrayList<>();
       boolean create = true;
       while(create) {
           try {
               rooms.add(createRoom(rooms));
           } catch (Exception e) {
               //doesn't add current malformed room but keeps list of those already created and allows more
               System.out.println("Room NOT added!");
               System.out.println("Please enter valid room data to try again");
           }

           System.out.println("Add another room? [Y/n]");
           String response = scanner.nextLine();
           if(!response.equals("Y")) {
               create = false;
           }
       }

       ar.addRoom(rooms);
    }

    // gets the current list of rooms being created to avoid duplicate room numbers
    private static IRoom createRoom(List<IRoom> rooms) {
        System.out.println("Please enter room number: ");
        String num = scanner.nextLine();
        if(hr.getRoom(num) != null || rooms.stream().anyMatch(r -> r.getRoomNumber().equals(num))) {
            System.out.println("Room " + num + " already exists!");
            throw new IllegalArgumentException();
        }
        System.out.println("Please enter room price:");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.println("Please enter room type: [single/double]");
        String type = scanner.nextLine();
        type = type.toLowerCase();
        RoomType rtype = switch (type) {
            case "single"-> RoomType.SINGLE;
            case "double"-> RoomType.DOUBLE;
            default -> throw new IllegalArgumentException();
        };
        System.out.println("Room successfully created!");
        if(price == 0) {
            return new FreeRoom(num,rtype);
        }
        return new Room(num,price,rtype);
    }

    private static void displayMenu() {
        System.out.println(menuText);
    }

}
