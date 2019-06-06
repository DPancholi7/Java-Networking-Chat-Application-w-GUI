/*
Developers : Dhvani & Shaiza
Class Name : File Window
Date : June 3rd 2019
Class Description : This Class Is The File Window Class, This Window Stores Files & Puts It On A List

 */
package ics4x.isp.GUI;

import ics4x.isp.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FileWindow extends JPanel {

    private Client controller;      //reference to controller

    private JTable fileTable;       //table to display received files
    private JLabel titleLabel;      //table to display "Files Received"

    public FileWindow(Client controller) {
        this.controller = controller;
        initComponents();               //initialize UI components
        setVisible(false);
    }

    private void initComponents() {
        //disable flow layout on panel
        this.setLayout(null);

        //"Files Received" label
        titleLabel = new JLabel("File Storage");
        titleLabel.setForeground(Color.black);
        titleLabel.setBounds(10, 10, 200, 30);
        this.add(titleLabel);

        //table is uneditable by the user
        fileTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        fileTable.setPreferredScrollableViewportSize(new Dimension(200, 300));
        fileTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(fileTable);        //scroll pane to enable scrolling on table
        scrollPane.setBounds(10, 50, 250, 350);
        this.add(scrollPane);
    }

    //a new preferred size for this panel
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 425);
    }

    //updates files table with an array of file names and an array of corresponding sending IDs
    public void updateTable(String[] fileNames, String[] sendingIDs) {
        //create a new table model with dynamic rows and 2 columns
        DefaultTableModel dtm = new DefaultTableModel(0, 2);
        //add each file name and its corresponding sending ID to a row
        for (int i = 0; i < fileNames.length; i++) {
            dtm.addRow(new Object[] {fileNames[i], sendingIDs[i]});
        }
        //set the table model on the table
        fileTable.setModel(dtm);
        //listens to a click event on any row of the table
        fileTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //get the selected row
                int row = fileTable.getSelectedRow();
                //if no row is selected, return
                if (row == -1)
                    return;
                //call thread-safe method on controller to open the selected file
                controller.OpenFile(row);
                //clear selection to listen to next click event
                fileTable.clearSelection();
            }
        });
    }

}
