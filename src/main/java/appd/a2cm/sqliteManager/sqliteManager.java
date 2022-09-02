/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appd.a2cm.sqliteManager;

import appd.Main;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Anwar Fazaa
 * This class will manage sqlite for cached mode
 * cached mode is useful for environments where there is high latency in connecting to events service
 * 
 */
public class sqliteManager {
    
    public String sqliteDatabaselocation;
    
    public sqliteManager() {
        
    }
    
    public void updateDbLocationAndCheckAvailability() {
        File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        File file = new File(jarFile.getParent(), "cache.db");
        sqliteDatabaselocation = file.getPath().replaceAll("%20*", " ");
        file = new File(sqliteDatabaselocation);
        if (!file.exists()) {
            
        }
        
    }
    
    public void createNewDatabase() throws SQLException {
        String dbUrl = "jdbc:sqlite:" + this.sqliteDatabaselocation;
        try {
            Connection conn = DriverManager.getConnection(dbUrl);   
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
