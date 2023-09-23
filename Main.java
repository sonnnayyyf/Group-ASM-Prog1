import View.AuthenticationSystem;

import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        AuthenticationSystem authenticationSystem = new AuthenticationSystem();
        authenticationSystem.view();
    }
}