/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.model;


/**
 *
 * @author Claudia
 * 
 * Classe utente generale
 */

public abstract class Utente {

    private int id;
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private int idConto;
    
    
    /** Costruttori */
    public Utente(){
    }
    
    public Utente(int id, String nome, String cognome, String username, String password, int idConto)
    {
        this.id =id;
        this.nome = nome;
        this.cognome = cognome;
        this.username= username;
        this.password = password;
        this.idConto = idConto;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @param cognome the cognome to set
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /** 
     *  @return user id Saldo
     */
    public int getIdConto() {
        return idConto;
    }
    
    /** 
     *  @param idConto the id Saldo to set
     */
    public void setIdConto(int idConto) {
        this.idConto =  idConto;
    }
    
    /** 
     *  @return saldo dell'utente
     */
    public double getSaldoUtente() {
        Saldo saldoUtente = SaldoFactory.getInstance().getSaldoById(idConto);
        return saldoUtente.getSaldo();
    }
}
