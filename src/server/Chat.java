package server;

import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {

    public static ArrayList<String> history = new ArrayList<String>();

    public static int getSize () {
        return history.size();
    }

}
