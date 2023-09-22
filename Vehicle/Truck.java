package Vehicle;

import Port.Port;
import Container.Container;


public class Truck extends Vehicle  {
    private TruckType type;
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

    @Override
    public boolean loadContainer(Container container){
        if (this.canLoadContainer(container)) {
            if (this.type == TruckType.BASIC && (container.getType() == Container.ContainerType.DRY_STORAGE || container.getType() == Container.ContainerType.OPEN_TOP || container.getType() == Container.ContainerType.OPEN_SIDE)) {
                this.containerList.add(container);
                return true;
            } else if (this.type == TruckType.REEFER && (container.getType() == Container.ContainerType.REFRIGERATED)) {
                this.containerList.add(container);
                return true;
            } else if (this.type == TruckType.TANKER && (container.getType() == Container.ContainerType.LIQUID)) {
                this.containerList.add(container);
                return true;
            }
        }
        return false;
    }

    // Determine if it Can Move to a Port
    public boolean canMoveToPort(Port departurePort, Port destinationPort) {
        // Check if the truck's fuel level is enough for the trip
        double requiredFuel = calculateFuelConsumption(departurePort.calculateDistance(destinationPort));
        if (this.currentFuel > requiredFuel) {
            if (destinationPort.isLandingAbility()) {
                double totalWeightWithContainers = this.getTotalWeight();
                if (totalWeightWithContainers <= this.carryingCapacity) {
                    // Both source and destination ports have the ability to receive trucks,
                    // and the truck has enough fuel and carrying capacity.
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public double calculateFuelConsumption(double distance) {
        double totalFuelConsumption = this.containerList.stream()
                .mapToDouble(container -> container.calculateFuelConsumption("Truck", distance))
                .sum();

        return totalFuelConsumption;
    }
}