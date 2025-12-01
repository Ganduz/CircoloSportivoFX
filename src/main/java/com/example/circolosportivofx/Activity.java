package com.example.circolosportivofx;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Activity.
 */
public abstract class Activity
{
    /**
     * The Subscribers.
     */
    protected ArrayList<Member> subscribers;
    /**
     * The Name.
     */
    protected String name;

    /**
     * Instantiates a new Activity.
     *
     * @param name the name
     */
    public Activity(String name)
    {
        this.subscribers = new ArrayList<Member>();
        this.name = name ;
    }

    public Activity(String name, ArrayList<Member> subscribers)
    {
        this.subscribers = new ArrayList<>(subscribers);
        this.name = name ;
    }

    /**
     * Gets subscribers.
     *
     * @return the subscribers
     */
    public ArrayList<Member> getSubscribers()
    {
        return subscribers;
    }

    /**
     * Sets subscribers.
     *
     * @param subscribers the subscribers
     */
    public void setSubscribers(ArrayList<Member> subscribers)
    {
        this.subscribers = subscribers;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Add subscriber.
     *
     * @param person per differenziare tra socio e admin si puo fare un controllo con instanceof per ora aggiungo semplicemente la persona alla lista dei subscribers
     */
    public void addSubscriber(Member person)
    {
        if (!subscribers.contains(person))
            subscribers.add(person);
    }

    /**
     * Remove subscriber.
     *
     * @param person the person
     */
    public void removeSubscriber(Person person)
    {
        subscribers.remove(person);
    }

    @Override
    public String toString() {
        return name;
    }
}
