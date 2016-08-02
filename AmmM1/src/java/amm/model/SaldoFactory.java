/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Claudia
 * 
 * Gestione dei conti 
 */

public class SaldoFactory {

    private static SaldoFactory singleton;
    String connectionString; 
    
    public void setConnectionString(String s){
	this.connectionString = s;
    }

    public String getConnectionString(){
            return this.connectionString;
    } 
    
    public static SaldoFactory getInstance() {
        if (singleton == null) {
            singleton = new SaldoFactory();
        }
        return singleton;
    }

    /* Costruttore */
    private SaldoFactory() {

    }

    /* Rende la lista dei conti di tutti gli utenti */
    public ArrayList<Saldo> getSaldiList() {
        ArrayList<Saldo> listaSaldi = new ArrayList<>();
        /* Connessione al db e creazione Statement */
        try(Connection conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
            Statement stmt = conn.createStatement()) {
            // Query per ottenere tutti i conti
            String sql = "SELECT * FROM Saldo";
            // eseguo la query
            ResultSet set = stmt.executeQuery(sql);
            //aggiungo alla lista tutte le righe ottenute
            while (set.next()) {
                int idConto = set.getInt("id");
                double saldo = set.getDouble("saldo");
                Saldo conto = new Saldo(idConto, saldo);
                
                listaSaldi.add(conto);
            }
        } catch (SQLException ex) {
            
            Logger.getLogger(SaldoFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaSaldi;
    } 
    
    /* Rende il conto relativo all'id passato come parametro */
    public Saldo getSaldoById(int id){
        Saldo conto = null;
        // Query di selezione del saldo tramite id successivamente settato
        String sql = "SELECT * FROM Saldo WHERE id = ?";
        /* Connessione al db e creazione Statement */
        try(Connection conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            //completo la query
            stmt.setInt(1, id);
            //eseguo la query
            ResultSet set = stmt.executeQuery();
            //se ottengo un risultato rendo il conto
            if(set.next()) {
                int idConto = set.getInt("id");
                double saldo = set.getDouble("saldo");
                conto = new Saldo(idConto, saldo);
            }
        } catch (SQLException ex) {

            Logger.getLogger(SaldoFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return conto;
    }       
}
