package controllers;

import models.Client;
import models.Connection;

public class ConnectionDAO {

    //Singleton class
    private static ConnectionDAO instance;
    private Connection conn = null;

    private ConnectionDAO() {
    }

    public static ConnectionDAO getInstance() {
        if (instance == null) {
            instance = new ConnectionDAO();
        }
        return instance;
    }

    public Connection getConn(){
        return conn;
    }

    public void setConnection(Connection conn){
        getInstance().conn = conn;
        
        Thread novaThread = new Thread(conn);
        novaThread.start();
    }
    
}
