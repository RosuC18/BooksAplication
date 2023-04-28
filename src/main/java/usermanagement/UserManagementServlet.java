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
        boolean newUser = false;
        if (action != null && action.equalsIgnoreCase("NEW")) {

            newUser= newUser(req, resp);
            if(newUser)
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
            newUser = loginUser(req, resp);
            if(newUser)
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
                error(resp, "Email or password incorect");
            }
        }

    }

    private boolean newUser(HttpServletRequest req, HttpServletResponse resp) {

        DBUser dbUser = new DBUser();
        boolean chkemail=false;
        boolean inserted=false;
        String email = req.getParameter("email");
        String pwd = req.getParameter("pwd");
        String confirmPwd = req.getParameter("confirmPwd");
        String accepthtml = req.getParameter("accept");


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
        chkemail= dbUser.checkEmail(email);
        if(!chkemail){
            inserted=true;
        }
        else {
            error(resp, "Email already in use");
            return false;
        }
        boolean chkpwd=false;
        chkpwd=checkPwd(req, resp);
        if(chkpwd){
            inserted=true;
        }
        else{
            return false;
        }

        boolean accept=false;
         if (accepthtml != null && accepthtml.equalsIgnoreCase("YES"))
             accept=true;
        User u = new User(email,pwd,confirmPwd, accept);
         inserted = dbUser.newUser(u);
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

//================================Check Complexity Password=============================
public boolean checkPwd(HttpServletRequest req,HttpServletResponse resp){
    boolean chkPwd=false;
    int passwordLength=8, upChars=0, lowChars=0;
    int special=0, digits=0;
    char ch;
    String pwd = req.getParameter("pwd");
    int total = pwd.length();

        for(int i=0; i<total; i++)
        {
            ch = pwd.charAt(i);
            if(Character.isUpperCase(ch))
                upChars++;
            else if(Character.isLowerCase(ch))
                lowChars++;
            else if(Character.isDigit(ch))
                digits++;
            else
                special++;
        }

    if(total >=8 && upChars!=0 && lowChars!=0 && digits!=0 && special!=0)
    {
           chkPwd=true;

    }
    else
    {
        if(total<passwordLength)
            error(resp,"\nThe Password's Length has to be of 8 characters or more.");
        if(upChars==0)
            error(resp,"\nThe Password must contain at least one uppercase character.");
        if(lowChars==0)
            error(resp,"\nThe Password must contain at least one lowercase character.");
        if(digits==0)
            error(resp,"\nThe Password must contain at least one digit.");
        if(special==0)
            error(resp,"\nThe Password must contain at least one special character.");
    }
    return chkPwd;
}

}
