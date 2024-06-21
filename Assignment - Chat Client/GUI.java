import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.awt.GridLayout;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI implements ActionListener {       

    // Variables 
    private JButton buttonIn;
    private JButton buttonOut;
    private JButton confirmSetup;
    private JFrame frame;
    private JLabel label;
    private JLabel rLabel0; // rLabels display recieved data. Eventually.
    private JLabel rLabel1;
    private JLabel rLabel2;
    private JLabel rLabel3;
    private JLabel rLabel4;
    private JLabel rLabel5;
    private JLabel rLabel6;
    private JPanel panel;
    private Socket connector;
    private PrintWriter writer;
    private String output;
    private String output2;
    private String output3;
    private String username;
    private String hostname;
    private JTextField hostnameBox;
    private JTextField usernameBox;
    private JTextField sendBox;



    public GUI(boolean original) {
    
    // initialize objects
        frame = new JFrame();
        label = new JLabel("Recent output");
        rLabel0 = new JLabel(" ");
        rLabel1 = new JLabel(" ");
        rLabel2 = new JLabel(" ");
        rLabel3 = new JLabel(" ");
        rLabel4 = new JLabel(" ");
        rLabel5 = new JLabel(" ");
        rLabel6 = new JLabel(" ");
        hostnameBox = new JTextField("Hostname");
        usernameBox = new JTextField("Username");
        sendBox = new JTextField("Enter text here");


        confirmSetup = new JButton("Confirm");
        confirmSetup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                confirmSetupActionPerformed(evt);
            }
        });

        buttonIn = new JButton("Send text");
        buttonIn.addActionListener(this);
        
        buttonOut = new JButton("Clear text");
        buttonOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonOutActionPerformed(evt);
            }
        });
        
        // Create GUI

        if(original == false) {
            panel = new JPanel();
            panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
            panel.setLayout(new GridLayout(5, 0));
            panel.add(hostnameBox);
            panel.add(usernameBox);
            panel.add(confirmSetup);

        } else {
            
        // Start login GUI
            new GUI(false);

        // panel setup
            panel = new JPanel();
            panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
            panel.setLayout(new GridLayout(8, 0));
            panel.add(sendBox);
            panel.add(buttonIn);
            panel.add(buttonOut);
            panel.add(label);
            panel.add(rLabel0);
            panel.add(rLabel4);
            panel.add(rLabel1);
            panel.add(rLabel5);
            panel.add(rLabel2);
            panel.add(rLabel6);
            panel.add(rLabel3);

        }

        // frame setup
            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setTitle("Normal GUI");
            frame.pack();
            frame.setVisible(true);
    }
    public static void main(String args[]) {
        System.out.println("Magic");
            new GUI(true);        
            ClientThread dataReceiver = new ClientThread(null);
            Thread t2 = new Thread(dataReceiver);
            t2.start();
    }
    

    // Used to return output to data sender
    public String returnOutput() {
        output3 = output2;
        output2 = "";
        return output3;
    }

    // Button actions
    
    public void actionPerformed(ActionEvent e) {
            //label.setText("The button was pressed");
            
            output2 = sendBox.getText();
            output = sendBox.getText();
            System.out.println("Message sent.");
            rLabel6.setText(rLabel5.getText()); 
            rLabel5.setText(rLabel4.getText());
            rLabel4.setText(rLabel3.getText()); 
            rLabel3.setText(rLabel2.getText()); 
            rLabel2.setText(rLabel1.getText()); 
            rLabel1.setText(rLabel0.getText());
            rLabel0.setText(label.getText());  
            label.setText(output);

            String toSend = "[" + username +"]: " + output;
            writer.println(toSend); 

    }

    private void buttonOutActionPerformed(java.awt.event.ActionEvent evt) {      
        // Clear all labels                                   
        rLabel6.setText(""); 
        rLabel5.setText("");
        rLabel4.setText(""); 
        rLabel3.setText(""); 
        rLabel2.setText(""); 
        rLabel1.setText("");
        rLabel0.setText("");  
        label.setText("Text cleared.");
    }    
    
    private void confirmSetupActionPerformed(java.awt.event.ActionEvent evt) {                                         
            username = usernameBox.getText();
            hostname = hostnameBox.getText();
            System.out.println(username);
            System.out.println(hostname);
            frame.setVisible(false); 
            // connect
            try {
                connector = new Socket(hostname, 8888);
                OutputStream outputStream = connector.getOutputStream();
                writer = new PrintWriter(outputStream, true);
                writer.println(username);

                
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // threads for input and output
            
    }    
    }