package Vehicle;

import java.util.List;
import java.util.Map;
import Port.Port;
import Container.Container;




public abstract class Vehicle {
    // Class attributes
    private String vehicleID;
    private String name;
    public double currentFuel;
    public double carryingCapacity;
    private double fuelCapacity;
    public Port currentPort;
    private int numberOfContainers;
    private Map<String, Integer> totalNumContainerByType;
    public List<Container> containerDetails;

    //Getters and Setters
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

    public boolean canLoadContainer(Container container) {
        double totalWeightWithContainer = calculateTotalWeightWithContainer(container);
        return totalWeightWithContainer <= carryingCapacity;
    }

    private double calculateTotalWeightWithContainer(Container container) {
        // Implement logic to calculate the total weight with the new container
        // For example, you might sum the weights of all containers currently loaded
        double totalWeight = 0.0;
        for (Container loadedContainer : containerDetails) {
            totalWeight += loadedContainer.getWeight();
        }
        return totalWeight + container.getWeight();
    }


    // Load a Container
    public void loadContainer(Container container) {
        if (canLoadContainer(container)) {
            containerDetails.add(container);
            numberOfContainers++;
        } else {
            // Handle the case where the container cannot be loaded
        }
    }

    // Unload a Container
    public void unloadContainer(Container container) {
        if (containerDetails.remove(container)) {
            numberOfContainers--;
        } else {
            // Handle the case where the container is not found for unloading
        }
    }

}

