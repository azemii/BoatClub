package View;

import Model.Boat;
import Model.BoatClub;
import Model.JsonWriter;
import Model.Member;


import java.io.IOException;
import java.util.Scanner;

/***
 * A class that handles the display of all the different pages that are provided
 * as a choice by the main menu.
 */
public class PageHandler {

    // --- Local Variables ----
    private Scanner scanner = new Scanner(System.in);
    private InputChecker inputChecker = null;
    private BoatClub boatClub = null;
    private JsonWriter jsonWriter = null;

    public PageHandler(BoatClub boatClub){
        this.boatClub = boatClub;
        jsonWriter = new JsonWriter(boatClub);
        inputChecker = new InputChecker();
    }




    /**
     * Shows the default main page of the program. Shows the Main menu and asks for a
     * menu choice 1-n.
     *
     */
    public void showHomepage(){
        String[] pageNames = {"Create a new Member","Delete a Member","Modify a Member","Lookup Member","Show a simple list", "Show a verbose list","Add a new boat", "Delete a boat"};
        System.out.println("--- Homepage ---");

        // We use our enum to display the correct list number according to the order inside the enum.
        // We use that same list number to get the correct string representation from the pageNames array.
        System.out.println((PageType.CREATE_MEMBER.ordinal()+1) +". "+ pageNames[PageType.CREATE_MEMBER.ordinal()] + "\n"+
                (PageType.DELETE_MEMBER.ordinal()+1) +". "+ pageNames[PageType.DELETE_MEMBER.ordinal()] + "\n"+
                (PageType.MODIFY_MEMBER.ordinal()+1) +". "+ pageNames[PageType.MODIFY_MEMBER.ordinal()] + "\n"+
                (PageType.LOOKUP_MEMBER.ordinal()+1) +". "+ pageNames[PageType.LOOKUP_MEMBER.ordinal()] + "\n"+
                (PageType.SIMPLE_LIST.ordinal()+1) +". "+ pageNames[PageType.SIMPLE_LIST.ordinal()] + "\n"+
                (PageType.VERBOSE_LIST.ordinal()+1) +". "+ pageNames[PageType.VERBOSE_LIST.ordinal()] + "\n"+
                (PageType.ADD_BOAT.ordinal()+1) +". "+ pageNames[PageType.ADD_BOAT.ordinal()] + "\n" +
                (PageType.DELETE_BOAT.ordinal()+1) +". "+ pageNames[PageType.DELETE_BOAT.ordinal()] + "\n"
        );
        StringBuilder menuChoiceInput = new StringBuilder();
        promptForValidMenuChoice(menuChoiceInput);

        // parsing from SB to int is safe here after the check
        int menuChoice = 0;
        menuChoice = Integer.parseInt(menuChoiceInput.toString());
        if (menuChoice < 1 || menuChoice > 8){
            // not a valid selection from the menu
            showHomepage();
        }
        handleMenuChoice(menuChoice);

    }

    //------- MEMBER PAGES -------
    /**
     * Displays the page where the user has the option to create a new member.
     */
    private void showCreateNewMemeberPage() {
        // Create separation from the top so we don't get a clustered view on the
        // console application.
        newPageSpace();


        boolean done = false;
        System.out.println("--- Create a new Member ---");
        StringBuilder tempName = new StringBuilder();
        StringBuilder tempPersonalNumber = new StringBuilder();
        while(!done) {
            // Prompt the user for a valid name input
            promptForValidName(tempName);

            // Prompt the user for a valid personal number
            promptForValidPersonalNumber(tempPersonalNumber);

            System.out.println("\n\nAdd another member y/n?");
            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                done = true;
            }
        }

        boatClub.addMemberToClub(tempName.toString(),tempPersonalNumber.toString());
        // Write the new changes to file

        jsonWriter.writeBoatClubToJson();

        // return back to main menu
        newPageSpace();
        showHomepage();
    }


    /**
     * Displays the page that allows that allows for member deletion
     */
    private void showDeleteMemberPage(){
        // Hidden dependency with the switch statement due to the hardcoded case choices.
        // Local hidden dependency OK.
        newPageSpace();
        System.out.println("--- Delete a member --- ");
        System.out.println("1. Delete a member using a name \n"+
                "2. Delete a member using a unique ID");

        StringBuilder sbChoice = new StringBuilder();
        promptForValidMenuChoice(sbChoice);
        int  choice = Integer.parseInt(sbChoice.toString());
        switch (choice){
            case 1:
                StringBuilder name = new StringBuilder();
                promptForValidName(name);
                boatClub.deleteMemberWithName(name.toString());
                //jsonWriter.writeBoatClubToJson();
                newPageSpace();
                showHomepage();
                break;

            case 2:
                StringBuilder stringID = new StringBuilder();
                promptForValidMenuChoice(stringID);
                int idOfMemberToRemoveFromClub = Integer.parseInt(stringID.toString());
                boatClub.deleteMemberWithID(idOfMemberToRemoveFromClub);
                //jsonWriter.writeBoatClubToJson();
                newPageSpace();
                showHomepage();
                break;
            default:
                // Input is not 1 or 2, re-show the delete page
                System.out.println("That's now a valid selection");
                showDeleteMemberPage();
                break;
        }
    }

    /**
     * Displays the page that allows for member modification
     */
    private void showModifyMemberPage(){
        newPageSpace();
        System.out.println("--- Modify a member ---");

        // Get input from the user to se
        StringBuilder name = new StringBuilder();
        // Prompt until we get a matching name.
        do {
            promptForValidName(name);
        }while (!boatClub.containsMember(name.toString()));

            Member memberToModify = boatClub.getMemberWithName(name.toString());
            System.out.println("Modifying: " + memberToModify.getName());

            boolean modify = true; // Used to check what we want to modify.
            do {
                System.out.print("New name (Press enter to skip): ");
                name.setLength(0); // reset the SB
                name.append(scanner.nextLine());
                if (name.toString().equalsIgnoreCase("")){
                    // break out of the loop if we hit enter
                    modify = false; // we don't change the name
                    break;
                }
            }while(!inputChecker.fullNameCheck(name.toString()));

            if (modify){
                memberToModify.setName(name.toString());
                //jsonWriter.writeBoatClubToJson();
            }

            modify = true; // reset the boolean value for the personal number choice.
            String personalNumber = "";
            do {
                System.out.print("New personal number (Press enter to skip): ");
                personalNumber = scanner.nextLine();
                if (personalNumber.equalsIgnoreCase("")){
                    // break out of the loop if we hit enter
                    modify = false; // we don't want any modification on the current name
                    break;
                }
            }while(!inputChecker.personalNumberCheck(personalNumber));

            if (modify ){
                memberToModify.setPersonalNumber(personalNumber);
                jsonWriter.writeBoatClubToJson();
            }
            System.out.println("Modification complete");
            newPageSpace();
            showHomepage();

    }

    private void showLookupSpecificMember(){
        newPageSpace();
        System.out.println("--- Lookup a member ----");
        StringBuilder name = new StringBuilder();
        promptForValidName(name);
        Member member = boatClub.getMemberWithName(name.toString());
        System.out.println("Name: " + member.getName());
        System.out.println("Personal Number: " + member.getPersonalNumber());
        System.out.println("ID: " + member.getUniqueID());
        System.out.println("Boats: " + member.getNumberOfBoatsOwned());

    }


    // ------- LIST PAGES -------

    /**
     * Prints a list of all the members with their name, memberID and number of boats
     */
    private void printSimpleMembersList(){
        newPageSpace();
        System.out.println("--- Showing a simple list of all the boat club members ---");
        for (Member x : boatClub.getBoatClubMembers()) {
            System.out.println("Name: "+ x.getName() +" Personal Number: " + x.getPersonalNumber() + " ID: "+  x.getUniqueID() + " Boats: " + x.getNumberOfBoatsOwned());
        }
        System.out.println("Press enter to get back to the Home Page");
        if (scanner.nextLine().isEmpty()){
            newPageSpace();
            showHomepage();
        }
    }

    /**
     * Prints a verbose list of all the members with their name, personal number, member id and boats with boat information
     */
    private void printVerboseMembersList(){
        newPageSpace();
        System.out.println("--- Showing a verbose list of all the boat club members ---");
        for (Member x : boatClub.getBoatClubMembers()) {
            System.out.println("Name: "+ x.getName() +" Personal Number: " + x.getPersonalNumber() + " ID: "+  x.getUniqueID());
            x.printBoats();
        }
        System.out.println("Press enter to get back to the Home Page");
        if (scanner.nextLine().isEmpty()){
            newPageSpace();
            showHomepage();
        }
    }

    // ------- BOAT PAGES -------

    /**
     * Adds a new boat to a selected member.
     */
    private void showAddNewBoatPage(){
        newPageSpace();
        StringBuilder memberName = new StringBuilder();
        Member member = null;

        System.out.println("--- Add a new boat ---");

        // If there are no members in the boat club we just retrun to the homepage.
        if (boatClub.getNumberOfMembers() == 0){
            System.out.println("There are no members in the club...");
            newPageSpace();
            showHomepage();
        }

            // Prompt for name until we get a match in the boat club.
            do {
                promptForValidName(memberName);
            }while(!boatClub.containsMember(memberName.toString()));

               // Add boat
               int typeChoice = 0;
               System.out.println("Adding boat(s) to " + memberName);
               while (typeChoice < 1 || typeChoice > 5) {
                   System.out.println("Choose boat type");
                   System.out.println((
                           Boat.BoatType.SAILBOAT.ordinal() + 1) + ". " + Boat.BoatType.SAILBOAT.toString() + "\n" +
                           (Boat.BoatType.MOTORSAILER.ordinal() + 1) + ". " + Boat.BoatType.MOTORSAILER.toString() + "\n" +
                           (Boat.BoatType.KAYAK.ordinal() + 1) + ". " + Boat.BoatType.KAYAK.toString() + "\n" +
                           (Boat.BoatType.CANOE.ordinal() + 1) + ". " + Boat.BoatType.CANOE.toString() + "\n" +
                           (Boat.BoatType.OTHER.ordinal() + 1) + ". " + Boat.BoatType.OTHER.toString() + "\n"
                   );
                   StringBuilder sbTypeChoice = new StringBuilder();
                   promptForValidMenuChoice(sbTypeChoice);
                   typeChoice = Integer.parseInt(sbTypeChoice.toString());

               }
               double boatLength = 0;
               StringBuilder stringLength = new StringBuilder();
               while (boatLength < 1) {
                   do {
                       System.out.print("Enter length: ");
                       stringLength.setLength(0);
                       stringLength.append(scanner.nextLine());
                   }while (!isDouble(stringLength));
                   // Parse safe after check
                   boatLength = Double.parseDouble(stringLength.toString());
               }
               // add boat to the member
               member = boatClub.getMemberWithName(memberName.toString());
               member.addBoat(Boat.BoatType.values()[typeChoice], boatLength);

               jsonWriter.writeBoatClubToJson();

               // prompt for another boat addition.
               System.out.print("Add more boats?(y/n): ");
               String addMore = scanner.nextLine();
               if (addMore.equalsIgnoreCase("y")) {
                   newPageSpace();
                   showAddNewBoatPage();
               } else {
                   newPageSpace();
                   showHomepage();
               }

    }


    private void showDeleteBoatPage(){
        newPageSpace();
        System.out.println("--- Delete a boat --- ");
        StringBuilder memberName = new StringBuilder();

        // Ask for a member name until we find a match in our members list.
        do {
            promptForValidName(memberName);
        } while (!boatClub.containsMember(memberName.toString()));

        Member member = boatClub.getMemberWithName(memberName.toString());

        // Member does not own any boats, return to homepage.
        if (member.getNumberOfBoatsOwned() <= 0){
            System.out.println("This member does not own any boats.");
            showHomepage();
        }

        member.printBoats();
        StringBuilder stringBoatSelection = new StringBuilder();
        int boatSelection = -1;
        do {
            promptForValidMenuChoice(stringBoatSelection);
            boatSelection = Integer.parseInt(stringBoatSelection.toString());
        } while (boatSelection > member.getNumberOfBoatsOwned() && boatSelection < 0);

        member.deleteBoat(boatSelection);

         jsonWriter.writeBoatClubToJson();
        newPageSpace();
        showHomepage();
    }



    // MARK: - Menu Handler
    private void handleMenuChoice(int menuChoice) {
        switch (menuChoice){
            case 1:
                showCreateNewMemeberPage();
                break;
            case 2:
                showDeleteMemberPage();
                break;
            case 3:
                showModifyMemberPage();
                break;
            case 4:
                showLookupSpecificMember();
                break;
            case 5:
                printSimpleMembersList();
                break;
            case 6:
                printVerboseMembersList();
                break;
            case 7:
                showAddNewBoatPage();
                break;
            case 8:
                showDeleteBoatPage();
                break;
            default:
                System.out.println("DEFAULT VALUE ENTERED");
                break;
        }
    }

    // --- Helper Methods

    /**
     * Creates space for a new page to avoid a clustered console application output.
     */
    private void newPageSpace(){
        for (int i = 0; i < 30 ; i++) {
            System.out.println();
        }
    }

    private void promptForValidName(StringBuilder name){
        do {
            System.out.print("Enter the name of the member: ");
            name.setLength(0); // reset for every iteration
            name.append(scanner.nextLine());
        }while (!inputChecker.fullNameCheck(name.toString()));
    }

    private void promptForValidMenuChoice(StringBuilder choice){
        do {
            System.out.print("Selection: ");
            choice.setLength(0); // reset for every iteration
            choice.append(scanner.nextLine());
        }while(!inputChecker.validMenuSelection(choice.toString()));
    }

    private void promptForValidPersonalNumber(StringBuilder personalNumber){
        do {
            System.out.print("Enter personal number (YYYYYYYY-XXXX): ");
            personalNumber.setLength(0); // reset for every iteration
            personalNumber.append(scanner.nextLine());
        }
        while(!inputChecker.personalNumberCheck(personalNumber.toString()));
    }

    /***
     * Checks if the input string is a double
     * @param length The string to check if parsable to double
     * @return true if string is parsable to double
     */
    private boolean isDouble (StringBuilder length){
        try{
            Double.parseDouble(length.toString());
        }catch(NumberFormatException | NullPointerException e){
            return false;
        }
        return true;
    }

    //--- Enum ---

    public enum PageType {
        CREATE_MEMBER,
        DELETE_MEMBER,
        MODIFY_MEMBER,
        LOOKUP_MEMBER,
        SIMPLE_LIST,
        VERBOSE_LIST,
        ADD_BOAT,
        DELETE_BOAT
    }

}
