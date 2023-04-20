package usermanagement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import usermanagement.db.DBUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/userManagement")
public class UserManagementServlet extends HttpServlet {




    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action"); // name as in the html form
        System.out.println("action is:" + action);
        boolean succes = false;
        if (action != null && action.equalsIgnoreCase("NEW")) {

            succes= newUser(req, resp);
            if(succes)
            {
                RequestDispatcher rd=req.getRequestDispatcher("login.html");
                try {
                    rd.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                error(resp, "there is an error while trying to create this user, pls try again");
            }


        } else if (action != null && action.equalsIgnoreCase("LOGIN")) {
            //afisare
            succes = loginUser(req, resp);
            if(succes)
            {
                RequestDispatcher rd=req.getRequestDispatcher("listMyStuff.jsp");
                try {
                    rd.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                RequestDispatcher rd=req.getRequestDispatcher("login.html");
                try {
                    rd.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (action != null && action.equalsIgnoreCase("OUT")) {
            HttpSession s = req.getSession();
            s.invalidate();
            try {
                resp.sendRedirect("login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

         else {
            System.out.println("action didn`t come ");
            error(resp, "error on ui side");
        }

    }

    private boolean newUser(HttpServletRequest req, HttpServletResponse resp) {


        String email = req.getParameter("email");
        String pwd = req.getParameter("pwd");
        String confirmPwd = req.getParameter("confirmPwd");
        String accepthtml = req.getParameter("accept");
        String offerhtml= req.getParameter("offer");

        System.out.println(pwd+confirmPwd);
        // validari
        if(!pwd.equals(confirmPwd))
        {
           error(resp, "Password is not the same as confirmed password");
           return false;
        }
        if(!accepthtml.equalsIgnoreCase("YES"))
        {
            error(resp, "You must accept terms and conditions");
            return false;
        }



        boolean accept=false;
        boolean offer=false;
         if (accepthtml != null && accepthtml.equalsIgnoreCase("YES"))
             accept=true;
        if (offerhtml != null && offerhtml.equalsIgnoreCase("YES"))
            offer=true;

        DBUser dbUser = new DBUser();
        User u = new User(email,pwd,confirmPwd, accept, offer);
        boolean inserted = dbUser.newUser(u);


        return inserted;

    }

    private boolean loginUser(HttpServletRequest req, HttpServletResponse resp) {
        User u = null;
        String email = req.getParameter("email");
        String pwd = req.getParameter("pwd");
        boolean isLoggedIn=false;

        DBUser dbUser = new DBUser();
        u = dbUser.login(email, pwd);
        if (u != null)
        {
            HttpSession s = req.getSession();
            s.setAttribute("id", u.getId());
            s.setAttribute("email", u.getEmail());
            isLoggedIn=true;
        }
       return isLoggedIn;
    }



    private void error( HttpServletResponse resp, String mesaj) {

        try {
            PrintWriter pw = resp.getWriter();
            pw.println(mesaj);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void returnJsonResponse(HttpServletResponse response, String jsonResponse) {
//        response.setContentType("application/json");
//        PrintWriter pr = null;
//        try {
//            pr = response.getWriter();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        assert pr != null;
//        pr.write(jsonResponse);
//        pr.close();
//    }

}
