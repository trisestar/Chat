package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Date;

public class Server {

    private Date time;

    public static void main(String[] args) {
        try {

            //new TestThread();


            ServerSocket socketListener = new ServerSocket(8080);
            System.out.println("("+ Calendar.getInstance().getTime()+")"+"Сервер запущен: IP-адрес =127.0.0.1, PORT=8080");

            while (true) {
                Socket client = null;

                while (client == null) {
                    client = socketListener.accept();
                }
                new ClientThread(client); //Создаем новый поток, которому передаем сокет
                System.out.println("Пользователь присоеденился");
                ClientThread.numOfUsers++;
                System.out.println("Всего пользователей: " + ClientThread.numOfUsers);
            }
        } catch (SocketException e) {
            System.err.println("Socket exception");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("I/O exception");
            e.printStackTrace();
        }
        System.out.println("("+ Calendar.getInstance().getTime()+")"+"Сервер остановлен");
    }

}
