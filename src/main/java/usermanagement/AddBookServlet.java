package usermanagement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import usermanagement.db.DBBookList;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/addbook")
public class AddBookServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession s = req.getSession();
        Object o = s.getAttribute("id");
        String authorname = req.getParameter("author");
        String titlename = req.getParameter("title");
        if(o!=null && (authorname!=null||titlename!=null))
        {
            long id_user = (int)o;
            BookList mbl = new BookList(authorname,titlename, id_user);
            DBBookList db=new DBBookList();
            db.newBook(mbl);
        }
        else
        {
            error(resp, "Operation forbidden. User is not logged in or authorname or title are not inserted");
        }
    }

    private void returnJsonResponse(HttpServletResponse response, String jsonResponse) {
        response.setContentType("application/json");
        PrintWriter pr = null;
        try {
            pr = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert pr != null;
        pr.write(jsonResponse);
        pr.close();
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
}