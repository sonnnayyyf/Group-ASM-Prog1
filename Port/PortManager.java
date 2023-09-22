package Port;

import Trip.Trip;
import Trip.TripsManager;

import java.util.ArrayList;

public class PortManager {
    private static PortManager instance;
    private ArrayList<Port> listOfPorts = new ArrayList<Port>();
    public static PortManager getInstance(){
        if (instance == null) {
            instance = new PortManager();
        }
        return instance;
    }
    public ArrayList<Port> getPorts(){
        return this.listOfPorts;
    }
}
