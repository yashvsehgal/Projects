/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package travel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yash
 */



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DAO {
    
    private String db_url;
    private String driver;
    private String db_user;
    private String db_password;

    public DAO() {
        
        driver = "com.mysql.jdbc.Driver";
        db_url = "jdbc:mysql://localhost:3306/travelbuddy1";
        db_user = "root";
        db_password = "admin";
        
        try{
        Class.forName(driver);
        }
        catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        
    }
    
    public Connection getConnection(){
        Connection conn=null;
        try{
            conn = DriverManager.getConnection(db_url,db_user,db_password);
        }
        catch(SQLException ex){
          //  Logger.getLogger(DAO.class.getName()).log(Level.SEVERE,null,ex);
            System.out.println(ex.getMessage());
        }
        return conn;
        
    }
    
    public void close(Connection connection){
        if(connection != null){
            try{
                connection.close();
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void close(Statement statement){
        if(statement != null){
            try{
                statement.close();
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void close(ResultSet rs){
        if(rs != null){
            try{
                rs.close();
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    
    
    
    
    
    
}
