package client;

import server.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client_2 {

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
            String buf, login = null, password;
            int int_buf;
            Message clientMessage = new Message();
            String lastMessageID = "0";
            boolean statement = true, auth = false;

            while (true) {
                System.out.println("1-Вход\n2-Регистрация");
                int_buf = sc.nextInt();
                switch (int_buf) {
                    case 1: {
                        System.out.println("Введите логин");
                        sc.nextLine();
                        login = sc.nextLine();
                        System.out.println("Введите пароль");
                        password = sc.nextLine();
                        clientMessage.setCommand("check user");
                        clientMessage.setBuf(login + "&" + password);
                        coos.writeObject(clientMessage);
                        clientMessage = (Message) cois.readObject();
                        if (clientMessage.getCommand().equals("success")) {

                            auth = true;
                        } else {
                            System.out.println("Неверный логин или пароль");
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Введите логин");
                        sc.nextLine();
                        login = sc.nextLine();
                        System.out.println("Введите пароль");
                        password = sc.nextLine();
                        clientMessage.setCommand("add user");
                        clientMessage.setBuf(login + "&" + password);
                        System.out.println("Отправляю " + clientMessage.getBuf());
                        coos.writeObject(clientMessage);
                        clientMessage = (Message) cois.readObject();
                        if (clientMessage.getCommand().equals("success")) {
                            auth = true;
                        } else {
                            System.out.println("error");
                        }
                        break;
                    }
                }
                if (auth) break;
            }

            while (true) {
                System.out.println("Введите номер комнаты (от 0 до 99999)");
                int_buf = sc.nextInt();
                sc.nextLine();
                if (int_buf < 0 || int_buf > 99999) {
                    System.out.println("Неверный номер комнаты");
                } else {
                    ClientData.room_id = int_buf;
                    clientMessage.setCommand("set room");
                    clientMessage.setBuf(String.valueOf(int_buf));
                    coos.writeObject(clientMessage);
                    clientMessage = (Message) cois.readObject();
                    break;
                }
            }

            clientMessage.setCommand("get history");
            coos.writeObject(clientMessage);
            clientMessage = (Message) cois.readObject();

            AutoUpdate autoUpdate = new AutoUpdate();

            while (statement) {
                //System.out.println("_________" + NewClientMessage.message);

                if (ClientData.message.equals("0")) {

                    clientMessage.setCommand("check");
                    clientMessage.setBuf(lastMessageID);
                    coos.writeObject(clientMessage);
                    clientMessage = (Message) cois.readObject();
                    if (!clientMessage.getBuf().equals("old")) {
                        lastMessageID = clientMessage.getBuf();
                        String[] chat = clientMessage.getCommand().split("#");
                        for (String str : chat) {
                            if (str.split("&")[1].equals(login)) {
                                System.out.println(str.split("&")[0]);
                                System.out.println("------");
                            } else {
                                System.out.print(str.split("&")[1]);
                                System.out.print(": ");
                                System.out.println(str.split("&")[0]);
                                System.out.println("------");
                            }
                        }
                    }
                } else if (!ClientData.message.equals("/end")) {
                    int_buf = Integer.parseInt(lastMessageID);
                    int_buf++;
                    lastMessageID = String.valueOf(int_buf);
                    clientMessage.setCommand("new");
                    clientMessage.setBuf(ClientData.message);
                    //System.out.println("Отправка " + clientMessage.getCommand() + " " + clientMessage.getBuf());
                    coos.writeObject(clientMessage);
                    clientMessage = (Message) cois.readObject();
                    System.out.println("------");

                } else {
                    statement = false;
                    autoUpdate.setStop();
                }

                ClientData.message = "0";
                Thread.sleep(50);
            }

            sc.close();
            coos.close();
            cois.close();
            clientSocket.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
