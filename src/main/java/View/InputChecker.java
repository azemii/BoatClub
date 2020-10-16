package View;

import Model.BoatClub;

public class  InputChecker {



    public InputChecker(){

    }

    /**
     * A cehck that verifies that the input string is only composed of letter (A-Z, a-z).
     * @param fullName: String containing a full name
     * @return: Returns true if the input is only composed of letters.
     */
    public boolean fullNameCheck(String fullName){
        boolean matches = fullName.matches("[a-zA-Z ]+");
        return matches;
    }

    /**
     * A check that verifies that the input string is only composed of numbers and dasehs (-).
     *
     * @param personalNumber: String containing a personal number (YYYYYYYY-XXXX)
     * @return returns true if the input string is only composed of numbers and dashes.
     */
    public boolean personalNumberCheck(String personalNumber){
        boolean matches = personalNumber.matches("[0-9-]+");
        return matches;
    }

    /**
     * Checks that the input is a integer and it is inside the bounds of the main menu list
     * @return returns true if the input is an integer inside the bounds of the main menu
     */
    public boolean validMenuSelection(String input){
        int choice = 0;
        try {
            choice = Integer.parseInt(input);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

}
