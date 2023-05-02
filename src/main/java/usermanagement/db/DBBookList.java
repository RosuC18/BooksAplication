package usermanagement.db;
import usermanagement.BookList;
import usermanagement.GeneratePDF;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static usermanagement.db.PostgresConn.LoadConn;

public class DBBookList {

    //=================================New Book=========================================
    public boolean newBook(BookList u) {
        boolean isInserted=false;
        try {

            PreparedStatement pSt = LoadConn().prepareStatement("INSERT INTO books (author,title,id_user) VALUES(?,?,?)");
            pSt.setString(1,u.getAuthorname());

           pSt.setString(2, u.getTitlename());

           pSt.setLong(3, u.getId_user());
           pSt.executeUpdate();

            String directory = "C:\\PDF_Java";
            File fileObject = new File(directory);
            String[] filesFolders = fileObject.list();
            String authorname=u.getAuthorname();
            boolean fileFind=false;
            for(String ff: filesFolders)
                if(ff.equalsIgnoreCase(authorname+".pdf")){
                   fileFind=true;
                   break;
            }
            else {
                    fileFind=false;
                }
            if(!fileFind)
            {
                GeneratePDF gPdf = new GeneratePDF();
            gPdf.createPDF(u.getAuthorname());}

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
        try {
            PreparedStatement pSt = LoadConn().prepareStatement("select author from books where  id_user=?");
           pSt.setLong(1, opnB.getId_user());
           ResultSet rs =pSt.executeQuery();

            while(rs.next()) {
                String authorname = rs.getString("author");
               if(opnB.getAuthorname().equalsIgnoreCase(authorname.trim())){

                GeneratePDF gPdf = new GeneratePDF();
                gPdf.openPDF(authorname.trim());
                   System.out.println("opendb2");

                break;

            }

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
//=====================================Check Authorname================================
public boolean chkAuthor(BookList author){
    boolean chkauthor=false;
    try {
        PreparedStatement pSt = LoadConn().prepareStatement("select author from books where  id_user=?");
        pSt.setLong(1, author.getId_user());
        ResultSet rs =pSt.executeQuery();

        while(rs.next()) {
            String authorname = rs.getString("author");
            if(author.getAuthorname().equalsIgnoreCase(authorname.trim())){

                System.out.println("check method");
                chkauthor=false;
                break;

            }
            else{
                chkauthor=true;
            }
        }
      if (rs.getRow()==0){
          chkauthor=true;
      }
        pSt.close();

    } catch (SQLException  e) {
        throw new RuntimeException(e);
    }
    return chkauthor;
}

}
