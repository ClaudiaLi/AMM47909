/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.servlet;

import amm.model.Articoli;
import amm.model.Utente;
import amm.model.Venditore;
import amm.model.Cliente;
import amm.model.SaldoFactory;
import amm.model.ArticoliFactory;
import amm.model.ClienteFactory;
import amm.model.VenditoreFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Claudia
 * 
 */
// Servlet per login.html
@WebServlet(name = "Login", urlPatterns = {"/login.html"}, loadOnStartup = 0)
public class LoginServlet extends HttpServlet {
    // Genero la stringa di connessione
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_CLEAN_PATH = "../../web/WEB-INF/db/ammdb";
    private static final String DB_BUILD_PATH = "WEB-INF/db/ammdb";

    @Override 
    public void init(){
        String dbConnection = "jdbc:derby:" + this.getServletContext().getRealPath("/") + DB_BUILD_PATH;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArticoliFactory.getInstance().setConnectionString(dbConnection);
        VenditoreFactory.getInstance().setConnectionString(dbConnection);
        ClienteFactory.getInstance().setConnectionString(dbConnection);
        SaldoFactory.getInstance().setConnectionString(dbConnection);
    }
    
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
        
        HttpSession session = request.getSession(true);
        
        
        if(session.getAttribute("utente") == null){
            /* L'utente è loggato? no, allora gestisco l'autenticazione */
            if(request.getParameter("submit") != null){
                // è stato premuto 'Accedi', verifico le credenziali
                String username = request.getParameter("username");
                String password = request.getParameter("pswd");   
                
                // Verifico che siano diverse da null
                if (username != null && password != null ){
                    //Controllo se è un cliente
                    Cliente buyer = ClienteFactory.getInstance().findCliente(username, password);
                    if(buyer != null){
                        //il cliente è loggato, imposto gli attributi di sessione
                        session.setAttribute("utente", buyer);
                        //Articoli in vendita da mostrare al cliente
                        ArrayList<Articoli> listaArticoli = ArticoliFactory.getInstance().getArticoliObjectList(); 
                        int size = listaArticoli.size();
                        request.setAttribute("listaArticoli", listaArticoli);
                        request.setAttribute("size", size);
                        request.getRequestDispatcher("cliente.jsp").forward(request, response);
                    }
                    
                    //L'utente non era un cliente, controllo se è un venditore
                    Venditore seller = VenditoreFactory.getInstance().findVenditore(username, password);
                    if(seller != null){
                        //utente loggato come venditore, imposto gli attributi di sessione
                        session.setAttribute("user", seller);
                        //Articoli in vendita del venditore
                        ArrayList<Articoli> listaArticoli = VenditoreFactory.getInstance().getArticoliObjectBySeller(seller.getId());
                        session.setAttribute("utente", seller);
                        request.setAttribute("listaArticoli", listaArticoli);
                        
                        /* Se la lista è vuota non mostro le opzioni di modifica*/
                        if(listaArticoli != null)
                            request.setAttribute("listaSize", listaArticoli.size());
                        else
                            request.setAttribute("listaSize", 0);  
                        request.getRequestDispatcher("venditore.jsp").forward(request, response);
                    }
                    
                    /* Se arrivo sin qui le credenziali non sono valide*/
                    request.setAttribute("errore", true);
                    request.getRequestDispatcher("login.jsp").forward(request, response);   
                    
                }
                // Le credenziali son nulle
                else{
                    request.setAttribute("errore", true);
                    request.getRequestDispatcher("login.jsp").forward(request, response);   
                }
            }
            // Richiamo la jsp per il login
            request.getRequestDispatcher("login.jsp").forward(request, response);    
        }
        /* L'utente è già loggato e lo rimando alla pagina appropriata */
        else{
                // Utente Venditore
                if((Utente)session.getAttribute("utente") instanceof Venditore){
                    int id = ((Utente)session.getAttribute("utente")).getId();
                    ArrayList<Articoli> listaArticoli = VenditoreFactory.getInstance().getArticoliObjectBySeller(id);
                    request.setAttribute("listaArticoli", listaArticoli);
                    if(listaArticoli != null)
                            request.setAttribute("listaSize", listaArticoli.size());
                        else
                            request.setAttribute("listaSize", 0); 
                    request.getRequestDispatcher("venditore.jsp").forward(request, response);
                }
                // Utente Cliente
                else{
                    ArrayList<Articoli> listaArticoli = ArticoliFactory.getInstance().getArticoliObjectList(); 
                    int size = listaArticoli.size();
                    request.setAttribute("listaArticoli", listaArticoli);
                    request.setAttribute("size", size);
                    request.getRequestDispatcher("cliente.jsp").forward(request, response);
                }

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
