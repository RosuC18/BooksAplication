package usermanagement.db;



import usermanagement.BookList;
import usermanagement.GeneratePDF;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static usermanagement.db.PostgresConn.LoadConn;

public class DBBookList {

    //=================================New Book=========================================
    public boolean newBook(BookList u) {

        System.out.println(u);
        int update=-1;
        boolean isInserted=false;
        PreparedStatement pst=null;
        try {

            PreparedStatement pSt = LoadConn().prepareStatement("INSERT INTO books (author,title,id_user) VALUES(?,?,?)");
            pSt.setString(1,u.getAuthorname());

           pSt.setString(2, u.getTitlename());

           pSt.setLong(3, u.getId_user());
           pSt.executeUpdate();
            GeneratePDF gPdf = new GeneratePDF();
            gPdf.createPDF(u.getAuthorname());
            pSt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            isInserted=false;

        }
        return isInserted;

    }


//======================================== List&Search=====================================
    public List<BookList> getBookList (int idUser, String search) {

        BookList mbl =null;
        List<BookList> list = new ArrayList<>();

        try {

            PreparedStatement pSt = LoadConn().prepareStatement("select * from books where id_user=? and author like CONCAT( '%',?,'%') ORDER BY author desc ");


            pSt.setInt(1, idUser);
            pSt.setString(2, search);
            ResultSet rs = pSt.executeQuery();

            while (rs.next()) {

                mbl = new BookList();
                mbl.setId_user(rs.getInt("id_user"));
                mbl.setAuthorname(rs.getString("author"));
                mbl.setTitlename(rs.getString("title"));

                list.add(mbl);

            }

            rs.close();
            pSt.close();


        } catch (SQLException  e) {
            e.printStackTrace();
        }


        return list;
    }

//==============================Open book by author===============================
    public void open(BookList opnB){
        System.out.println("met open");
        try {
            PreparedStatement pSt = LoadConn().prepareStatement("select * from books where author=? or title=? and id_user=?");
           pSt.setString(1, opnB.getAuthorname());
           pSt.setString(2,opnB.getTitlename());
            pSt.setLong(3, opnB.getId_user());
           ResultSet rs =pSt.executeQuery();
            System.out.println("opendb1");
            while(rs.next()) {
                String authorname = rs.getString("author");
                GeneratePDF gPdf = new GeneratePDF();
                gPdf.openPDF(authorname.trim());

                System.out.println("opendb2");
            }
            pSt.close();

        } catch (SQLException  e) {
            throw new RuntimeException(e);
        }

    }

//==============================Delete one by one==============================
public void delete(BookList delB)  {


        boolean isDeleted=false;
    try {
        PreparedStatement pSt = LoadConn().prepareStatement("delete from books where author=? and id_user=?");
        pSt.setString(1, delB.getAuthorname());
        pSt.setLong(2, delB.getId_user());
        int val = pSt.executeUpdate();
        if(val!=-1)
            isDeleted=true;
        System.out.println(isDeleted);
        pSt.close();
    } catch (SQLException  e) {
        throw new RuntimeException(e);
    }
}
    //==============================Delete all==============================
    public void deleteAll(BookList delB)  {


        boolean isDeleted=false;
        try {
            PreparedStatement pSt = LoadConn().prepareStatement("delete from books where id_user=? ");
          //  pSt.setString(1, delB.getAuthorname());
           pSt.setLong(1, delB.getId_user());
            int val = pSt.executeUpdate();
            if(val!=-1)
                isDeleted=true;
            System.out.println(isDeleted);
            pSt.close();
        } catch (SQLException  e) {
            throw new RuntimeException(e);
        }
    }
}
