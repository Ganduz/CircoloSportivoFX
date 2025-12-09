package com.example.circolosportivofx;

/**
 * The type Person.
 *
 * @abstract @class Person Abstract class that represents a person with attributes name, surname, email, and password.
 */
public abstract class Person {

    protected String name;

    protected String surname;

    protected String email;

    protected String password;

    public Person(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return  name + " " + surname;
    }

    /**
     * Compares this person with another person for equality based on their attributes.
     * @param person the person to compare with
     * @return true if all attributes are equal, false otherwise
     */
    public Boolean isEqual(Person person) {
        return this.name.equals(person.getName()) &&
                this.surname.equals(person.getSurname()) &&
                this.email.equals(person.getEmail()) &&
                this.password.equals(person.getPassword());
    }

}
