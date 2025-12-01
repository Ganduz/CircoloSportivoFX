package com.example.circolosportivofx;

import java.util.ArrayList;

/**
 * The type Course.
 */
public final class Course extends Activity
{

    /**
     * Instantiates a new Course.
     *
     * @param name the name
     */
    public Course(String name)
    {
        super(name);
    }

    public  Course(String name,  ArrayList<Member> subscribers)
    {
        super(name, subscribers);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
