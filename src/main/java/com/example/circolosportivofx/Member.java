package com.example.circolosportivofx;

/**
 * The type Member.
 */
public class Member extends Person {
    /**
     * Instantiates a new Member.
     *
     * @param name     the name
     * @param surname  the surname
     * @param email    the email
     * @param password the password
     */
    public Member(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }

    /**
     * Instantiates a new Member.
     *
     * @param person the person
     */
    public Member(Person person) {
        super(person.getName(), person.getSurname(), person.getEmail(), person.getPassword());
    }

    /**
     * Enroll activity.
     *
     * @param activity the activity
     */
    public void enrollActivity(Activity activity) {
        activity.addSubscriber(this);
    }

    /**
     * Unenroll activity.
     *
     * @param activity the activity
     */
    public void unenrollActivity(Activity activity) {
        activity.removeSubscriber(this);
    }

    @Override
    public String toString() {
        return "(Member) " + super.toString();
    }


}
