package homework3;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;

public class StaffSystem {

	static String sql;

	public static void main(String[] args) throws SQLException, ParseException {
		Scanner in = new Scanner(System.in);
		for (;;) {
			System.out.println();
			System.out.println("1.查询人员信息列表");
			System.out.println("2.根据条件查询单个人员信息");
			System.out.println("3.新增人员信息");
			System.out.println("4.修改人员信息");
			System.out.println("5.删除人员信息");
			System.out.println("6.退出");
			System.out.println();
			System.out.print("请输入功能序号：");
			int funcNum = Integer.parseInt(in.nextLine());
			switch (funcNum) {
			case 1:
				sql = "select * from staff";
				DoSQL.printSQL(sql);
				break;
			case 2:
				int id;
				int symbol = 0;
				System.out.println("请输入列名的序号");
				System.out.println("1.编号");
				System.out.println("2.姓名");
				System.out.println("3.年龄");
				System.out.println("4.生日");
				System.out.println("5.通信地址");
				id = Integer.parseInt(in.nextLine()) - 1;
				switch (id) {
				case 0:
					System.out.println("请输入查找方式的序号");
					System.out.println("1.大于");
					System.out.println("2.等于");
					System.out.println("3.小于");
					symbol = Integer.parseInt(in.nextLine()) - 1;
					break;
				case 1:
					System.out.println("请输入查找方式的序号");
					System.out.println("1.精确查找");
					System.out.println("2.模糊查找");
					symbol = Integer.parseInt(in.nextLine()) - 1;
					break;
				case 2:
					System.out.println("请输入查找方式的序号");
					System.out.println("1.大于");
					System.out.println("2.等于");
					System.out.println("3.小于");
					symbol = Integer.parseInt(in.nextLine()) - 1;
					break;
				case 3:
					System.out.println("请输入查找方式的序号");
					System.out.println("1.早于");
					System.out.println("2.等于");
					System.out.println("3.晚于");
					symbol = Integer.parseInt(in.nextLine()) - 1;
					break;
				case 4:
					System.out.println("请输入查找方式的序号");
					System.out.println("1.精确查找");
					System.out.println("2.模糊查找");
					symbol = Integer.parseInt(in.nextLine()) - 1;
					break;
				}
				System.out.println("查找内容");
				String text = in.nextLine();
				sql = DoSQL.writeSearchSQL(sql, id, symbol, text);
				DoSQL.printSQL(sql);
				break;
			case 3:
				System.out.println("请输入新增的人员信息(以空格分隔)：");
				System.out.println("编号 姓名 年龄 生日 通信地址");
				String inputInfo = in.nextLine();
				String[] newInfo = inputInfo.split("\\s+");
//				sql = "Insert into staff (sno, sname, sage, sbirth, sadd) Values ('";
//				for (int i = 0; i < newInfo.length - 1; i++) {
//					sql += newInfo[i] + "', '";
//				}
//				sql += newInfo[4] + "');";
////				for (String ss : newInfo) {
////					sql += ss + "', '";
////				}
////				System.out.println(sql);
//				DoSQL.executeSQL(sql);
				DoSQL.executeInsertConsole(newInfo);
				System.out.println("已新增数据");
				break;
			case 4:
				System.out.println("请输入修改人员的当前编号：");
				String inputSno = in.nextLine();
				System.out.println("请输入列名的序号");
				System.out.println("1.编号");
				System.out.println("2.姓名");
				System.out.println("3.年龄");
				System.out.println("4.生日");
				System.out.println("5.通信地址");
				int changedCol = Integer.parseInt(in.nextLine()) - 1;
				System.out.println("请输入修改后的信息：");
				String tobeChanged = in.nextLine();
				sql = DoSQL.writeChangeSQL(sql, changedCol, inputSno, tobeChanged);
				DoSQL.executeSQL(sql);
				System.out.println("已修改数据");
				break;
			case 5:
				System.out.println("请输入删除人员的编号：");
				String deleteSno = in.nextLine();
				sql = "Delete From Staff Where sno='" + deleteSno + "'";
				DoSQL.executeSQL(sql);
				System.out.println("已删除数据");
				break;
			case 6:
				System.exit(0);
			}

		}

	}

}
