public class Pair {
    protected String key;   // The Key value of the Pair object
    protected int value;    // The value of the Pair object

    // *****************

    /**
     * The constructor that initializes the members to null values
     */
    public Pair() {
        this.key = null;
        this.value = 0;
    }

    // *****************

    /**
     * This function sets the key and values of the Pair object
     * @param key the new key value of the Pair object
     * @param value the new value of the Pair object
     */
    public void set(String key, int value) {
        this.key = key;
        this.value = value;
    }

    // *****************

    /**
     * @return the key value of the Pair object
     */
    public String getKey() {
        return this.key;
    }

    // *****************

    /**
     * @return the value of the Pair object
     */
    public int getValue() {
        return this.value;
    }

    // *****************
}
