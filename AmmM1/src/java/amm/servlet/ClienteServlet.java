/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import amm.model.Articoli;
import amm.model.Utente;
import amm.model.Cliente;
import amm.model.Venditore;
import amm.model.ClienteFactory;
import amm.model.ArticoliFactory;
import amm.model.VenditoreFactory;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Claudia
 * 
 * Servlet per cliente.html
 */

@WebServlet(name = "Cliente", urlPatterns = {"/cliente.html"})
public class ClienteServlet extends HttpServlet {

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
         ArrayList<Articoli> listaArticoli = null;
        
        //La sessione non esiste perciò l'utente non è autorizzato
        if(session == null){
           request.setAttribute("utente", "cliente");
           request.getRequestDispatcher("accessoNegato.jsp").forward(request, response);
        }
        
        // Un venditore non è autorizzato
        if(((Utente)session.getAttribute("utente") instanceof Venditore) || session.getAttribute("utente") == null){
            request.setAttribute("utente", "cliente");
            request.getRequestDispatcher("accessoNegato.jsp").forward(request, response);
        }
        else{
            
            // Acquisto
            if( request.getParameter("conferma") != null)
            { 
                    // info del cliente
                    Cliente buyer = (Cliente)session.getAttribute("utente");
                    int id = -1;

                    // l'id è intero?
                    try{
                      id = Integer.parseInt(request.getParameter("idSelezionato"));
                    }catch(RuntimeException e){
                       //nonè stato selezionato nessun oggetto o l'id è sbagliato
                      request.setAttribute("pagamento", false);
                      request.setAttribute("errore", true); 
                      listaArticoli = ArticoliFactory.getInstance().getArticoliObjectList(); 
                      request.setAttribute("listaArticoli", listaArticoli);
                      request.getRequestDispatcher("cliente.jsp").forward(request, response);
                    }
                    
                    // info sull'oggetto selezionato
                    Articoli articoloSelezionato = ArticoliFactory.getInstance().getArticoliObjectById(id);
                    
                    // l'id dell'oggetto non è valido
                    if(articoloSelezionato == null){
                      request.setAttribute("pagamento", false);
                      request.setAttribute("errore", true); 
                      listaArticoli = ArticoliFactory.getInstance().getArticoliObjectList(); 
                      request.setAttribute("listaArticoli", listaArticoli);
                      request.getRequestDispatcher("cliente.jsp").forward(request, response);
                    }
                    
                    //risalgo al venditore dell'oggetto
                    int idVenditore = articoloSelezionato.getIdVenditore();
                    Venditore seller = (Venditore)VenditoreFactory.getInstance().getVenditoreById(idVenditore);

                    request.setAttribute("utente", "cliente");
                    request.setAttribute("articolo", articoloSelezionato);
                    
                    int idAccountBuyer = buyer.getIdConto();
                    int idAccountSeller = seller.getIdConto();
                    double prezzo = articoloSelezionato.getPrezzo();
                    try{
                        //l'utente ha confermato l'acquisto
                        if(ClienteFactory.getInstance().buyArticolo(id, idAccountBuyer, idAccountSeller))
                            request.setAttribute("pagamento", true);
                        else
                            request.setAttribute("pagamento", false);
                    }catch(SQLException e){
                        request.setAttribute("errore", true); 
                        request.setAttribute("pagamento", false);
                    }
                    //stampo l'esito della transazione
                    request.setAttribute("conferma", true); 
                    listaArticoli = ArticoliFactory.getInstance().getArticoliObjectList(); 
                    request.setAttribute("listaArticoli", listaArticoli);
                    request.getRequestDispatcher("cliente.jsp").forward(request, response);
            }
            //l'utente seleziona un articolo da cui ricaviamo il suo id
            if(request.getParameter("idArticolo") != null)
            {
                int idArticoloSelezionato = -1;
                
                //l'id è un intero?
                try{
                  idArticoloSelezionato = Integer.parseInt(request.getParameter("idArticolo"));
                }catch(RuntimeException e){ 
                  request.setAttribute("errore", true);
                  listaArticoli = ArticoliFactory.getInstance().getArticoliObjectList(); 
                  request.setAttribute("listaArticoli", listaArticoli);
                  request.getRequestDispatcher("cliente.jsp").forward(request, response);
                }
                
                // info sull'articolo selezionato
                Articoli articoloSelezionato = ArticoliFactory.getInstance().getArticoliObjectById(idArticoloSelezionato);
                
                //verifico l'id dell'articolo e ne faccio il riepilogo, altrimenti rimando l'utente alla pagina cliente
                if(articoloSelezionato!=null){
                    request.setAttribute("utente", "cliente");
                    session.setAttribute("articoloSelezionato", articoloSelezionato);
                    request.setAttribute("articolo", session.getAttribute("articoloSelezionato"));
                    request.getRequestDispatcher("riepilogo.jsp").forward(request, response);
                }
                else{
                    listaArticoli = ArticoliFactory.getInstance().getArticoliObjectList(); 
                    request.setAttribute("listaArticoli", listaArticoli);
                    request.getRequestDispatcher("cliente.jsp").forward(request, response);
                }
            }
            
            //lista degli oggetti da visualizzare nella pagina cliente
            listaArticoli = ArticoliFactory.getInstance().getArticoliObjectList(); 
            int size = listaArticoli.size();
            request.setAttribute("listaArticoli", listaArticoli);
            request.setAttribute("size", size);
            request.getRequestDispatcher("cliente.jsp").forward(request, response);
        }
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
