package usermanagement.db;



import usermanagement.BookList;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBBookList {

    protected static Connection LoadConn()
    {
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="Postgres.2023";
        Connection conn = null;
        try {
           conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public boolean newBook(BookList u) {

        System.out.println(u);

        boolean isInserted=false;
        try {
            PreparedStatement pSt = LoadConn().prepareStatement("INSERT INTO books (author,title,id_user) VALUES(?,?,?)");
            pSt.setString(1,u.getAuthorname());

           pSt.setString(2, u.getTitlename());

           pSt.setLong(3, u.getId_user());

            int insert = pSt.executeUpdate();
            if(insert!=-1)
                isInserted=true;
            System.out.println(isInserted);

            pSt.close();
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
    } finally {
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
        } finally {
        }
    }



    public static void main(String[] args) {

        DBBookList db = new DBBookList();


        List<BookList> l = db.getBookList(5,"");

        for(int i = 0;i<l.size();i++) {

            BookList mbl = (BookList) l.get(i);

            System.out.println(mbl.toString()); // just to test we get the right data from db
        }




    }
}
