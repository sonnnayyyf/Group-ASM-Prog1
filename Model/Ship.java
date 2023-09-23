package Model;

public class Ship extends Vehicle{

    public Ship(String vehicleID, String name, double carryingCapacity, double fuelCapacity, String currenPort) {
        super(vehicleID, name, carryingCapacity, fuelCapacity, currenPort);
    }

    @Override
    public boolean loadContainer(Container container){
        if (this.canLoadContainer(container)) {
            this.containerList.add(container);
            return true;
        }
        return false;
    }

    // Determine if it Can Move to a Port
    public boolean canMoveToPort(Port departurePort, Port destinationPort) {
        if (!departurePort.getPortID().equals(destinationPort.getPortID())) {
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
    public String getDetails() {
        StringBuilder details = new StringBuilder();
        details.append("===== Ship Details =====").append("\n");
        details.append("ID: ").append(getVehicleID()).append("\n");
        details.append("Name: ").append(getName()).append("\n");
        details.append("Fuel Capacity: ").append(this.fuelCapacity).append("\n");
        details.append("Current fuel: ").append(this.currentFuel).append("\n");
        details.append("Total weight of containers: ").append(this.getTotalWeight()).append("\n");
        details.append("Container count: ").append("\n");
        details.append("\t DRY_STORAGE").append(this.getNumberOfContainersByType(Container.ContainerType.DRY_STORAGE)).append("\n");
        details.append("\t OPEN_TOP").append(this.getNumberOfContainersByType(Container.ContainerType.OPEN_TOP)).append("\n");
        details.append("\t OPEN_SIDE").append(this.getNumberOfContainersByType(Container.ContainerType.OPEN_SIDE)).append("\n");
        details.append("\t REFRIGERATED").append(this.getNumberOfContainersByType(Container.ContainerType.REFRIGERATED)).append("\n");
        details.append("\t LIQUID").append(this.getNumberOfContainersByType(Container.ContainerType.LIQUID)).append("\n");
        return details.toString();
    }
}