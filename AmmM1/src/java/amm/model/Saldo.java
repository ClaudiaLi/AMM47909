/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amm.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 *
 * @author Claudia
 * 
 * Saldo di utenti venditore e cliente
 */

public class Saldo {
    
    private int id; // id del conto dell'utente
    double saldo;   // saldo del conto
    
    /** Costruttori */
    public Saldo(){
        this.id = 0;
        this.saldo = 0.0;
    }
    
    public Saldo(int id, double saldo){
        this.id = id;
        this.saldo = saldo;
    }
    
    /** 
     *  @return id
     */
    public int getId() {
        return id;
    }

    /** 
     *  @param id setta l'id del conto preso come parametro
     */
    public void setId(int id) {
        this.id = id;
    }

    /** 
     *  @return saldo
     */
    public double getSaldo() {
        return saldo;
    }
}