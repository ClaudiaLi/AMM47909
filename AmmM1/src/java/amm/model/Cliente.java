/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.model;

/**
 *
 * @author Claudia
 */

public class Cliente extends Utente {
    
    /** Costruttori  */
    
    public Cliente() {
    }
    
    public Cliente(int id, String nome, String cognome, String username, String password, int idConto){
        super(id, nome, cognome, username, password, idConto);
    }

}
