package client;

import java.io.Serializable;
import java.util.Scanner;

public class AutoUpdate extends Thread implements Serializable {

    private boolean stop = false;

    public AutoUpdate() {

        this.start();
    }


    public void run() {

        System.out.println("Начало чата");
        System.out.println();
        Scanner sc = new Scanner(System.in);
        String buf;
        while (!stop) {

            ClientData.message = sc.nextLine();
            //System.out.println("Введено " + NewClientMessage.message);
            // NewClientMessage.setMessage(sc.nextLine());
            // System.out.println("Введено " + NewClientMessage.getMessage());
        }

    }

    public void setStop() {
        stop = true;
    }
}