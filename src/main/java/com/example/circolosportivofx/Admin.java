package com.example.circolosportivofx;

/**
 * The type Admin.
 */
public class Admin extends Member {

    public Admin(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }

    public Admin(Person person) {
        super(person);
    }

    public Admin(Member member) {
        super(member);
    }

    /**
     * Add member.
     *
     * @param name     the name
     * @param surname  the surname
     * @param email    the email
     * @param password the password
     * @param data     the data
     */
    public void addMember(String name, String surname, String email, String password, Data data) {
        data.getMembers().add(new Member(name, surname, email, password));
    }

    /**
     * Overload for addMember method above but instead of parameters it takes a Person object.
     *
     * @param person the person
     * @param data   the data
     */
    public void addMember(Person person, Data data) {
        data.getMembers().add(new Member(person));
    }

    /**
     * Remove member or admin.
     *
     * @param member the member
     * @param data   the data
     */
    public void removeMember(Member member, Data data) {
        data.getMembers().remove(member);
    }

    /**
     * Add activity.
     *
     * @param activity the activity
     * @param data     the data
     */
    public void addActivity(Activity activity, Data data) {
        data.getActivities().add(activity);
    }

    /**
     * Remove activity.
     *
     * @param activity the activity
     * @param data     the data
     */
    public void removeActivity(Activity activity, Data data) {
        data.getActivities().remove(activity);
    }

    /**
     * Add admin.
     *
     * @param name     the name
     * @param surname  the surname
     * @param email    the email
     * @param password the password
     * @param data     the data
     */
    public void addAdmin(String name, String surname, String email, String password, Data data) {
        data.getMembers().add(new Admin(name, surname, email, password));
    }

    /**
     * Add admin from member.
     *
     * @param member the member
     * @param data   the data
     */
    public void addAdmin(Member member, Data data) {
        data.getMembers().add(new Admin(member));
    }


    /**
     * Modify member.
     *
     * @param member   the member
     * @param name     the name
     * @param surname  the surname
     * @param email    the email
     * @param password the password
     */
    public void modifyMember(Member member, String name, String surname, String email, String password) {
        member.setName(name);
        member.setSurname(surname);
        member.setEmail(email);
        member.setPassword(password);
    }

    @Override
    public String toString() {
        return "(Admin) " + name + " " + surname;
    }

}
