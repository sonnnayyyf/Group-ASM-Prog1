package Model;

public class PortManager extends User{
    private String port;

    public PortManager(String uID, String name, String email, String address, String phone, String userName, String password, String port){
        super(uID, name, email, address, phone, userName, password);
        this.port = port;
    }
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

}