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

import Model.Port;
import Model.Trip;
import Model.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

public class TripController {
    private static TripController instance;
    private ArrayList<Trip> listOfTrips = new ArrayList<Trip>();
    public static TripController getInstance(){
        if (instance == null) {
            instance = new TripController();
        }
        return instance;
    }

    public ArrayList<Trip> getListOfTrips() {
        this.updateTripHistory();
        return listOfTrips;
    }
    public void addTrip(Trip trip){
        listOfTrips.add(trip);
        this.updateTripHistory();
        this.saveTripsToFile();
    }

    public void removeTripByVehicleID(String id) {
        listOfTrips.removeIf(trip -> trip.getVehicleID().equals(id));
    }
    public ArrayList<Trip> getTripsOfPort(String portID) {
        this.updateTripHistory();
        return listOfTrips.stream()
                .filter(trip -> trip.getArrivalPort().equals(portID) || trip.getDeparturePort().equals(portID))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void createTrip(String vehicleID, Date departureDate, Date arrivalDate, String departurePort, String arrivalPort, Trip.TripStatus status){
        this.updateTripHistory();
        this.listOfTrips.add(new Trip("t-"+generateUniqueTripID(), vehicleID, departureDate, arrivalDate, departurePort, arrivalPort, status));
        this.saveTripsToFile();
    }
    public ArrayList<Trip> getTripsFromPort(String portID) {
        this.updateTripHistory();
        return listOfTrips.stream()
                .filter(trip -> trip.getArrivalPort().equals(portID))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Trip> getTripsToPort(String portID) {
        this.updateTripHistory();
        return listOfTrips.stream()
                .filter(trip -> trip.getDeparturePort().equals(portID))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Trip> getTripsInRange(Date dateA, Date dateB) {
        this.updateTripHistory();
        return listOfTrips.stream()
                .filter(trip -> trip.getArrivalDate().after(dateA) && trip.getDepartureDate().before(dateB))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Trip> getTripsOnDate(Date date) {
        this.updateTripHistory();
        return listOfTrips.stream()
                .filter(trip -> trip.getArrivalDate().equals(date) || trip.getDepartureDate().equals(date))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Trip> getTripsInRangeAtPort(String portId, Date dateA, Date dateB) {
        this.updateTripHistory();
        return this.getTripsOfPort(portId).stream()
                .filter(trip -> trip.getArrivalDate().after(dateA) && trip.getDepartureDate().before(dateB))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Trip> getTripsOnDateAtPort(String portId, Date date) {
        this.updateTripHistory();
        return this.getTripsOfPort(portId).stream()
                .filter(trip -> trip.getArrivalDate().equals(date) || trip.getDepartureDate().equals(date))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public void updateTripHistory() {
        Date currentDate = new Date();
        long sevenDaysInMillis = 7L * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        Date sevenDaysAgo = new Date(currentDate.getTime() - sevenDaysInMillis);
        this.listOfTrips = this.listOfTrips.stream()
                .filter(trip -> trip.getArrivalDate().after(sevenDaysAgo))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public void saveTripsToFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("dataFile/trips.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(listOfTrips);

            System.out.println("Vehicles have been saved to " + "dataFile/trips.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTripsFromFile() {
        try (FileInputStream fileInputStream = new FileInputStream("dataFile/trips.ser");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            ArrayList<Trip> loadedTrips = (ArrayList<Trip>) objectInputStream.readObject();

            listOfTrips = loadedTrips;

            System.out.println("Vehicles have been loaded from " + "dataFile/trips.ser");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public double getFuelDailyConsumption(String portID){
        List<Trip> tripList = this.getTripsOfPort(portID);
        double totalFuelConsumption = 0;
        long duration = 0;
        for (Trip trip:
             tripList) {
            Vehicle vehicle = VehicleController.getInstance().getVehicleByID(trip.getVehicleID()).get();
            Port departurePort = PortController.getInstance().getPortByID(trip.getDeparturePort()).get();
            Port arrivalPort = PortController.getInstance().getPortByID(trip.getArrivalPort()).get();
            totalFuelConsumption+=vehicle.calculateFuelConsumption(departurePort.calculateDistance(arrivalPort));
            duration += trip.getArrivalDate().getTime() - trip.getDepartureDate().getTime();
        }
        duration = duration / (24 * 60 * 60 * 1000L);
        return totalFuelConsumption / duration;
    }
    private synchronized String generateUniqueTripID() {
        int maxAssignedNumber = 0;
        for (Trip trip : listOfTrips) {
            String tripID = trip.getTripID();
            if (tripID.startsWith("trip-")) {
                try {
                    int number = Integer.parseInt(tripID.substring(5));
                    maxAssignedNumber = Math.max(maxAssignedNumber, number);
                } catch (NumberFormatException e) {
                }
            }
        }
        return String.valueOf(maxAssignedNumber+1);
    }
}