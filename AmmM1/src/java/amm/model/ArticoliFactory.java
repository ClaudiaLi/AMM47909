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

public class ArticoliFactory {
    
    private static ArticoliFactory singleton;   
    String connectionString; 

    public void setConnectionString(String s){
	this.connectionString = s;
    }

    public String getConnectionString(){
            return this.connectionString;
    } 
    
    public static ArticoliFactory getInstance() {
        if (singleton == null) {
            singleton = new ArticoliFactory();
        }
        return singleton;
    }

    /* Costruttore */
    private ArticoliFactory() {

    }
    
    /* Rende la lista di articoli */
    public ArrayList<Articoli> getArticoliObjectList() {
        ArrayList<Articoli> listaArticoli = new ArrayList<>();
        
        /* Connessione al db e creazione Statement */
        try(Connection conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
            Statement stmt = conn.createStatement()) {
            // Definisco la query per ottenere l'elenco degli articoli
            String sql = "SELECT * FROM Articolo";
            // la eseguo
            ResultSet set = stmt.executeQuery(sql);
            // I risultati ottenuti vengono aggiunti alla lista
            while (set.next()) {
                int id = set.getInt("id");
                String nomeArticolo = set.getString("nomeArticolo");
                String urlImmagine = set.getString("urlImmagine");
                String descrizione = set.getString("descrizione");
                double prezzo = set.getDouble("prezzo");
                int quantita = set.getInt("quantita");
                int idVenditore = set.getInt("idVenditore");
                Articoli item = new Articoli(id, nomeArticolo, urlImmagine, descrizione, prezzo, quantita, idVenditore);
                
                listaArticoli.add(item);
            }
        } catch (SQLException ex) {
            // se la query fallisce viene sollevata una SQLexception
            Logger.getLogger(ArticoliFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaArticoli;
    }  
    
    /* Rende l'articolo di cui viene fornito l'id */
    public Articoli getArticoliObjectById(int id){
        Articoli item = null;
        
        String sql = "SELECT * FROM Articolo WHERE id = ?";
        
        // Connessione al db e creazione Statement 
        try(Connection conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            // completo la query
            stmt.setInt(1, id);
            // eseguo la query
            ResultSet set = stmt.executeQuery();
            // se ottengo un risultato costruisco l'oggetto
            if(set.next()) {
                String nomeArticolo = set.getString("nomeArticolo");
                String urlImmagine = set.getString("urlImmagine");
                String descrizione = set.getString("descrizione");
                double prezzo = set.getDouble("prezzo");
                int quantita = set.getInt("quantita");
                int id_venditore = set.getInt("idVenditore");
                item = new Articoli(id, nomeArticolo, urlImmagine, descrizione, prezzo, quantita,  id_venditore);
            }
        } catch (SQLException ex) {
            // se la query fallisce viene sollevata una SQLexception
            Logger.getLogger(ArticoliFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return item;
    }
    
    /* Rende true se l'articolo di cui viene fornito l'id viene rimosso con successo */
    public boolean removeArticolo(int idArticolo){
        boolean success = false;
        // Query per la rimozione dell'articolo
        String sql = "DELETE FROM Articolo WHERE id = ?";
        // Connessione al db e creazione Statement 
        try(Connection conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            // completo la query
            stmt.setInt(1, idArticolo);
            // eseguo la query
            int rows = stmt.executeUpdate();
            //Se la query è andata a buon fine setto il flag
            if(rows == 1) 
                success = true;
        } catch (SQLException ex) {
            // se la query fallisce viene sollevata una SQLexception
            Logger.getLogger(ArticoliFactory.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return success;
    }
    
    
    /* Rende true se l'oggetto passato come parametro viene inserito correttamente */
    public boolean insertArticolo(Articoli item){
        boolean success = false;
        // Query per l'aggiunta di un nuovo articolo
        String query = "INSERT INTO Articolo "
                        + "(id, nomeArticolo, urlImmagine, descrizione, prezzo, quantita, idVenditore) VALUES "
                        + "(default, ?, ?, ?, ?, ?, ?)";
        // Connessione al db e creazione Statement 
        try(Connection conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
            PreparedStatement stmt = conn.prepareStatement(query)) {
            // completo la query
            stmt.setString(1, item.getNomeArticolo());
            stmt.setString(2, item.getUrlImmagine());
            stmt.setString(3, item.getDescrizione());
            stmt.setDouble(4, item.getPrezzo());
            stmt.setInt(5, item.getQuantita());
            stmt.setInt(6, item.getIdVenditore());
            // eseguo la query
            int rows = stmt.executeUpdate();
            //Se la query è andata a buon fine setto il flag
            if(rows == 1)
                success = true; 
        }catch(SQLException ex){
            Logger.getLogger(ArticoliFactory.class.getName()).log(Level.SEVERE, null, ex);  
        }
        
        return success;
    }
    
    /* Rende true se l'oggetto passato come parametro viene modificato correttamente */
    public boolean alterArticolo(Articoli item){
        boolean success = false;
        // Query di modifica
        String sql = " UPDATE Articolo "
                    + "SET nomeArticolo = ?, urlImmagine = ?, descrizione = ?, prezzo = ?,"
                    + "quantita = ?, idVenditore = ? "
                    + "WHERE id = ? ";
        // Connessione al db e creazione Statement 
        try(Connection conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            // completo la query
            stmt.setString(1, item.getNomeArticolo());
            stmt.setString(2, item.getUrlImmagine());
            stmt.setString(3, item.getDescrizione());
            stmt.setDouble(4, item.getPrezzo());
            stmt.setInt(5, item.getQuantita());
            stmt.setInt(6, item.getIdVenditore());
            stmt.setInt(7, item.getId());
            // eseguo la query
            int rows = stmt.executeUpdate();
            //Se la query è andata a buon fine setto il flag
            if(rows == 1) 
                success = true;
        } catch (SQLException ex) {
           
            Logger.getLogger(ArticoliFactory.class.getName()).
            log(Level.SEVERE, null, ex);
        } 
       
        return success;
    }
    
    /* Rende la lista degli oggetti che contengono la stringa passata come parametro */
    public ArrayList<Articoli> getArticoliObjectListByPattern(String stringa) {
        ArrayList<Articoli> lista = new ArrayList<>();
        //Query di ricerca del pattern negli articoli
        String sql = "SELECT *" +
                     "FROM Articolo " + 
                     "WHERE nomeArticolo LIKE ? OR descrizione LIKE ?";         
        // Connessione al db e creazione Statement 
        try(Connection conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            // completo la query
            stringa = "%"+stringa+"%";
            stmt.setString(1, stringa);
            stmt.setString(2, stringa);
            // eseguo la query
            ResultSet set = stmt.executeQuery();
            // Aggiungo tutti gli articoli che contengono il pattern
            while (set.next()) {
                int id = set.getInt("id");
                String nomeArticolo = set.getString("nomeArticolo");
                String urlImmagine = set.getString("urlImmagine");
                String descrizione = set.getString("descrizione");
                double prezzo = set.getDouble("prezzo");
                int quantita = set.getInt("quantita");
                int id_venditore = set.getInt("idVenditore");
                Articoli car = new Articoli(id, nomeArticolo, urlImmagine, descrizione, prezzo, quantita, id_venditore);
                
                lista.add(car);
            }
        }catch(SQLException ex) {
           Logger.getLogger(ArticoliFactory.class.getName()).
            log(Level.SEVERE, null, ex);
        }
       
        return lista;
    }  
}