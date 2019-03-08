package homework3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	
	public static Connection getConnection()
	{

		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/homework3?useUnicode=true&characterEncoding=UTF-8";
			// MySQL配置时的用户名
			String user = "root";
			// MySQL配置时的密码
			String password = "123";
			// 连续数据库
			connection = DriverManager.getConnection(url, user, password);
//			if (!connection.isClosed())
//				System.out.println("Succeeded connecting to the Database!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// URL指向要访问的数据库名test
		return connection;
		
	}

}
