package View;

import Model.Container;
import Controller.ContainerManager;
import Model.Port;
import Controller.PortsManager;
import Model.Vehicle;
import Controller.VehicleManager;
import Model.Truck;
import Model.Ship;

import java.util.*;

public class UserMenu {
    private PortsManager portsManager;
    private ContainerManager containerManager;
    private VehicleManager vehicleManager;
    private Scanner scanner;

    public UserMenu() {
        this.portsManager = PortsManager.getInstance();
        portsManager.loadPortsFromFile();
        this.containerManager = ContainerManager.getInstance();
        containerManager.loadContainersFromFile();
        this.vehicleManager = VehicleManager.getInstance();
        vehicleManager.loadVehiclesFromFile();
        this.scanner = new Scanner(System.in);
    }
    public void view(){
        int choice;
        do {
            System.out.println("===== Port Manager Menu =====");
            System.out.println("1. Port");
            System.out.println("2. Container");
            System.out.println("3. Vehicle");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    portMenu();
                    break;
                case 2:
                    containerMenu();
                    break;
                case 3:
                    vehicleMenu();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }
    private void portMenu() {
        int choice;
        do {
            System.out.println("===== Port Manager Menu =====");
            System.out.println("1. Add Port");
            System.out.println("2. Remove Port");
            System.out.println("3. View Port Details");
            System.out.println("4. Return");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter port name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter latitude: ");
                        double latitude = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter longitude: ");
                        double longitude = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter storing capacity: ");
                        int storingCapacity = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter landing availability (Y/n): ");
                        String input = scanner.nextLine();
                        boolean landingAvailability;
                        if (input.toLowerCase().equals("y")) {
                            landingAvailability = true;
                        } else {
                            landingAvailability = false;
                        }

                        Port port = new Port(name, latitude, longitude, storingCapacity, landingAvailability);
                        portsManager.addPort(port);
                        System.out.println(port.getPortID()+"|"+port.getName()+" has been added");

                    } catch (Exception e){
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 2:
                    try {
                        this.displayPortPreview();
                        System.out.print("Enter port ID to remove (p-<number>): ");
                        String id = scanner.nextLine();
                        if (portsManager.removePort(id)) {
                            System.out.println("Removed port " + id + "from the system");
                        } else {
                            System.out.println("Invalid ID, please try again");
                        }
                    }catch (Exception e){
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 3:
                    try {
                        this.displayPortPreview();
                        System.out.print("Enter port ID to view (p-<number>): ");
                        String id = scanner.nextLine();
                        if (portsManager.contains(id)) {
                            Optional<Port> port = portsManager.getPortByID(id);
                            System.out.println(port.get().getPortDetails());
                        } else {
                            System.out.println("No port with such id");
                        }
                    }catch (Exception e) {
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 4:
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private void containerMenu() {
        int choice;
        do {
            System.out.println("===== Port Manager Menu =====");
            System.out.println("1. Add Container");
            System.out.println("2. View Container");
            System.out.println("3. Remove Container");
            System.out.println("4. Remove Container");
            System.out.println("11. Exit");
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
                        Container container = containerManager.addContainer(weight,type);
                        System.out.println("Container "+container.getContainerID()+" has been added");
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 2:
                    try {
                        this.previewAllContainers();
                    }catch (Exception e){
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 3:
                    try {
                        this.previewAllContainers();
                        System.out.print("Enter container ID to remove (c-<number>): ");
                        String id = scanner.nextLine();
                        if (containerManager.contains(id)) {
                            containerManager.removeContainer(id);
                        } else {
                            System.out.println("No container with such id");
                        }
                    }catch (Exception e) {
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 4:
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private void vehicleMenu() {
        int choice;
        do {
            System.out.println("===== Port Manager Menu =====");
            System.out.println("1. Add A Vehicle");
            System.out.println("2. View & Update Vehicles");
            System.out.println("3. Remove Vehicles");
            System.out.println("4. Load Container");
            System.out.println("5. Unload Container");
            System.out.println("6. Move Vehicle to Port");
            System.out.println("7. Refuel Vehicle");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter vehicle name:");
                        String name = scanner.nextLine();
                        System.out.print("Enter vehicle type (TRUCK/SHIP):");
                        String type = scanner.nextLine().toUpperCase();
                        String truckType = "";
                        if (type.equals("TRUCK")){
                            System.out.print("Enter truck type (BASIC/REEFER/TANKER):");
                            truckType = scanner.nextLine().toUpperCase();
                        }
                        System.out.print("Enter carrying capacity: ");
                        Double carryingCapacity = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter fuel capacity: ");
                        Double fuelCapacity = Double.parseDouble(scanner.nextLine());
                        if (type.equals("TRUCK")){
                            Truck truck = vehicleManager.addTruck(name, carryingCapacity, fuelCapacity, truckType);
                            System.out.println("Truck "+truck.getVehicleID() + " has been added");
                        } else if (type.equals("SHIP")){
                            Ship ship = vehicleManager.addShip(name, carryingCapacity, fuelCapacity);
                            System.out.println("Truck "+ship.getVehicleID() + " has been added");
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
                        this.previewAllVehicles();
                        System.out.print("Enter vehicle ID to view (v-<number>): ");
                        String id = scanner.nextLine();
                        if (vehicleManager.contains(id)) {
                            System.out.println(vehicleManager.getVehicleByID(id).get().getDetails());
                        } else {
                            System.out.println("No vehicle with such id");
                        }
                        System.out.print("\n1. Edit \n2. Return \nDo you want to:");
                        int input = Integer.parseInt(scanner.nextLine());
                        switch (input){
                            case 1:
                                Vehicle vehicle = vehicleManager.getVehicleByID(id).get();
                                System.out.print("Enter vehicle name:");
                                String name = scanner.nextLine();
                                if (!name.isEmpty()){vehicle.setName(name);};
                                if (vehicle instanceof Truck){
                                    System.out.print("Enter truck type (BASIC/REEFER/TANKER):");
                                    String truckType = scanner.nextLine().toUpperCase();
                                    if (!truckType.isEmpty()){((Truck)vehicle).setType(Truck.TruckType.valueOf(truckType));}
                                }
                                System.out.print("Enter carrying capacity: ");
                                String carryingCapacity = scanner.nextLine();
                                if(!carryingCapacity.isEmpty()){vehicle.setCarryingCapacity(Double.parseDouble(carryingCapacity));}
                                System.out.print("Enter fuel capacity: ");
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
                        this.previewAllVehicles();
                        System.out.print("Enter vehicle ID to remove (v-<number>): ");
                        String id = scanner.nextLine();
                        if (vehicleManager.contains(id)) {
                            vehicleManager.removeVehicle(id);
                        } else {
                            System.out.println("No vehicle with such id");
                        }
                    }catch (Exception e) {
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 4:
                    try {
                        this.previewAllVehicles();
                        System.out.print("Enter vehicle ID to load (v-<number>): ");
                        String id = scanner.nextLine();
                        if (vehicleManager.contains(id)) {
                            this.previewAllContainers();
                            System.out.print("Enter container ID to load (c-<number>): ");
                            String containerID = scanner.nextLine();
                            if (containerManager.contains(containerID)){
                                vehicleManager.getVehicleByID(id).get().loadContainer(containerManager.getContainerByID(containerID).get());
                                containerManager.removeContainer(containerID);
                                System.out.println("Loaded");
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
                        this.previewAllVehicles();
                        System.out.print("Enter vehicle ID to unload (v-<number>): ");
                        String id = scanner.nextLine();
                        if (vehicleManager.contains(id)) {
                            Vehicle vehicle = vehicleManager.getVehicleByID(id).get();
                            System.out.println("===== Container Preview =====");
                            System.out.printf("%-10s%-15s%-10s%n", "ID", "Type", "Weight (kg)");
                            for (Container container : vehicle.getContainerList()) {
                                System.out.printf("%-10s%-15s%-10.2f%n", container.getContainerID(), container.getType(), container.getWeight());
                            }
                            System.out.print("Enter container ID to unload (c-<number>): ");
                            String containerId = scanner.nextLine();
                            if (vehicle.containsContainer(containerId)) {
                                vehicle.unloadContainer(containerId);
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
                case 6:
                    try {

                    }catch (Exception e) {
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 7:
                    try {
                        this.previewAllVehicles();
                        System.out.print("Enter vehicle ID to refuel (v-<number>): ");
                        String id = scanner.nextLine();
                        if (vehicleManager.contains(id)) {
                            vehicleManager.getVehicleByID(id).get().refuel();
                            System.out.println("Vehicle refueled.");
                        } else {
                            System.out.println("No vehicle with such id");
                        }
                    }catch (Exception e) {
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 8:
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }
    private void displayPortPreview() {
        List<Port> ports = portsManager.getAllPorts();

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

    private void previewAllContainers() {
        List<Container> containers = containerManager.getAllContainers();
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

    public void previewAllVehicles() {
        List<Vehicle> vehicles = vehicleManager.getAllVehicles();

        System.out.println("===== Vehicle Preview =====");
        System.out.println("ID\tName\tType");
        for (Vehicle vehicle : vehicles) {
            String row = vehicle.getVehicleID() + "\t" + vehicle.getName() + "\t";
            if (vehicle instanceof Truck){
                row += "TRUCK | " + ((Truck) vehicle).getType();
            } else if (vehicle instanceof Ship) {
                row += "SHIP";
            }
            System.out.println(row);
        }
    }

    public static void main(String[] args) {
        UserMenu userMenu = new UserMenu();
        userMenu.view();
    }
}