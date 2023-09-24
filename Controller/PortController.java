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
import Model.Port;
import Model.Vehicle;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PortController {
    private static PortController instance;
    private ArrayList<Port> listOfPorts = new ArrayList<Port>();
    public static PortController getInstance(){
        if (instance == null) {
            instance = new PortController();
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
    public void addPort(String name, double latitude, double longitude, int storingCapacity, boolean landingAbility){
        Port port = new Port("p-"+generateUniquePortID(),name, latitude, longitude, storingCapacity, landingAbility);
        this.listOfPorts.add(port);
        savePortsToFile();
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
    public List<Container> getContainersAtPort(String portID) {
        // Check if the portID exists in your data structure (this.contains).
        if (!this.contains(portID)) {
            // Return an empty list if the port doesn't exist.
            return Collections.emptyList();
        }

        // Get the port associated with the portID.
        Port port = this.getPortByID(portID).orElse(null);

        // Check if the port is null or if it has no containers.
        if (port == null || port.getListOfContainer().isEmpty()) {
            return Collections.emptyList();
        }

        // Map container IDs to Container objects and collect them into a list.
        return port.getListOfContainer().stream()
                .map(containerID -> ContainerController.getInstance().getContainerByID(containerID).orElse(null))
                .filter(Objects::nonNull) // Filter out null containers (if any).
                .collect(Collectors.toList());
    }

    public void updatePort (String id, Port port){
        if (this.contains(id)){
            this.listOfPorts.removeIf(port1 -> port1.getPortID().equals(id));
            this.listOfPorts.add(port);
            this.savePortsToFile();
        }
    }

    private synchronized String generateUniquePortID() {
        int maxAssignedNumber = 0;
        for (Port port : listOfPorts) {
            String portID = port.getPortID();
            if (portID.startsWith("p-")) {
                try {
                    int number = Integer.parseInt(portID.substring(2));
                    maxAssignedNumber = Math.max(maxAssignedNumber, number);
                } catch (NumberFormatException e) {
                }
            }
        }
        return String.valueOf(maxAssignedNumber+1);
    }
}
