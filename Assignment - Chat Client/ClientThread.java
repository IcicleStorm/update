import java.net.Socket;

public class ClientThread extends Thread {
    // Use UDP (#29, 4:30) to Reciv text to server.
    private Socket sock;
    private GUI ui;
    
    // Constructor
    public ClientThread(Socket s, GUI gui) {
        sock = s;
        ui = gui;
    }

    public void run() {
        while(true) {
            // the thread sleeps for 2 seconds
            try { 
                Thread.sleep(2000);
            } catch (Exception e) { 
                System.out.println("sleep error");
            }
            // print
            System.out.println("test");
            
        }
    }
    

}

