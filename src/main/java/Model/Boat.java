package Model;

public class Boat {
    private BoatType type; // Sailboat, Motorsailer, kayak/Canoe, Other
    private double length; // in meters

    /**
     * Construct a boat with a type (Sailboat, Motorsailer, kayak/Canoe, Other) and a length (in meters)
     * @param type The type of boat
     * @param length The length of the boat in meters
     */
    public Boat(BoatType type, double length){
        this.type = type;
        this.length = length;
    }


    public BoatType getType(){
        return type;
    }

    public double getLength(){
        return length;
    }

    public enum BoatType{
        SAILBOAT,MOTORSAILER,KAYAK,CANOE,OTHER;
    }

}
