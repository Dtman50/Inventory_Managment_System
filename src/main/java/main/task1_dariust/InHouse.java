package main.task1_dariust;

/**
 *
 * @author Darius Taylor
 */

public class InHouse extends Part {
    private int machineID;
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     *
     * @return the machine id
     */
    public int getMachineID() {
        return machineID;
    }

    /**
     *
     * @param machineID sets machine id
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
