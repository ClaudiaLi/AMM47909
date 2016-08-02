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
 * Articoli messi in vendita
 */

public class Articoli {
    
    private int id;
    private String nomeArticolo;
    private String urlImmagine;
    private String descrizione;
    private double prezzo;
    private int quantita; 
    private int idVenditore;    // id di colui che vende l'articolo
    
    /* Costruttori */
    public Articoli(){
         this.id = 0;
         this.idVenditore = 0;
         this.nomeArticolo = "";
         this.urlImmagine = "";
         this.descrizione = "";
         this.prezzo = 0.0;
         this.quantita = 0;
    }
    
    public Articoli(int id, String nome, String url, String descrizione, double prezzo, int quantita, int idVenditore){
         this.id = id;
         this.nomeArticolo = nome;
         this.urlImmagine = url;
         this.descrizione = descrizione;
         this.prezzo = prezzo;
         this.quantita = quantita;
         this.idVenditore = idVenditore;
    }
    
    /** 
     *  @return id
     */
    public int getId() {
        return id;
    }

    /** 
     *  @param id setta l'id preso come parametro
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /** 
     *  @return nomeArticolo dell'articolo
     */
    public String getNomeArticolo() {
        return nomeArticolo;
    }

    /** 
     *  @param nome the name to set
     */
    public void setNomeArticolo(String nome) {
        this.nomeArticolo = nome;
    }
    
    /** 
     *  @return url dell'immagine dell'articolo
     */
    public String getUrlImmagine() {
        return urlImmagine;
    }

    /** 
     *  @param url the url to set
     */
    public void setUrlImmagine(String url) {
        this.urlImmagine = url;
    }
    
    /** 
     *  @return descrizione dell'articolo
     */
    public String getDescrizione() {
        return descrizione;
    }

    /** 
     *  @param descrizione dell'articolo
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    /** 
     *  @return prezzo dell'articolo
     */
    public double getPrezzo() {
        return prezzo;
    }

    /** 
     * @param prezzo price to set
     */
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
    
    /** 
    *   @return quantità disponibile dell'articolo
    */
    public int getQuantita() {
        return quantita;
    }

    /** 
     *  @param quantita quantità disponibile passata come parametro
     */
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    } 
    
    /** 
     *  @return idVenditore di colui che vende l'articolo
     */
    public int getIdVenditore() {
        return idVenditore;
    }

    /** 
     *  @param idVenditore 
     */
    public void setIdVenditore(int idVenditore) {
        this.idVenditore = idVenditore;
    }
    
    @Override
    public String toString(){
       return nomeArticolo;
    }
}
