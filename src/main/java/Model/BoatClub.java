package Model;

import java.util.ArrayList;
import java.util.Iterator;

public class BoatClub {

    // Local variables
    private ArrayList<Member> members = new ArrayList<>();


    // Used to assign a unique ID to each new member. We can't use the position of the
    // arraylist since when we remove members and and then add back the ID's wont
    // be unique.
    private int uniqueID = 1;



    public BoatClub(){

    }


    // --- Member Related Functions
    public void addMemberToClub(Member member){
        members.add(member);
        member.setUniqueID(uniqueID);
        uniqueID +=1;
    }

    public void addMemberToClub(String name, String personalNumber){
        Member temp = new Member(name,personalNumber);
        addMemberToClub(temp);
    }

    /**
     * Removes a member from the boat club.
     * @param name: The name of the member that will be removed from the boat club.
     */
    public void deleteMemberWithName(String name){
        Iterator<Member> iterator = members.iterator();
       while(iterator.hasNext()){
           Member x = iterator.next();
           if (x.getName().equalsIgnoreCase(name)) {
               iterator.remove();
               System.out.println(x.getName() + " removed");
           }
       }
    }

    /**
     * Removes a member from the boat club.
     * @param id: The id if the member that will be removed from the boat club
     */
    public void deleteMemberWithID(int id){
        Iterator<Member> iterator = members.iterator();
        while(iterator.hasNext()){
            Member x = iterator.next();
            if (x.getUniqueID() == id) {
                iterator.remove();
                System.out.println(x.getName() + " removed");
            }
        }
    }

    public Member getMemberWithName(String name) {
        Iterator<Member> iterator = members.iterator();
        Member returnMember = null;
        while(iterator.hasNext()){
            Member x = iterator.next();
            if (x.getName().equalsIgnoreCase(name)) {
                returnMember = x;
            }
        }
        return returnMember;
    }

    public boolean containsMember(String name){
        boolean returnValue = false;
        Iterator<Member> iterator = members.iterator();
        while(iterator.hasNext()){
            Member x = iterator.next();
            if (x.getName().equalsIgnoreCase(name)) {
                returnValue = true;
            }
        }
        return returnValue;
    }

    public ArrayList<Member> getBoatClubMembers(){
        return members;
    }
    public int  getNumberOfMembers(){
        return members.size();
    }






    /**
     * Returns a int that is used to represent a unique identifier for a member
     * in the boat club. The unique id is a integer is extracted by tracking all
     * members that have been in the club, weather still active members or not.
     * @return
     */
    public int getUniqueID(){ return uniqueID; }





}
