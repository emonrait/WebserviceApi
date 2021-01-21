/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testapi;

import com.erainfotechbd.agentbaning.dbconnection.DBConnectionHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class LoginDao {

    public LoginModel doLogin(LoginModel model) {
        LoginModel outmodel = new LoginModel();

        String username = "admin";
        String password = "admin";
        String name = "Emon Raihan";
        String mobile = "01816028491";
        String email = "emonrait@gmail.com";

        if (username.equals(model.getUsername()) && password.equals(model.getPassword())) {
            outmodel.setEmail(email);
            outmodel.setName(name);
            outmodel.setMobile(mobile);
            outmodel.setOutCode("0");
            outmodel.setOutMessage("Login Successfull");
        } else {
            outmodel.setOutCode("1");
            outmodel.setOutMessage("Login Unsuccessfull");
        }
        return outmodel;

    }

    public List<LoginModel> exchangeHouseName() {
        String sql = "SELECT INITCAP(COMP_NAME) AS COMPANYNAME, COMP_ID AS COMPANYID  FROM EMOB.UTL_COMPANY  WHERE COMP_TYPE = '3' ORDER BY COMP_ID";

        List<LoginModel> list = new ArrayList<>();
        Connection con = null;
        try {
            con = DBConnectionHandler.getConnection("O");
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LoginModel outModel = new LoginModel();
                outModel.setDesc(rs.getString("COMPANYNAME"));
                outModel.setCode(rs.getString("COMPANYID"));
                list.add(outModel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            DBConnectionHandler.releaseConnection(con);

        }
        return list;
    }

    public static void main(String[] args) {
        // Value Check
//        LoginDao loginDao = new LoginDao();
//        LoginModel model = new LoginModel();
//        model.setUsername("admin");
//        model.setPassword("admin");
//        //System.out.println("model = " + model);
//        LoginModel logininfo = loginDao.doLogin(model);
//        System.out.println("Name = " + logininfo.getName());
//        System.out.println("Email Id = " + logininfo.getEmail());
//        System.out.println("Mobil No = " + logininfo.getMobile());
//        System.out.println("Out Code = " + logininfo.getOutCode());
//        System.out.println("Out Message = " + logininfo.getOutMessage());

    }

}
