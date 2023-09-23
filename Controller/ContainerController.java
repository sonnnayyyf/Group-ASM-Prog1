package Controller;

import Model.Container;
import Model.Trip;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContainerController {
    private static ContainerController instance;
    private List<Container> listOfContainers;
    private int lastAssignedNumber = 0;

    private ContainerController() {
        listOfContainers = new ArrayList<>();
    }

    public static ContainerController getInstance() {
        if (instance == null) {
            instance = new ContainerController();
        }
        return instance;
    }

    public List<Container> getAllContainers() {
        return listOfContainers;
    }
    public Optional<Container> getContainerByID(String containerID){
        return this.listOfContainers.stream()
                .filter(port -> port.getContainerID().equals(containerID))
                .findFirst();
    }
    public boolean contains(String containerID){
        return this.listOfContainers.stream().anyMatch(container -> container.getContainerID().equals(containerID));
    }

    public Container addContainer(double weight, Container.ContainerType type) {
        Container container = new Container("c-"+this.generateUniqueContainerID(),weight,type);
        listOfContainers.add(container);
        this.saveContainersToFile();
        return container;
    }

    public boolean removeContainer(String containerID) {
        if (listOfContainers.removeIf(container -> container.getContainerID().equals(containerID))){
            this.saveContainersToFile();
            return true;
        }
        return false;
    }
    public void saveContainersToFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("dataFile/containers.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(listOfContainers);

            System.out.println("Containers have been saved to " + "dataFile/containers.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadContainersFromFile() {
        try (FileInputStream fileInputStream = new FileInputStream("dataFile/containers.ser");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            ArrayList<Container> loadedContainers = (ArrayList<Container>) objectInputStream.readObject();

            listOfContainers = loadedContainers;

            System.out.println("Containers have been loaded from " + "dataFile/containers.ser");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void updateContainer(String id, Container container){
        if (this.contains(id)){
            this.listOfContainers.removeIf(container1 -> container.getContainerID().equals(id));
            this.listOfContainers.add(container);
            this.saveContainersToFile();
        }
    }
    private synchronized String generateUniqueContainerID() {
        int maxAssignedNumber = 0;
        for (Container container : listOfContainers) {
            String containerID = container.getContainerID();
            if (containerID.startsWith("c-")) {
                try {
                    int number = Integer.parseInt(containerID.substring(2));
                    maxAssignedNumber = Math.max(maxAssignedNumber, number);
                } catch (NumberFormatException e) {
                }
            }
        }
        return String.valueOf(maxAssignedNumber+1);
    }

    public static void main(String[] args) {
       ContainerController containerController = ContainerController.getInstance();
       containerController.addContainer(78.9, Container.ContainerType.LIQUID);
    }
}

