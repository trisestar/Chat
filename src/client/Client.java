package client;

import server.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

    private String newMessage;


    public static void main(String[] args) {
        try {
            System.out.println("server connecting....");
            Socket clientSocket = new Socket("127.0.0.1", 8080);
            System.out.println("connection established....");
            //BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream coos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream cois = new ObjectInputStream(clientSocket.getInputStream());
            Scanner sc = new Scanner(System.in);
            String buf;
            int number;

            String lastMessageID = "0";


            AutoUpdate autoUpdate = new AutoUpdate();
            while (true) {
                //System.out.println("_________" + NewClientMessage.message);
                Message clientMessage = new Message();
                if (NewClientMessage.message.equals("0")) {

                    clientMessage.setCommand("check");
                    clientMessage.setBuf(lastMessageID);
                    coos.writeObject(clientMessage);
                    clientMessage = (Message) cois.readObject();
                    if (!clientMessage.getBuf().equals("old")){
                        lastMessageID = clientMessage.getBuf();
                        //ArrayList<String> messages = clientMessage.getMessages();
                        System.out.println("Получено " + clientMessage.getCommand());
                        String [] mes = clientMessage.getCommand().split("%");



                        System.out.println("------");

                        for (String str : mes){
                            System.out.println(str);
                            System.out.println("------");
                        }
                    }
                } else if (!NewClientMessage.message.equals( "/end")) {
                    clientMessage.setCommand("new");
                    clientMessage.setBuf(NewClientMessage.message);
                    System.out.println("Отправка " + clientMessage.getCommand() + " " + clientMessage.getBuf());
                    coos.writeObject(clientMessage);
                    clientMessage = (Message) cois.readObject();


                } else break;





                NewClientMessage.message="0";
                Thread.sleep(2000);
            }



            sc.close();
            coos.close();
            cois.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }


}
