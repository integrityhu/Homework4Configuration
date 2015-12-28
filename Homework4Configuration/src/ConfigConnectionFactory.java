import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class ConfigConnectionFactory {
    
    public static Connection getPGConnection(String host,String port,String db,String user, String passwd, boolean ssl) throws SQLException {
        //jdbc:postgresql://<host>:<port>/<database>
        String url = "jdbc:postgresql://"+host+":"+port+"/"+db;
        Properties props = new Properties();
        props.setProperty("user",user);
        props.setProperty("password",passwd);
        props.setProperty("charSet","utf-8");
        props.setProperty("ssl",Boolean.toString(ssl));
        Connection conn = DriverManager.getConnection(url, props);
        return conn;
    }

    public static Connection getSQLiteConnection(String file) throws SQLException, ClassNotFoundException {
        //X:\\data.db
        String url = "jdbc:sqlite:"+file;
        Class.forName("org.sqlite.JDBC");  
        Connection conn = DriverManager.getConnection(url); 
        return conn;
    }

    public static DataSource getMySQLDataSource(String host,String port,String db,String user, String passwd) throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername(user);
        dataSource.setPassword(passwd);
        //jdbc:mysql://<host>:<port>/<database>
        dataSource.setUrl("jdbc:mysql://"+host+":"+port+"/"+db+"?useUnicode=true&autoReconnect=true&characterEncoding=UTF-8");
        
        return dataSource;
    }

}
