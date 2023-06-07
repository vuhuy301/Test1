package Controller;

import DAL.AccountDAO;
import DAL.SuportMessage;
import Model.Account;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Login_Controller extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Login_Controller</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login_Controller at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("views/login.jsp").forward(request, response);
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
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        AccountDAO ad = new AccountDAO();

        Account a = ad.login(userName, password);

        if (a == null) {
            SuportMessage.sendToast(session, 0, "Tài Khoản Hoặc Mật Khẩu Không Chính Xác !");
            request.getRequestDispatcher("views/login.jsp").forward(request, response);
        } else {
            if (a.getIsBlock()) {
                SuportMessage.sendToast(session, 0, "Tài Khoản Của Bạn Đã Bị Block !");
                request.getRequestDispatcher("views/login.jsp").forward(request, response);
            }else if (!a.getIsVerify()) {
                SuportMessage.sendToast(session, 2, "Tài Khoản Của Bạn Chưa Verify Email !");
                request.getRequestDispatcher("views/login.jsp").forward(request, response);
            }else {
                
                session.setAttribute("account", a);
                SuportMessage.sendToast(session, 1, "Đăng Nhập Thành Công !");
                response.sendRedirect("home");
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
