/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author user
 */
@WebServlet(name = "LoginApi", urlPatterns = {"/LoginApi"})
public class LoginApi extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginApi</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginApi at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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

        String requestCode = request.getParameter("requestCode");
        System.out.println("requestCode = " + requestCode);
        if (requestCode.equals("1")) {
            String userName = request.getParameter("username");
            System.out.println("username = " + userName);

            String password = request.getParameter("password");
            System.out.println("password = " + password);

            try {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                LoginDao dao = new LoginDao();
                LoginModel reqModel = new LoginModel();
                reqModel.setUsername(userName);
                reqModel.setPassword(password);

                LoginModel cardModel = dao.doLogin(reqModel);

                JSONObject jSONObject = new JSONObject();
                jSONObject.put("name", cardModel.getName());
                jSONObject.put("outCode", cardModel.getOutCode());
                jSONObject.put("outMessage", cardModel.getOutMessage());
                jSONObject.put("email", cardModel.getEmail());
                jSONObject.put("mobilr", cardModel.getMobile());

                System.out.println("Login Information = " + jSONObject);
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(jSONObject.toString());
                response.getWriter().flush();

            } catch (Exception ex) {
                Logger.getLogger(LoginApi.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else if(requestCode.equals("5")){
            
            //Exchange Houase
            try {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                LoginDao dao = new LoginDao();
                JSONArray ja = new JSONArray();
                for (LoginModel cardModel : dao.exchangeHouseName()) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", cardModel.getCode());
                    jSONObject.put("desc", cardModel.getDesc());
                    ja.add(jSONObject);
                }

                System.out.println("Exchange house list = " + ja);
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(ja.toString());
                response.getWriter().flush();
            } catch (Exception ex) {
                Logger.getLogger(LoginApi.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        }

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
