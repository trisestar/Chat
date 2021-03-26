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
            DbConnect dbConnect = new DbConnect();
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
                        if (info.getSize() != Integer.parseInt(dbConnect.query("getSize", String.valueOf(info.getRoom_id())))) {
                            buf = dbConnect.query("getChat", String.valueOf(info.getRoom_id()));
                            String[] splitedChat = buf.split("&");
                            for (int i = info.getSize(); i < splitedChat.length; i++) {
                                info.addMsg(splitedChat[i].split("#")[0]);
                                info.addUser(splitedChat[i].split("#")[1]);
                            }
                            for (int i = 0; i < info.getSize(); i++) {
                                System.out.println(info.getMsgById(i));
                            }
                        }

                        if (info.getSize() != Integer.parseInt(message.getBuf())) {

                            buf = "";
                            for (int i = Integer.parseInt(message.getBuf()); i < info.getSize(); i++) {
                                buf += info.getMsgById(i);
                                buf += "&";
                                buf += info.getUserById(i);
                                buf += "#";
                            }
                            message.setCommand(buf);
                            message.setBuf(String.valueOf(info.getSize()));

                        } else {
                            message.setBuf("old");
                        }
                        outputStream.writeObject(message);
                        break;
                    }

                    case "new": {
                        info.addMsg(message.getBuf());
                        info.addUser(info.getUser());
                        buf = message.getBuf() + "&" + info.getUser() + "&" + Calendar.getInstance().getTime() + "&" + info.getRoom_id();
                        System.out.println(buf);
                        dbConnect.query("newMessage", buf);
                        outputStream.writeObject(message);
                        break;
                    }

                    case "add user": {
                        if (dbConnect.query("addUser", message.getBuf()).equals("success")) {
                            message.setCommand("success");
                            info.setUser(message.getBuf().split("&")[0]);
                        } else {
                            message.setCommand("error");
                        }
                        outputStream.writeObject(message);
                        break;
                    }

                    case "check user": {
                        if (dbConnect.query("checkUser", message.getBuf()).equals("success")) {
                            message.setCommand("success");
                            info.setUser(message.getBuf().split("&")[0]);
                        } else {
                            message.setCommand("error");
                        }
                        outputStream.writeObject(message);
                        break;
                    }

                    case "get history": {
                        buf = dbConnect.query("getChat", String.valueOf(info.getRoom_id()));
                        if (!buf.isEmpty()) {
                            String[] splitedChat = buf.split("&");
                            for (String str : splitedChat) {
                                info.addMsg(str.split("#")[0]);
                                info.addUser(str.split("#")[1]);
                            }

                            for (int i = 0; i < info.getSize(); i++) {
                                System.out.println(info.getMsgById(i));
                            }
                        }
                        message.setCommand("success");
                        outputStream.writeObject(message);
                        break;
                    }

                    case "set room": {
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

        } catch (
                SocketException e) {
            System.out.println("Пользователь отсоединился");
            numOfUsers--;
            System.out.println("Дата: " + Calendar.getInstance().getTime());
            System.out.println("Всего пользователей: " + numOfUsers);
        } catch (
                EOFException e) {
            System.out.println("...");
            e.printStackTrace();
            try {
                socket.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException |
                ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}


