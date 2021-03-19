package server;

import java.io.Serializable;

public class ServerInfo implements Serializable {
    private String user;
    private int room_id;

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
}
