package server;


import java.io.*;
import java.util.ArrayList;

public class Message implements Serializable {

    private String command;
    private String buf;
    private ArrayList<String> messages;
    private ArrayList<Integer> id;
    private ArrayList<String> name;
    private ArrayList<String> time;


    public Message() {
    }

    public Message(ArrayList<String> messages) {
        this.messages = messages;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getBuf() {
        return buf;
    }

    public void setBuf(String buf) {
        this.buf = buf;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public ArrayList<Integer> getId() {
        return id;
    }

    public void setId(ArrayList<Integer> id) {
        this.id = id;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public void setTime(ArrayList<String> time) {
        this.time = time;
    }


}