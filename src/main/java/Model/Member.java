package Model;
import java.util.ArrayList;

public class Member {


    // --- Local Variables ---

    private int uniqueID;
    private String name;
    private String personalNumber;
    private ArrayList<Boat> boatList = new ArrayList<>();



    public Member(String name, String personalNumber){
        this.name = name;
        this.personalNumber = personalNumber;
    }



    public int getUniqueID() {
        return uniqueID;
    }
    public void setUniqueID(int ID){

        this.uniqueID = ID;
    }

    public String getName() {

        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }
    public void setPersonalNumber(String personalNumber){
        this.personalNumber = personalNumber;
    }
    private void addBoat(Boat boat){
        boatList.add(boat);
    }
    public void addBoat(Boat.BoatType type, double length){
        Boat temp = new Boat(type,length);
        addBoat(temp);
    }

    public void deleteBoat(int index)
    {
        boatList.remove(index);
    }



    public void printBoats(){
        for (Boat b : boatList
             ) {
            System.out.println(boatList.indexOf(b) + ". Type:  " + b.getType() + " Length: " + b.getLength());
        }
    }

    public ArrayList<Boat> getBoatList() {
        return boatList;
    }
    public void setBoatList(ArrayList<Boat> boatList) {
        this.boatList = boatList;
    }

    @Override
    public String toString() {
        return "{"+ "uniqueID:" + uniqueID +
                ", name:" + name +
                ", personalNumber:" + personalNumber +
                ", boatList:" + boatList + "}";
    }

    public int getNumberOfBoatsOwned() {
        return boatList.size();
    }
}
