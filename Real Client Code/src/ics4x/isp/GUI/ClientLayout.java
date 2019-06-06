/*
Developers : Dhvani & Shaiza
Class Name : Client Frame
Date : June 3rd 2019
Class Description : This Class Is The Client Frame Class Which Is Responsible On Loading And Setting The Entire GUI For This Application.


 */
package ics4x.isp.GUI;

import ics4x.isp.Client;

import javax.swing.*;
import java.awt.*;

public class ClientLayout extends JFrame {

    private Client controller;          //reference to controller

    private ChatWindow chatWindow;      //an instance of chat windoe

    private OnlineUsers OnlineUsers;      //an instance of peer status window

    private FileWindow fileWindow;      //an instance of file window

    public ClientLayout(Client controller) {
        this.controller = controller;

        //window is closed on clicking the close button
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //set layout manager to Border layout
        this.setLayout(new BorderLayout());

        //create chat window and put it in the middle
        chatWindow = new ChatWindow(controller);
        chatWindow.setVisible(true);
        this.add(chatWindow, BorderLayout.CENTER);

        //create peer status window and put it on the left side
        OnlineUsers = new OnlineUsers(controller);
        OnlineUsers.setVisible(true);
        this.add(OnlineUsers, BorderLayout.WEST);

        //create file window and put it on the right side
        fileWindow = new FileWindow(controller);
        fileWindow.setVisible(true);
        this.add(fileWindow, BorderLayout.EAST);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(false);
        this.setResizable(false);
    }

    //returns the chat window
    public ChatWindow getChatWindow() {
        return chatWindow;
    }

    //returns the peer status window
    public OnlineUsers getOnlineUsers() {
        return OnlineUsers;
    }

    //returns the file window
    public FileWindow getFileWindow() {
        return fileWindow;
    }
}
