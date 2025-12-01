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
     * @param name the name
     */
    public Competition(String name)
    {
        super(name);
    }

    public Competition(String name,  ArrayList<Member> subscribers)
    {
        super(name, subscribers);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
