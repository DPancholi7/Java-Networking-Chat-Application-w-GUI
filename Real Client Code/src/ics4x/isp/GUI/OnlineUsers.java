/*
Developers : Dhvani & Shaiza
Class Name : Peer Status Window
Date : June 3rd 2019
Class Description : This Class Is The Peer Status Window Which Is Responsible In Showing Online Users


 */
package ics4x.isp.GUI;

import ics4x.isp.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class OnlineUsers extends JPanel {

    private Client controller;      //reference to controller

    private JTable table;           //table to display active peers

    public OnlineUsers(Client controller) {
        this.controller = controller;
        initComponents();               //initialize UI components
        this.setVisible(false);
    }

    private void initComponents() {
        //disable flow layout on panel
        this.setLayout(null);

        //label that displays "Peers Status"
        JLabel clientStatusLabel = new JLabel(" Online Users ");
        clientStatusLabel.setForeground(Color.black);
        clientStatusLabel.setBounds(10, 10, 200, 30);
        this.add(clientStatusLabel);

        //table to display active peers
        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(200, 300));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);    //scroll pane to enable scrolling on table
        scrollPane.setBounds(10, 50, 250, 350);
        this.add(scrollPane);
    }

    //a new preferred size for this panel
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 425);
    }

    //update peers in the table with a new array of peers' IDs
    public void updateTable(ArrayList<String> peerIDs) {
        //create a new table model
        DefaultTableModel dtm = new DefaultTableModel(0, 1);
        //add each peer ID to the model
        for (String peerID : peerIDs) {
            dtm.addRow(new Object[] {peerID});
        }
        //set the model on the table
        table.setModel(dtm);
    }

}
