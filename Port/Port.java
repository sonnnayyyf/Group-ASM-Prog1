package Port;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import javax.swing.*;

import Container.Container;
import Trip.Trip;
import Trip.TripsManager;

public class Port {
    private String portID;
    private String name;
    private double latitude;
    private double longitude;
    private double storingCapacity;
    private boolean landingAbility;
    private List<Container> listOfContainer = new ArrayList<Container>();
    private int numberOfVehicles;

    public Port(String portID, String name, double latitude, double longitude, int storingCapacity, boolean landingAbility) {
        this.portID = portID;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
        this.numberOfVehicles = 0;
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
    public List<Container> getListOfContainer() {
        return listOfContainer;
    }

    public void setListOfContainer(List<Container> listOfContainer) {
        this.listOfContainer = listOfContainer;
    }

    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public void setNumberOfVehicles(int numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }
    public void addContainers(ArrayList<Container> listOfContainer) {
        this.listOfContainer.addAll(listOfContainer);
    }

    public void removeContainer(String containerID) {
        this.listOfContainer.removeIf(container -> container.getContainerID().equals(containerID));
    }

    public void addVehicles(int amount) {
        numberOfVehicles += amount;
    }

    public void removeVehicles(int amount) {
        numberOfVehicles -= amount;
    }

    public double getRemainingCapacity(){
        double remainingCapacity = this.storingCapacity;
        for (Container container:
                this.listOfContainer) {
            remainingCapacity -= container.getWeight();
        }
        return remainingCapacity;
    }

    public ArrayList<Trip> getTripHistory(){
        return TripsManager.getInstance().getTripsOfPort(this.portID);
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
}