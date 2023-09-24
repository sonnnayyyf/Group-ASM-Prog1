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

public class FuelRate {

    // Class attributes
    private double rateByShip;
    private double rateByTruck;

    public FuelRate(double rateByShip, double rateByTruck){
        this.rateByShip = rateByShip;
        this.rateByTruck = rateByTruck;
    }

    // Getters and Setters
    public double getRateByShip() {
        return rateByShip;
    }
    public void setRateByShip(double rateByShip) {
        this.rateByShip = rateByShip;
    }
    public double getRateByTruck() {
        return rateByTruck;
    }
    public void setRateByTruck(double rateByTruck) {
        this.rateByTruck = rateByTruck;
    }
}