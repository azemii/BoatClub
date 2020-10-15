package View;


import Model.BoatClub;

  public class MainView {
   private BoatClub boatClub = null;

    public MainView(BoatClub boatClub) {
        this.boatClub = boatClub;
        PageHandler pageHandler = new PageHandler(boatClub);
        pageHandler.showHomepage();
    }







}
