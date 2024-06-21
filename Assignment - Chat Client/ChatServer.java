import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    // the port the server is going to listen on
    private static int port = 8888;

    // list of connected users' names
    private Set<String> userNames = new HashSet<>();

    // list of connected users' thread handling their connection to the server
    private Set<UserThread> userThreads = new HashSet<>();

    // constructor (don't need anything right now)
    public ChatServer() {
    }

    // main method to start up the program
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.startChatServer();
    }

    // method to attempt to fire up the chat server and then accept connections from users
    // and create a new thread to handle each connected user
    public void startChatServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            System.out.println("Chat Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("User Connected.");
 
                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }
 
        } catch (IOException ex) {
            System.out.println("Chat Server Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // method to broadcast any incoming messages to all connected users
    public void broadcast(String message, UserThread excludeUser) {
        for (UserThread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }
    
    // method to add users' names to our list of user names
    public void addUserName(String userName) {
        userNames.add(userName);
    }

    // method to remove a user from the list of connected users
    public void removeUser(String userName, UserThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println(userName + " has left the chat.");
        }
    }

    // method to return if we have any connected users
    public boolean hasUsers() {
        return !this.userNames.isEmpty();
    }

    // getter method to return list of usernames
    Set<String> getUserNames() {
        return this.userNames;
    }
}