package homework1;

import homework1.client.ClientGUI;
import homework1.server.ServerWindow;

public class Program {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();
        new ClientGUI(serverWindow);
        new ClientGUI(serverWindow);
    }
}
