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
     * @param type the type
     * @param name the name
     */
    public Course(String type, String name)
    {
        super(type, name);
    }

    public  Course(String type, String name,  ArrayList<Member> subscribers)
    {
        super(type, name, subscribers);
    }
    {

    }
}
