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