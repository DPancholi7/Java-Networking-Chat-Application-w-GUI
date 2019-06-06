/*
Developers : Dhvani & Shaiza
Class Name : Client
Date : June 3rd 2019
Class Description : This Class Is The Client Class Which Connects To The Server & Sends The Packets To The Server

 */

package ics4x.isp;

import ics4x.isp.GUI.ClientLayout;
import ics4x.isp.GUI.LoginWindow;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        
        new Client("127.0.0.1"); // Client IP
    }

    private String clientID;
    private String serverIP;
    private Socket socket;
    private final int port = 6789;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ClientLayout ClientLayout;
    private LoginWindow loginWindow;
    private FileController FileController;

    private Client(String serverIP) {
        this.serverIP = serverIP;
        FileController = new FileController();
        ClientLayout = new ClientLayout(this);
        ClientLayout.setVisible(false);
        loginWindow = new LoginWindow(this);
        loginWindow.setVisible(true);
        //start connection to server
        startRunning();
    }
    private void sendClientID() {
        try {
            System.out.println("id: " + clientID);
            //create packet with client ID
            Packet packet = new Packet(clientID);
            //write packet to output stream to server
            outputStream.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void usernameInputFinished(String username) {
        clientID = username;
        loginWindow.setVisible(false);
        ClientLayout.getChatWindow().setClientUsername(username);
        ClientLayout.setTitle(username);
        ClientLayout.setVisible(true);
        sendClientID();
    }


    public synchronized void SendMSG(String message) {
        try {

            Packet packet = new Packet(clientID, message);

            outputStream.writeObject(packet);

            ClientLayout.getChatWindow().DisplayToChat("You", message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public synchronized void SendFile(File file) {
        try {

            Packet packet = new Packet(clientID, file);

            outputStream.writeObject(packet);

            ClientLayout.getChatWindow().DisplayToChat("You sent a file: \"" + file.getName() + "\"");
        } catch (IOException exc) {

        }
    }

    //thread-safe method to open a file using OS default program and save the file to user's desktop
    public synchronized void OpenFile(int fileIndex) {
        //get file of given index
        File file = FileController.getFile(fileIndex);
        try {
            //save file to desktop and open with OS default program
            Desktop.getDesktop().open(file);
        } catch (IOException exc) {
            System.out.println("Failed To Open " + file.getPath());
        }
    }

    //shows GUI for chat, hides GUI for login, and sends client ID to server
    

    //starts connection to server and keeps reading from server
    

    
    private void PacketTransmission(Packet packet) {
        //return if packet has no sending ID
        if (packet.getSendingID() == null || packet.getSendingID().isEmpty())
            return;

        switch (packet.getContentType()) {
            //if text, display in dialog text area
            case 1:
                if (packet.getTextContent().isEmpty())
                    return;
                ClientLayout.getChatWindow().DisplayToChat(packet.getSendingID(), packet.getTextContent());
            //if file, save in file manager and display in file window
            case 2:
                if (packet.getFile().getPath().equals(""))
                    return;
                System.out.println("File Received From " + packet.getSendingID() + ": " + packet.getFile().getName());
                ClientLayout.getChatWindow().DisplayToChat(packet.getSendingID() + " sent you a file.");
                FileController.addFile(packet.getFile(), packet.getSendingID());
                ClientLayout.getFileWindow().updateTable(FileController.getFileNames(), FileController.getSendingIDs());
            //if array of active peers, update peer status window
            case 4:
                ClientLayout.getOnlineUsers().updateTable(packet.getActivePeers());
        }
    }
    private void startRunning() {
        System.out.print("Client Running...");
        ClientLayout.getChatWindow().setStatus("Attempting Connection ...");
        //try socket connection
        try {
            socket = new Socket(serverIP, port);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Server Might Be Down!","Warning",JOptionPane.WARNING_MESSAGE);
        }
        ClientLayout.getChatWindow().setStatus("Connected to: " + socket.getInetAddress().getHostName());

        //initialize input and output streams with socket's input and output streams
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        while (true) {
            try {
                //wait and read packet from server
                Packet packet = (Packet) inputStream.readObject();
                //process the packet by its content type
                PacketTransmission(packet);
            } catch (ClassNotFoundException | IOException exc) {
                //exc.printStackTrace();
            }
        }
    }

 
   

}

