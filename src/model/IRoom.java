package model;

/**
 * Interface for all rooms
 * @author Amy Lanclos
 */
public interface IRoom {

    /**
     * @return This room's number
     */
    public String getRoomNumber();

    /**
     * @return Price of this room per night
     */
    public Double getRoomPrice();

    /**
     * @return The type of this Room
     */
    public RoomType getRoomType();

    /**
     * @return If the room is free
     */
    public boolean isFree();
}
