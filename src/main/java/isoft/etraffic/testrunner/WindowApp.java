package isoft.etraffic.testrunner;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import isoft.etraffic.enums.Database;
import isoft.etraffic.db.DBQueries;

public class WindowApp {

	private JFrame frame;
	private JTable table;
	JComboBox<String> scopeLst, modulesLst, transactionsLst, channelsLst, sddiChannelsLst, environmentsLst, databaseLst;
	DefaultTableModel model;

	/** Launch the application */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowApp window = new WindowApp();
					window.frame.setTitle("E-Traffic Test Automation");
					window.frame.setIconImage(
							new ImageIcon(System.getProperty("user.dir") + "\\attachments\\projectavatar.jpg")
									.getImage());
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/** Create the application. */
	public WindowApp() {
		initialize();
	}

	/** Initialize the contents of the frame. */
	@SuppressWarnings("serial")
	private void initialize() {
		createFrame();
		final String[][] databaseArray = { { "" }, { "TEST1", "TEST2" }, { "ERDB" }, { "TRFUAT", "TRFUAT2" },
				{ "DEVDB" } };

		scopeLst = new JComboBox<String>();
		scopeLst.setModel(
				new DefaultComboBoxModel<String>(new String[] { "Smoke", "Regression", "By Selected Transaction" }));
		scopeLst.setBounds(50, 20, 180, 20);
		frame.getContentPane().add(scopeLst);

		modulesLst = new JComboBox<String>();
		modulesLst.setModel(new DefaultComboBoxModel<String>(new String[] { "Modules", "VHL", "DRL", "CTA", "SPL" }));
		modulesLst.setBounds(50, 60, 110, 20);
		frame.getContentPane().add(modulesLst);

		channelsLst = new JComboBox<String>();
		channelsLst.setModel(new DefaultComboBoxModel<String>(new String[] { "FTF", "SDDI" }));
		channelsLst.setBounds(200, 60, 110, 20);
		frame.getContentPane().add(channelsLst);

		sddiChannelsLst = new JComboBox<String>();
		sddiChannelsLst.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Online", "PublicAccess", "TrustedAgent", "CallCenter" }));
		sddiChannelsLst.setBounds(350, 60, 110, 20);
		frame.getContentPane().add(sddiChannelsLst);
		sddiChannelsLst.setEnabled(false);

		transactionsLst = new JComboBox<String>();
		transactionsLst.setEnabled(false);
		transactionsLst.setBounds(200, 105, 300, 20);
		frame.getContentPane().add(transactionsLst);
		// transactionsLst.addItem(servicesArray[0][0]);

		JButton getTrnsBtn = new JButton("Load xml files");
		getTrnsBtn.setBounds(50, 100, 120, 30);
		frame.getContentPane().add(getTrnsBtn);

		environmentsLst = new JComboBox<String>();
		environmentsLst.setModel(new DefaultComboBoxModel<String>(new String[] { "", "Stagging", "ER", "UAT", "DEV" }));
		environmentsLst.setBounds(50, 150, 110, 20);
		frame.getContentPane().add(environmentsLst);

		databaseLst = new JComboBox<String>();
		databaseLst.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));
		databaseLst.setBounds(200, 150, 110, 20);
		frame.getContentPane().add(databaseLst);

		final JButton addBtn = new JButton("Add");
		addBtn.setBounds(350, 150, 60, 20);
		frame.getContentPane().add(addBtn);
		addBtn.setEnabled(false);

		final JButton runBtn = new JButton("Run");
		runBtn.setBounds(420, 150, 60, 20);
		runBtn.setEnabled(false);
		frame.getContentPane().add(runBtn);

		modulesLst.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				transactionsLst.removeAllItems();
			}
		});

		scopeLst.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				enableListBasedOnValue(scopeLst, transactionsLst, "By Selected Transaction");
				transactionsLst.removeAllItems();
				// addBtn.setEnabled(false);
			}
		});

		channelsLst.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				enableListBasedOnValue(channelsLst, sddiChannelsLst, "SDDI");
				transactionsLst.removeAllItems();
				// addBtn.setEnabled(false);
			}
		});

		sddiChannelsLst.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				transactionsLst.removeAllItems();
				// addBtn.setEnabled(false);
			}
		});

		environmentsLst.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				databaseLst.removeAllItems();
				addBtn.setEnabled(true);
				int x = environmentsLst.getSelectedIndex();
				for (int i = 0; i < databaseArray[x].length; i++) {
					databaseLst.addItem(databaseArray[x][i]);
				}
			}
		});

		getTrnsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setFilePath();
				// addBtn.setEnabled(false);
			}
		});
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String scope = (String) scopeLst.getSelectedItem();
				String module = (String) modulesLst.getSelectedItem();
				String channel = (String) channelsLst.getSelectedItem();
				String transaction = "";
				if (transactionsLst.isVisible()) {
					transaction = (String) transactionsLst.getSelectedItem();
				}

				if (sddiChannelsLst.isEnabled()) {
					channel = channel + "-" + (String) sddiChannelsLst.getSelectedItem();
				}
				String env = (String) environmentsLst.getSelectedItem();
				Object[] row = { scope, module, transaction, channel, env, false };

				model = (DefaultTableModel) table.getModel();

				model.addRow(row);
				runBtn.setEnabled(true);
			}
		});

		runBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (environmentsLst.getSelectedItem().toString().equals(""))
					JOptionPane.showMessageDialog(null, "Environmet is mandatory!");
				else {
					if (JOptionPane.showConfirmDialog(null,
							"Are you sure to execute selected scripts on "
									+ environmentsLst.getSelectedItem().toString() + " using "
									+ databaseLst.getSelectedItem().toString() + " Database.") == 0) {
						setDatabaseConnection();
						runSelectedScripts();
					} else {
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(40, 180, 500, 350);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable) e.getSource();
				int row = target.getSelectedRow();
				int column = target.getSelectedColumn();
				if (column == 5) {
					if (target.getRowCount() == 1)
						runBtn.setEnabled(false);
					((DefaultTableModel) table.getModel()).removeRow(row);
				}
			}
		});

		scrollPane.setViewportView(table);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Scope", "Module", "Transaction", "Channel", "Env", "Remove" }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Object.class, Object.class, Object.class, Object.class, Object.class,
					Boolean.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});

	}

	protected void runSelectedScripts() {

		int rowCount = model.getRowCount();
		String[][] tableRow = new String[rowCount][5];
		for (int rowNum = 0; rowNum < rowCount; rowNum++) {
			System.out.println("Selected Scripts:");
			System.out.print("Row#" + (rowNum + 1) + "");
			for (int colNum = 0; colNum < 5; colNum++) {
				System.out.print(" | ");
				tableRow[rowNum][colNum] = model.getValueAt(rowNum, colNum).toString();
				System.out.print(model.getValueAt(rowNum, colNum).toString());
			}
		}
		System.out.println();

		for (int rowNum = 0; rowNum < tableRow.length; rowNum++) {
			ThreadRun temp = new ThreadRun(tableRow[rowNum]);
			temp.start();
		}
	}

	private void createFrame() {
		frame = new JFrame();
		frame.setSize(new Dimension(1000, 1000));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setSize(600, 600);
	}

	private void enableListBasedOnValue(JComboBox<String> parentLst, JComboBox<String> childLst, String value) {
		String selectedValue = (String) parentLst.getSelectedItem();
		if (selectedValue.equals(value))
			childLst.setEnabled(true);
		else
			childLst.setEnabled(false);
	}

	public void setFilePath() {
		String folderName = "";
		transactionsLst.removeAll();
		List<String> fileList = new ArrayList<String>();

		// if (scopeLst.getSelectedItem().toString().equals("Smoke"))
		// if (modulesLst.getSelectedItem().toString().equals("FTF")) {
		// folderName = System.getProperty("user.dir") + "\\Smoke\\" +
		// modulesLst.getSelectedItem().toString()
		// + "FTF";
		// fileList = getXmlFile(folderName);
		// } else {
		// folderName = System.getProperty("user.dir") + "\\Smoke\\" +
		// modulesLst.getSelectedItem().toString()
		// + sddiChannelsLst.getSelectedItem().toString();
		// fileList = getXmlFile(folderName);
		// }
		//
		// if (scopeLst.getSelectedItem().toString().equals("Regression")) {
		// if (channelsLst.getSelectedItem().toString().equals("FTF"))
		// fileList.add(modulesLst.getSelectedItem().toString() +
		// channelsLst.getSelectedItem().toString());
		// else
		// fileList.add(modulesLst.getSelectedItem().toString() +
		// sddiChannelsLst.getSelectedItem().toString());
		// }

		if (scopeLst.getSelectedItem().toString().equals("By Selected Transaction")) {
			if (channelsLst.getSelectedItem().toString().equals("FTF")) {
				folderName = System.getProperty("user.dir") + "\\XMLFiles" + "\\Run"
						+ modulesLst.getSelectedItem().toString() + "FTF";
				fileList = getXmlFile(folderName);
			} else {
				folderName = System.getProperty("user.dir") + "\\XMLFiles" + "\\Run"
						+ modulesLst.getSelectedItem().toString() + "SDDI";
				List<String> fileListtemp = getXmlFile(folderName);
				// fileList.clear();
				for (String name : fileListtemp) {
					if (name.contains(sddiChannelsLst.getSelectedItem().toString())) {
						fileList.add(name);
					}
				}
			}
		} else {
			if (channelsLst.getSelectedItem().toString().equals("FTF"))
				fileList.add(modulesLst.getSelectedItem().toString() + channelsLst.getSelectedItem().toString());
			else
				fileList.add(modulesLst.getSelectedItem().toString() + sddiChannelsLst.getSelectedItem().toString());

		}
		System.out.println("folderName: " + folderName);
		if (fileList.size() == 0)
			JOptionPane.showMessageDialog(null, "No Transactions found!");
		else
			fillTransactionList(fileList);
	}

	private void fillTransactionList(List<String> fileList) {
		transactionsLst.removeAll();
		for (String name : fileList) {
			transactionsLst.addItem(name);
			//System.out.println(name);
		}
	}

	public List<String> getXmlFile(String filePath) {
		File file = new File(filePath);

		List<String> fileList = new ArrayList<String>();
		try {
			String[] files = file.list();

			for (String name : files) {
				name = name.substring(0, name.indexOf("."));
				fileList.add(name);
			}
		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "No Transactions found!");
		}
		return fileList;
	}

	private void setDatabaseConnection() {
		String selectedDatabase = databaseLst.getSelectedItem().toString();
		switch (selectedDatabase) {
		case "TEST1":
			DBQueries.database = Database.Test1;
			break;
		case "TEST2":
			DBQueries.database = Database.Test2;
			break;
		case "ERDB":
			DBQueries.database = Database.ERDB;
			break;
		case "TRFUAT":
			DBQueries.database = Database.TRFUAT;
			break;
		case "TRFUAT2":
			DBQueries.database = Database.TRFUAT2;
			break;
		default:
			DBQueries.database = Database.Test1;
			break;
		}
	}
}
