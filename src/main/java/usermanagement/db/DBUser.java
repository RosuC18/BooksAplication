package usermanagement.db;

import usermanagement.User;

import java.sql.*;

import static usermanagement.db.PostgresConn.LoadConn;

public class DBUser {

    //============================================New User==========================================================
    public boolean newUser(User u) {

        System.out.println(u);

        boolean isInserted=false;
        try {

            PreparedStatement pSt = LoadConn().prepareStatement("INSERT INTO users (email, password) VALUES(?,?)");
            pSt.setString(1,u.getEmail());
            pSt.setString(2,u.getPwd());

            int insert = pSt.executeUpdate();
            if(insert!=-1)
                isInserted=true;
            System.out.println(isInserted);

            pSt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            isInserted=false;

        }


        return isInserted;
    }
//=========================================Login User================================================================
    public User login (String username, String password) {

        User u = null;
        try {
            PreparedStatement pSt = LoadConn().prepareStatement("select id, email from users where email=? and password=?");

            pSt.setString(1,username);
            pSt.setString(2,password);

            // 3. executie
            ResultSet rs = pSt.executeQuery();

            // atata timp cat am randuri
            while (rs.next()) {

                u = new User();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
            }
            rs.close();
            pSt.close();
        } catch (SQLException  e) {
            e.printStackTrace();
        }


        return u;
    }
    //===========================================Check Email====================================================
    public boolean checkEmail(String ReqEmail){
        String ResEmail=null;
        boolean chkOk=false;
        try {
            PreparedStatement pSt1=LoadConn().prepareStatement("select email from users ");
            ResultSet rs = pSt1.executeQuery();
            while (rs.next()) {
                ResEmail=rs.getString("email").trim();
                if (ReqEmail.equalsIgnoreCase(ResEmail) || ReqEmail==null){
                    chkOk=true;
                    break;
                }

            }}
        catch (SQLException e) {
            e.printStackTrace();}
        return chkOk;

    }

}
