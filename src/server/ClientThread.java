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
            int room_id = 0;
            new DbThread();
            Message message;
            ObjectOutputStream outputStream;
            ObjectInputStream inputStream;
            inputStream = new ObjectInputStream(this.socket.getInputStream());
            outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            String buf = "";
            int int_buf;
            while (true) {

                message = (Message) inputStream.readObject();
                ch = message.getCommand();
                //if (!message.getCommand().equals("check")) {
                //System.out.println("Получено: " + message.getCommand() + " " + message.getBuf());
                //}
                switch (ch) {

                    case "check": {
                        if (Integer.parseInt(message.getBuf()) != Chat.getSize()) {
                            //System.out.println("Отправляю с " + message.getBuf() + " до " + Chat.getSize());
                            buf = "";
                            for (int i = Integer.parseInt(message.getBuf()); i < Chat.getSize(); i++) {
                                buf += Chat.history.get(i);
                                buf += "%";
                                //System.out.println("Добавил " + i + " элемент");
                            }
                            message.setCommand(buf);
                           // System.out.println("buf = " + buf);
                            //System.out.println("Отправлено " + message.getCommand());
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
                        //outputStream.writeObject(new Message(Chat.history));
                        outputStream.writeObject(message);
                        break;
                    }

                    case "reload": {
                        outputStream.writeObject(new Message(Chat.history));
                        break;
                    }

                    default: {
                        System.out.println("Error");
                        break;
                    }



                    case "Выход": {
                        socket.close();
                        outputStream.close();
                        inputStream.close();

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


