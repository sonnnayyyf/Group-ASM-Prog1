package Trip;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Date;

public class TripsManager {
    private static TripsManager instance;
    private ArrayList<Trip> listOfTrips;
    public static TripsManager getInstance(){
        if (instance == null) {
            instance = new TripsManager();
        }
        return instance;
    }

    public ArrayList<Trip> getListOfTrips() {
        return listOfTrips;
    }
    public void addTrip(Trip trip){
        listOfTrips.add(trip);
    }
    public ArrayList<Trip> getTripsOfPort(String portID) {
        return listOfTrips.stream()
                .filter(trip -> trip.getArrivalPort().getPortID().equals(portID) || trip.getDeparturePort().getPortID().equals(portID))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Trip> getTripsFromPort(String portID) {
        return listOfTrips.stream()
                .filter(trip -> trip.getArrivalPort().getPortID().equals(portID))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Trip> getTripsToPort(String portID) {
        return listOfTrips.stream()
                .filter(trip -> trip.getDeparturePort().getPortID().equals(portID))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Trip> getTripsInRange(Date dateA, Date dateB) {
        return listOfTrips.stream()
                .filter(trip -> trip.getArrivalDate().after(dateA) && trip.getDepartureDate().before(dateB))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Trip> getTripsOnDate(Date date) {
        return listOfTrips.stream()
                .filter(trip -> trip.getArrivalDate().equals(date) || trip.getDepartureDate().equals(date))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}