package server;

import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {
    public static ArrayList<String> history = new ArrayList<String>();
    public static ArrayList<String> users = new ArrayList<String>();

    private int room_id;

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public static int getSize() {
        return history.size();
    }

}
