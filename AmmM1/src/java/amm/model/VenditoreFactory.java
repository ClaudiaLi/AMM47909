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
 */

public class VenditoreFactory {
    
    private static VenditoreFactory singleton;
    String connectionString; 
    
    public void setConnectionString(String s){
	this.connectionString = s;
    }
    
    public String getConnectionString(){
            return this.connectionString;
    } 
    
    public static VenditoreFactory getInstance() {
        if (singleton == null) {
            singleton = new VenditoreFactory();
        }
        return singleton;
    }

    /* Costruttore*/
    private VenditoreFactory() {

    }

     /* Rende la lista dei venditori */
    public ArrayList<Venditore> getSellerList() {
        ArrayList<Venditore> sellerList = new ArrayList<>();
        
        try(Connection conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
            // creo lo statement
            Statement stmt = conn.createStatement()) {
            // Seleziono la lista di tutti i venditori
            String sql = "SELECT * FROM Venditore";
            // la eseguo
            ResultSet set = stmt.executeQuery(sql);
            // riempio la lista
            while (set.next()) {
                int id = set.getInt("id");
                String nome = set.getString("nome");
                String cognome = set.getString("cognome");
                String username = set.getString("username");
                String password = set.getString("password");
                int id_conto = set.getInt("idSaldo");
                Venditore venditore = new Venditore(id, nome, cognome, username, password, id_conto);
                
                sellerList.add(venditore);
            }
        } catch (SQLException ex) {
            
            Logger.getLogger(VenditoreFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sellerList;
    }
    
    /* Rende il venditore corrispondente all'id passato */
    public Venditore getVenditoreById(int id){
        // Query che rende il venditore dato il suo id
        String query = "select * from Venditore where id = ?";
        
        try(Connection conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
            // creo lo statement
            PreparedStatement stmt = conn.prepareStatement(query)){
            //Completo la query
            stmt.setInt(1, id);
            //Eseguo la query
            ResultSet set = stmt.executeQuery();
            
            if(set.next()) {
                Venditore venditore = new Venditore();
                venditore.setId(set.getInt("id"));
                venditore.setNome(set.getString("nome"));
                venditore.setCognome(set.getString("cognome"));
                venditore.setUsername(set.getString("username"));
                venditore.setPassword(set.getString("password"));
                venditore.setIdConto(set.getInt("idSaldo"));
                
                return venditore; 
            }
        } catch(SQLException ex) {
            Logger.getLogger(VenditoreFactory.class.getName()).log(Level.SEVERE, null, ex);  
        }
       
        return null;
    }    
    
    /* Trova il venditore associato all'username a alla password passati per parametro */
    public Venditore findVenditore(String username, String password){
        Venditore venditore = null;
        // Query di selezione per username e password
        String query = "select * from Venditore where password = ? and username = ?";
        
        try(Connection conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
            // creo lo statement
            PreparedStatement stmt = conn.prepareStatement(query)){
            //Completo la query
            stmt.setString(1, password);
            stmt.setString(2, username);
            //Eseguo la query
            ResultSet set = stmt.executeQuery();
            
            if(set.next()) {
                venditore = new Venditore();
                venditore.setId(set.getInt("id"));
                venditore.setNome(set.getString("nome"));
                venditore.setCognome(set.getString("cognome"));
                venditore.setUsername(set.getString("username"));
                venditore.setPassword(set.getString("password"));
                venditore.setIdConto(set.getInt("idSaldo"));
            }
        } catch(SQLException ex){
            Logger.getLogger(VenditoreFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return venditore;
    }
    
    /* Rende la lista di articoli relativi al venditore che viene passato per id */
    public ArrayList<Articoli> getArticoliObjectBySeller(int idVenditore){
        ArrayList<Articoli> lista = new ArrayList<>();
        
        /* verifica id corretto */
        if(VenditoreFactory.getInstance().getVenditoreById(idVenditore) != null){
            // Seleziono gli articoli relativi al venditore
            String sql = "SELECT * FROM Articolo WHERE idVenditore = ?";
            
            try(Connection conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                //completo la query
                stmt.setInt(1, idVenditore);
                //eseguo la query
                ResultSet set = stmt.executeQuery();
                //se ottengo un risultato costruisco la lista
                while (set.next()) {
                    int id = set.getInt("id");
                    String nomeArticolo = set.getString("nomeArticolo");
                    String urlImmagine = set.getString("urlImmagine");
                    String descrizione = set.getString("descrizione");
                    double prezzo = set.getDouble("prezzo");
                    int quantita = set.getInt("quantita");
                    int id_venditore = set.getInt("idVenditore");
                    Articoli item = new Articoli(id, nomeArticolo, urlImmagine, descrizione, prezzo, quantita,  id_venditore);
                    
                    lista.add(item);
                }
            } catch (SQLException ex) {
                // se la query fallisce viene sollevata una SQLexception
                Logger.getLogger(ArticoliFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
        else
            return null;
        
        return lista;
    }
    
}
