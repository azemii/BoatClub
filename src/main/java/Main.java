
import Model.BoatClub;
import Model.Member;
import View.MainView;



public class Main {

    public static void main(String[] args) {
        BoatClub boatClub = new BoatClub();
       /*
        boatClub.addMemberToClub(new Member("aaa","111"));
        boatClub.addMemberToClub(new Member("bbb","222"));
        boatClub.addMemberToClub(new Member("ccc","333"));
        boatClub.addMemberToClub(new Member("ddd","444"));
        boatClub.addMemberToClub(new Member("eee","555"));
        boatClub.addMemberToClub("ggggggggggg","232323");

        */

        MainView mainview = new MainView(boatClub);



    }
}
