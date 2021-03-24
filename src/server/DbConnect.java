package server;

import java.sql.*;

public class DbConnect {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/chat_db";
    static final String USER = "postgres";
    static final String PASS = "12379580";
    private Connection connection = null;

    public DbConnect() {
        try {
            int room_id = 0;
            String user = "qwe";

            Class.forName("org.postgresql.Driver");
            System.out.println("Connected");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            if (connection != null) {
                System.out.println("Connected to database");
            } else {
                System.out.println("Error");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public String query(String command, String info) {

        try {

            switch (command) {

                case "newMessage": {
                    PreparedStatement preparedStatement = connection.prepareStatement
                            ("INSERT INTO messages(message, name, date, room_id) VALUES (?, ?, ?, ?)");
                    preparedStatement.setString(1, info.split("&")[0]);
                    preparedStatement.setString(2, info.split("&")[1]);
                    preparedStatement.setString(3, info.split("&")[2]);
                    preparedStatement.setInt(4, Integer.parseInt(info.split("&")[3]));
                    preparedStatement.executeUpdate();
                    return "success";
                }

                case "addUser": {
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES (?,?)");
                    preparedStatement.setString(1, info.split("&")[0]);
                    preparedStatement.setString(2, info.split("&")[1]);
                    preparedStatement.executeUpdate();
                    return "success";
                }
                case "checkUser": {
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login=? and password=?");
                    preparedStatement.setString(1, info.split("&")[0]);
                    preparedStatement.setString(2, info.split("&")[1]);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        return "success";
                    } else
                        return "error";


                }
                case "getChat": {
                    String chat = new String();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM messages WHERE room_id=? ");
                    preparedStatement.setInt(1, Integer.parseInt(info));
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        chat += resultSet.getString(2);
                        chat += "#";
                        chat += resultSet.getString(3);
                        chat += "&";
                    }
                    return chat;
                }
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "error";
    }

}
