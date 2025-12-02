package com.example.circolosportivofx;

import org.json.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * The type Data.
 */
public class Data {
    private static Data instance = null;

    private final  String filepathUsers = "data/json/users.json";
    private final  String filepathActivities = "data/json/activities.json";
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
        try (InputStream inputStream = Files.newInputStream(Paths.get(filepathUsers));){
            String jsonText = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

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

    public void loadActivities() {
        try(InputStream inputStream = Files.newInputStream(Paths.get(filepathActivities));) {
            //JsonObject root = JsonParser.parseReader(new FileReader(filepathActivities)).getAsJsonObject();

            // Usa InputStreamReader invece di FileReader
            String jsonText = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            //System.out.println(jsonText);
            if (jsonText.isBlank()) {
                System.out.println("File JSON vuoto, niente da caricare.");
                return;
            }
            JSONObject root = new JSONObject(jsonText);

            JSONArray competitionsArray = root.getJSONArray("competitions");
            JSONArray coursesArray = root.getJSONArray("courses");

            saveActivities("competition", competitionsArray);
            saveActivities("course", coursesArray);

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

    private void saveActivities(String type, JSONArray array) {
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

}
