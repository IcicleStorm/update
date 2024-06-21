import java.io.*;
import java.net.*;

public class UserThread extends Thread {
    // socket for network communication
    private Socket socket;

    // instance of the chat server itself
    private ChatServer server;

    // PrintWriter object to stream output to
    private PrintWriter writer;

    // constructor
    public UserThread(Socket sock, ChatServer serv) {
        this.socket = sock;
        this.server = serv;
    }

    // override Thread's run() method
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
 
            printUsers();
 
            String userName = reader.readLine();
            server.addUserName(userName);
 
            String serverMessage = "New User Connected: " + userName;
            server.broadcast(serverMessage, this);
 
            String clientMessage;
 
            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(serverMessage, this);
 
            } while (!clientMessage.equals("bye"));
 
            server.removeUser(userName, this);
            socket.close();
 
            serverMessage = userName + " has left the chat.";
            server.broadcast(serverMessage, this);
 
        } catch (IOException ex) {
            System.out.println("UserThread Error: " + ex.getMessage());
            ex.printStackTrace();
        } 
    }

    // method for printing list of connected users
    public void printUsers() {
        if (server.hasUsers()) {
            writer.println("Connected Users: " + server.getUserNames());
        } else {
            writer.println("No Users Connected");
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }
}
