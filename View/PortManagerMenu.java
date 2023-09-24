package View;

import Controller.TripController;
import Model.*;
import Controller.ContainerController;
import Controller.PortController;
import Controller.VehicleController;

import java.text.SimpleDateFormat;
import java.util.*;

public class PortManagerMenu implements Menu{
    private PortManager portManager;
    private Port port;
    private PortController portController;
    private ContainerController containerController;
    private VehicleController vehicleController;
    private TripController tripController;
    private Scanner scanner;
    private SimpleDateFormat sdf;

    public PortManagerMenu(PortManager portManager) {
        this.portManager = portManager;
        this.portController = PortController.getInstance();
        portController.loadPortsFromFile();
        this.containerController = ContainerController.getInstance();
        containerController.loadContainersFromFile();
        this.vehicleController = VehicleController.getInstance();
        vehicleController.loadVehiclesFromFile();
        this.tripController = TripController.getInstance();
        tripController.loadTripsFromFile();
        this.port = portController.getPortByID(portManager.getPort()).get();
        this.scanner = new Scanner(System.in);
        this.sdf = new SimpleDateFormat("dd-MM-yyyy");
    }
    public void view(){
        int choice;
        do {
            System.out.println("===== Port Manager Menu =====");
            System.out.println("Welcome " + portManager.getName() + "!");
            System.out.println("Current Port: " + port.getPortID() + " | " + port.getName());
            System.out.println("1. View Port Details");
            System.out.println("2. Container");
            System.out.println("3. Vehicle");
            System.out.println("4. Trip");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println(this.port.getPortDetails());
                    break;
                case 2:
                    containerMenu();
                    break;
                case 3:
                    vehicleMenu();
                    break;
                case 4:
                    tripMenu();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void containerMenu() {
        int choice;
        do {
            System.out.println("===== Container Manager Menu =====");
            System.out.println("1. Add Container");
            System.out.println("2. View|Edit|Remove Container");
            System.out.println("3. Return");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter container weight: ");
                        Double weight = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter container type (DRY_STORAGE/OPEN_TOP/OPEN_SIDE/REFRIGERATED/LIQUID): ");
                        Container.ContainerType type = Container.ContainerType.valueOf(scanner.nextLine());
                        Container container = containerController.addContainer(weight,type);
                        this.port.addContainer(container.getContainerID());
                        portController.updatePort(port.getPortID(), port);
                        System.out.println("Container "+container.getContainerID()+" has been added");
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 2:
                    try {
                        this.previewContainers();
                        System.out.print("Enter container id (c-<number>): ");
                        String containerID = scanner.nextLine();
                        if (port.containsContainer(containerID)){
                            System.out.print("1. Edit\n2. Remove\n3. Return\nDo you want to: ");
                            switch (Integer.parseInt(scanner.nextLine())){
                                case 1: {
                                    Container container = containerController.getContainerByID(containerID).get();
                                    System.out.print("Enter container weight (Enter to skip): ");
                                    String weight = scanner.nextLine();
                                    if (!weight.isEmpty()){container.setWeight(Double.parseDouble(weight));}
                                    System.out.print("Enter container type (DRY_STORAGE/OPEN_TOP/OPEN_SIDE/REFRIGERATED/LIQUID/Enter to skip): ");
                                    String type = scanner.nextLine().toUpperCase();
                                    if (!type.isEmpty()){container.setType(Container.ContainerType.valueOf(type));}
                                    containerController.updateContainer(containerID, container);
                                    break;
                                }
                                case 2: {
                                    portController.removePort(containerID);
                                    System.out.println("Container " + containerID + " has been removed");
                                }
                            }
                        } else {
                            System.out.println("No container with such id or you don't have permission.");
                        }
                    }catch (Exception e){
                        System.out.println("An error occurred, please try again.");
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    private void vehicleMenu() {
        int choice;
        do {
            System.out.println("===== Vehicle Manager Menu =====");
            System.out.println("1. Add A Vehicle");
            System.out.println("2. View & Update Vehicle");
            System.out.println("3. Load Container");
            System.out.println("4. Unload Container");
            System.out.println("5. Move Vehicle to Port");
            System.out.println("6. Refuel Vehicle");
            System.out.println("7. View average daily fuel consumption");
            System.out.println("8. Return");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter vehicle name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter vehicle type (TRUCK/SHIP): ");
                        String type = scanner.nextLine().toUpperCase();
                        String truckType = "";
                        if (type.equals("TRUCK")){
                            System.out.print("Enter truck type (BASIC/REEFER/TANKER): ");
                            truckType = scanner.nextLine().toUpperCase();
                        }
                        System.out.print("Enter carrying capacity: ");
                        Double carryingCapacity = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter fuel capacity: ");
                        Double fuelCapacity = Double.parseDouble(scanner.nextLine());
                        if (type.equals("TRUCK")){
                            Truck truck = vehicleController.addTruck(name, carryingCapacity, fuelCapacity, truckType, port.getPortID());
                            System.out.println("Truck "+truck.getVehicleID() + " has been added");
                        } else if (type.equals("SHIP")){
                            Ship ship = vehicleController.addShip(name, carryingCapacity, fuelCapacity, port.getPortID());
                            System.out.println("Ship "+ship.getVehicleID() + " has been added");
                        } else {
                            System.out.println("Invalid arguments. Please try again");
                        }
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 2:
                    try {
                        this.previewVehicles();
                        System.out.print("Enter vehicle ID to view (tr-<number>/sh-<number>): ");
                        String id = scanner.nextLine();
                        System.out.println(vehicleController.getVehicleByID(id).get().getDetails());
                        System.out.print("\n1. Edit \n2. Return \nDo you want to:");
                        int input = Integer.parseInt(scanner.nextLine());
                        switch (input){
                            case 1:
                                Vehicle vehicle = vehicleController.getVehicleByID(id).get();
                                System.out.print("Enter vehicle name (Enter to skip):");
                                String name = scanner.nextLine();
                                if (!name.isEmpty()){vehicle.setName(name);};
                                if (vehicle instanceof Truck){
                                    System.out.print("Enter truck type (BASIC/REEFER/TANKER/Enter to skip):");
                                    String truckType = scanner.nextLine().toUpperCase();
                                    if (!truckType.isEmpty()){((Truck)vehicle).setType(Truck.TruckType.valueOf(truckType));}
                                }
                                System.out.print("Enter carrying capacity (Enter to skip): ");
                                String carryingCapacity = scanner.nextLine();
                                if(!carryingCapacity.isEmpty()){vehicle.setCarryingCapacity(Double.parseDouble(carryingCapacity));}
                                System.out.print("Enter fuel capacity (Enter to skip): ");
                                String fuelCapacity = scanner.nextLine();
                                if(!fuelCapacity.isEmpty()){vehicle.setFuelCapacity(Double.parseDouble(fuelCapacity));}
                                System.out.println("Vehicle updated");
                                break;
                            case 2:
                                break;
                        }
                    }catch (Exception e){
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 3:
                    try {
                        this.previewVehicles();
                        System.out.print("Enter vehicle ID to load (tr-<number>/sh-<number>): ");
                        String id = scanner.nextLine();
                        if (vehicleController.contains(id)) {
                            this.previewContainers();
                            System.out.print("Enter container ID to load (c-<number>): ");
                            String containerID = scanner.nextLine();
                            if (containerController.contains(containerID)){
                                Vehicle vehicle = vehicleController.getVehicleByID(id).get();
                                if (vehicle.loadContainer(containerController.getContainerByID(containerID).get())){
                                    vehicleController.updateVehicle(vehicle.getVehicleID(), vehicle);
                                    port.removeContainer(containerID);
                                    portController.updatePort(port.getPortID(), port);
                                    System.out.println("Loaded");
                                } else {
                                    System.out.println("Load container failed");
                                }
                            }
                        } else {
                            System.out.println("No vehicle with such id");
                        }
                    }catch (Exception e) {
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 4:
                    try {
                        this.previewVehicles();
                        System.out.print("Enter vehicle ID to unload (tr-<number>/sh-<number>): ");
                        String id = scanner.nextLine();
                        if (vehicleController.contains(id)) {
                            Vehicle vehicle = vehicleController.getVehicleByID(id).get();
                            System.out.println("===== Container Preview =====");
                            System.out.printf("%-10s%-15s%-10s%n", "ID", "Type", "Weight (kg)");
                            for (Container container : vehicle.getContainerList()) {
                                System.out.printf("%-10s%-15s%-10.2f%n", container.getContainerID(), container.getType(), container.getWeight());
                            }
                            System.out.print("Enter container ID to unload (c-<number>): ");
                            String containerId = scanner.nextLine();
                            if (vehicle.containsContainer(containerId)) {
                                vehicle.unloadContainer(containerId);
                                vehicleController.updateVehicle(vehicle.getVehicleID(), vehicle);
                                this.port.addContainer(containerId);
                                portController.updatePort(port.getPortID(), port);
                                System.out.println("Container unloaded.");
                            } else {
                                System.out.println("No container with such id");
                            }
                        } else {
                            System.out.println("No vehicle with such id");
                        }
                    }catch (Exception e) {
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 5:
                    try {
                        this.previewVehicles();
                        System.out.print("Enter vehicle ID to move (tr-<number>/sh-<number>): ");
                        String id = scanner.nextLine();
                        if (vehicleController.contains(id)) {
                            this.displayPortPreview();
                            System.out.print("Enter port ID to move to (p-<number>): ");
                            String portID = scanner.nextLine();
                            if (portController.contains(portID)){
                                Vehicle vehicle = vehicleController.getVehicleByID(id).get();
                                if (vehicle.canMoveToPort(port, portController.getPortByID(portID).get())){
                                    System.out.println("Enter expected arrival date (dd-mm-yyyy): ");
                                    String arrivalDate = scanner.nextLine();
                                    vehicle.setCurrentPort(portID);
                                    tripController.createTrip(vehicle.getVehicleID(), new Date(), sdf.parse(arrivalDate), this.port.getPortID(), portID, Trip.TripStatus.ONGOING);
                                    vehicleController.updateVehicle(vehicle.getVehicleID(), vehicle);
                                } else {
                                    System.out.print("Unable to move vehicle at the moment. Reason: ");
                                    if (vehicle instanceof Truck){
                                        if (!port.isLandingAbility()) {
                                            System.out.println("Destination port does not support landing vehicles");
                                        } else {
                                            System.out.println("Not enough fuel or invalid port arguments");
                                        }
                                    } else {
                                        System.out.println("Not enough fuel or invalid port arguments");
                                    }
                                }
                            }
                        }
                    }catch (Exception e) {
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 6:
                    try {
                        this.previewVehicles();
                        System.out.print("Enter vehicle ID to refuel (v-<number>): ");
                        String id = scanner.nextLine();
                        if (vehicleController.contains(id)) {
                            vehicleController.getVehicleByID(id).get().refuel();
                            vehicleController.saveVehiclesToFile();
                            System.out.println("Vehicle refueled.");
                        } else {
                            System.out.println("No vehicle with such id");
                        }
                    }catch (Exception e) {
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 7:
                    double stat = tripController.getFuelDailyConsumption(port.getPortID());
                    System.out.println("Daily fuel consumption: "+String.valueOf(stat)+ " liters");
                    break;
                case 8:
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    public void tripMenu(){
        int choice;
        do {
            System.out.println("===== Trip Menu =====");
            System.out.println("1. View all trip");
            System.out.println("2. View trip in range");
            System.out.println("3. View trip on a date");
            System.out.println("4. Return");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    try {
                        printTripTable(tripController.getTripsOfPort(port.getPortID()));
                    } catch (Exception e){
                        System.out.println("An error occurred: "+e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.print("Enter start date (dd-mm-yyyy): ");
                        Date startDate = sdf.parse(scanner.nextLine());
                        System.out.print("Enter end date (dd-mm-yyyy): ");
                        Date endDate = sdf.parse(scanner.nextLine());
                        printTripTable(tripController.getTripsInRangeAtPort(port.getPortID(), startDate, endDate));
                    } catch (Exception e){
                        System.out.println("An error occurred: "+e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println("Enter date (dd-mm-yyyy): ");
                        Date date = sdf.parse(scanner.nextLine());
                        printTripTable(tripController.getTripsOnDateAtPort(port.getPortID(), date));
                    } catch (Exception e){
                        System.out.println("An error occurred: "+e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Returning...");
            }
        } while (choice != 4);
    }
    private void displayPortPreview() {
        List<Port> ports = portController.getAllPorts();

        if (ports.isEmpty()) {
            System.out.println("No ports available.");
        } else {
            System.out.println("===== Port Preview =====");
            System.out.printf("%-5s%-15s%-15s%-15s%n", "ID", "Name", "Latitude", "Longitude");

            for (Port port : ports) {
                System.out.printf("%-5s%-15s%-15.2f%-15.2f%n", port.getPortID(), port.getName(), port.getLatitude(), port.getLongitude());
            }
        }
    }

    private void previewContainers() {
        List<Container> containers = this.portController.getContainersAtPort(port.getPortID());
        if (containers.isEmpty()) {
            System.out.println("No containers available.");
        } else {
            System.out.println("===== Container Preview =====");
            System.out.printf("%-10s%-15s%-10s%n", "ID", "Type", "Weight (kg)");

            for (Container container : containers) {
                System.out.printf("%-10s%-15s%-10.2f%n", container.getContainerID(), container.getType(), container.getWeight());
            }
        }
    }

    public void previewVehicles() {
        List<Vehicle> vehicles = this.vehicleController.getAllVehiclesAtPort(port.getPortID());
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles available.");
        } else {
            System.out.println("===== Vehicle Preview =====");
            System.out.println("ID\tName\tType");
            for (Vehicle vehicle : vehicles) {
                String row = vehicle.getVehicleID() + "\t" + vehicle.getName() + "\t";
                if (vehicle instanceof Truck) {
                    row += "TRUCK | " + ((Truck) vehicle).getType();
                } else if (vehicle instanceof Ship) {
                    row += "SHIP";
                }
                System.out.println(row);
            }
        }
    }
    public void printTripTable(List<Trip> trips) {
        // Define the table header
        System.out.printf("%-10s %-10s %-20s %-20s %-15s %-15s %-10s%n",
                "Trip ID", "Vehicle ID", "Departure Date", "Arrival Date", "Departure Port", "Arrival Port", "Status");

        // Loop through the list of trips and print each trip as a row in the table
        for (Trip trip : trips) {
            System.out.printf("%-10s %-10s %-20s %-20s %-15s %-15s %-10s%n",
                    trip.getTripID(),
                    trip.getVehicleID(),
                    sdf.format(trip.getDepartureDate()),
                    sdf.format(trip.getArrivalDate()),
                    trip.getDeparturePort(),
                    trip.getArrivalPort(),
                    trip.getStatus());
        }
    }
}