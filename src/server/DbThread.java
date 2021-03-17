package server;

import java.io.Serializable;
import java.net.Socket;
import java.sql.*;

public class DbThread extends Thread implements Serializable {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/chat_db";
    static final String USER = "postgres";
    static final String PASS = "12379580";

    public DbThread() {
        this.start();
    }

    public void run() {

        try {
            int room_id = 0;
            String user = "qwe";

            Class.forName("org.postgresql.Driver");
            System.out.println("Connected");
            Connection connection = null;
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            if (connection != null) {
                System.out.println("Connected to database");
            } else {
                System.out.println("Error");
            }
            String sql= "SELECT * FROM messages WHERE room_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,room_id);
            //preparedStatement.setString(2,"qwe");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                //System.out.println(resultSet.getString(1) + resultSet.getString(2));
                Chat.history.add(resultSet.getString(2));
                Chat.users.add(resultSet.getString(3));
            }
            for (int i =0;i<Chat.getSize();i++){
                System.out.println(Chat.users.get(i)+": "+Chat.history.get(i));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
