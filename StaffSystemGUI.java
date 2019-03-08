package homework3;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;

public class StaffSystemGUI extends JFrame {

	private JPanel contentPane;
	private JTextField text_content;
	Object[] colNames = new Object[] { "编号", "姓名", "年龄", "生日", "通信地址" };
	CardLayout card = new CardLayout(0, 0);
	JTable table_select = new JTable(new Object[][] { { null, null, null, null, null } }, colNames);
	String[] availableID = new String[] { "编号", "姓名", "年龄", "生日", "通信地址" };
	String[][] availableSymbol = new String[][] { { "大于", "等于", "小于" }, { "精确查找", "模糊查找" }, { "大于", "等于", "小于" },
			{ "早于", "等于", "晚于" }, { "精确查找", "模糊查找" } };
	JComboBox chooseID = new JComboBox(availableID);
	JComboBox chooseSymbol = new JComboBox(availableSymbol[0]);
	private JTable table_view;
	String sql;
	boolean hasChanged = false;
	List<Integer> changedRow = new LinkedList<Integer>();
	List<Integer> changedCol = new LinkedList<Integer>();
//	Iterator<Integer> itRow = changedRow.iterator();
//	Iterator<Integer> itCol = changedCol.iterator();
//	int rowSelected = table_view.getSelectedRow();
//	int rowCountNow;
//	int colCountNow;
//	Object[][] tableViewNow = new Object[rowCountNow][];
//	int rowSQLCount;
//	Object[][] tableSQLNow = new Object[rowSQLCount][];

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StaffSystemGUI frame = new StaffSystemGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//	public int GetRowSQL() throws SQLException {
//		rowSQLCount = 0;
//		Statement statement = DBConnection.getConnection().createStatement();
//		sql = "select * from staff";
//		ResultSet rs = statement.executeQuery(sql);
//		List<Object[]> rowData = new LinkedList<Object[]>();
//		while (rs.next()) {
//			rowSQLCount++;
//		}
//		return rowSQLCount;
//	}

//	
//	public void UpdateTableView() {
//		rowCountNow = table_view.getModel().getRowCount();
//		colCountNow = table_view.getModel().getColumnCount();
//		for (int i = 0; i < rowCountNow; i++) {
//			tableViewNow[i][0] = table_view.getValueAt(i, 0).toString();
//			tableViewNow[i][1] = table_view.getValueAt(i, 1).toString();
//			tableViewNow[i][2] = table_view.getValueAt(i, 2).toString();
//			tableViewNow[i][3] = table_view.getValueAt(i, 3).toString();
//			tableViewNow[i][4] = table_view.getValueAt(i, 4).toString();
//		}
//	}

	public StaffSystemGUI() {

		setIconImage(Toolkit.getDefaultToolkit().getImage("/Users/cassendra/Downloads/猫猫.png"));
		setTitle("人员管理系统");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		JPanel p_card = new JPanel(card);
		contentPane.add(p_card, "name_42293493139015");
		p_card.setLayout(card);

		JPanel p_welcome = new JPanel();
		p_card.add(p_welcome, "p_welcome");
		p_welcome.setBorder(new EmptyBorder(10, 100, 50, 100));
		p_welcome.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lbl_welcome = new JLabel("人员管理系统");
		lbl_welcome.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lbl_welcome.setHorizontalAlignment(SwingConstants.CENTER);
		p_welcome.add(lbl_welcome);

		JButton btn_gotoSelect = new JButton("查询人员信息");
		btn_gotoSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(p_card, "p_select");
				DoSQL.updateTableSQL(table_select);
			}
		});
		p_welcome.add(btn_gotoSelect);

		JButton btn_gotoChange = new JButton("修改人员信息");
		btn_gotoChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(p_card, "p_change");
				DoSQL.updateTableSQL(table_view);
			}
		});
		p_welcome.add(btn_gotoChange);

		JPanel p_select = new JPanel();
		p_card.add(p_select, "p_select");
		p_select.setLayout(new BorderLayout(0, 0));

		JPanel p_sNorth = new JPanel();
		p_select.add(p_sNorth, BorderLayout.NORTH);
		p_sNorth.setLayout(new BorderLayout(0, 0));

		JButton btn_sBack = new JButton("返回");
		btn_sBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(p_card, "p_welcome");
			}
		});
		p_sNorth.add(btn_sBack, BorderLayout.WEST);

		JPanel p_sInfo = new JPanel();
		p_sNorth.add(p_sInfo, BorderLayout.CENTER);
		GridBagLayout gbl_p_sInfo = new GridBagLayout();
		gbl_p_sInfo.columnWidths = new int[] { 108, 82, 130, 0 };
		gbl_p_sInfo.rowHeights = new int[] { 27, 0 };
		gbl_p_sInfo.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_p_sInfo.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		p_sInfo.setLayout(gbl_p_sInfo);

		GridBagConstraints gbc_chooseID = new GridBagConstraints();
		gbc_chooseID.anchor = GridBagConstraints.NORTH;
		gbc_chooseID.insets = new Insets(0, 0, 0, 5);
		gbc_chooseID.gridx = 0;
		gbc_chooseID.gridy = 0;
		p_sInfo.add(chooseID, gbc_chooseID);
		chooseID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseSymbol.removeAllItems();
				int index = chooseID.getSelectedIndex();
				for (int i = 0; i < availableSymbol[index].length; i++) {
					chooseSymbol.addItem(availableSymbol[index][i]);
				}
			}
		});

		GridBagConstraints gbc_chooseSymbol = new GridBagConstraints();
		gbc_chooseSymbol.anchor = GridBagConstraints.NORTH;
		gbc_chooseSymbol.insets = new Insets(0, 0, 0, 5);
		gbc_chooseSymbol.gridx = 1;
		gbc_chooseSymbol.gridy = 0;
		p_sInfo.add(chooseSymbol, gbc_chooseSymbol);

		text_content = new JTextField();
		GridBagConstraints gbc_text_content = new GridBagConstraints();
		gbc_text_content.fill = GridBagConstraints.HORIZONTAL;
		gbc_text_content.anchor = GridBagConstraints.NORTH;
		gbc_text_content.gridx = 2;
		gbc_text_content.gridy = 0;
		p_sInfo.add(text_content, gbc_text_content);
		text_content.setColumns(10);

		JButton btn_select = new JButton("查询");
		btn_select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = chooseID.getSelectedIndex();
				int symbol = chooseSymbol.getSelectedIndex();
				String text = text_content.getText();
				sql = DoSQL.writeSearchSQL(sql, id, symbol, text);
				DoSQL.showSearch(table_select, sql);
			}
		});
		p_sNorth.add(btn_select, BorderLayout.EAST);

		JPanel p_sCenter = new JPanel();
		p_select.add(p_sCenter);
		p_sCenter.setLayout(new CardLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		p_sCenter.add(scrollPane, "name_37968046663990");

		scrollPane.setViewportView(table_select);

		JPanel p_change = new JPanel();
		p_card.add(p_change, "p_change");
		p_change.setLayout(new BorderLayout(0, 0));

		JPanel p_cNorth = new JPanel();
		p_change.add(p_cNorth, BorderLayout.NORTH);
		p_cNorth.setLayout(new BorderLayout(0, 0));

		JButton btn_cBack = new JButton("返回");
		p_cNorth.add(btn_cBack, BorderLayout.WEST);
		btn_cBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(p_card, "p_welcome");
			}
		});

		ImageIcon catIcon = new ImageIcon("/Users/cassendra/Downloads/猫猫.png");
		catIcon.setImage(catIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));

		JButton btn_change = new JButton("新增");
		p_cNorth.add(btn_change, BorderLayout.EAST);

		JLabel lbl_logo = new JLabel("");
		lbl_logo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "请打高分谢谢！", "来自作者 施玥 的消息", JOptionPane.CLOSED_OPTION);
			}
		});
		lbl_logo.setIcon(catIcon);
		lbl_logo.setHorizontalAlignment(JLabel.CENTER);
		p_cNorth.add(lbl_logo);

		JPanel p_cCenter = new JPanel();
		p_change.add(p_cCenter, BorderLayout.CENTER);
		p_cCenter.setLayout(new CardLayout(0, 0));

		JScrollPane scrollPane2 = new JScrollPane();
		p_cCenter.add(scrollPane2, "name_46168734772387");

		table_view = new JTable();
		table_view.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				table_view.getModel().addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						if (e.getFirstRow() != 0) {
							switch (e.getColumn()) {
							case 0:
								if (!DoJudge.isNumeric(table_view.getValueAt(e.getFirstRow(), e.getColumn()).toString())) {
									JOptionPane.showMessageDialog(null, "编号必须为四位数字", "来自作者 施玥 的消息", JOptionPane.CLOSED_OPTION);
									return;
								} else if (table_view.getValueAt(e.getFirstRow(), e.getColumn()).toString().length() != 4) {
									JOptionPane.showMessageDialog(null, "编号必须为四位数字", "来自作者 施玥 的消息", JOptionPane.CLOSED_OPTION);
									return;
								}
								break;
							case 1:
								break;
							case 2:
								if (!DoJudge.isNumeric(table_view.getValueAt(e.getFirstRow(), e.getColumn()).toString())) {
									JOptionPane.showMessageDialog(null, "年龄必须为数字", "来自作者 施玥 的消息", JOptionPane.CLOSED_OPTION);
									return;
								} else if (!DoJudge.isDateMatchAge(table_view.getValueAt(e.getFirstRow(), 3).toString(), table_view.getValueAt(e.getFirstRow(), 2).toString())) {
									JOptionPane.showMessageDialog(null, "年龄和生日不匹配", "来自作者 施玥 的消息", JOptionPane.CLOSED_OPTION);
								}
								break;
							case 3:
								//生日的格式不对
								if (DoJudge.isDateMatchAge(table_view.getValueAt(e.getFirstRow(), 3).toString(), table_view.getValueAt(e.getFirstRow(), 2).toString())) {
									JOptionPane.showMessageDialog(null, "年龄和生日不匹配", "来自作者 施玥 的消息", JOptionPane.CLOSED_OPTION);
								}
								break;
							case 4:
								break;
							}
							changedRow.add(e.getFirstRow());
							changedCol.add(e.getColumn());
							hasChanged = true;
						}
					}
				});

				if (e.getValueIsAdjusting()) {
					return;
				}

				if (table_view.getSelectedRow() == 0) {
					btn_change.setText("新增");
				} else if (hasChanged) {
					btn_change.setText("修改");
				} else {
					btn_change.setText("删除");
				}

			}
		});
		btn_change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				switch (btn_change.getText()) {
				case "新增":
					try {
						DoSQL.executeInsertSQL(table_view);
						DoSQL.updateTableSQL(table_view);
					} catch (Exception e1) {
						if (table_view.getValueAt(0, 0) == null && table_view.getValueAt(0, 1) == null) {
							JOptionPane.showMessageDialog(null, "新增不能为空，请至少输入编号、姓名", "来自作者 施玥 的消息",
									JOptionPane.CLOSED_OPTION);
						} else if (table_view.getValueAt(0, 0) == null) {
							JOptionPane.showMessageDialog(null, "请输入编号", "来自作者 施玥 的消息", JOptionPane.CLOSED_OPTION);
						} else if (table_view.getValueAt(0, 1) == null) {
							JOptionPane.showMessageDialog(null, "请输入姓名", "来自作者 施玥 的消息", JOptionPane.CLOSED_OPTION);
						} else {
							JOptionPane.showMessageDialog(null, "输入有误", "来自作者 施玥 的消息", JOptionPane.CLOSED_OPTION);
						}
					}
					break;
				case "修改":
					try {
						for (int i = 0; i < changedRow.size(); i++) {
							sql = DoSQL.writeChangeSQL(sql, changedCol.get(i),
									table_view.getValueAt(changedRow.get(i), 0).toString(),
									table_view.getValueAt(changedRow.get(i), changedCol.get(i)).toString());
							DoSQL.executeSQL(sql);
						}
						DoSQL.updateTableSQL(table_view);
						hasChanged = false;
						btn_change.setText("删除");
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "编号不能重复", "来自作者 施玥 的消息", JOptionPane.CLOSED_OPTION);
						return;
					}
					break;
				case "删除":
					sql = "Delete From Staff Where sno='"
							+ table_view.getValueAt(table_view.getSelectedRow(), 0).toString() + "'";
					DoSQL.executeSQL(sql);
					DoSQL.updateTableSQL(table_view);
					break;

				}
			}
		});
		scrollPane2.setViewportView(table_view);

	}

}
