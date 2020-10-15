package Model;

// A class that handles the serialization of a boat club to a JSON file.

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.Scanner;


public class JsonWriter {
    private BoatClub boatClub;


    public JsonWriter(BoatClub boatClub){
        this.boatClub = boatClub;
        readBoatClubFromJson();
    }


    /**
     * Writes the entire boat club to JSON and saves it to a file
     */
    public void writeBoatClubToJson(){
        Gson gson = new Gson();
        String jsonString = gson.toJson(boatClub);

        try(FileWriter file = new FileWriter("boatClub.json")){
            file.write(jsonString);
            file.flush();
        }catch (IOException e){

        }


    }

    /**
     * Read a json file and parses it to a BoatClub and overrides all data in the current
     * boat club. (File stored in source code folder)
     */
    public void readBoatClubFromJson() {
        StringBuilder json = new StringBuilder();
        try {
            Scanner in = new Scanner(new FileReader("boatclub.json"));
            while (in.hasNext()) {
                json.append(in.next());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Loading from file failed due to: \n 1. This is the first time running the program \n 2. The file is not located in the correct folder");
        }
        Gson gson = new Gson();
        boatClub = gson.fromJson(json.toString(), BoatClub.class);
    }

}

