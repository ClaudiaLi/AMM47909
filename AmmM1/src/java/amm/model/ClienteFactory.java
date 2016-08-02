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

public class ClienteFactory {
    
    private static ClienteFactory singleton;
    String connectionString; 
    

    public void setConnectionString(String s){
	this.connectionString = s;
    }
    
    public String getConnectionString(){
            return this.connectionString;
    }  

    public static ClienteFactory getInstance() {
        if (singleton == null) {
            singleton = new ClienteFactory();
        }
        return singleton;
    }

    /* Costruttore */
    private ClienteFactory() {

    }


    
    /* Dato un id viene reso il cliente */
    public Cliente getClienteById(int id){
        Cliente customer = null;
        // Query che rende un cliente dato il suo id
        String query = "SELECT * FROM Cliente WHERE id = ?";
        //Apro la connessione e preparo lo statement
        try(Connection conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
            PreparedStatement stmt = conn.prepareStatement(query)){
            //Completo la query
            stmt.setInt(1, id);
            //Eseguo la query
            ResultSet set = stmt.executeQuery();
            
            if(set.next()) {
                customer = new Cliente();
                customer.setId(set.getInt("id"));
                customer.setNome(set.getString("nome"));
                customer.setCognome(set.getString("cognome"));
                customer.setUsername(set.getString("username"));
                customer.setPassword(set.getString("password"));
                customer.setIdConto(set.getInt("idSaldo"));
            }
        } catch(SQLException ex){
            Logger.getLogger(ClienteFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return customer;
    }  
    
    /* Trova il cliente in base a username e password */
    public Cliente findCliente(String username, String password){
        Cliente customer = null;
        // Query di selezione per username e password
        String query = "select * from Cliente where password = ? and username = ?";
        //Apro la connessione e preparo lo statement
        try(Connection conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
            PreparedStatement stmt = conn.prepareStatement(query)){
            //Completo la query
            stmt.setString(1, password);
            stmt.setString(2, username);
            //Eseguo la query
            ResultSet set = stmt.executeQuery();
           
            if(set.next()){
                customer = new Cliente();
                customer.setId(set.getInt("id"));
                customer.setNome(set.getString("nome"));
                customer.setCognome(set.getString("cognome"));
                customer.setUsername(set.getString("username"));
                customer.setPassword(set.getString("password"));
                customer.setIdConto(set.getInt("idSaldo"));
            }
        } catch(SQLException e) {
            Logger.getLogger(ClienteFactory.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return customer;
    }  
    
    /** 
     *  @param  idItem id dell'articolo da comprare
     *  @param  idSaldoCliente id cliente
     *  @param  idSaldoVenditore id venditore
     *  @return  true se l'operazione è andata a buon fine
     */
    public boolean buyArticolo(int idItem, int idSaldoCliente, int idSaldoVenditore) throws SQLException{
        boolean flag = false;
        
        // Recupero i conti del cliente e del venditore rispettivamente
        Saldo cliente = SaldoFactory.getInstance().getSaldoById(idSaldoCliente);
        Saldo venditore = SaldoFactory.getInstance().getSaldoById(idSaldoVenditore);
        double saldoCliente = cliente.getSaldo();
        double saldoVenditore = venditore.getSaldo();
        //Recupero l'articolo del quale si vuole effettuare l'acquisto
        Articoli item = ArticoliFactory.getInstance().getArticoliObjectById(idItem);
        //Recupero la quantità disponibile e il prezzo relativi all'articolo
        int quantita = item.getQuantita();
        double prezzo = item.getPrezzo();        
        
        // Se il cliente ha un saldo inferiore al prezzo non si può effettuare la transazione
        if(saldoCliente < prezzo)  return flag;
        
        // SQL relativo alla transazione
        String queryRimuovi = null;
        /* Se rimane un solo articolo in vendita, questo viene eliminato, altrimenti la sua quantità viene decrementata di 1 */ 
        if(quantita == 1){
            queryRimuovi = " DELETE FROM Articolo WHERE id = " + idItem;
        }
        else{
            queryRimuovi = " UPDATE Articolo SET quantita = " + (quantita - 1) + "WHERE id = " + idItem; 
        }
        /* Modifico il saldo cliente e il saldo venditore */
        String querySaldoCliente = " UPDATE Saldo SET saldo = " + (saldoCliente - prezzo) + "WHERE id = " + idSaldoCliente;
        String querySaldoVenditore = " UPDATE Saldo SET saldo = " + (saldoVenditore + prezzo) + "WHERE id = " + idSaldoVenditore;
        
        //variabili per la connessione e lo statement
        Connection conn = null;
        Statement stmt = null;
        //Connessione al database e completamento query
        try{ 
            conn = DriverManager.getConnection(connectionString, "ClaudiaLicheri", "47909");
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            //Eseguo le query e associo il numero di righe modificate
            int q1 = stmt.executeUpdate(queryRimuovi);
            int q2 = stmt.executeUpdate(querySaldoCliente);
            int q3 = stmt.executeUpdate(querySaldoVenditore);
            
            /* Se una delle query fallisce, riporto il db allo stato precedente */
            if(q1 != 1 || q2 != 1 || q3 != 1){ 
               conn.rollback();
            }else{
                /* Se le query sono andate a buon fine, rendo le modifiche permanenti 
                    e imposto il flag a true */
                conn.commit();
                flag = true;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            
                /* In caso venga lanciata un'eccezione provo a eseguire la rollback, 
                    la quale può generare ulteriori eccezioni, e imposto il flag a false*/
                try{
                    if(conn != null)
                        conn.rollback();
                }catch(SQLException ex1){
                    Logger.getLogger(ClienteFactory.class.getName()).log(Level.SEVERE, null, ex1);
                }
                flag = false;
            
        } // Chiudo tutte le risorse utilizzate
        finally{
            /* Effettuo la chiusura di ciò che è stato aperto, la quale può lanciare un'eccezione */
            try{
                if(stmt != null) 
                    stmt.close();
                if(conn != null){
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch(SQLException ex){
                Logger.getLogger(ClienteFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /* Nel caso vada tutto a buon fine restituisco true */
        return flag;
    }
}
