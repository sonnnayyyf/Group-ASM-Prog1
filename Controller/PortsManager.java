package Controller;

import Model.Port;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class PortsManager {
    private static PortsManager instance;
    private ArrayList<Port> listOfPorts = new ArrayList<Port>();
    public static PortsManager getInstance(){
        if (instance == null) {
            instance = new PortsManager();
        }
        return instance;
    }
    public ArrayList<Port> getAllPorts(){
        return this.listOfPorts;
    }
    public Optional<Port> getPortByID(String portID){
        return this.listOfPorts.stream()
                .filter(port -> port.getPortID().equals(portID))
                .findFirst();
    }
    public boolean contains(String portID){
        return this.listOfPorts.stream().anyMatch(port -> port.getPortID().equals(portID));
    }
    public int getNumberOfPorts(){
        return this.listOfPorts.size();
    }
    public boolean addPort(Port port){
        if (!this.contains(port.getPortID())) {
            this.listOfPorts.add(port);
            this.savePortsToFile();
            return true;
        }
        return false;
    }
    public boolean removePort(String portID){
        if (listOfPorts.removeIf(port -> port.getPortID().equals(portID))) {
            this.savePortsToFile();
            return true;
        }
        return false;
    }
    public void savePortsToFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("dataFile/ports.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(listOfPorts);

            System.out.println("Ports have been saved to " + "dataFile/ports.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadPortsFromFile() {
        try (FileInputStream fileInputStream = new FileInputStream("dataFile/ports.ser");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            ArrayList<Port> loadedPorts = (ArrayList<Port>) objectInputStream.readObject();

            listOfPorts = loadedPorts;

            System.out.println("Ports have been loaded from " + "dataFile/ports.ser");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //Used for testing
        PortsManager portsManager = PortsManager.getInstance();

        Port port1 = new Port("Port 1", 40.7128, -74.0060, 1000, true);
        Port port2 = new Port("Port 2", 34.0522, -118.2437, 800, true);
        Port port3 = new Port("Port 3", 51.5074, -0.1278, 1200, false);
        Port port4 = new Port("Port 4", 48.8566, 2.3522, 600, true);
        Port port5 = new Port("Port 5", 35.682839, 139.759455, 1500, true);

        portsManager.addPort(port1);
        portsManager.addPort(port2);
        portsManager.addPort(port3);
        portsManager.addPort(port4);
        portsManager.addPort(port5);

        portsManager.getAllPorts().clear();

        portsManager.loadPortsFromFile();

        ArrayList<Port> loadedPorts = portsManager.getAllPorts();

        for (Port port : loadedPorts) {
            System.out.println("Loaded Port: " + port.getPortID() + " - " + port.getName());
        }
    }
}
