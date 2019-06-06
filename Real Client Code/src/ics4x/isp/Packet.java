/*
Developers : Dhvani & Shaiza
Class Name : Packet (Packet Transmission)
Date : June 3rd 2019
Class Description : This Class Is The Packet Class Which Sets The Network Protocol & Its Responsible For Working WIth The Client To Transmit Packets


 */

package ics4x.isp;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Packet implements Serializable {

    private String sendingID;
    private String textContent = "";
    private File file = new File("");
    private ArrayList<String> activePeers = new ArrayList<>();

    private int contentType = 1;
    /*
    Content Type:
    0 : id
    1 : text (default)
    2 : file
    4 : active peers
     */

    //constructor for packet that holds just the sending ID
    public Packet(String sendingID) {
        this.sendingID = sendingID;
        this.contentType = 0;
    }

    //constructor for packet that holds text content
    public Packet(String sendingID, String textContent) {
        this.sendingID = sendingID;
        this.textContent = textContent;
        this.contentType = 1;
    }

    //constructor for packet that holds file content
    public Packet(String sendingID, File file) {
        this.sendingID = sendingID;
        this.file = file;
        this.contentType = 2;
    }

    //constructor for packet that holds an array of active peers
    public Packet(String sendingID, ArrayList<String> activePeers) {
        this.sendingID = sendingID;
        this.activePeers.addAll(activePeers);
        this.contentType = 4;
    }

    //returns the sending ID
    public String getSendingID() {
        return sendingID;
    }

    //returns the text content
    public String getTextContent() {
        return textContent;
    }

    //returns the file
    public File getFile() {
        return file;
    }

    //returns the content type
    public int getContentType() {
        return contentType;
    }

    //returns the array of active peers
    public ArrayList<String> getActivePeers() {
        return activePeers;
    }

}