package Trip;


import java.text.SimpleDateFormat;
import java.util.Date;
import Vehicle.Vehicle;
import Port.Port;

public class Trip {
    private Vehicle vehicle;
    private Date departureDate;
    private Date arrivalDate;
    private Port departurePort;

    private Port arrivalPort;
    private TripStatus status;

    public enum TripStatus {
        ONGOING,
        COMPLETED
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(Port departurePort) {
        this.departurePort = departurePort;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public void setArrivalPort(Port arrivalPort) {
        this.arrivalPort = arrivalPort;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public Trip(Vehicle vehicle, Date departureDate, Date arrivalDate, Port departurePort, Port arrivalPort, TripStatus status) {
        this.vehicle = vehicle;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = status;
    }

    public void displayTripDetails() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("Trip Details:");
        System.out.println("Vehicle Information: " + vehicle);
        System.out.println("Departure Date: " + dateFormat.format(departureDate));
        System.out.println("Arrival Date: " + dateFormat.format(arrivalDate));
        System.out.println("Departure Port: " + departurePort);
        System.out.println("Arrival Port: " + arrivalPort);
        System.out.println("Status: " + status);
    }





}
