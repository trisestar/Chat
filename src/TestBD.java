import server.DbThread;

import java.sql.*;

public class TestBD {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/chat_db";
    static final String USER = "postgres";
    static final String PASS = "12379580";

    public static void main(String[] argv) {

        new DbThread();
    }
}