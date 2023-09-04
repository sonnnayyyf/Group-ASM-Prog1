package Vehicle;

public class Truck {
    private TruckType type;

    public enum TruckType
    {
        BASIC,
        REEFER,
        TANKER
    }

    public Truck(String vehicleID, String name, double currentFuel, double carryingCapacity, double fuelCapacity, TruckType type)
    {
        super();
        this.type = type;
    }

    public TruckType getType()
    {
        return type;
    }

    public void setType(TruckType type)
    {
        this.type = type;
    }

    // Enum definition for TruckType inside the Truck class
    public boolean canUtilizePort(Port destinationPort)
    {

    }
}
