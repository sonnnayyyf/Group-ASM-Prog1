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
package Model;


import Controller.VehicleController;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Trip implements Serializable {
    private String tripID;
    private String vehicleID;
    private Date departureDate;
    private Date arrivalDate;
    private String departurePort;

    private String arrivalPort;
    private TripStatus status;

    public enum TripStatus {
        ONGOING,
        COMPLETED
    }
    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }
    public String getVehicleID() {
        return this.vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
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

    public String getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(String departurePort) {
        this.departurePort = departurePort;
    }

    public String getArrivalPort() {
        return arrivalPort;
    }

    public void setArrivalPort(String arrivalPort) {
        this.arrivalPort = arrivalPort;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public Trip(String tripID, String vehicleID, Date departureDate, Date arrivalDate, String departurePort, String arrivalPort, TripStatus status) {
        this.tripID = tripID;
        this.vehicleID = vehicleID;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = status;
    }

    public String getTripDetails() {
        StringBuilder builder = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        builder.append("Trip Details:").append("\n");
        builder.append("Vehicle: " + VehicleController.getInstance().getVehicleByID(vehicleID)).append("\n");
        builder.append("Departure Date: " + dateFormat.format(departureDate)).append("\n");
        builder.append("Arrival Date: " + dateFormat.format(arrivalDate)).append("\n");
        builder.append("Departure Port: " + departurePort).append("\n");
        builder.append("Arrival Port: " + arrivalPort).append("\n");
        builder.append("Status: " + status);
        return builder.toString();
    }

}
