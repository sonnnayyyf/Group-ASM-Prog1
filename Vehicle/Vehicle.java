package Vehicle;

import java.util.List;
import java.util.Map;

public class Vehicle {
    private String vehicleID;
    private String name;
    private double currentFuel;
    private double carryingCapacity;
    private double fuelCapacity;
    private Port currentPort;
    private int numberOfContainers;
    private Map<String, Integer> totalNumContainerByType;
    private List<Container> containerDetails;
    public String getVehicleID() {
        return vehicleID;
    }
    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getCurrentFuel() {
        return currentFuel;
    }
    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }
    public double getCarryingCapacity() {
        return carryingCapacity;
    }
    public void setCarryingCapacity(double carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }
    public double getFuelCapacity() {
        return fuelCapacity;
    }
    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }
    public Port getCurrentPort() {
        return currentPort;
    }
    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }
    public int getNumberOfContainers() {
        return numberOfContainers;
    }
    public void setNumberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }
    public Map<String, Integer> getTotalNumContainerByType() {
        return totalNumContainerByType;
    }
    public void setTotalNumContainerByType(Map<String, Integer> totalNumContainerByType) {
        this.totalNumContainerByType = totalNumContainerByType;
    }
    public List<Container> getContainerDetails() {
        return containerDetails;
    }
    public void setContainerDetails(List<Container> containerDetails) {
        this.containerDetails = containerDetails;
    }

    public Vehicle() {}

    public Vehicle(String vehicleID, String name, double currentFuel, double carryingCapacity, double fuelCapacity)
    {
        this.vehicleID = vehicleID;
        this.name = name;
        this.currentFuel = currentFuel;
        this.carryingCapacity = carryingCapacity;
        this.fuelCapacity = fuelCapacity;
    }
}
