package Model;

import Controller.ContainerController;
import Controller.PortController;
import Controller.TripController;
import Controller.VehicleController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Port implements Serializable {
    private String portID;
    private String name;
    private double latitude;
    private double longitude;
    private double storingCapacity;
    private boolean landingAbility;
    private List<String> listOfContainer;

    public Port(String portID, String name, double latitude, double longitude, int storingCapacity, boolean landingAbility) {
        this.portID = portID;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
        this.listOfContainer = new ArrayList<>();
    }
    public String getPortID() {
        return portID;
    }

    public void setPortID(String portID) {
        this.portID = portID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getStoringCapacity() {
        return storingCapacity;
    }

    public void setStoringCapacity(double storingCapacity) {
        this.storingCapacity = storingCapacity;
    }

    public boolean isLandingAbility() {
        return landingAbility;
    }

    public void setLandingAbility(boolean landingAbility) {
        this.landingAbility = landingAbility;
    }

    public int getNumberOfContainers() {
        return listOfContainer.size();
    }
    public List<String> getListOfContainer() {
        return listOfContainer;
    }

    public void setListOfContainer(List<String> listOfContainer) {
        this.listOfContainer = listOfContainer;
    }

    public boolean containsContainer(String containerID){
        return this.listOfContainer.stream().anyMatch(container -> container.equals(containerID));
    }
    public boolean addContainer(String containerID) {
        if (!this.listOfContainer.contains(containerID)){
            this.listOfContainer.add(containerID);
            return true;
        }
        return false;
    }

    public void removeContainer(String containerID) {
        this.listOfContainer.removeIf(container -> container.equals(containerID));
    }

    public double getRemainingCapacity(){
        double remainingCapacity = this.storingCapacity;
        for (String containerID : this.listOfContainer){
            remainingCapacity -= ContainerController.getInstance().getContainerByID(containerID).get().getWeight();
        }
        return remainingCapacity;
    }

    public ArrayList<Trip> getTripHistory(){
        return TripController.getInstance().getTripsOfPort(this.portID);
    }
    public double calculateDistance(Port otherPort) {
        double earthRadius = 6371;
        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(otherPort.latitude);
        double lon2 = Math.toRadians(otherPort.longitude);

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }

    public int getNumberOfVehicles(){
        return VehicleController.getInstance().getAllVehiclesAtPort(this.portID).size();
    }
    public String getPortDetails() {
        return  "Port ID: " + this.portID + "\n" +
                "Name: " + this.name + "\n" +
                "Latitude: " + this.latitude + "\n" +
                "Longitude: " + this.longitude + "\n" +
                "Storing Capacity: " + this.storingCapacity + "\n" +
                "Remaining Capacity: " + this.getRemainingCapacity() + "\n" +
                "Landing Ability: " + this.landingAbility + "\n" +
                "Number of Containers: " + this.listOfContainer.size() + "\n"+
                "Number of Vehicles: " + this.getNumberOfVehicles() + "\n";
    }

}