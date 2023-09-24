/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2023B
  Assessment: Group Assessment
  Author: Group 30
  ID: s3978145 - Phan Nguyen Viet Nhan
s3962514-Duong Viet Hoang
s3978450-Tran Hoang Son
s3977767-Tran Nhat Minh

  Acknowledgement:

*/
package Controller;

import Model.Container;
import Model.Ship;
import Model.Truck;
import Model.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VehicleController {
    private static VehicleController instance;
    private List<Vehicle> listOfVehicles;
    private int lastAssignedNumber = 0;

    private VehicleController() {
        listOfVehicles = new ArrayList<>();
    }

    public static VehicleController getInstance() {
        if (instance == null) {
            instance = new VehicleController();
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

    public Truck addTruck(String name, double carryingCapacity, double fuelCapacity, String type, String portID) {
        Truck vehicle = new Truck("tr-" + this.generateUniqueVehicleID(), name, carryingCapacity, fuelCapacity, Truck.TruckType.valueOf(type),portID);

        if (vehicle != null) {
            listOfVehicles.add(vehicle);
            this.saveVehiclesToFile();
        }

        return vehicle;
    }

    public Ship addShip(String name, double carryingCapacity, double fuelCapacity, String portID) {
        Ship vehicle = new Ship("sh-" + this.generateUniqueVehicleID(), name, carryingCapacity, fuelCapacity,portID);

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

    public List<Vehicle> getAllVehiclesAtPort(String portID){
        return this.listOfVehicles.stream().filter(vehicle -> vehicle.getCurrentPort().equals(portID)).collect(Collectors.toCollection(ArrayList::new));
    }

    public void updateVehicle(String id, Vehicle vehicle){
        if (this.contains(id)){
            this.listOfVehicles.removeIf(vehicle1 -> vehicle1.getVehicleID().equals(id));
            this.listOfVehicles.add(vehicle);
            this.saveVehiclesToFile();
        }
    }
    private synchronized String generateUniqueVehicleID() {
        int maxAssignedNumber = 0;
        for (Vehicle vehicle : listOfVehicles) {
            String vehicleID = vehicle.getVehicleID();
            if (vehicleID.startsWith("sh-")||(vehicleID.startsWith("tr-"))) {
                try {
                    int number = Integer.parseInt(vehicleID.substring(3));
                    maxAssignedNumber = Math.max(maxAssignedNumber, number);
                } catch (NumberFormatException e) {
                }
            }
        }
        return String.valueOf(maxAssignedNumber+1);
    }

//    public static void main(String[] args) {
//        VehicleController.getInstance().addTruck("HONDA", 876, 344, "BASIC");
//    }
}


