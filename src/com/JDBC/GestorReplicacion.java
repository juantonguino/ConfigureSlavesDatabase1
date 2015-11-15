/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juandiego
 */
public class GestorReplicacion {
    
    public final static int ID_DATA_BASE=1;
    
    private int masterConectNow;
    
    private FachadaDB fachadaDB;
    
    private Connection connection;

    public GestorReplicacion() {
        fachadaDB= new FachadaDB();
        connection= fachadaDB.crearConexion();
        masterConectNow=2;
    }
    
    public String showMasterStatus(){
        try {
            ResultSet resultado = this.fachadaDB.seleccionar("show master status;");
            String file="";
            int position=0;
            while (resultado.next()) {                
                file = resultado.getString("File");
                position = resultado.getInt("Position");   
            }
            return file+"/"+position;
        } catch (SQLException ex) {
            Logger.getLogger(GestorReplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public boolean changeMasterTo2() {
        boolean retorno = false;
        try {
            String[] resultado = showMasterStatusDataBase2().split("/");
            System.out.println("se esta conectand a la base de datos 2="+showMasterStatusDataBase2());
            String masterLogFile = resultado[0];
            String Pos = resultado[1];
            String stopSlave = "STOP SLAVE";
            String changeMasterTo = "CHANGE MASTER TO "
                    + "MASTER_HOST = '192.168.0.202', "
                    + "MASTER_USER = 'user12', "
                    + "MASTER_PASSWORD = 'admin', "
                    + "MASTER_LOG_FILE = '" + masterLogFile + "', "
                    + "MASTER_LOG_POS = " + Pos + ";";
            String startSlave = "START SLAVE";
            masterConectNow=2;
            retorno = true;
        } catch (Exception e) {
            retorno = false;
        }
        return retorno;
    }
    
    public boolean changeMasterTo3(){
        boolean retorno = false;
        try {
            String[] resultado = showMasterStatusDataBase3().split("/");
            System.out.println("se esta conectandp a la base de datos 3="+showMasterStatusDataBase3());
            String masterLogFile = resultado[0];
            String Pos = resultado[1];
            String stopSlave = "STOP SLAVE";
            String changeMasterTo = "CHANGE MASTER TO "
                    + "MASTER_HOST = '192.168.0.203', "
                    + "MASTER_USER = 'user13', "
                    + "MASTER_PASSWORD = 'admin', "
                    + "MASTER_LOG_FILE = '" + masterLogFile + "', "
                    + "MASTER_LOG_POS = " + Pos + ";";
            String startSlave = "START SLAVE";
            masterConectNow=3;
            retorno = true;
        } catch (Exception e) {
            retorno = false;
        }
        return retorno;
    }
    
    private static String showMasterStatusDataBase2() {
        com.database2.PublicacionMaestro_Service service = new com.database2.PublicacionMaestro_Service();
        com.database2.PublicacionMaestro port = service.getPublicacionMaestroPort();
        return port.showMasterStatus();
    }

    private static String showMasterStatusDataBase3() {
        com.database3.PublicacionMaestro_Service service = new com.database3.PublicacionMaestro_Service();
        com.database3.PublicacionMaestro port = service.getPublicacionMaestroPort();
        return port.showMasterStatus();
    }
    
    public boolean showMasterOnlineDatabase2(){
        boolean retorno=false;
        try{
            showMasterStatusDataBase2();
            retorno=true;
        }
        catch(Exception e){
            retorno=false;
        }
        return retorno;
    }
    public boolean showMasterOnlineDatabase3(){
        boolean retorno=false;
        try{
            showMasterStatusDataBase3();
            retorno=true;
        }
        catch(Exception e){
            retorno=false;
        }
        return retorno;
    }

    public int getMasterConectNow() {
        return masterConectNow;
    }

    public void setMasterConectNow(int masterConectNow) {
        this.masterConectNow = masterConectNow;
    }
    
    
}
