package org.jun;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JRadioButton;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;


public class LibraryManagement {

	private JFrame frmLibraryManagement;
	JTabbedPane tabbedPane;
	private JTextField textSearchBookId;
	private JTextField textSearchBookTitle;
	private JTextField textSearchFullName;
	private JTextField textSearchFirstName;
	private JTextField textSearchMI;
	private JTextField textSearchLastName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibraryManagement window = new LibraryManagement();
					window.frmLibraryManagement.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LibraryManagement() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLibraryManagement = new JFrame();
		frmLibraryManagement.setTitle("Library Management");
		frmLibraryManagement.setBounds(100, 100, 691, 474);
		frmLibraryManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmLibraryManagement.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmLibraryManagement.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		ButtonGroup partNameGroup = new ButtonGroup();
		
		JPanel panelSearch = new JPanel();
		tabbedPane.addTab("Book Search", null, panelSearch, null);
		panelSearch.setLayout(null);
		
		JLabel lblBookId = new JLabel("Book ID");
		lblBookId.setFont(new Font("Dialog", Font.BOLD, 13));
		lblBookId.setBounds(56, 27, 70, 19);
		panelSearch.add(lblBookId);
		
		textSearchBookId = new JTextField();
		textSearchBookId.setFont(new Font("Dialog", Font.PLAIN, 13));
		textSearchBookId.setBounds(160, 27, 167, 19);
		panelSearch.add(textSearchBookId);
		textSearchBookId.setColumns(10);
		
		JLabel lblBookTitle = new JLabel("Book Title");
		lblBookTitle.setFont(new Font("Dialog", Font.BOLD, 13));
		lblBookTitle.setBounds(362, 29, 90, 15);
		panelSearch.add(lblBookTitle);
		
		textSearchBookTitle = new JTextField();
		textSearchBookTitle.setFont(new Font("Dialog", Font.PLAIN, 13));
		textSearchBookTitle.setBounds(483, 27, 147, 19);
		panelSearch.add(textSearchBookTitle);
		textSearchBookTitle.setColumns(10);
		

		
		textSearchFullName = new JTextField();
		textSearchFullName.setBounds(160, 56, 470, 19);
		panelSearch.add(textSearchFullName);
		textSearchFullName.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(69, 83, 132, 23);
		panelSearch.add(lblFirstName);
		
		textSearchFirstName = new JTextField();
		textSearchFirstName.setBounds(160, 85, 114, 19);
		panelSearch.add(textSearchFirstName);
		textSearchFirstName.setColumns(10);
		
		JLabel lblMi = new JLabel("MI");
		lblMi.setBounds(303, 85, 36, 15);
		panelSearch.add(lblMi);
		
		textSearchMI = new JTextField();
		textSearchMI.setBounds(345, 85, 45, 19);
		panelSearch.add(textSearchMI);
		textSearchMI.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(424, 85, 90, 15);
		panelSearch.add(lblLastName);
		
		textSearchLastName = new JTextField();
		textSearchLastName.setBounds(516, 85, 114, 19);
		panelSearch.add(textSearchLastName);
		textSearchLastName.setColumns(10);
		
		JRadioButton rdbtnFullName = new JRadioButton("Full Name");
		rdbtnFullName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setFullNameEnabled(true);
			}
		});
		rdbtnFullName.setSelected(true);
		rdbtnFullName.setFont(new Font("Dialog", Font.BOLD, 13));
		rdbtnFullName.setBounds(40, 54, 112, 23);
		panelSearch.add(rdbtnFullName);
		
		JRadioButton rdbtnPartsOfName = new JRadioButton("");
		rdbtnPartsOfName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFullNameEnabled(false);
			}
		});
		rdbtnPartsOfName.setBounds(40, 85, 28, 23);
		panelSearch.add(rdbtnPartsOfName);
		partNameGroup.add(rdbtnFullName);
		partNameGroup.add(rdbtnPartsOfName);
		setFullNameEnabled(true);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*String query = "SELECT BOOK.Book_id, BOOK.Title, BOOK_AUTHORS.Author_name AS Authors, "
						+ " BOOK_COPIES.Branch_id, BOOK_COPIES.No_of_copies "
						+ " FROM BOOK_COPIES, BOOK, BOOK_AUTHORS "
						+ " WHERE BOOK_COPIES.Book_id = BOOK.Book_id AND BOOK_AUTHORS.Book_id = BOOK.Book_id "
						+ "  ";*/
				String query = "SELECT  DISTINCT  B.Book_id, "
						+ 				" B.Title, "
						+ 				" (SELECT GROUP_CONCAT(Author_name SEPARATOR ', ') "
						+ 				"  FROM BOOK B1, BOOK_AUTHORS A1  "
						+ 				"  WHERE B1.Book_id = A1.Book_id AND B.Book_id = B1.Book_id  "
						+ 				"  GROUP BY B1.Book_id) AS Authors, "
						+ 				" C.Branch_id, "
						+ 				" C.No_of_copies, "
						+ 				" C.No_of_copies - "
						+ 						" (SELECT COUNT(*) "
						+ 						"  FROM BOOK_LOANS L "
						+ 						"  WHERE B.Book_id = L.Book_id AND C.Branch_id = L.Branch_id) "
						+ 					" AS Available "
						+ 		" FROM BOOK B, BOOK_COPIES C, BOOK_AUTHORS A"
						+ 		" WHERE B.Book_id = C.Book_id AND A.Book_id = B.Book_id ";
				Statement stmt = DBConnector.instance().createStatement();
				
				if(fullNameEnabled) {
					boolean valid = false;
					String BookId = textSearchBookId.getText();	
					if(!(BookId == null || BookId.equals("") || BookId.matches("\\s+"))) {
						query += " AND B.Book_id LIKE '%" + BookId + "%' ";
						valid = true;
					}
					
					String fullName = textSearchFullName.getText();	
					if(!(fullName == null || fullName.equals("") || fullName.matches("\\s+"))) {
						query += " AND A.Author_name LIKE '%" + fullName + "%' ";
						valid = true;
					}
					
					query += " ;";
					if(!valid) {
						JOptionPane.showMessageDialog(null, "Invalid searching infos");
						return;
					}
					// while(!firstName.matches("^[^\\d\\s]+$"));
					ResultSet rs = null;
					
					try {
						System.out.println(query);
						rs = stmt.executeQuery(query);
						if(null == rs) {
							return;
						}
						
						ResultsModel model = new ResultsModel();
						model.setResultSet(rs);
						tableSearch.setModel(model);
					
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		
				} else {
					
					boolean valid = false;
					String BookId = textSearchBookId.getText();	
					if(!(BookId == null || BookId.equals("") || BookId.matches("\\s+"))) {
						query += " AND B.Book_id LIKE '%" + BookId + "%' ";
						valid = true;
					}
					
					String firstName = textSearchFirstName.getText();	
					if(!(firstName == null || firstName.equals("") || firstName.matches("\\s+"))) {
						query += " AND Fname LIKE '%" + firstName + "%' ";
						valid = true;
					}
					
					String MI = textSearchMI.getText();	
					if(!(MI == null || MI.equals("") || MI.matches("\\s+"))) {
						query += " AND MI LIKE '%" + MI + "%' ";
						valid = true;
					}
					
					String lastName = textSearchLastName.getText();	
					if(!(lastName == null || lastName.equals("") || lastName.matches("\\s+"))) {
						query += " AND Lname LIKE '%" + lastName + "%' ";
						valid = true;
					}
					
					query += " ;";
					
					if(!valid) {
						JOptionPane.showMessageDialog(null, "Invalid searching infos");
						return;
					}
					// while(!firstName.matches("^[^\\d\\s]+$"));
					ResultSet rs = null;
					
					try {
						System.out.println(query);
						rs = stmt.executeQuery(query);
						if(null == rs) {
							return;
						}
						
						ResultsModel model = new ResultsModel();
						model.setResultSet(rs);
						tableSearch.setModel(model);
					
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		
				}
			}
		});
		btnSearch.setBounds(84, 125, 117, 25);
		panelSearch.add(btnSearch);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				ResultsModel model = (ResultsModel) tableSearch.getModel();
				model.setResultSet(null);
				//tableSearch.setModel(model);
				
			}
		});
		btnClear.setBounds(424, 125, 117, 25);
		panelSearch.add(btnClear);
		
		JScrollPane scrollPaneSearch = new JScrollPane();
		scrollPaneSearch.setBounds(12, 162, 660, 227);
		panelSearch.add(scrollPaneSearch);
		
		tableSearch = new JTable();
		scrollPaneSearch.setViewportView(tableSearch);
		
		JButton btnCheckOut = new JButton("Check Out");
		btnCheckOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableSearch.getSelectedRow();
				if(-1 == row) {
					return;
				}
				
				String bookId = (String) tableSearch.getValueAt(row, 0);
				String branchId = (String) tableSearch.getValueAt(row, 3);
				textCheckOutBookId.setText(bookId);
				textCheckOutBranchId.setText(branchId);
				tabbedPane.setSelectedIndex(1);
			}
		});
		btnCheckOut.setBounds(262, 125, 117, 25);
		panelSearch.add(btnCheckOut);
		
		
		JPanel panelCheckOut = new JPanel();
		tabbedPane.addTab("Check Out", null, panelCheckOut, null);
		panelCheckOut.setLayout(null);
		
		textCheckOut = new JTextField();
		textCheckOut.setBackground(Color.WHITE);
		textCheckOut.setEditable(false);
		textCheckOut.setFont(new Font("Dialog", Font.PLAIN, 15));
		textCheckOut.setBounds(100, 46, 417, 30);
		panelCheckOut.add(textCheckOut);
		textCheckOut.setColumns(10);
		
		JLabel lblBookId_1 = new JLabel("Book ID");
		lblBookId_1.setBounds(100, 120, 70, 15);
		panelCheckOut.add(lblBookId_1);
		
		textCheckOutBookId = new JTextField();
		textCheckOutBookId.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				doUpdate();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				doUpdate();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				doUpdate();
			}
			
			private void doUpdate() {
				if(10 != textCheckOutBookId.getText().length()) {
					textCheckOut.setText(null);
					return;
				}
				textCheckOut.setText(getBookTitleById(textCheckOutBookId.getText()));
			}
		});
		textCheckOutBookId.setBounds(217, 118, 114, 19);
		panelCheckOut.add(textCheckOutBookId);
		textCheckOutBookId.setColumns(10);
		
		JLabel lblBranchId = new JLabel("Branch ID");
		lblBranchId.setBounds(378, 120, 70, 15);
		panelCheckOut.add(lblBranchId);
		
		textCheckOutBranchId = new JTextField();
		textCheckOutBranchId.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				doUpdate();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				doUpdate();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				doUpdate();
			}
			
			private void doUpdate() {
				if(10 != textCheckOutBookId.getText().length()) {
					textCheckOut.setText(null);
					return;
				}
				if(textCheckOutBranchId.getText().length() < 1) {
					textCheckOutAvailable.setText(null);
					textCheckOutCopies.setText(null);
					return;
				}
				String bookId = textCheckOutBookId.getText();
				String branchId = textCheckOutBranchId.getText();
				int copies = getNumCopies(bookId, branchId);
				if(copies < 0) {
					textCheckOutAvailable.setText(null);
					textCheckOutCopies.setText(null);
					return;
				}
				int checkedout = getNumBooksCheckedOut(bookId, branchId);
				if(checkedout < 0) {
					textCheckOutAvailable.setText(null);
					textCheckOutCopies.setText(null);
					return;
				}
				textCheckOutAvailable.setText( "" + (copies - checkedout));
				textCheckOutCopies.setText("" + copies);
			}
		});
		textCheckOutBranchId.setBounds(529, 118, 114, 19);
		panelCheckOut.add(textCheckOutBranchId);
		textCheckOutBranchId.setColumns(10);
		
		JLabel lblCardNo = new JLabel("Card NO");
		lblCardNo.setBounds(100, 189, 70, 15);
		panelCheckOut.add(lblCardNo);
		
		textCheckOutCardNo = new JTextField();
		textCheckOutCardNo.setBounds(217, 187, 114, 19);
		panelCheckOut.add(textCheckOutCardNo);
		textCheckOutCardNo.setColumns(10);
		
		JButton btnCheckOut_1 = new JButton("Check Out");
		btnCheckOut_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String bookId = textCheckOutBookId.getText();
				if((bookId == null || bookId.equals("") || bookId.matches("\\s+"))) {
					return;
				}
				String branchId = textCheckOutBranchId.getText();
				if((branchId == null || branchId.equals("") || branchId.matches("\\s+"))) {
					return;
				}
				String cardNo = textCheckOutCardNo.getText();
				if((cardNo == null || cardNo.equals("") || cardNo.matches("\\s+"))) {
					return;
				}
				
				int copies = getNumCopies(bookId, branchId);
				if(copies < 0) {
					return;
				}
				int checkedout = getNumBooksCheckedOut(bookId, branchId);
				if(checkedout < 0) {
					return;
				}
				
				if(checkedout >= copies) {
					JOptionPane.showMessageDialog(null, "All copies of " + bookId + " ckecked out. You can not check out");
					return;
				}
				
				int loansPerson = getLoansPerCard(cardNo);
				if(loansPerson < 0) {
					return;
				}
				if(loansPerson >= 3) {
					JOptionPane.showMessageDialog(null, "You have checked out 3 books, can not check out more");
					return;
				}
				
				String query = "INSERT INTO BOOK_LOANS (Book_id, Branch_id, Card_no, Date_out, Due_date) "
						+ " values ( " + bookId + "," + branchId + ", " + cardNo + ", "
						+ " CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY)" + "); ";
				Statement stmt = DBConnector.instance().createStatement();
				try {
					stmt.executeUpdate(query);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Check out " + bookId + " succeed. Due date is " + getDueDateFromCurrent());
				copies = getNumCopies(bookId, branchId);
				if(copies < 0) {
					textCheckOutAvailable.setText(null);
					textCheckOutCopies.setText(null);
					return;
				}
				checkedout = getNumBooksCheckedOut(bookId, branchId);
				if(checkedout < 0) {
					textCheckOutAvailable.setText(null);
					textCheckOutCopies.setText(null);
					return;
				}
				textCheckOutAvailable.setText( "" + (copies - checkedout));
				textCheckOutCopies.setText("" + copies);
			}
		});
		btnCheckOut_1.setBounds(100, 251, 117, 25);
		panelCheckOut.add(btnCheckOut_1);
		
		JLabel lblAvailableCopies = new JLabel("Available");
		lblAvailableCopies.setBounds(100, 147, 92, 15);
		panelCheckOut.add(lblAvailableCopies);
		
		textCheckOutAvailable = new JTextField();
		textCheckOutAvailable.setEditable(false);
		textCheckOutAvailable.setBounds(217, 149, 50, 19);
		panelCheckOut.add(textCheckOutAvailable);
		textCheckOutAvailable.setColumns(10);
		
		JLabel lblCopiesInBranch = new JLabel("Copies in Branch");
		lblCopiesInBranch.setBounds(377, 151, 140, 15);
		panelCheckOut.add(lblCopiesInBranch);
		
		textCheckOutCopies = new JTextField();
		textCheckOutCopies.setEditable(false);
		textCheckOutCopies.setBounds(529, 145, 50, 19);
		panelCheckOut.add(textCheckOutCopies);
		textCheckOutCopies.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_2, null);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_3, null);
	}
	
	private boolean fullNameEnabled = true;
	private JTable tableSearch;
	private JTextField textCheckOut;
	private JTextField textCheckOutBookId;
	private JTextField textCheckOutBranchId;
	private JTextField textCheckOutCardNo;
	private JTextField textCheckOutAvailable;
	private JTextField textCheckOutCopies;
	private void setFullNameEnabled(boolean enabled) {
		textSearchFirstName.setEnabled(!enabled);
		textSearchMI.setEnabled(!enabled);
		textSearchLastName.setEnabled(!enabled);
		textSearchFullName.setEnabled(enabled);
		fullNameEnabled = enabled;
	}
	
	private int getNumBooksCheckedOut(String bookId, String branchId) {
		if((bookId == null || bookId.equals("") || bookId.matches("\\s+"))) {
			return -1;
		}
		if((branchId == null || branchId.equals("") || branchId.matches("\\s+"))) {
			return -1;
		}
		
		String query = "SELECT COUNT(*) FROM BOOK_LOANS WHERE Book_id = " + bookId
				+ " AND Branch_id = " + branchId +" ; ";
		
		int copies = -1;
		Statement stmt = DBConnector.instance().createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			if(null == rs) {
				return -1;
			}
			if(rs.first()) {
				copies = rs.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return copies;
	}
	
	private int getLoansPerCard(String cardNo) {
		if((cardNo == null || cardNo.equals("") || cardNo.matches("\\s+"))) {
			return -1;
		}
		
		String query = "SELECT COUNT(*) FROM BOOK_LOANS WHERE Card_no = " + cardNo + " ; ";
		
		int loans = -1;
		Statement stmt = DBConnector.instance().createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			if(null == rs) {
				return -1;
			}
			if(rs.first()) {
				loans = rs.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return loans;
	}
	
	private int getNumCopies(String bookId, String branchId) {
		if((bookId == null || bookId.equals("") || bookId.matches("\\s+"))) {
			return -1;
		}
		if((branchId == null || branchId.equals("") || branchId.matches("\\s+"))) {
			return -1;
		}
		
		String query = "SELECT No_of_copies FROM BOOK_COPIES WHERE Book_id = " + bookId
				+ " AND Branch_id = " + branchId +" ; ";
		
		int copies = -1;
		Statement stmt = DBConnector.instance().createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			if(null == rs) {
				return -1;
			}
			if(rs.first()) {
				copies = rs.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return copies;
	}
	
	private String getBookTitleById(String bookId) {
		if((bookId == null || bookId.equals("") || bookId.matches("\\s+"))) {
			return null;
		}
		String query = "SELECT Title FROM BOOK WHERE Book_id = " + bookId + "; ";
		Statement stmt = DBConnector.instance().createStatement();
		
		ResultSet rs = null;
		String title = null;
		
		try {
			rs = stmt.executeQuery(query);
			if(null == rs) {
				return null;
			}
			if(rs.first()) {
				title = rs.getString(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return title;
	}
	
	private String getDueDateFromCurrent() {
		String query = "SELECT DATE_ADD(CURDATE(), INTERVAL 14 DAY);";
		Statement stmt = DBConnector.instance().createStatement();
		
		ResultSet rs = null;
		String due = null;
		
		try {
			rs = stmt.executeQuery(query);
			if(null == rs) {
				return null;
			}
			if(rs.first()) {
				due = rs.getString(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return due;
	}
}
