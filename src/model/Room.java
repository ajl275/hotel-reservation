package model;

/**
 * A room
 *
 * @author Amy Lanclos
 */
public class Room implements IRoom {

    private final String roomNumber;
    private final Double price;
    private final RoomType enumeration;

    /**
     * Constructor for a room
     * @param number #
     * @param price $
     * @param type what kind
     */
    public Room(String number, Double price, RoomType type) {
        roomNumber = number;
        this.price = price;
        enumeration = type;
    }

    /**
     * @return This room's number
     */
    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * @return Price of this room per night
     */
    @Override
    public Double getRoomPrice() {
        return price;
    }

    /**
     * @return The type of this Room
     */
    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    /**
     * @return If the room is free
     */
    @Override
    public boolean isFree() {
        return price.equals(0.0);
    }

    /**
     * @return A string representation of this room
     */
    @Override
    public String toString() {
        return "Room Number: " + roomNumber +
                "\nType: " + Room.roomTyper(enumeration) +
                "\nPrice: " + price;
    }

    public static String roomTyper(RoomType type) {
        switch(type) {
            case SINGLE: return "Single";
            case DOUBLE: return "Double";
            default: return "Invalid";
        }
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        Room room = (Room)o;
        return (price == null && room.getRoomPrice() == null || (!(price == null || room.getRoomPrice() == null) && price.equals(room.getRoomPrice()))) && (enumeration == room.getRoomType()) &&
                (roomNumber == null && room.getRoomNumber() == null || (!(roomNumber == null || room.getRoomNumber() == null) && roomNumber.equals(room.getRoomNumber())));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (roomNumber == null ? 0 : roomNumber.hashCode());
        hash = 31 * hash + (price == null ? 0 : price.hashCode());
        hash = 31 * hash + (enumeration == RoomType.SINGLE ? 0 : 1);
        return hash;
    }
}
