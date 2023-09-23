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
        Map<String, Map<String, Double>> fuelConsumptionRates = new HashMap<>();
        fuelConsumptionRates.put("Dry storage", createFuelConsumptionMap(3.5, 4.6));
        fuelConsumptionRates.put("Open top", createFuelConsumptionMap(2.8, 3.2));
        fuelConsumptionRates.put("Open side", createFuelConsumptionMap(2.7, 3.2));
        fuelConsumptionRates.put("Refrigerated", createFuelConsumptionMap(4.5, 5.4));
        fuelConsumptionRates.put("Liquid", createFuelConsumptionMap(4.8, 5.3));
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