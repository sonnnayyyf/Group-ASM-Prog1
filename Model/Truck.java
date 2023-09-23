package Model;


public class Truck extends Vehicle  {
    private TruckType type;
    // Enum definition for TruckType inside the Truck class
    public enum TruckType {
        BASIC,
        REEFER,
        TANKER
    }

    public Truck(String vehicleID, String name, double carryingCapacity, double fuelCapacity, TruckType type, String currentPort) {
        super(vehicleID, name, carryingCapacity, fuelCapacity, currentPort);
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
    @Override
    public boolean canMoveToPort(Port departurePort, Port destinationPort) {
        if (!departurePort.getPortID().equals(destinationPort.getPortID())) {
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
    @Override
    public String getDetails() {
        StringBuilder details = new StringBuilder();
        details.append("===== Truck Details =====").append("\n");
        details.append("ID: ").append(getVehicleID()).append("\n");
        details.append("Name: ").append(getName()).append("\n");
        details.append("Type: ").append(this.type).append("\n");
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