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
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class Container implements Serializable {
    // Class attributes
    private String containerID;
    public double weight;
    private ContainerType type;
    private Map<String, FuelRate> fuelConsumption;

    public Container(String containerID, double weight, ContainerType type) {
        this.containerID = containerID;
        this.weight = weight;
        this.type = type;
    }

    // Getters and Setters
    public String getContainerID() {
        return containerID;
    }

    public void setContainerID(String containerID) {
        this.containerID = containerID;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ContainerType getType() {
        return type;
    }

    public void setType(ContainerType type) {
        this.type = type;
    }

    public Map<String, FuelRate> getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(Map<String, FuelRate> fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    // Enum definition for ContainerType inside the Container class
    public enum ContainerType {
        DRY_STORAGE,
        OPEN_TOP,
        OPEN_SIDE,
        REFRIGERATED,
        LIQUID,
    }

    public double calculateFuelConsumption(String vehicleType, double distanceKm) {
        // Define fuel consumption rates per container type and vehicle type
        Map<ContainerType, Map<String, Double>> fuelConsumptionRates = new HashMap<>();
        fuelConsumptionRates.put(ContainerType.DRY_STORAGE, createFuelConsumptionMap(3.5, 4.6));
        fuelConsumptionRates.put(ContainerType.OPEN_TOP, createFuelConsumptionMap(2.8, 3.2));
        fuelConsumptionRates.put(ContainerType.OPEN_SIDE, createFuelConsumptionMap(2.7, 3.2));
        fuelConsumptionRates.put(ContainerType.REFRIGERATED, createFuelConsumptionMap(4.5, 5.4));
        fuelConsumptionRates.put(ContainerType.LIQUID, createFuelConsumptionMap(4.8, 5.3));
        if (fuelConsumptionRates.containsKey(type) &&
                fuelConsumptionRates.get(type).containsKey(vehicleType)) {
            double rate = fuelConsumptionRates.get(type).get(vehicleType);
            return rate * weight * distanceKm;
        } else {
            return -1.0; // Return -1 to indicate invalid input
        }
    }

    private static Map<String, Double> createFuelConsumptionMap(double shipRate, double truckRate) {
        Map<String, Double> map = new HashMap<>();
        map.put("Ship", shipRate);
        map.put("Truck", truckRate);
        return map;
    }
}