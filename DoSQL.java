package homework3;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DoSQL {

	public static void executeSQL(String sql) {
		try {
			Statement statement = DBConnection.getConnection().createStatement();
			statement.execute(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void printSQL(String sql) {
		try {
			Statement statement = DBConnection.getConnection().createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println("编号\t姓名\t年龄\t生日\t\t通信地址");
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
						+ rs.getString(4) + "\t" + rs.getString(5));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String writeSearchSQL(String sql, int id, int symbol, String text) {
		if (text.equals("")) {
			sql = "Select * from staff";
		} else if (id == 0) { // 编号
			if (symbol == 0) { // 大于
				sql = "Select * from staff where sno>" + text;
			} else if (symbol == 1) { // 等于
				sql = "Select * from staff where sno=" + text;
			} else { // 小于
				sql = "Select * from staff where sno<" + text;
			}
		} else if (id == 1) { // 姓名
			if (symbol == 0) { // 精确查找
				sql = "Select * from staff where sname='" + text + "'";
			} else { // 模糊查找
				sql = "Select * from staff where sname Like '%" + text + "%'";
			}
		} else if (id == 2) { // 年龄
			if (symbol == 0) { // 大于
				sql = "Select * from staff where sage>" + text;
			} else if (symbol == 1) { // 等于
				sql = "Select * from staff where sage=" + text;
			} else { // 小于
				sql = "Select * from staff where sage<" + text;
			}
		} else if (id == 3) { // 生日
			if (symbol == 0) { // 早于
				sql = "Select * from staff where sbirth<'" + text + "'";
			} else if (symbol == 1) { // 等于
				sql = "Select * from staff where sbirth='" + text + "'";
			} else { // 晚于
				sql = "Select * from staff where sbirth>'" + text + "'";
			}
		} else if (id == 4) { // 通信地址
			if (symbol == 0) { // 精确查找
				sql = "Select * from staff where sadd='" + text + "'";
				System.out.println(sql);
			} else { // 模糊查找
				sql = "Select * from staff where sadd Like '%" + text + "%'";
			}
		}
		return sql;
	}

	public static String writeChangeSQL(String sql, int changedCol, String changedSno, String changedContent) {
		switch (changedCol) {
		case 0:
			sql = "Update staff Set sno='" + changedContent + "' Where sno='" + changedSno + "'";
			break;
		case 1:
			sql = "Update staff Set sname='" + changedContent + "' Where sno='" + changedSno + "'";
			break;
		case 2:
			sql = "Update staff Set sage='" + changedContent + "' Where sno='" + changedSno + "'";
			break;
		case 3:
			sql = "Update staff Set sbirth='" + changedContent + "' Where sno='" + changedSno + "'";
			break;
		case 4:
			sql = "Update staff Set sadd='" + changedContent + "' Where sno='" + changedSno + "'";
			break;
		}
		return sql;
	}

	public static void executeInsertSQL(JTable table) throws SQLException, ParseException {
		PreparedStatement pst = DBConnection.getConnection()
				.prepareStatement("Insert into staff (sno, sname, sage, sbirth, sadd) Values (?,?,?,?,?)");
		pst.setString(1, table.getValueAt(0, 0).toString());
		pst.setString(2, table.getValueAt(0, 1).toString());
		if (table.getValueAt(0, 2) == null) {
			pst.setString(3, null);
		} else {
			pst.setInt(3, Integer.parseInt(table.getValueAt(0, 2).toString()));
		}
		if (table.getValueAt(0, 3) == null) {
			pst.setDate(4, null);
		} else {
			pst.setDate(4, DoDate.stringToSQLDate(table.getValueAt(0, 3).toString()));
		}
		if (table.getValueAt(0, 4) == null) {
			pst.setString(5, null);
		} else {
			pst.setString(5, table.getValueAt(0, 4).toString());
		}
		pst.execute();
	}

	public static void executeInsertConsole(String string[]) throws SQLException, ParseException {
		PreparedStatement pst = DBConnection.getConnection()
				.prepareStatement("Insert into staff (sno, sname, sage, sbirth, sadd) Values (?,?,?,?,?)");
		pst.setString(1, string[0]);
		pst.setString(2, string[1]);
		pst.setInt(3, Integer.parseInt(string[2]));
		pst.setDate(4, DoDate.stringToSQLDate(string[3]));
		pst.setString(5, string[4]);
		pst.execute();
	}
	
	public static void updateTableSQL(JTable table) {
		Object[] colNames = new Object[] { "编号", "姓名", "年龄", "生日", "通信地址" };
		try {
			Statement statement = DBConnection.getConnection().createStatement();
			String sql = "select * from staff";
			ResultSet rs = statement.executeQuery(sql);
			List<Object[]> rowData = new LinkedList<Object[]>();
			while (rs.next()) {
//				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(rs.getDate(4));
//				int birthYear = cal.get(Calendar.YEAR);
//				rowData.add(new Object[] { rs.getString(1), rs.getString(2), (2019 - birthYear), rs.getDate(4),
//						rs.getString(5) });
				rowData.add(new Object[] { rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDate(4),
						rs.getString(5) });
			}
			Object[][] data = new Object[rowData.size() + 1][];
			int i = 1;
			for (Object[] d : rowData) {
				data[i] = d;
				i++;
			}
			table.setModel(new DefaultTableModel(data, colNames));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void showSearch(JTable table, String sql) {
		Object[] colNames = new Object[] { "编号", "姓名", "年龄", "生日", "通信地址" };
		try {
			Statement statement = DBConnection.getConnection().createStatement();
			ResultSet rs = statement.executeQuery(sql);
			List<Object[]> rowData = new LinkedList<Object[]>();
			while (rs.next()) {
//				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(rs.getDate(4));
//				int birthYear = cal.get(Calendar.YEAR);
//				rowData.add(new Object[] { rs.getString(1), rs.getString(2), (2019 - birthYear), rs.getDate(4),
//						rs.getString(5) });
				rowData.add(new Object[] { rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDate(4),
						rs.getString(5) });
			}
			Object[][] data = new Object[rowData.size()][];
			int i = 0;
			for (Object[] d : rowData) {
				data[i] = d;
				i++;
			}
			table.setModel(new DefaultTableModel(data, colNames));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
