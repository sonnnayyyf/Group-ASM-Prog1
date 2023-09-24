/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2023B
  Assessment: Group Assessment
  Author: Group 30
  ID: s3978145 - Phan Nguyen Viet Nhan
s3962514-Duong Viet Hoang
s3978450-Tran Hoang Son
s3977767-Tran Nhat Minh

  Acknowledgement:

*/
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public abstract class Vehicle implements Serializable {
    // Class attributes
    protected String vehicleID;
    protected String name;
    protected double currentFuel;
    protected double carryingCapacity;
    protected double fuelCapacity;
    protected String currentPort;

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
    public String getCurrentPort() {
        return currentPort;
    }
    public void setCurrentPort(String currentPort) {
        this.currentPort = currentPort;
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
    public boolean containsContainer(String id) { return this.containerList.stream().anyMatch(container -> container.getContainerID().equals(id)); }
    public abstract boolean loadContainer(Container container);

    public void unloadContainer(String containerID){
        this.containerList.removeIf(container -> container.getContainerID().equals(containerID));
    }

    public void refuel(){
        this.currentFuel = this.fuelCapacity;
    }
    public abstract double calculateFuelConsumption(double distance);
    public Vehicle() {}

    public Vehicle(String vehicleID, String name, double carryingCapacity, double fuelCapacity, String currentPort)
    {
        this.vehicleID = vehicleID;
        this.name = name;
        this.currentFuel = fuelCapacity;
        this.carryingCapacity = carryingCapacity;
        this.fuelCapacity = fuelCapacity;
        this.currentPort = currentPort;
    }

    public boolean canLoadContainer(Container container) {
        double totalWeightWithContainer = this.getTotalWeight()+container.getWeight();
        return totalWeightWithContainer <= carryingCapacity;
    }
    public abstract boolean canMoveToPort(Port departurePort, Port destinationPort);
    public double getTotalWeight() {
        double totalWeight = this.containerList.stream()
                .mapToDouble(container -> container.getWeight())
                .sum();

        return totalWeight;
    }
    public String getDetails() {
        StringBuilder details = new StringBuilder();
        details.append("===== Vehicle Details =====").append("\n");
        details.append("ID: ").append(getVehicleID()).append("\n");
        details.append("Name: ").append(getName()).append("\n");
        details.append("Fuel Capacity: ").append(this.fuelCapacity).append("\n");
        details.append("Current fuel: ").append(this.currentFuel).append("\n");
        details.append("Total weight of containers: ").append(this.getTotalWeight()).append("\n");
        details.append("Container count: ").append("\n");
        details.append("\t DRY_STORAGE: ").append(this.getNumberOfContainersByType(Container.ContainerType.DRY_STORAGE)).append("\n");
        details.append("\t OPEN_TOP: ").append(this.getNumberOfContainersByType(Container.ContainerType.OPEN_TOP)).append("\n");
        details.append("\t OPEN_SIDE: ").append(this.getNumberOfContainersByType(Container.ContainerType.OPEN_SIDE)).append("\n");
        details.append("\t REFRIGERATED: ").append(this.getNumberOfContainersByType(Container.ContainerType.REFRIGERATED)).append("\n");
        details.append("\t LIQUID: ").append(this.getNumberOfContainersByType(Container.ContainerType.LIQUID)).append("\n");
        return details.toString();
    }
}