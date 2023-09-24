import Controller.PortController;
import Model.Port;
import View.AuthenticationMenu;

import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        AuthenticationMenu authenticationMenu = new AuthenticationMenu();
        authenticationMenu.view();
    }
}