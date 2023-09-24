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