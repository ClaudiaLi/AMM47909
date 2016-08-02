/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.model;

import java.util.ArrayList;

/**
 *
 * @author Claudia
 */

public class Venditore extends Utente{
    
    /** Costruttori */
        public Venditore() {
    }
        
    public Venditore(int id, String nome, String cognome, String username, String password, int idConto){
        super(id, nome, cognome, username, password, idConto);
    }


    /** 
     *  @return articoli in vendita
     */
    public ArrayList<Articoli> getAutoInVendita() {
        return VenditoreFactory.getInstance().getArticoliObjectBySeller(getId());
    }
    
}
