package server;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerInfo implements Serializable {
    private String user;
    private int room_id;
    private ArrayList<String> history = new ArrayList<String>();
    private ArrayList<String> users = new ArrayList<String>();

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        System.out.println("user = " + user);
        this.user = user;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        System.out.println("room_id = " + room_id);
        this.room_id = room_id;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public String getMsgById(int id) {
        return history.get(id);
    }

    public String getUserById(int id) {
        return users.get(id);
    }

    public void addMsg(String str) {
        history.add(str);
    }

    public void addUser(String str) {
        users.add(str);
    }

    public int getSize() {
        return history.size();
    }

}
