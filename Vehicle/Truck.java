package Vehicle;

import Port.Port;
import Container.Container;


public class Truck extends Vehicle  {
    private TruckType type;
    private Container container;

    // Enum definition for TruckType inside the Truck class
    public enum TruckType {
        BASIC,
        REEFER,
        TANKER
    }

    public Truck(String vehicleID, String name, double currentFuel, double carryingCapacity, double fuelCapacity, TruckType type) {
        super(vehicleID, name, currentFuel, carryingCapacity, fuelCapacity);
        this.type = type;
    }

    public TruckType getType() {
        return type;
    }

    public void setType(TruckType type) {
        this.type = type;
    }

    // Determine if it Can Move to a Port
    public boolean canMoveToPort(Port departurePort, Port destinationPort) {
        // Check if the truck's fuel level is enough for the trip
        double requiredFuel = calculateFuelConsumption(departurePort.calculateDistance(destinationPort));
        if (currentFuel > requiredFuel) {
            if (destinationPort.isLandingAbility()) {
                double totalWeightWithContainers = calculateTotalWeightWithContainers();
                if (totalWeightWithContainers <= carryingCapacity) {
                    // Both source and destination ports have the ability to receive trucks,
                    // and the truck has enough fuel and carrying capacity.
                    return true;
                }
            }
        }
        return false;
    }

    public double calculateFuelConsumption (double distance)
    {
        return container.calculateFuelConsumption("Truck", distance);
    }

    private double calculateTotalWeightWithContainers() {
        // Implement logic to calculate the total weight of loaded containers
        // Iterate through the containerDetails and sum their weights
        double totalWeight = 0.0;
        for (Container container : containerDetails) {
            totalWeight += container.getWeight();
        }
        return totalWeight;
    }
}


