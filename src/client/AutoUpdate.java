package client;

import java.io.Serializable;
import java.util.Scanner;

public class AutoUpdate extends Thread implements Serializable {

    public AutoUpdate() {

        this.start();
    }

    public void run() {

        System.out.println("Начало чата");
        Scanner sc = new Scanner(System.in);
        String buf;
        while (true) {

            ClientData.message = sc.nextLine();
            //System.out.println("Введено " + NewClientMessage.message);
            // NewClientMessage.setMessage(sc.nextLine());
            // System.out.println("Введено " + NewClientMessage.getMessage());
        }
    }
}