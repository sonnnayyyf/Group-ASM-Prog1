package Controller;

import Model.Ship;
import Model.Truck;
import Model.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleManager {
    private static VehicleManager instance;
    private List<Vehicle> listOfVehicles;
    private int lastAssignedNumber = 0;

    private VehicleManager() {
        listOfVehicles = new ArrayList<>();
    }

    public static VehicleManager getInstance() {
        if (instance == null) {
            instance = new VehicleManager();
        }
        return instance;
    }

    public List<Vehicle> getAllVehicles() {
        return listOfVehicles;
    }

    public Optional<Vehicle> getVehicleByID(String vehicleID) {
        return this.listOfVehicles.stream()
                .filter(vehicle -> vehicle.getVehicleID().equals(vehicleID))
                .findFirst();
    }

    public boolean contains(String vehicleID) {
        return this.listOfVehicles.stream().anyMatch(vehicle -> vehicle.getVehicleID().equals(vehicleID));
    }

    public Truck addTruck(String name, double carryingCapacity, double fuelCapacity, String type) {
        Truck vehicle = new Truck(this.generateUniqueVehicleID(), name, carryingCapacity, fuelCapacity, Truck.TruckType.valueOf(type));

        if (vehicle != null) {
            listOfVehicles.add(vehicle);
            this.saveVehiclesToFile();
        }

        return vehicle;
    }

    public Ship addShip(String name, double carryingCapacity, double fuelCapacity) {
        Ship vehicle = new Ship(this.generateUniqueVehicleID(), name, carryingCapacity, fuelCapacity);

        if (vehicle != null) {
            listOfVehicles.add(vehicle);
            this.saveVehiclesToFile();
        }

        return vehicle;
    }

    public boolean removeVehicle(String vehicleID) {
        if (listOfVehicles.removeIf(vehicle -> vehicle.getVehicleID().equals(vehicleID))) {
            this.saveVehiclesToFile();
            return true;
        }
        return false;
    }

    public void saveVehiclesToFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("dataFile/vehicles.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(listOfVehicles);

            System.out.println("Vehicles have been saved to " + "dataFile/vehicles.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadVehiclesFromFile() {
        try (FileInputStream fileInputStream = new FileInputStream("dataFile/vehicles.ser");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            ArrayList<Vehicle> loadedVehicles = (ArrayList<Vehicle>) objectInputStream.readObject();

            listOfVehicles = loadedVehicles;

            System.out.println("Vehicles have been loaded from " + "dataFile/vehicles.ser");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private synchronized String generateUniqueVehicleID() {
        int maxAssignedNumber = 0;
        for (Vehicle vehicle : listOfVehicles) {
            String vehicleID = vehicle.getVehicleID();
            if (vehicleID.startsWith("v-")) {
                try {
                    int number = Integer.parseInt(vehicleID.substring(2));
                    maxAssignedNumber = Math.max(maxAssignedNumber, number);
                } catch (NumberFormatException e) {
                }
            }
        }
        return "v-" + String.valueOf(maxAssignedNumber+1);
    }

    public static void main(String[] args) {
        VehicleManager.getInstance().addTruck("HONDA", 876, 344, "BASIC");
    }
}


