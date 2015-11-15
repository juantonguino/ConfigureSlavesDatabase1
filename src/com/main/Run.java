/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.JDBC.GestorReplicacion;

/**
 *
 * @author juandiego
 */
public class Run {
     public static void main(String[] args) {
         GestorReplicacion gestor= new GestorReplicacion();
         System.out.println(gestor.showMasterOnlineDatabase2());
         System.out.println(gestor.showMasterOnlineDatabase3());
         while(true){
             if(gestor.getMasterConectNow()==2&&gestor.showMasterOnlineDatabase3()==true){
                 gestor.changeMasterTo3();
             }
             else if(gestor.getMasterConectNow()==3&&gestor.showMasterOnlineDatabase3()==false){
                 gestor.changeMasterTo2();
             }
         }
    }
}
