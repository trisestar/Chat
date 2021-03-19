package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;

public class ClientThread extends Thread implements Serializable {


    public static int numOfUsers = 0;

    private String ch;
    private String string;
    private int count;
    private Socket socket;
    private int lastId = 0;


    public ClientThread(Socket socket) {
        this.socket = socket;
        this.start();
    }

    public void run() {
        try {
            ServerInfo info = new ServerInfo();
            Message message;
            DbConnect dbConnect =new DbConnect();
            ObjectOutputStream outputStream;
            ObjectInputStream inputStream;
            inputStream = new ObjectInputStream(this.socket.getInputStream());
            outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            String buf = "";
            int int_buf;

            while (true) {
                message = (Message) inputStream.readObject();
                ch = message.getCommand();
                switch (ch) {

                    case "check": {
                        if (Integer.parseInt(message.getBuf()) != Chat.getSize()) {
                            buf = "";
                            for (int i = Integer.parseInt(message.getBuf()); i < Chat.getSize(); i++) {
                                buf += Chat.history.get(i);
                                buf += "%";
                            }
                            message.setCommand(buf);
                            message.setBuf(String.valueOf(Chat.getSize()));
                            outputStream.writeObject(message);

                        } else {
                            message.setBuf("old");
                            outputStream.writeObject(message);
                        }
                        break;
                    }

                    case "new": {
                        Chat.history.add(message.getBuf());
                        outputStream.writeObject(message);
                        break;
                    }


                    case "add user":{
                        System.out.println("кейс добавления юзера "+message.getBuf());

                        if (dbConnect.query("add", message.getBuf()).equals("success")){
                            message.setCommand("success");
                            info.setUser(message.getBuf().split("&")[0]);
                            outputStream.writeObject(message);
                        } else {
                            message.setCommand("error");
                            outputStream.writeObject(message);
                        }
                        break;
                    }

                    case "check user":{


                        if (dbConnect.query("check", message.getBuf()).equals("success")){
                            message.setCommand("success");
                            info.setUser(message.getBuf().split("&")[0]);
                            outputStream.writeObject(message);
                        } else {
                            message.setCommand("error");
                            outputStream.writeObject(message);
                        }
                        break;
                    }

                    case "get history":{
                        dbConnect.query("getChat", String.valueOf(info.getRoom_id()));
                        message.setCommand("success");
                        outputStream.writeObject(message);
                        break;
                    }

                    case "set room":{
                        info.setRoom_id(Integer.parseInt(message.getBuf()));
                        message.setCommand("success");
                        outputStream.writeObject(message);
                        break;
                    }

                    case "exit": {
                        socket.close();
                        outputStream.close();
                        inputStream.close();

                        break;
                    }
                    default: {
                        System.out.println("error");
                        break;
                    }
                }

            }

        } catch (SocketException e) {
            System.out.println("Пользователь отсоединился");
            numOfUsers--;
            System.out.println("Дата: " + Calendar.getInstance().getTime());
            System.out.println("Всего пользователей: " + numOfUsers);
        } catch (EOFException e) {
            System.out.println("...");
            e.printStackTrace();
            try {
                socket.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}


