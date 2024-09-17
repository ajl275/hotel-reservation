package model;

/**
 * A room with a price of 0
 * @author Amy Lanclos
 */
public class FreeRoom extends Room{

    /**
     * free room constructor
     * @param number number of the room
     * @param type Double or Single
     */
    public FreeRoom(String number, RoomType type) {
        super(number, 0.0, type);
    }

    @Override
    public boolean isFree() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "\nThis room is Free!";
    }
}
