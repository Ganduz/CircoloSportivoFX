package com.example.circolosportivofx;

/**
 * The type Member.
 */
public class Member extends Person {

    public Member(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }

    public Member(Person person) {
        super(person.getName(), person.getSurname(), person.getEmail(), person.getPassword());
    }

    /**
     * Enrolls the member in the specified activity.
     * Other method to add a member, not used because we used  activity.addSubscriber(member)
     *
     * @param activity the activity to enroll in
     */
    public void enrollActivity(Activity activity) {
        activity.addSubscriber(this);
    }

    /**
     * Unenrolls the member from the specified activity.
     * Other method to remove, not used because we used  activity.removeSubscriber(member)
     *
     * @param activity the activity to unenroll from
     */
    public void unenrollActivity(Activity activity) {
        activity.removeSubscriber(this);
    }

    @Override
    public String toString() {
        return "(Member) " + super.toString();
    }

}
