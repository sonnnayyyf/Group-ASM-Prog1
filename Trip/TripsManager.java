package Trip;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Date;

public class TripsManager {
    private static TripsManager instance;
    private ArrayList<Trip> listOfTrips = new ArrayList<Trip>();
    public static TripsManager getInstance(){
        if (instance == null) {
            instance = new TripsManager();
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
    }
    public ArrayList<Trip> getTripsOfPort(String portID) {
        this.updateTripHistory();
        return listOfTrips.stream()
                .filter(trip -> trip.getArrivalPort().getPortID().equals(portID) || trip.getDeparturePort().getPortID().equals(portID))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Trip> getTripsFromPort(String portID) {
        this.updateTripHistory();
        return listOfTrips.stream()
                .filter(trip -> trip.getArrivalPort().getPortID().equals(portID))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Trip> getTripsToPort(String portID) {
        this.updateTripHistory();
        return listOfTrips.stream()
                .filter(trip -> trip.getDeparturePort().getPortID().equals(portID))
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
    public void updateTripHistory() {
        Date currentDate = new Date();
        long sevenDaysInMillis = 7L * 24 * 60 * 60 * 1000; // 7 days in milliseconds
        Date sevenDaysAgo = new Date(currentDate.getTime() - sevenDaysInMillis);
        this.listOfTrips = this.listOfTrips.stream()
                .filter(trip -> trip.getArrivalDate().after(sevenDaysAgo))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}