package Vehicle;

import Port.Port;
import Container.Container;

public class Ship extends Vehicle{
    private Vehicle.Ship.ShipType type;

    // Enum definition for TruckType inside the Truck class
    public enum ShipType {
        BASIC,
        REEFER,
        TANKER
    }

    public Ship(String vehicleID, String name, double currentFuel, double carryingCapacity, double fuelCapacity, Vehicle.Ship.ShipType type) {
        super(vehicleID, name, currentFuel, carryingCapacity, fuelCapacity);
        this.type = type;
    }

    public Vehicle.Ship.ShipType getType() {
        return type;
    }

    public void setType(Vehicle.Ship.ShipType type) {
        this.type = type;
    }

    @Override
    public boolean loadContainer(Container container){
        if (this.canLoadContainer(container)) {
            this.containerList.add(container);
        }
        return false;
    }

    // Determine if it Can Move to a Port
    public boolean canMoveToPort(Port departurePort, Port destinationPort) {
        // Check if the truck's fuel level is enough for the trip
        double requiredFuel = calculateFuelConsumption(departurePort.calculateDistance(destinationPort));
        if (currentFuel > requiredFuel) {
            double totalWeightWithContainers = this.getTotalWeight();
            if (totalWeightWithContainers <= carryingCapacity) {
                // Both source and destination ports have the ability to receive trucks,
                // and the truck has enough fuel and carrying capacity.
                return true;
            }
        }
        return false;
    }

    @Override
    public double calculateFuelConsumption(double distance) {
        double totalFuelConsumption = this.containerList.stream()
                .mapToDouble(container -> container.calculateFuelConsumption("Ship", distance))
                .sum();

        return totalFuelConsumption;
    }
}