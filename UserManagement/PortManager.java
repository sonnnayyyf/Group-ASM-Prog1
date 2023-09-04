package UserManagement;

import Port.Port;
import java.util.List;

public class PortManager extends User {
    private Port managedPort;

    public PortManager(String userId, String username, String password, Port managedPort) {
        super(userId, username, password, "Port Manager");
        this.managedPort = managedPort;
    }

    public List<Trip> viewCurrentTraffic() {
        // Return the list of current trips at the managed port
        return this.managedPort.getTrafficRecords();
    }

    public void scheduleTrip(Trip trip) {
        // Schedule a new trip
        this.managedPort.addTrip(trip);
        System.out.println("Trip scheduled successfully.");
    }

    public Port getManagedPort() {
        return managedPort;
    }

    public void setManagedPort(Port managedPort) {
        this.managedPort = managedPort;
    }
}
