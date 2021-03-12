package server;

import java.io.Serializable;
import java.net.Socket;
import java.util.Calendar;

public class TestThread extends Thread implements Serializable {

    public TestThread() {
        this.start();
    }

    public void run() {
        while (true) {
            System.out.println("(" + Calendar.getInstance().getTime() + ")" + "Тест потока");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
