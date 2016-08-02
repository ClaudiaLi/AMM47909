/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.servlet;

import amm.model.Articoli;
import amm.model.Venditore;
import amm.model.Utente;
import amm.model.ArticoliFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Claudia
 */
@WebServlet(name = "Filter", urlPatterns = {"/filter.json"})
public class FilterServlet extends HttpServlet {

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
        // Se la sessione non esiste l'accesso è negato
        if(session == null){
           request.setAttribute("utente", "Cliente");
           request.getRequestDispatcher("accessoNegato.jsp").forward(request, response);
        }
        
        // Se l'utente è un venditore l'accesso è negato
        if(((Utente)session.getAttribute("utente") instanceof Venditore) || session.getAttribute("utente") == null){
            request.setAttribute("utente", "Cliente");
            request.getRequestDispatcher("accessoNegato.jsp").forward(request, response);
        }
        
        String command = request.getParameter("cmd");
        if (command != null) 
        {
            if (command.equals("search") && request.getParameter("q")!=null ) 
            {
                // Ricerca dell'articolo in base al pattern
                ArrayList<Articoli> listaArticoli = ArticoliFactory.getInstance()
                        .getArticoliObjectListByPattern(request.getParameter("q"));
                // Imposto la lista come attributo della request
                request.setAttribute("listaArticoli", listaArticoli);
                
                // Quando si restituisce del json e' importante segnalarlo ed evitare il caching
                response.setContentType("application/json");
                response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
                response.setHeader("Cache-Control", "no-store, no-cache, "
                        + "must-revalidate");
                // Genero il json con una jsp
                request.getRequestDispatcher("articoliJson.jsp").forward(request, response);
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
