package com.example.circolosportivofx;

import java.util.ArrayList;

/**
 * The type Activity.
 * @abstract @class Activity Abstract class that represents an activity with a name and a list of subscribers.
 */
public abstract class Activity
{

    protected ArrayList<Member> subscribers;

    protected String name;

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

    public ArrayList<Member> getSubscribers()
    {
        return subscribers;
    }

    public void setSubscribers(ArrayList<Member> subscribers)
    {
        this.subscribers = subscribers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a subscriber to the activity if they are not already enrolled.
     *
     * @param person the member to be added as a subscriber
     */
    public void addSubscriber(Member person)
    {
        if (!subscribers.contains(person))
            subscribers.add(person);
        else
            System.out.println("Subscriber already enrolled in this activity.");
    }

    /**
     * Removes a subscriber from the activity based on their email.
     *
     * @param person the person to be removed as a subscriber
     */
    public void removeSubscriber(Person person)
    {

        int i;
        for (i = 0; i < subscribers.size(); i++)
        {
            if(subscribers.get(i).getEmail().equals(person.getEmail()))
            {
                subscribers.remove(i);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public Boolean isSubscribed(Member person)
    {
        for(Member m : subscribers)
        {
            if(m.getEmail().equals(person.getEmail()))
                return true;
        }
        return false;
    }
}
