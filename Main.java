import Controller.PortController;
import Model.Port;
import Model.PortManager;
import View.AuthenticationMenu;
import View.PortManagerMenu;

import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        PortManagerMenu authenticationMenu = new PortManagerMenu(new PortManager("1", "a","a","a", "a", "a", "a", "p-2"));
        authenticationMenu.view();
    }
}