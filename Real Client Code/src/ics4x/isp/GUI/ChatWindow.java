/*
Developers : Dhvani & Shaiza
Class Name : Chat-Window
Date : June 3rd 2019
Class Description : This Class Is The ChatWindow Class Which Is Responsible For The Graphical User Interface When Chatting In The Chat Window


 */

package ics4x.isp.GUI;

import ics4x.isp.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ChatWindow extends JPanel {

    private Client controller;  //reference to controller

    private javax.swing.JTextArea chatArea;         //text area to display chat dialog
    private javax.swing.JTextField inputTextField;  //text field to read user's next message
    private javax.swing.JLabel statusLabel;         //label to show connection status

    private String username = "Client";             //username to display on frame title

    public ChatWindow(Client controller) {
        this.controller = controller;
        initComponents();           //initialize UI components
        this.setVisible(true);
        statusLabel.setVisible(true);
    }

    private void initComponents() {

        JPanel panel = new javax.swing.JPanel();        //panel to hold components
        inputTextField = new javax.swing.JTextField();
        JButton sendButton = new javax.swing.JButton(); //send button
        JScrollPane scrollPane = new javax.swing.JScrollPane(); //scroll pane to enable scrolling on dialog text area
        chatArea = new javax.swing.JTextArea();
        JLabel promptLabel = new javax.swing.JLabel();  //label that prompts user to type messages
        statusLabel = new javax.swing.JLabel();
        JLabel bgLabel = new javax.swing.JLabel();      //label that displays background image

        panel.setLayout(null);      //disable flow layout for panel

        //input text field
        inputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //call thread-safe method on controller to send current message when user hits enter key
                controller.SendMSG(inputTextField.getText());
                //clear text field for the next message
                inputTextField.setText("");
            }
        });
        panel.add(inputTextField);
        inputTextField.setBounds(30, 370, 270, 30);

        //send button
        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //call thread-safe method on controller to send current message when user presses the button
                controller.SendMSG(inputTextField.getText());
                //clear text field for the next message
                inputTextField.setText("");
            }
        });
        panel.add(sendButton);
        sendButton.setBounds(320, 370, 80, 30);

        //button to choose a file
        JButton ChooseFile = new JButton("File");
        ChooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //displays file chooser
                File file = chooseFile();
                //if user selects a valid file
                if (file != null) {
                    //call thread-safe method on controller to send chosen file when user presses the button
                    controller.SendFile(file);
                }
            }
        });
        panel.add(ChooseFile);
        ChooseFile.setBounds(320, 335, 80, 30);

        //dialog text area
        chatArea.setColumns(20);    //initial columns of 20
        chatArea.setRows(5);        //initial rows of 5
        scrollPane.setViewportView(chatArea);   //connect text area to scroll pane to enable scrolling
        panel.add(scrollPane);
        scrollPane.setBounds(30, 40, 360, 270);

        //prompt label
        promptLabel.setForeground(new java.awt.Color(255, 255, 255));
        promptLabel.setText("Write your text here");
        panel.add(promptLabel);
        promptLabel.setBounds(30, 340, 150, 20);

        //connection status label
        statusLabel.setForeground(Color.white);
        statusLabel.setText("Not connected to server.");
        panel.add(statusLabel);
        statusLabel.setBounds(30, 10, 300, 40);

        //background label
        bgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgT.jpg"))); //
        panel.add(bgLabel);
        bgLabel.setBounds(0, 0, 420, 410);

        //layout manager
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(414, 428));
    }

    //updates connection status label
    public void setStatus(String status) {
        this.statusLabel.setText(status);
    }

    //adds a new message to text area with sending ID
    public void DisplayToChat(String id, String message) {
        chatArea.append(id + " - " + message + "\n");
    }

    //adds a system notification to text area (normally an incoming file)
    public void DisplayToChat(String notification) {
        chatArea.append("\t" + notification + "\n");
    }

    //updates the username
    public void setClientUsername(String username) {
        this.username = username;
    }

    //displays a file chooser and returns the chosen file
    private File chooseFile() {
        //create new file choose
        JFileChooser jfc = new JFileChooser();
        //reads response value from file chooser
        int returnValue = jfc.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            //return chosen file if file is valid
            System.out.println("User chose " + jfc.getSelectedFile().getName());
            return jfc.getSelectedFile();
        } else {
            //return null if file is not valid
            return null;
        }
    }

}
