package geekbrainsChat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    String userName;

    public String getUserName() {
        return userName;
    }

    public ClientHandler(Server server, Socket socket){
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    while (true){
                            String str = in.readUTF();
                            if(str.startsWith("/auth")){
                                String [] tokens = str.split(" ");
                                String checkUserName = DBService.getNameByLoginAndPass(tokens[1], tokens[2]);
                                if(checkUserName!=null){
                                    if(!server.isUserConnected(checkUserName)){
                                        userName = checkUserName;
                                        System.out.println(checkUserName + " connected");
                                        out.writeUTF("/authOK");
                                        server.subscribe(ClientHandler.this);
                                        server.broadcastMsg("Master: " + userName + " join BOBO Chat" + "\n");
                                        sendMsg("Master: Welcome to BOBO Chat, Dear " + userName + "\n");
                                        break;
                                    } else sendMsg("Master: Ups, seams that U've already entered this chat" + "\n");
                                } else sendMsg("Master: Incorrect login/password, try again.." + "\n");
                            }
                    }
                    while (isConnected()) {
                        String str = in.readUTF();
                        if(str.startsWith("/")){
                            String [] tokens = str.split(" ", 2);
                            switch (tokens[0]) {
                                case "/end" -> {
                                    out.writeUTF("/session finished");
                                }
                                case "/w" -> {
                                    String[] msgParts = str.split(" ", 3);
                                    server.sendPrivateMsg(userName, msgParts[1], msgParts[2]);
                                }
                                case "/nick" -> {
                                    String[] msgParts = str.split(" ", 2);
                                    DBService.changeUsername(userName, msgParts[1]);
                                    server.broadcastMsg("Master: " + userName + " changed nick to " + msgParts[1] + "\n");
                                    userName = msgParts[1];
                                    server.broadcastClientList();
                                }
                            }
                        } else {
                            server.broadcastMsg(userName + ": " + str);
                        }
                    }
                } catch (IOException e){
                    System.out.println(userName + " disconnected");
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        System.out.println("Socket closing error");
                    }
                    server.unsubscribe(ClientHandler.this);
                    server.broadcastMsg("Master: " + userName + " left BOBO Chat" + "\n");
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg (String msg){
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isConnected(){
        return (socket != null || !socket.isClosed());
    }


}
