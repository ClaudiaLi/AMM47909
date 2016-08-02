/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.servlet;

import amm.model.Articoli;
import amm.model.Utente;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import amm.model.Cliente;
import amm.model.ArticoliFactory;
import amm.model.VenditoreFactory;
import java.util.ArrayList;
/**
 *
 * @author Claudia
 * 
 * Servlet per venditore.html
 */

@WebServlet(name = "Venditore", urlPatterns = {"/venditore.html"})
public class VenditoreServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(false);
        
        // Se la sessione non esiste nego l'accesso
        if(session == null){
           request.setAttribute("utente", "Venditore");
           request.getRequestDispatcher("accessoNegato.jsp").forward(request, response);
        }
        // Ai clienti è negato l'accesso
        if(((Utente)session.getAttribute("utente") instanceof Cliente) || session.getAttribute("utente") == null){
            request.setAttribute("utente", "Venditore");
            request.getRequestDispatcher("accessoNegato.jsp").forward(request, response);
        }
        
       
        /* è stato premuto il pulsante "aggiungi": verifico i campi inseriti*/
        if(request.getParameter("aggiungi") != null){
                Articoli newItem = new Articoli();
                //Recupero dei campi
                String nome = request.getParameter("nomeArticolo");
                String descrizione = request.getParameter("descrizione");
                String imgUrl = request.getParameter("urlimmagine");
                int quantita = 0;
                double prezzo = 0.0;
                // Flag per controllare i campi 
                boolean flag = true;
                   
                /* Verifico che tutti i campi da passare alla pagina di riepilogo siano corretti */         
                //provo a convertire le stringhe in numeri
                try{
                    quantita = Integer.parseInt(request.getParameter("quantita"));
                    prezzo = Double.parseDouble(request.getParameter("prezzo"));
                }catch(RuntimeException e){
                    flag = false;
                }
                if(!(nome.equals("")) && nome != null){
                    newItem.setNomeArticolo(nome);
                }else
                    flag = false;
                    
                if(!(descrizione.equals("")) && descrizione != null){
                    newItem.setDescrizione(descrizione);
                }else
                    flag = false;
                   
                if(!(imgUrl.equals("")) && imgUrl != null){
                    newItem.setUrlImmagine(imgUrl);
                }else
                    flag = false;
  
                if(quantita > 0 )
                    newItem.setQuantita(quantita);
                else
                    flag = false;
                   
                if(prezzo > 0 )
                    newItem.setPrezzo(prezzo);
                else
                    flag = false;
                   
                // Se il flag è ancora vero significa che tutti i campi sono validi e posso aggiungere l'oggetto
                if(flag == true){
                    newItem.setIdVenditore(((Utente)session.getAttribute("utente")).getId());
                    if(ArticoliFactory.getInstance().insertArticolo(newItem)){
                        request.setAttribute("articolo", newItem);
                        request.setAttribute("conferma", true);
                    }
                    else{ 
                        request.setAttribute("errore", true);
                        request.setAttribute("messaggioErrore", "L'articolo non è stato inserito correttamente, riprova!");
                        int id = ((Utente)session.getAttribute("utente")).getId();
                        ArrayList<Articoli> listaArticoli = VenditoreFactory.getInstance().getArticoliObjectBySeller(id);
                        request.setAttribute("listaArticoli", listaArticoli);
                        if(listaArticoli != null)
                            request.setAttribute("listaSize", listaArticoli.size());
                        else
                            request.setAttribute("listaSize", 0); 
                        request.getRequestDispatcher("venditore.jsp").forward(request, response);
                        }
                } // Altrimenti mostro un messaggio d'errore
                else{
                    request.setAttribute("errore", true);
                    request.setAttribute("messaggioErrore", "L'articolo non è stato inserito correttamente, riprova!");
                    int id = ((Utente)session.getAttribute("utente")).getId();
                    ArrayList<Articoli> listaArticoli = VenditoreFactory.getInstance().getArticoliObjectBySeller(id);
                    request.setAttribute("listaArticoli", listaArticoli);
                    if(listaArticoli != null)
                        request.setAttribute("listaSize", listaArticoli.size());
                    else
                        request.setAttribute("listaSize", 0); 
                    request.getRequestDispatcher("venditore.jsp").forward(request, response);
                }
                   
                request.setAttribute("utente", "venditore");
                request.getRequestDispatcher("riepilogo.jsp").forward(request, response);
        }
           
        /* è stato premuto il pulsante "modifica": verifico i campi inseriti*/
        if(request.getParameter("modifica") != null){
            // recupero i campi
            String nome = request.getParameter("nomeArticolo");
            String descrizione = request.getParameter("descrizione");
            String imgUrl = request.getParameter("urlimmagine");  
            String price = request.getParameter("prezzo");
            String quant = request.getParameter("quantita");
            
            int idArticolo = 0;
               
                /* Verifico che sia stato selezionato un articolo */
                try{
                    idArticolo = Integer.parseInt(request.getParameter("idarticolo"));
                }catch(RuntimeException e){
                    request.setAttribute("errore", true);
                    request.setAttribute("messaggioErrore", "Errore! Scegli un articolo da modificare!");
                    int id = ((Utente)session.getAttribute("utente")).getId();
                    ArrayList<Articoli> listaArticoli = VenditoreFactory.getInstance().getArticoliObjectBySeller(id);
                    request.setAttribute("listaArticoli", listaArticoli);
                    if(listaArticoli != null)
                        request.setAttribute("listaSize", listaArticoli.size());
                    else
                        request.setAttribute("listaSize", 0); 
                    request.getRequestDispatcher("venditore.jsp").forward(request, response);
                }
                    
                // Ottengo l'articolo dal suo id
                Articoli articolo = ArticoliFactory.getInstance().getArticoliObjectById(idArticolo);
                // numero dei campi modificati
                int nCampi = 0; 
                // Uso il flag per il controllo
                boolean flag = true;   
                    
                /* La modifica avviene solo se i campi sono affettivamente modificati e le modifiche son valide. 
                    Altrimenti i campi rimangono come prima*/
                if(!(nome.equals("")) && nome!= null){
                    articolo.setNomeArticolo(nome);
                    nCampi++;
                }
                    
                if(!(descrizione.equals("")) && descrizione != null){
                    articolo.setDescrizione(descrizione);
                    nCampi++;
                }
                  
                if(!(imgUrl.equals("")) && imgUrl != null){
                    articolo.setUrlImmagine(imgUrl);
                    nCampi++;
                }
                  
                if(!(quant.equals("")) && quant != null){
                    try{
                        int quantita = Integer.parseInt(quant);
                        if(quantita > 0 ){
                            articolo.setQuantita(quantita);
                            nCampi++;
                        }
                        else 
                            flag = false;
                    }catch(RuntimeException e){
                        flag = false;
                    }
                }
                    
                if(!(price.equals("")) && price != null){
                    try{
                        double prezzo = Double.parseDouble(price);
                        if(prezzo > 0 ){
                            articolo.setPrezzo(prezzo);
                            nCampi++;
                        }
                        else
                            flag = false;
                        if(flag ==false)throw new NullPointerException();
                    }catch(RuntimeException e){
                        flag = false;
                    }
                }
                    
                /* Se non è stato modificato niente non viene visualizzato il riepilogo */
                if(nCampi == 0){
                    int id = ((Utente)session.getAttribute("utente")).getId();
                    ArrayList<Articoli> listaArticolo = VenditoreFactory.getInstance().getArticoliObjectBySeller(id);
                    request.setAttribute("listaArticoli", listaArticolo);
                    request.setAttribute("modifica", false);
                    if(listaArticolo != null)
                        request.setAttribute("listaSize", listaArticolo.size());
                    else
                        request.setAttribute("listaSize", 0); 
                    request.getRequestDispatcher("venditore.jsp").forward(request, response);
                }
                    
                // Il flag di controllo è vero quindi posso proseguire con la modifica
                if(flag == true){
                    articolo.setIdVenditore(((Utente)session.getAttribute("utente")).getId());
                    if(ArticoliFactory.getInstance().alterArticolo(articolo)){
                        request.setAttribute("articolo", articolo);
                        request.setAttribute("modifica", true);
                    }
                    else{ 
                        request.setAttribute("modifica", false);
                    } 
                } // Altrimenti verrà visualizzato l'errore
                else{
                    request.setAttribute("errore", true);
                    request.setAttribute("messaggioErrore", "Errore! La modifica non è avvenuta con successo! Riprova!");
                    int id = ((Utente)session.getAttribute("utente")).getId();
                    ArrayList<Articoli> listaArticoli = VenditoreFactory.getInstance().getArticoliObjectBySeller(id);
                    request.setAttribute("listaArticoli", listaArticoli);
                    if(listaArticoli != null)
                        request.setAttribute("listaSize", listaArticoli.size());
                    else
                        request.setAttribute("listaSize", 0); 
                    request.getRequestDispatcher("venditore.jsp").forward(request, response);
                }
                   
                request.setAttribute("utente", "venditore");
                request.getRequestDispatcher("riepilogo.jsp").forward(request, response);
        }
            
        /* è stato premuto il pulsante "elimina": verifico i campi inseriti*/
        if(request.getParameter("elimina") != null){
                int idArticolo = 0;
                /* Verifico che sia stato selezionato un articolo */
                try{
                     idArticolo = Integer.parseInt(request.getParameter("idarticolo"));
                }catch(RuntimeException e){
                    request.setAttribute("errore", true);
                    request.setAttribute("messaggioErrore", "Errore! Seleziona un articolo!");
                    int id = ((Utente)session.getAttribute("utente")).getId();
                    ArrayList<Articoli> listaArticolo = VenditoreFactory.getInstance().getArticoliObjectBySeller(id);
                    request.setAttribute("listaArticoli", listaArticolo);
                    if(listaArticolo != null)
                        request.setAttribute("listaSize", listaArticolo.size());
                    else
                        request.setAttribute("listaSize", 0); 
                    request.getRequestDispatcher("venditore.jsp").forward(request, response);
                }
                
                /* Eliminazione oggetto e verifica */
                if(ArticoliFactory.getInstance().removeArticolo(idArticolo)){
                    request.setAttribute("elimina", true);
                }else{
                    request.setAttribute("errore", true);
                    request.setAttribute("messaggioErrore", "Errore nell'eliminazione dell'oggetto.");
                }
                
                request.setAttribute("utente", "venditore");
                int id = ((Utente)session.getAttribute("utente")).getId();
                ArrayList<Articoli> listaArticolo = VenditoreFactory.getInstance().getArticoliObjectBySeller(id);
                request.setAttribute("listaArticoli", listaArticolo);
                if(listaArticolo != null)
                    request.setAttribute("listaSize", listaArticolo.size());
                else
                    request.setAttribute("listaSize", 0); 
                request.getRequestDispatcher("venditore.jsp").forward(request, response);
        }
              
            // Visualizzo il form di inserimento di default
            int id = ((Utente)session.getAttribute("utente")).getId();
            ArrayList<Articoli> listaArticolo = VenditoreFactory.getInstance().getArticoliObjectBySeller(id);
            request.setAttribute("listaArticoli", listaArticolo);
            if(listaArticolo != null)
                request.setAttribute("listaSize", listaArticolo.size());
            else
                request.setAttribute("listaSize", 0); 
            request.getRequestDispatcher("venditore.jsp").forward(request, response);
        
}


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
