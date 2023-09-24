package View;

import Controller.*;
import Model.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AdminMenu implements Menu{
    private Admin admin;
    private PortController portController = PortController.getInstance();
    private ContainerController containerController = ContainerController.getInstance();
    private VehicleController vehicleController = VehicleController.getInstance();
    private UserController userController = UserController.getInstance();
    private TripController tripController = TripController.getInstance();
    private Scanner scanner = new Scanner(System.in);
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");


    public AdminMenu(Admin admin){

        this.admin = admin;
        portController.loadPortsFromFile();
        containerController.loadContainersFromFile();
        vehicleController.loadVehiclesFromFile();
        tripController.loadTripsFromFile();
    }
    // Display the homepage for admin when he/she log in successfully
    public void view(){
        int choice;
        do {
            System.out.println("\n================================================= HOMEPAGE =================================================");
            System.out.println("1. Port Managers");
            System.out.println("2. Ports");
            System.out.println("3. Containers");
            System.out.println("4. Vehicles");
            System.out.println("5. Trips");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1: userMenu(); break;
                case 2: portMenu(); break;
                case 3: containerMenu(); break;
                case 4: vehicleMenu(); break;
                case 5: tripMenu(); break;
            }
        } while (choice != 6);
    }

    private void userMenu(){
        int choice;
        do {
            System.out.println("===== Admin Menu =====");
            System.out.println("1. Add Port Manager");
            System.out.println("2. Remove User");
            System.out.println("3. View All Users");
            System.out.println("4. Return");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    try{
                        this.displayPortPreview();
                        System.out.println("=====Create new user=====");
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter address: ");
                        String address = scanner.nextLine();
                        System.out.print("Enter phone: ");
                        String phone = scanner.nextLine();
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        System.out.print("Enter port id (p-<number>): ");
                        String port = scanner.nextLine();
                        userController.createManager(name, email, address, phone, username, password, port);
                    } catch (Exception e){
                        System.out.println("An error occurred. Please try again");
                    }
                    break;
                case 2:
                    try{
                        System.out.print("Enter user to remove: ");
                        String username = scanner.nextLine();
                        userController.removeUser(username);
                    } catch (Exception e){
                        System.out.println("An error occurred. Please try again");
                    }
                    break;
                case 3:
                    this.printUserTable(userController.getAllManagers());
                    break;
                case 4:
                    System.out.println("Returning...");
            }
        } while (choice!=4);
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

                        portController.addPort(name, latitude, longitude, storingCapacity, landingAvailability);
                        System.out.println("Port has been added");

                    } catch (Exception e){
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 2:
                    try {
                        this.displayPortPreview();
                        System.out.print("Enter port ID to remove (p-<number>): ");
                        String id = scanner.nextLine();
                        if (portController.removePort(id)) {
                            System.out.println("Removed port " + id + " from the system");
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
                        if (portController.contains(id)) {
                            Optional<Port> port = portController.getPortByID(id);
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
            System.out.println("===== Container Manager Menu =====");
            System.out.println("1. View|Edit|Remove Container");
            System.out.println("2. Return");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {

                case 1:
                    try {
                        this.previewContainers();
                        System.out.print("Enter container id (c-<number>): ");
                        String containerID = scanner.nextLine();
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
                                    containerController.removeContainer(containerID);
                                    System.out.println("Container " + containerID + " has been removed");
                                }
                            }
                    }catch (Exception e){
                        System.out.println("An error occurred, please try again.");
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 2);
    }

    private void vehicleMenu() {
        int choice;
        do {
            System.out.println("===== Vehicle Manager Menu =====");
            System.out.println("1. View & Update or Remove Vehicle");
            System.out.println("2. Return");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    try {
                        this.previewVehicles();
                        System.out.print("Enter vehicle ID to view (tr-<number>/sh-<number>): ");
                        String id = scanner.nextLine();
                        System.out.println(vehicleController.getVehicleByID(id).get().getDetails());
                        System.out.print("\n1. Edit \n2. Remove \n3. Return \nDo you want to: ");
                        int input = Integer.parseInt(scanner.nextLine());
                        switch (input){
                            case 1:
                                Vehicle vehicle = vehicleController.getVehicleByID(id).get();
                                System.out.print("Enter vehicle name (Enter to skip): ");
                                String name = scanner.nextLine();
                                if (!name.isEmpty()){vehicle.setName(name);};
                                if (vehicle instanceof Truck){
                                    System.out.print("Enter truck type (BASIC/REEFER/TANKER/Enter to skip): ");
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
                            case 2: {
                                vehicleController.removeVehicle(id);
                                System.out.println("Vehicle " + id + " has been removed");
                            }
                            case 3:
                                break;
                        }
                    }catch (Exception e){
                        System.out.println("An error occurred, please try again.");
                    }
                    break;
                case 2:
                    System.out.println("Returning...");
                    break;
            }
        } while (choice != 2);
    }

    public void tripMenu(){
        int choice;
        do {
            System.out.println("===== Trip Menu =====");
            System.out.println("1. View all trip");
            System.out.println("2. View trip in range");
            System.out.println("3. View trip on a date");
            System.out.println("4. Return");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    try {
                        printTripTable(tripController.getListOfTrips());
                    } catch (Exception e){
                        System.out.println("An error occurred: "+e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("Enter start date (dd-mm-yyyy): ");
                        Date startDate = sdf.parse(scanner.nextLine());
                        System.out.println("Enter end date (dd-mm-yyyy): ");
                        Date endDate = sdf.parse(scanner.nextLine());
                        printTripTable(tripController.getTripsInRange(startDate,endDate));
                    } catch (Exception e){
                        System.out.println("An error occurred: "+e.getMessage());
                    }
                case 3:
                    try {
                        System.out.println("Enter date (dd-mm-yyyy): ");
                        Date date = sdf.parse(scanner.nextLine());
                        printTripTable(tripController.getTripsOnDate(date));
                    } catch (Exception e){
                        System.out.println("An error occurred: "+e.getMessage());
                    }
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
        List<Container> containers = this.containerController.getAllContainers();
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
    public void previewVehicles() {
        List<Vehicle> vehicles = this.vehicleController.getAllVehicles();
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
    public void printUserTable(List<User> users) {
        // Define the table header
        System.out.printf("%-10s %-20s %-30s %-30s %-15s %-15s%n",
                "User ID", "Name", "Email", "Address", "Phone", "Username", "Port");

        // Loop through the list of users and print each user as a row in the table
        for (User user : users) {
            System.out.printf("%-10s %-20s %-30s %-30s %-15s %-15s%n %-15s%n",
                    user.getuID(),
                    user.getName(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getPhone(),
                    user.getUsername());
                    ((PortManager) user).getPort();
        }
    }
}


