package installer;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Installer {
    
    public static void main(String args[]) {
        String dbName = null;
        String login = null;
        String password = null;
        
        Statement st = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "jdbc driver not found");
            System.exit(-1);
        }
        
        System.out.print(" read config...");
        try {
            Scanner scanner = new Scanner(new File("config.txt"));
            dbName = scanner.nextLine();
            login = scanner.nextLine();
            password = scanner.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "config.txt not found");
            System.exit(-1);
        }
        
        try {
            System.out.print(" done\n create database...");
            st = DriverManager.getConnection(
                    "jdbc:mysql://localhost/?user="+login+"&password="+password
            ).createStatement();
            st.executeUpdate("DROP DATABASE IF EXISTS "+dbName);
            st.executeUpdate("CREATE DATABASE "+dbName);
            st.executeQuery("USE "+dbName);
            System.out.print(" done\n create tables...");
            Scanner scanner = new Scanner(new File("data/create_tables.sql"));
            String query = "";
            String line;
            while(scanner.hasNext()) {
                line = scanner.nextLine();
                query += line;
                if(line.endsWith(";")) {
                    st.addBatch(query);
                    query = "";
                }
            }
            st.executeBatch();
            st.clearBatch();
            System.out.print(" done\n insert data...");
            scanner = new Scanner(new File("data/inserts.sql"));
            query = "";
            while(scanner.hasNext()) {
                line = scanner.nextLine();
                query += line;
                if(line.endsWith(";")) {
                    st.addBatch(query);
                    query = "";
                }
            }
            st.executeBatch();
            System.out.print(" done");
        } catch(SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "sql exception");
            System.exit(-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "scanner exception");
            System.exit(-1);
        }
    } 
}
