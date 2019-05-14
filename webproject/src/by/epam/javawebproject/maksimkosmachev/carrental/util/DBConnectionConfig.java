package by.epam.javawebproject.maksimkosmachev.carrental.util;

public class DBConnectionConfig {
    public static final String JDBC_MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/car_rental?characterEncoding=UTF-8" +
            "&serverTimezone=UTC";
    public static final String DATABASE_USER = "root";
    public static final String PASSWORD = "admin";
    public static final int CONNECTION_QUANTITY=10;
}
