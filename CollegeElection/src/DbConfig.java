import java.sql.*;
import java.io.*;
import java.util.Properties;

public class DbConfig {
    private static Properties p = new Properties();
    static {
        try (InputStream in = new FileInputStream("config.properties")) { p.load(in); }
        catch (IOException e) { System.err.println("config.properties not found; using defaults."); }
    }
    public static Connection connect() throws SQLException {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { throw new SQLException("MySQL JDBC driver not found. Put Connector/J in lib folder.", e); }
        return DriverManager.getConnection(p.getProperty("db.url", "jdbc:mysql://localhost:3306/CollegeElection?serverTimezone=UTC"),
                p.getProperty("db.user", "root"), p.getProperty("db.password", ""));
    }
    public static boolean testConnection() {
        try (Connection c = connect()) { return c != null; }
        catch (Exception e) { javax.swing.JOptionPane.showMessageDialog(null, "Database connection failed:\n" + e.getMessage(), "Database Error", javax.swing.JOptionPane.ERROR_MESSAGE); return false; }
    }
}
