package geekbrainsChat.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller implements Initializable {

    @FXML
    TextArea chatArea;
    @FXML
    TextField msgField;
    @FXML
    HBox upperPan;
    @FXML
    HBox bottomPan;
    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;
    @FXML
    ListView <String> clientList;

    final String IP = "localhost";
    final int PORT = 8189;
    Socket clientSocket;
    DataInputStream in;
    DataOutputStream out;
    String nick;
    int hisToShow = 10;

    private AtomicBoolean isAuthorised = new AtomicBoolean();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setAuthorised(false);
        try {
            clientSocket = new Socket(IP, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread timer = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Thread.sleep(120000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!isAuthorised.get()) {
                    try {
                        clientSocket.close();
                        System.out.println("Socket closed by timeout");
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Socket closing failed");
                    }
                }
            }
        });
        timer.setDaemon(true);
        timer.start();
    }

    public void setAuthorised(boolean isAuthorised) {
        this.isAuthorised.set(isAuthorised);
        if(!isAuthorised){
            upperPan.setVisible(true);
            upperPan.setManaged(true);
            bottomPan.setVisible(false);
            bottomPan.setManaged(false);
            clientList.setVisible(false);
            clientList.setManaged(false);
        } else {
            upperPan.setVisible(false);
            upperPan.setManaged(false);
            bottomPan.setVisible(true);
            bottomPan.setManaged(true);
            clientList.setVisible(true);
            clientList.setManaged(true);
        }
    }

    public void tryToAuth(){
        if(isNotConnected()) {
            try {
                clientSocket = new Socket(IP, PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            setAuthorised(false);
        }

        connect();
            try {
            out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                        try {
                            while(true){
                                String msg = in.readUTF();
                                if(msg.startsWith("/authOK")){
                                    String[] tokens = msg.split(" ", 2);
                                    nick = tokens[1];
                                    setAuthorised(true);
                                    ArrayList<String> history = readHistory(nick);
                                    if(history.size() > 0){
                                        if(history.size() <= hisToShow) {
                                            for (String s : history) {
                                                System.out.println("tis line: " + s);
                                                chatArea.appendText(s + "\n");
                                            }
                                        } else {
                                            for(int i = history.size() - hisToShow; i < hisToShow; i++){
                                                chatArea.appendText(history.get(i) + "\n");
                                            }
                                        }
                                    }
                                    break;
                                }
                                if(msg.equals("/newNick")){
                                    String[] tokens = msg.split(" ", 2);
                                    nick = tokens[1];
                                    break;
                                }
                                else{
                                    chatArea.appendText(msg);
                                }
                            }

                            while (true) {
                                String msg = in.readUTF();
                                if(msg.startsWith("/")){
                                    if(msg.startsWith("/clientList ")){
                                        String [] tokens = msg.split(" ");

                                        Platform.runLater(() -> {
                                            clientList.getItems().clear();
                                            for(int i = 1; i < tokens.length; i++) {
                                                clientList.getItems().add(tokens[i]);
                                            }
                                        });
                                    }
                                    if(msg.equals("/session finished")) break;
                                } else {chatArea.appendText(msg + "\n");}
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {

                            String[] history = chatArea.getText().split("\n");
                            writeHistory(nick, history);

                            try {
                                clientSocket.close();
                                System.out.println("Socket closed");
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("Socket closing failed");
                            }
                            setAuthorised(false);
                        }
                }
            });
            t1.setDaemon(true);
            t1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() {
        try {
            out.writeUTF(msgField.getText()); //for private msg use "/w userName text" construction
            msgField.clear();
            msgField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeHistory(String nick, String[] msg){
        try {
            File historyFile = new File(nick + ".txt");
            historyFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(historyFile, true));
            writer.write(String.join("\n", msg));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> readHistory(String nick){
        ArrayList<String> str = new ArrayList<>();
        File historyFile = new File(nick + ".txt");
        try {
            historyFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(historyFile))) {
            String s;
            while ( (s = reader.readLine()) != null) {
                    str.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } return str;
    }

    public boolean isNotConnected(){
        return (clientSocket == null || clientSocket.isClosed());
    }
}
