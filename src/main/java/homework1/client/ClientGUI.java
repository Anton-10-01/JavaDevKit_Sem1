package homework1.client;

import homework1.server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private boolean isConnect;
    private JTextArea log = new JTextArea();
    private JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private JButton btnLogin = new JButton("login");
    private JTextField textIP = new JTextField("127.0.0.1");
    private JTextField textPort = new JTextField("8189");
    private JTextField textLogin = new JTextField("Petya");
    private JPasswordField textPassword = new JPasswordField("1234");

    private JPanel panelBottom = new JPanel(new BorderLayout());
    private JTextField tfMessage = new JTextField();
    private JLabel nameClient = new JLabel();
    private JButton btnSend = new JButton("Send");

    public ClientGUI(ServerWindow serverWindow){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        //setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Chat Client");

        panelTop.add(textIP);
        panelTop.add(textPort);
        panelTop.add(textLogin);
        panelTop.add(textPassword);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(serverWindow.isConect()) {
                    serverWindow.logMessage(textLogin.getText() + ": подключился к чату");
                    serverWindow.addClient(ClientGUI.this);
                    nameClient.setText(textLogin.getText());
                    isConnect = true;
                }
                addTextTry(serverWindow);
            }
        });

        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(nameClient, BorderLayout.WEST);
        panelBottom.add(btnSend, BorderLayout.EAST);


        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isConnect && serverWindow.isConect()) {
                    log.append(logMessage() + "\n");
                    serverWindow.sendMessage(logMessage(), ClientGUI.this);
                    tfMessage.setText(null);
                } else if (!isConnect){
                    log.append("Вы не подключены к чату!\n");
                } else {
                    log.append("Сервер остановлен. Невозможно отправить сообщение!\n");
                }
            }
        });

        add(panelBottom, BorderLayout.SOUTH);

        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        add(scrollLog);

        setVisible(true);
    }

    private void addTextTry(ServerWindow serverWindow){
        log.append(connectionServer(serverWindow));
        setVisible(true);
    }
    private String connectionServer(ServerWindow serverWindow){
        if(serverWindow.isConect()) {
            return "Вы успешно подключились!\n";
        } else {
            return "Подключение не удалось!\n";
        }
    }
    public String logMessage(){
        return textLogin.getText() + ": " + tfMessage.getText();
    }
    public void receiveMessage(String message) {
        log.append(message + "\n");
    }

}
