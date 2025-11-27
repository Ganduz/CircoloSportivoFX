import java.util.ArrayList;
import java.util.List;

/**
 * The type Activity.
 */
public abstract class Activity
{
    /**
     * The Type.
     */
    protected String type;
    /**
     * The Subscribers.
     */
    protected ArrayList<Person> subscribers;
    /**
     * The Name.
     */
    protected String name;

    /**
     * Instantiates a new Activity.
     *
     * @param type the type
     * @param name the name
     */
    public Activity(String type, String name)
    {
        this.type = type;
        this.subscribers = new ArrayList<Person>();
        this.name = name ;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Gets subscribers.
     *
     * @return the subscribers
     */
    public ArrayList<Person> getSubscribers()
    {
        return subscribers;
    }

    /**
     * Sets subscribers.
     *
     * @param subscribers the subscribers
     */
    public void setSubscribers(ArrayList<Person> subscribers)
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
    public void addSubscriber(Person person)
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


}
