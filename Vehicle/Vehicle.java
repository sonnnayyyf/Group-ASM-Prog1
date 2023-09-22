package Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Port.Port;
import Container.Container;




public abstract class Vehicle {
    // Class attributes
    protected String vehicleID;
    protected String name;
    protected double currentFuel;
    protected double carryingCapacity;
    protected double fuelCapacity;
    protected Port currentPort;
    protected Map<String, Integer> totalNumContainerByType;

    protected List<Container> containerList = new ArrayList<Container>();

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
    public Map<String, Integer> getTotalNumContainerByType() {
        return totalNumContainerByType;
    }
    public void setTotalNumContainerByType(Map<String, Integer> totalNumContainerByType) {
        this.totalNumContainerByType = totalNumContainerByType;
    }
    public List<Container> getContainerList() {
        return containerList;
    }

    public void setContainerList(List<Container> containerList) {
        this.containerList = containerList;
    }
    public int getNumberOfContainers(){
        return this.containerList.size();
    }
    public int getNumberOfContainersByType(Container.ContainerType type){
        return this.containerList.stream().filter(container -> container.getType().equals(type)).collect(Collectors.toCollection(ArrayList::new)).size();
    }
    public abstract boolean loadContainer(Container container);

    public void unloadContainer(String containerID){
        this.containerList.removeIf(container -> container.getContainerID().equals(containerID));
    }

    public void refuel(){
        this.currentFuel = this.fuelCapacity;
    }
    public abstract double calculateFuelConsumption(double distance);
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
        double totalWeightWithContainer = this.getTotalWeight()+container.getWeight();
        return totalWeightWithContainer <= carryingCapacity;
    }

    public double getTotalWeight() {
        double totalWeight = this.containerList.stream()
                .mapToDouble(container -> container.getWeight())
                .sum();

        return totalWeight;
    }
}