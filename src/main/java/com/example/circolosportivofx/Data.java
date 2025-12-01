package com.example.circolosportivofx;

import com.google.gson.*;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import org.json.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * The type Data.
 */
public class Data {
    private static Data instance = null;

    private final  String filepathUsers = "json/users.json";
    private final  String filepathActivities = "json/activities.json";
    private ArrayList<Member> members = new ArrayList<>();
    private  ArrayList<Activity> activities = new ArrayList<>();
    private Member loggedUser = null;


    // Metodo per ottenere l'istanza unica
    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    private Data() {
        loadUsers();
        loadActivities();
    }

    /**
     * Gets members.
     *
     * @return the members
     */
    public ArrayList<Member> getMembers() {
        return members;
    }

    /**
     * Sets members.
     *
     * @param members the members
     */
    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }

    /**
     * Gets activities.
     *
     * @return the activities
     */
    public ArrayList<Activity> getActivities() {
        return activities;
    }

    /**
     * Sets activities.
     *
     * @param activities the activities
     */
    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }


    private void loadUsers() {
        try {
            // Legge tutto il JSON come oggetto unico
            //JsonObject root = JsonParser.parseReader(new FileReader(filepathUsers)).getAsJsonObject();
            InputStream inputStream = getClass().getResourceAsStream(filepathUsers);
            if (inputStream == null) {
                throw new FileNotFoundException("File JSON non trovato!");
            }

            // Usa InputStreamReader invece di FileReader

            //JSONObject root = JsonParser.parseReader(new InputStreamReader(inputStream).get);
            //System.out.println(root);
            String jsonText = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Parsing con org.json
            JSONObject root = new JSONObject(jsonText);




            // --- ADMINS ---
            JSONArray adminArray = root.getJSONArray("admins");
            System.out.println(adminArray);
            if (!adminArray.isEmpty()) {
                for (int i = 0; i < adminArray.length(); i++) {
                    JSONObject a = adminArray.getJSONObject(i);
                    System.out.println(a);

                    Admin admin = new Admin(
                            a.getString("name"),
                            a.getString("surname"),
                            a.getString("email"),
                            a.getString("password")
                    );

                    members.add(admin);
                }
            }

            // --- MEMBERS ---
            JSONArray membersArray = root.getJSONArray("members");
            //JsonArray membersArray = root.get(1).getAsJsonArray();
            if (!membersArray.isEmpty()) {
                for (int i = 0; i < membersArray.length(); i++) {
                    JSONObject m =  membersArray.getJSONObject(i);

                    Member member = new Member(
                            m.getString("name"),
                            m.getString("surname"),
                            m.getString("email"),
                            m.getString("password")
                    );

                    members.add(member);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveUsers() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject root = new JsonObject();
        JsonArray adminsArray = new JsonArray();
        JsonArray membersArray = new JsonArray();

        for (Member u : members) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", u.getName());
            obj.addProperty("surname", u.getSurname());
            obj.addProperty("email", u.getEmail());
            obj.addProperty("password", u.getPassword());

            if (u instanceof Admin) {
                adminsArray.add(obj);
            } else {
                membersArray.add(obj);
            }
        }

        root.add("admins", adminsArray);
        root.add("members", membersArray);

        try (FileWriter writer = new FileWriter(filepathUsers)) {
            gson.toJson(root, writer);
            System.out.println("Utenti salvati correttamente su " + filepathUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveActivities() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject root = new JsonObject();
        JsonArray coursesArray = new JsonArray();
        JsonArray competitionsArray = new JsonArray();

        for (Activity a : activities) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", a.getName());

            // Lista partecipanti divisa in admins e members
            JsonObject participantsObj = new JsonObject();
            JsonArray adminsArray = new JsonArray();
            JsonArray membersArray = new JsonArray();

            for (Member m : a.getSubscribers()) {
                JsonObject memberObj = new JsonObject();
                memberObj.addProperty("name", m.getName());
                memberObj.addProperty("surname", m.getSurname());
                memberObj.addProperty("email", m.getEmail());
                memberObj.addProperty("password", m.getPassword());

                if (m instanceof Admin) {
                    adminsArray.add(memberObj);
                } else {
                    membersArray.add(memberObj);
                }
            }

            participantsObj.add("admins", adminsArray);
            participantsObj.add("members", membersArray);
            obj.add("participants", participantsObj);

            if (a instanceof Course) {
                coursesArray.add(obj);
            } else if (a instanceof Competition) {
                competitionsArray.add(obj);
            }
        }

        root.add("courses", coursesArray);
        root.add("competitions", competitionsArray);

        try (FileWriter writer = new FileWriter(filepathActivities)) {
            gson.toJson(root, writer);
            System.out.println("Attività salvate correttamente in " + filepathActivities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadActivities() {
        try {
            //JsonObject root = JsonParser.parseReader(new FileReader(filepathActivities)).getAsJsonObject();
            InputStream inputStream = getClass().getResourceAsStream(filepathActivities);
            if (inputStream == null) {
                throw new FileNotFoundException("File JSON non trovato!");
            }

            // Usa InputStreamReader invece di FileReader
            String jsonText = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            //System.out.println(jsonText);
            if (jsonText.isBlank()) {
                System.out.println("File JSON vuoto, niente da caricare.");
                return;
            }

            JSONObject root = new JSONObject(jsonText);


            JSONArray activitiesArray = root.getJSONArray("activities");
            System.out.println(activitiesArray);
            if (activitiesArray != null) {
                for (int i = 0; i < activitiesArray.length(); i++) {
                    //System.out.println(i);
                    JSONObject obj = activitiesArray.getJSONObject(i);
                    String name = obj.getString("name");
                    String type = obj.getString("type");

                    JSONObject subscribersObj = obj.getJSONObject("subscribers");
                    ArrayList<Member> participants = new ArrayList<>();

                    // Admins
                    if(!subscribersObj.isNull("admins")) {
                        JSONArray admins = subscribersObj.getJSONArray("admins");
                        for (int j = 0; j < admins.length(); j++) {
                            JSONObject m = admins.getJSONObject(j);
                            participants.add(new Admin(
                                    m.getString("name"),
                                    m.getString("surname"),
                                    m.getString("email"),
                                    m.getString("password")
                            ));
                        }
                    }


                    // Members
                    if (!subscribersObj.isNull("members")) {
                        JSONArray members = subscribersObj.getJSONArray("members");
                        for (int j = 0; j < members.length(); j++) {
                            JSONObject m = members.getJSONObject(j);
                            participants.add(new Member(
                                    m.getString("name"),
                                    m.getString("surname"),
                                    m.getString("email"),
                                    m.getString("password")
                            ));
                        }
                    }

                    if(type.equals("competition"))
                        activities.add(new Competition(name, participants));
                    else
                        activities.add(new Course(name, participants));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Member getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Member loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Activity getActivityByName(String name) {
        for (Activity activity : Data.getInstance().getActivities()) {
            if (activity.getName().equals(name)) {
                return activity;
            }
        }
        return null;
    }

    public void writeOutput(String message) {
        try (FileWriter writer = new FileWriter("output.txt", true)) {
            writer.write(message + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void endOutput() {
        try (FileWriter writer = new FileWriter("output.txt", true)) {
            writer.write("----- End of Session -----" + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startOutput() {
        try (FileWriter writer = new FileWriter("output.txt")) {
            writer.write("----- Start of Session -----" + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
