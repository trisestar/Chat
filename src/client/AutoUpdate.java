package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Calendar;
import java.util.Scanner;

public class AutoUpdate extends Thread implements Serializable {

    public AutoUpdate() {

        this.start();
    }

    public void run() {

        System.out.println("Начало чата");
        Scanner sc = new Scanner(System.in);
        String buf;
        while (true){

            NewClientMessage.message = sc.nextLine();
            //System.out.println("Введено " + NewClientMessage.message);
           // NewClientMessage.setMessage(sc.nextLine());
           // System.out.println("Введено " + NewClientMessage.getMessage());
        }
    }
}