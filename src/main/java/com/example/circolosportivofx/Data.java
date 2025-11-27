import java.util.ArrayList;

/**
 * The type Data.
 */
public class Data {
    private  ArrayList<Member> members = new ArrayList<Member>();
    private  ArrayList<Activity> activities = new ArrayList<Activity>();


    /**
     * Gets members.
     *
     * @return the members
     */
    public ArrayList<Member> getMembers() {
        return members;
    }

    /**
     * Sets members.
     *
     * @param members the members
     */
    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }

    /**
     * Gets activities.
     *
     * @return the activities
     */
    public ArrayList<Activity> getActivities() {
        return activities;
    }

    /**
     * Sets activities.
     *
     * @param activities the activities
     */
    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }
}
