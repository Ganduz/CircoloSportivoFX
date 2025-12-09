package com.example.circolosportivofx;

import org.json.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Singleton class that manages the data of the sports club application,
 */
public class Data {
    private static Data instance = null;

    private final  String filepathUsers = "data/json/users.json";
    private final  String filepathActivities = "data/json/activities.json";
    private ArrayList<Member> members = new ArrayList<>();
    private  ArrayList<Activity> activities = new ArrayList<>();
    private Member loggedUser = null;


    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    private Data() {
        try{
            extractDataFolder();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadUsers();
        loadActivities();
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    public Member getLoggedUser() { return loggedUser; }

    public void setLoggedUser(Member loggedUser) { this.loggedUser = loggedUser; }

    /**
     * Load users from JSON file.
     */
    private void loadUsers() {
        try (InputStream inputStream = Files.newInputStream(Paths.get(filepathUsers));){
            String jsonText = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            JSONObject root = new JSONObject(jsonText);

            // --- ADMINS ---
            JSONArray adminArray = root.getJSONArray("admins");
            if (!adminArray.isEmpty()) {
                for (int i = 0; i < adminArray.length(); i++) {
                    JSONObject a = adminArray.getJSONObject(i);

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

    /**
     * Save users to JSON file.
     */
    public void saveUsers() {
        JSONObject root = new JSONObject();
        JSONArray adminsArray = new JSONArray();
        JSONArray membersArray = new JSONArray();

        for (Member u : members) {
            JSONObject obj = new JSONObject();
            obj.put("name", u.getName());
            obj.put("surname", u.getSurname());
            obj.put("email", u.getEmail());
            obj.put("password", u.getPassword());

            if (u instanceof Admin) {
                adminsArray.put(obj);
            } else {
                membersArray.put(obj);
            }
        }

        root.put("admins", adminsArray);
        root.put("members", membersArray);

        try (FileWriter writer = new FileWriter(filepathUsers)) {
            writer.write(root.toString(4));
            System.out.println("Utenti salvati correttamente su " + filepathUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save activities to JSON file.
     */
    public void saveActivities() {
        JSONObject root = new JSONObject();
        JSONArray coursesArray = new JSONArray();
        JSONArray competitionsArray = new JSONArray();

        for (Activity a : activities) {
            JSONObject obj = new JSONObject();
            obj.put("name", a.getName());

            // Lista partecipanti divisa in admins e members
            JSONObject participantsObj = new JSONObject();
            JSONArray adminsArray = new JSONArray();
            JSONArray membersArray = new JSONArray();

            for (Member m : a.getSubscribers()) {
                JSONObject memberObj = new JSONObject();
                memberObj.put("name", m.getName());
                memberObj.put("surname", m.getSurname());
                memberObj.put("email", m.getEmail());
                memberObj.put("password", m.getPassword());

                if (m instanceof Admin) {
                    adminsArray.put(memberObj);
                } else {
                    membersArray.put(memberObj);
                }
            }

            participantsObj.put("admins", adminsArray);
            participantsObj.put("members", membersArray);
            obj.put("subscribers", participantsObj);

            if (a instanceof Course) {
                coursesArray.put(obj);
            } else if (a instanceof Competition) {
                competitionsArray.put(obj);
            }
        }

        root.put("courses", coursesArray);
        root.put("competitions", competitionsArray);

        try (FileWriter writer = new FileWriter(filepathActivities)) {
            writer.write(root.toString(4));
            System.out.println("Attività salvate correttamente in " + filepathActivities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load activities from JSON file.
     */
    public void loadActivities() {
        try(InputStream inputStream = Files.newInputStream(Paths.get(filepathActivities));) {

            String jsonText = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            if (jsonText.isBlank()) {
                System.out.println("File JSON vuoto, niente da caricare.");
                return;
            }
            JSONObject root = new JSONObject(jsonText);

            JSONArray competitionsArray = root.getJSONArray("competitions");
            JSONArray coursesArray = root.getJSONArray("courses");

            loadFromList("competition", competitionsArray);
            loadFromList("course", coursesArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Write output message to log file.
     * @param message the message to write
     */
    public void writeOutput(String message) {
        try (FileWriter writer = new FileWriter("output.txt", true)) {
            writer.write(message + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mark the end of output session in log file.
     */
    public void endOutput() {
        try (FileWriter writer = new FileWriter("output.txt", true)) {
            writer.write("----- End of Session -----" + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mark the start of output session in log file.
     */
    public void startOutput() {
        try (FileWriter writer = new FileWriter("output.txt")) {
            writer.write("----- Start of Session -----" + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load activities from a JSON array.
     * @param type the type of activity ("course" or "competition")
     * @param array the JSON array containing activity data
     */
    private void loadFromList(String type, JSONArray array) {
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String name = obj.getString("name");

                JSONObject subscribersObj = obj.getJSONObject("subscribers");
                ArrayList<Member> subscribers = new ArrayList<>();

                // Admins
                if (!subscribersObj.isNull("admins")) {
                    JSONArray admins = subscribersObj.getJSONArray("admins");
                    for (int j = 0; j < admins.length(); j++) {
                        JSONObject m = admins.getJSONObject(j);
                        subscribers.add(new Admin(
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
                        subscribers.add(new Member(
                                m.getString("name"),
                                m.getString("surname"),
                                m.getString("email"),
                                m.getString("password")
                        ));                        }
                }

                if(type.equals("course"))
                    activities.add(new Course(name, subscribers));
                else
                    activities.add(new Competition(name, subscribers));
            }
        }
    }


    /**
     * Extracts JSON data files from the JAR to an external folder if they do not already exist.
     * @throws IOException if an I/O error occurs
     */
    public static void extractDataFolder() throws IOException {
        // External folder path to extract JSON files
        File outDir = new File("data/json");
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        // List of JSON files to extract
        String[] files = { "users.json", "activities.json" };

        for (String fileName : files) {
            File outFile = new File(outDir, fileName);

            if (!outFile.exists()) {
                String resourcePath = "json/" + fileName;

                try (InputStream is = CircoloSportivoApplication.class.getResourceAsStream(resourcePath)) {
                    if (is == null) {
                        throw new FileNotFoundException("Risorsa non trovata nel JAR: " + resourcePath);
                    }
                    Files.copy(is, outFile.toPath());
                    System.out.println("Copiato: " + outFile.getAbsolutePath());
                }
            }
        }
    }

}
