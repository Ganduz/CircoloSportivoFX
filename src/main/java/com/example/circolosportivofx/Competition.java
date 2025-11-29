package com.example.circolosportivofx;

import java.util.ArrayList;

/**
 * The type Competition.
 */
public final class Competition extends Activity
{

    /**
     * Instantiates a new Competition.
     *
     * @param type the type
     * @param name the name
     */
    public Competition(String type, String name)
    {
        super(type, name);
    }

    public Competition(String type, String name,  ArrayList<Member> subscribers)
    {
        super(type, name, subscribers);
    }

}
