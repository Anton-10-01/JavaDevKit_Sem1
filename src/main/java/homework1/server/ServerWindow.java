package homework1.server;

import homework1.client.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class ServerWindow extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 407;
    private boolean connect = false;
    private JButton btnStart, btnStop;
    private JTextArea textArea = new JTextArea(20, 1);
    private JPanel panelBattom = new JPanel(new GridLayout(1, 2));
    private List<ClientGUI> clients = new ArrayList<>();
    public ServerWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        setTitle("Chat server");
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("Сервер запущен!\n");
                saveLogText("Сервер запущен!");
                connect = true;
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("Сервер отключен!\n");
                saveLogText("Сервер отключен!");
                connect = false;
            }
        });

        panelBattom.add(btnStart);
        panelBattom.add(btnStop);

        add(panelBattom, BorderLayout.SOUTH);

        textArea.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(textArea);
        add(scrollLog);

        setVisible(true);
    }

    public boolean isConect() {
        return connect;
    }
    public void addClient(ClientGUI client){
        clients.add(client);
    }
    public void sendMessage(String message, ClientGUI sender){
        textArea.append(message + "\n");
        for(ClientGUI client: clients){
            if(client != sender){
                client.receiveMessage(message);
            }
        }
        saveLogText(message);
    }
    public void logMessage(String message){
        textArea.append(message + "\n");
        saveLogText(message);
    }
    public void saveLogText(String message) {
        String nameLogFile = "./src/main/java/homework1/server/logFile.txt";
        try(PrintWriter printWriter = new PrintWriter(new FileWriter(nameLogFile, true))){
            printWriter.write(message + "\n");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
