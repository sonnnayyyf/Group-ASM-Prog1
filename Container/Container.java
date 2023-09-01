package Container;

import java.util.List;
import java.util.Map;

public class Container {

    // Class attributes
    private String containerID;
    private double weight;
    private ContainerType type;
    private Map<String, FuelRate> fuelConsumption;

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
}