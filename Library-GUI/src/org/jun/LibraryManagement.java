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

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class LibraryManagement {

    private JFrame frmLibraryManagement;
    JTabbedPane tabbedPane;
    private JTextField textSearchBookId;
    private JTextField textSearchBookTitle;
    private JTextField textSearchFullName;
    private JTextField textSearchFirstName;
    private JTextField textSearchMI;
    private JTextField textSearchLastName;
    private JComboBox<String> comboBowState;

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
                        + " B.Title, "
                        + " (SELECT GROUP_CONCAT(Author_name SEPARATOR ', ') "
                        + "  FROM BOOK B1, BOOK_AUTHORS A1  "
                        + "  WHERE B1.Book_id = A1.Book_id AND B.Book_id = B1.Book_id  "
                        + "  GROUP BY B1.Book_id) AS Authors, "
                        + " C.Branch_id, "
                        + " C.No_of_copies, "
                        + " C.No_of_copies - "
                        + " (SELECT COUNT(*) "
                        + "  FROM BOOK_LOANS L "
                        + "  WHERE B.Book_id = L.Book_id AND C.Branch_id = L.Branch_id AND L.Date_in IS NULL) "
                        + " AS Available "
                        + " FROM BOOK B, BOOK_COPIES C, BOOK_AUTHORS A"
                        + " WHERE B.Book_id = C.Book_id AND A.Book_id = B.Book_id ";
                Statement stmt = DBConnector.instance().createStatement();

                if (fullNameEnabled) {
                    boolean valid = false;
                    String BookId = textSearchBookId.getText();
                    if (!(BookId == null || BookId.equals("") || BookId.matches("\\s+"))) {
                        query += " AND B.Book_id LIKE '%" + BookId + "%' ";
                        valid = true;
                    }

                    String fullName = textSearchFullName.getText();
                    if (!(fullName == null || fullName.equals("") || fullName.matches("\\s+"))) {
                        query += " AND A.Author_name LIKE '%" + fullName + "%' ";
                        valid = true;
                    }

                    query += " ;";
                    if (!valid) {
                        JOptionPane.showMessageDialog(null, "Invalid searching infos");
                        return;
                    }
                    // while(!firstName.matches("^[^\\d\\s]+$"));
                    ResultSet rs;

                    try {
                        System.out.println(query);
                        rs = stmt.executeQuery(query);
                        if (null == rs) {
                            return;
                        }

                        ResultsModel model = new ResultsModel();
                        model.setResultSet(rs);
                        tableSearch.setModel(model);

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {

                    boolean valid = false;
                    String BookId = textSearchBookId.getText();
                    if (!(BookId == null || BookId.equals("") || BookId.matches("\\s+"))) {
                        query += " AND B.Book_id LIKE '%" + BookId + "%' ";
                        valid = true;
                    }

                    String firstName = textSearchFirstName.getText();
                    if (!(firstName == null || firstName.equals("") || firstName.matches("\\s+"))) {
                        query += " AND Fname LIKE '%" + firstName + "%' ";
                        valid = true;
                    }

                    String MI = textSearchMI.getText();
                    if (!(MI == null || MI.equals("") || MI.matches("\\s+"))) {
                        query += " AND MI LIKE '%" + MI + "%' ";
                        valid = true;
                    }

                    String lastName = textSearchLastName.getText();
                    if (!(lastName == null || lastName.equals("") || lastName.matches("\\s+"))) {
                        query += " AND Lname LIKE '%" + lastName + "%' ";
                        valid = true;
                    }

                    query += " ;";

                    if (!valid) {
                        JOptionPane.showMessageDialog(null, "Invalid searching infos");
                        return;
                    }
                    // while(!firstName.matches("^[^\\d\\s]+$"));
                    ResultSet rs;

                    try {
                        System.out.println(query);
                        rs = stmt.executeQuery(query);
                        if (null == rs) {
                            return;
                        }

                        ResultsModel model = new ResultsModel();
                        model.setResultSet(rs);
                        tableSearch.setModel(model);

                    } catch (SQLException e1) {
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
                if (-1 == row) {
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
        tabbedPane.addTab("Book Check Out", null, panelCheckOut, null);
        panelCheckOut.setLayout(null);

        textCheckOut = new JTextField();
        textCheckOut.setBackground(Color.WHITE);
        textCheckOut.setEditable(false);
        textCheckOut.setFont(new Font("Dialog", Font.PLAIN, 15));
        textCheckOut.setBounds(100, 46, 543, 30);
        panelCheckOut.add(textCheckOut);
        textCheckOut.setColumns(10);

        JLabel lblBookId_1 = new JLabel("Book ID");
        lblBookId_1.setBounds(100, 120, 70, 15);
        panelCheckOut.add(lblBookId_1);

        textCheckOutBookId = new JTextField();
        textCheckOutBookId.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                doUpdate();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                doUpdate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                doUpdate();
            }

            private void doUpdate() {
                if (10 != textCheckOutBookId.getText().length()) {
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
                doUpdate();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                doUpdate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                doUpdate();
            }

            private void doUpdate() {
                if (10 != textCheckOutBookId.getText().length()) {
                    textCheckOut.setText(null);
                    return;
                }
                if (textCheckOutBranchId.getText().length() < 1) {
                    textCheckOutAvailable.setText(null);
                    textCheckOutCopies.setText(null);
                    return;
                }
                String bookId = textCheckOutBookId.getText();
                String branchId = textCheckOutBranchId.getText();
                int copies = getNumCopies(bookId, branchId);
                if (copies < 0) {
                    textCheckOutAvailable.setText(null);
                    textCheckOutCopies.setText(null);
                    return;
                }
                int checkedout = getNumBooksCheckedOut(bookId, branchId);
                if (checkedout < 0) {
                    textCheckOutAvailable.setText(null);
                    textCheckOutCopies.setText(null);
                    return;
                }
                textCheckOutAvailable.setText("" + (copies - checkedout));
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
                if ((bookId == null || bookId.equals("") || bookId.matches("\\s+"))) {
                    return;
                }
                String branchId = textCheckOutBranchId.getText();
                if ((branchId == null || branchId.equals("") || branchId.matches("\\s+"))) {
                    return;
                }
                String cardNo = textCheckOutCardNo.getText();
                if ((cardNo == null || cardNo.equals("") || cardNo.matches("\\s+"))) {
                    return;
                }

                if (!existBorrower(cardNo)) {
                    JOptionPane.showMessageDialog(null, "Card NO " + bookId + " does NOT exist");
                    return;
                }

                int copies = getNumCopies(bookId, branchId);
                if (copies < 0) {
                    return;
                }
                int checkedout = getNumBooksCheckedOut(bookId, branchId);
                if (checkedout < 0) {
                    return;
                }

                if (checkedout >= copies) {
                    JOptionPane.showMessageDialog(null, "All copies of " + bookId + " ckecked out. You can not check out");
                    return;
                }

                int loansPerson = getLoansPerCard(cardNo);
                if (loansPerson < 0) {
                    return;
                }
                if (loansPerson >= 3) {
                    JOptionPane.showMessageDialog(null, "You have checked out 3 books, can NOT check out more");
                    return;
                }

                boolean hasFine = checkFine(cardNo);
                if (hasFine == true) {
                    JOptionPane.showMessageDialog(null, "You have pay your fine before you check out more books");
                    return;
                }


                String query = "INSERT INTO BOOK_LOANS (Book_id, Branch_id, Card_no, Date_out, Due_date) "
                        + " values ( '" + bookId + "','" + branchId + "', '" + cardNo + "', "
                        + " CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY)" + "); ";
                Statement stmt = DBConnector.instance().createStatement();
                try {
                    //System.out.println(query);
                    stmt.executeUpdate(query);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Check out " + bookId + " succeed. Due date is " + getDueDateFromCurrent());
                copies = getNumCopies(bookId, branchId);
                if (copies < 0) {
                    textCheckOutAvailable.setText(null);
                    textCheckOutCopies.setText(null);
                    return;
                }
                checkedout = getNumBooksCheckedOut(bookId, branchId);
                if (checkedout < 0) {
                    textCheckOutAvailable.setText(null);
                    textCheckOutCopies.setText(null);
                    return;
                }
                textCheckOutAvailable.setText("" + (copies - checkedout));
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

        JPanel panelCheckIn = new JPanel();
        tabbedPane.addTab("Book Check In", null, panelCheckIn, null);
        panelCheckIn.setLayout(null);

        JLabel labelCheckIn = new JLabel("Book ID");
        labelCheckIn.setFont(new Font("Dialog", Font.BOLD, 13));
        labelCheckIn.setBounds(36, 24, 70, 19);
        panelCheckIn.add(labelCheckIn);

        textCheckInBookId = new JTextField();
        textCheckInBookId.setFont(new Font("Dialog", Font.PLAIN, 13));
        textCheckInBookId.setColumns(10);
        textCheckInBookId.setBounds(140, 24, 167, 19);
        panelCheckIn.add(textCheckInBookId);

        JLabel lblCardNo_1 = new JLabel("Card NO");
        lblCardNo_1.setFont(new Font("Dialog", Font.BOLD, 13));
        lblCardNo_1.setBounds(342, 26, 90, 15);
        panelCheckIn.add(lblCardNo_1);

        textCheckInCardNo = new JTextField();
        textCheckInCardNo.setFont(new Font("Dialog", Font.PLAIN, 13));
        textCheckInCardNo.setColumns(10);
        textCheckInCardNo.setBounds(463, 24, 147, 19);
        panelCheckIn.add(textCheckInCardNo);

        textCheckInBowFirstName = new JTextField();
        textCheckInBowFirstName.setColumns(10);
        textCheckInBowFirstName.setBounds(140, 57, 167, 19);
        panelCheckIn.add(textCheckInBowFirstName);

        JLabel labelCheckInBorrow = new JLabel("First Name");
        labelCheckInBorrow.setBounds(36, 55, 132, 23);
        panelCheckIn.add(labelCheckInBorrow);

        JLabel label_3 = new JLabel("Last Name");
        label_3.setBounds(342, 59, 90, 15);
        panelCheckIn.add(label_3);

        textCheckInBowLastName = new JTextField();
        textCheckInBowLastName.setColumns(10);
        textCheckInBowLastName.setBounds(463, 57, 147, 19);
        panelCheckIn.add(textCheckInBowLastName);

        JButton btnSearchLoan = new JButton("Search Loan");
        btnSearchLoan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String query = "SELECT Loan_id, Book_id, Branch_id, L.Card_no, Date_out, Due_date"
                        + " FROM BOOK_LOANS L JOIN BORROWER B ON L.Card_no = B.Card_no "
                        + " WHERE Date_in IS NULL ";

                boolean valid = false;
                String BookId = textCheckInBookId.getText();
                if (!(BookId == null || BookId.equals("") || BookId.matches("\\s+"))) {
                    query += " AND L.Book_id LIKE '%" + BookId + "%' ";
                    valid = true;
                }

                String cardNo = textCheckInCardNo.getText();
                if (!(cardNo == null || cardNo.equals("") || cardNo.matches("\\s+"))) {
                    query += " AND L.Card_no LIKE '%" + cardNo + "%' ";
                    valid = true;
                }

                String firstName = textCheckInBowFirstName.getText();
                if (!(firstName == null || firstName.equals("") || firstName.matches("\\s+"))) {
                    query += " AND B.Fname LIKE '%" + firstName + "%' ";
                    valid = true;
                }

                String lastName = textCheckInBowLastName.getText();
                if (!(lastName == null || lastName.equals("") || lastName.matches("\\s+"))) {
                    query += " AND B.Lname LIKE '%" + lastName + "%' ";
                    valid = true;
                }

                query += " ;";
                if (!valid) {
                    JOptionPane.showMessageDialog(null, "Invalid searching infos");
                    return;
                }
                // while(!firstName.matches("^[^\\d\\s]+$"));
                ResultSet rs;
                Statement stmt = DBConnector.instance().createStatement();

                try {
                    System.out.println(query);
                    rs = stmt.executeQuery(query);
                    if (null == rs) {
                        return;
                    }

                    ResultsModel model = new ResultsModel();
                    model.setResultSet(rs);
                    tableCheckIn.setModel(model);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnSearchLoan.setBounds(143, 100, 132, 25);
        panelCheckIn.add(btnSearchLoan);

        JButton btnCheckIn = new JButton("Check In");
        btnCheckIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableCheckIn.getSelectedRow();
                if (-1 == row) {
                    return;
                }

                String loanId = (String) tableCheckIn.getValueAt(row, 0);

                String query = "UPDATE BOOK_LOANS SET Date_in = CURDATE() WHERE Loan_id = " + loanId + " ;";
                Statement stmt = DBConnector.instance().createStatement();

                try {
                    stmt.executeUpdate(query);
                    String queryDiff = "SELECT DATEDIFF(Date_in, Due_date) FROM BOOK_LOANS WHERE Loan_id = " + loanId + " ;";
                    stmt = DBConnector.instance().createStatement();
                    ResultSet rs = stmt.executeQuery(queryDiff);
                    if (!rs.first()) {
                        return;
                    }
                    int diff = rs.getInt(1);
                    System.out.println(diff);

                    if (diff > 0) {
                        setFine(loanId, diff);
                        notifyFine(loanId);
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                ((ResultsModel) tableCheckIn.getModel()).removeRow(row);
                JOptionPane.showMessageDialog(null, "The Book checked in");
                // TODO Book Title print
            }
        });
        btnCheckIn.setBounds(465, 100, 117, 25);
        panelCheckIn.add(btnCheckIn);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 152, 660, 237);
        panelCheckIn.add(scrollPane);

        tableCheckIn = new JTable();
        scrollPane.setViewportView(tableCheckIn);

        JPanel panelFine = new JPanel();
        tabbedPane.addTab("Fine Payment", null, panelFine, null);
        panelFine.setLayout(null);

        JLabel lblLoanId = new JLabel("Loan ID");
        lblLoanId.setBounds(36, 21, 70, 15);
        panelFine.add(lblLoanId);

        textFineLoanId = new JTextField();
        textFineLoanId.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String loanId = textFineLoanId.getText();
                if (!loanId.matches("\\b\\d+\\b")) {
                    return;
                }

                if (!isReturned(loanId)) {
                    float fineEst = getEstimatedFine(loanId);
                    textFineFine.setText(String.format("%.2f", fineEst));
                    textFinePaid.setText("NO");
                    return;
                }

                textFinePaid.setText("YES");
                float fine = getFine(loanId);
                if (fine == 0.0 || fine < 0) {
                    textFineFine.setText("Paid");
                    return;
                }

                textFineFine.setText(String.format("%.2f", fine));
            }
        });
        textFineLoanId.setBounds(105, 19, 114, 19);
        panelFine.add(textFineLoanId);
        textFineLoanId.setColumns(10);

        JLabel lblCardNo_2 = new JLabel("Card NO");
        lblCardNo_2.setBounds(269, 21, 70, 15);
        panelFine.add(lblCardNo_2);

        textFineCardNo = new JTextField();
        textFineCardNo.setBounds(340, 19, 114, 19);
        panelFine.add(textFineCardNo);
        textFineCardNo.setColumns(10);

        JLabel lblFineest = new JLabel("Fine (est.)");
        lblFineest.setBounds(472, 21, 84, 15);
        panelFine.add(lblFineest);

        textFineFine = new JTextField();
        textFineFine.setEditable(false);
        textFineFine.setBounds(562, 19, 84, 19);
        panelFine.add(textFineFine);
        textFineFine.setColumns(10);

        JLabel lblPayFine = new JLabel("Pay Fine");
        lblPayFine.setBounds(36, 53, 70, 15);
        panelFine.add(lblPayFine);

        textFinePay = new JTextField();
        textFinePay.setBounds(105, 51, 114, 19);
        panelFine.add(textFinePay);
        textFinePay.setColumns(10);

        JLabel lblPaidAmount = new JLabel("Returned");
        lblPaidAmount.setBounds(269, 53, 70, 15);
        panelFine.add(lblPaidAmount);

        textFinePaid = new JTextField();
        textFinePaid.setEditable(false);
        textFinePaid.setBounds(340, 51, 114, 19);
        panelFine.add(textFinePaid);
        textFinePaid.setColumns(10);

        JLabel lblFineSummary = new JLabel("Fine Summary");
        lblFineSummary.setFont(new Font("Dialog", Font.BOLD, 15));
        lblFineSummary.setBounds(36, 92, 128, 15);
        panelFine.add(lblFineSummary);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(12, 119, 660, 270);
        panelFine.add(scrollPane_1);

        tableFine = new JTable();
        scrollPane_1.setViewportView(tableFine);

        JButton btnSubmitPayment = new JButton("Submit Payment");
        btnSubmitPayment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String loanId = textFineLoanId.getText();
                if (!loanId.matches("\\b\\d+\\b")) {
                    return;
                }
                if (!isReturned(loanId)) {
                    JOptionPane.showMessageDialog(null, "Can NOT pay the fine for the book not returned");
                    return;
                }

                String pay = textFinePay.getText();
                if (!pay.matches("[-+]?[0-9]*\\.?[0-9]+")) {
                    System.out.println("Invalid pay");
                }

                float paid = getFine(loanId);
                if (paid < 0) {
                    JOptionPane.showMessageDialog(null, "Already paid for " + loanId);
                    return;
                }

                payFine(loanId, Float.valueOf(pay));
                refreshFineSummary(true);
                JOptionPane.showMessageDialog(null, "Pay " + pay + " for " + loanId);
            }
        });
        btnSubmitPayment.setBounds(472, 48, 149, 25);
        panelFine.add(btnSubmitPayment);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshFineSummary(true);
            }
        });
        btnRefresh.setBounds(234, 87, 117, 25);
        panelFine.add(btnRefresh);

        JPanel panelBorrower = new JPanel();
        tabbedPane.addTab("Borrower Management", null, panelBorrower, null);
        panelBorrower.setLayout(null);

        JLabel lblNewLabel = new JLabel("First Name");
        lblNewLabel.setBounds(82, 52, 80, 15);
        panelBorrower.add(lblNewLabel);

        textBowFirstName = new JTextField();
        textBowFirstName.setBounds(180, 50, 114, 19);
        panelBorrower.add(textBowFirstName);
        textBowFirstName.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Last Name");
        lblNewLabel_1.setBounds(354, 52, 93, 15);
        panelBorrower.add(lblNewLabel_1);

        textBowLastName = new JTextField();
        textBowLastName.setBounds(483, 50, 114, 19);
        panelBorrower.add(textBowLastName);
        textBowLastName.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Address");
        lblNewLabel_2.setBounds(82, 104, 70, 15);
        panelBorrower.add(lblNewLabel_2);

        textBowAddress = new JTextField();
        textBowAddress.setBounds(180, 100, 114, 19);
        panelBorrower.add(textBowAddress);
        textBowAddress.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("City");
        lblNewLabel_3.setBounds(82, 143, 70, 15);
        panelBorrower.add(lblNewLabel_3);

        textBowCity = new JTextField();
        textBowCity.setBounds(180, 141, 114, 19);
        panelBorrower.add(textBowCity);
        textBowCity.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("State");
        lblNewLabel_4.setBounds(82, 189, 70, 15);
        panelBorrower.add(lblNewLabel_4);

        comboBowState = new JComboBox<>();
        comboBowState.setModel(new DefaultComboBoxModel<>(new String[]{"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"}));
        comboBowState.setBounds(180, 184, 51, 24);
        panelBorrower.add(comboBowState);

        JLabel lblNewLabel_5 = new JLabel("ZIP");
        lblNewLabel_5.setBounds(354, 189, 70, 15);
        panelBorrower.add(lblNewLabel_5);

        textBowZIP = new JTextField();
        textBowZIP.setBounds(483, 187, 114, 19);
        panelBorrower.add(textBowZIP);
        textBowZIP.setColumns(10);

        JButton btnNewBorrower = new JButton("Add New Borrower");
        btnNewBorrower.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String firstName = textBowFirstName.getText();
                if ((firstName == null || firstName.equals("") || firstName.matches("\\s+"))) {
                    return;
                }
                String lastName = textBowLastName.getText();
                if ((lastName == null || lastName.equals("") || lastName.matches("\\s+"))) {
                    return;
                }
                String address = textBowAddress.getText();
                if ((address == null || address.equals("") || address.matches("\\s+"))) {
                    return;
                }
                String city = textBowCity.getText();
                if ((city == null || city.equals("") || city.matches("\\s+"))) {
                    return;
                }
                String state = (String) comboBowState.getSelectedItem();
                if ((state == null || state.equals("") || state.matches("\\s+"))) {
                    return;
                }
                String phone = textBowPhone.getText();
                if ((phone == null || phone.equals("") || phone.matches("\\s+"))) {
                    return;
                }


                String query = "SELECT * FROM BORROWER B WHERE"
                        + " B.Fname = '" + firstName + "'"
                        + " AND B.Lname = '" + lastName + "'"
                        + " AND B.Address = '" + address + "'"
                        + " AND B.City = '" + city + "'"
                        + " AND B.State = '" + state + "'"
                        + " ;";

                ResultSet rs;
                Statement stmt = DBConnector.instance().createStatement();

                try {
                    System.out.println(query);
                    rs = stmt.executeQuery(query);
                    if (null == rs) {
                        return;
                    }

                    if (rs.first()) {
                        JOptionPane.showMessageDialog(null, "Borrower already exists");
                        return;
                    }

                    String insertSQL = "INSERT INTO BORROWER (Fname, Lname, Address, City, State, Phone) values"
                            + " ('" + firstName + "','" + lastName + "','" + address + "','" + city + "','" + state + "','" + phone + "');";
                    stmt = DBConnector.instance().createStatement();
                    stmt.executeUpdate(insertSQL);

                    stmt = DBConnector.instance().createStatement();
                    rs = stmt.executeQuery(query);
                    if (rs.first()) {
                        JOptionPane.showMessageDialog(null, "New Borrower added, Card NO is " + rs.getString(1));
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnNewBorrower.setBounds(82, 278, 193, 25);
        panelBorrower.add(btnNewBorrower);

        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setBounds(354, 104, 70, 15);
        panelBorrower.add(lblPhone);

        textBowPhone = new JTextField();
        textBowPhone.setBounds(483, 102, 114, 19);
        panelBorrower.add(textBowPhone);
        textBowPhone.setColumns(10);
    }

    private boolean fullNameEnabled = true;
    private JTable tableSearch;
    private JTextField textCheckOut;
    private JTextField textCheckOutBookId;
    private JTextField textCheckOutBranchId;
    private JTextField textCheckOutCardNo;
    private JTextField textCheckOutAvailable;
    private JTextField textCheckOutCopies;
    private JTextField textCheckInBookId;
    private JTextField textCheckInCardNo;
    private JTextField textCheckInBowFirstName;
    private JTextField textCheckInBowLastName;
    private JTable tableCheckIn;
    private JTextField textBowFirstName;
    private JTextField textBowLastName;
    private JTextField textBowAddress;
    private JTextField textBowCity;
    private JTextField textBowZIP;
    private JTextField textFineLoanId;
    private JTextField textFineCardNo;
    private JTextField textFineFine;
    private JTextField textFinePay;
    private JTextField textFinePaid;
    private JTable tableFine;
    private JTextField textBowPhone;

    private void setFullNameEnabled(boolean enabled) {
        textSearchFirstName.setEnabled(!enabled);
        textSearchMI.setEnabled(!enabled);
        textSearchLastName.setEnabled(!enabled);
        textSearchFullName.setEnabled(enabled);
        fullNameEnabled = enabled;
    }

    private int getNumBooksCheckedOut(String bookId, String branchId) {
        if ((bookId == null || bookId.equals("") || bookId.matches("\\s+"))) {
            return -1;
        }
        if ((branchId == null || branchId.equals("") || branchId.matches("\\s+"))) {
            return -1;
        }

        String query = "SELECT COUNT(*) FROM BOOK_LOANS WHERE Date_in IS NULL AND Book_id = " + bookId
                + " AND Branch_id = " + branchId + " ; ";

        int copies = -1;
        Statement stmt = DBConnector.instance().createStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            if (null == rs) {
                return -1;
            }
            if (rs.first()) {
                copies = rs.getInt(1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return copies;
    }

    private boolean existBorrower(String cardNo) {
        if ((cardNo == null || cardNo.equals("") || cardNo.matches("\\s+"))) {
            return false;
        }

        String query = "SELECT * FROM BORROWER WHERE Card_no = '" + cardNo + "' ; ";

        boolean exist = false;
        Statement stmt = DBConnector.instance().createStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            if (null == rs) {
                return false;
            }
            if (rs.first()) {
                exist = true;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return exist;
    }

    private int getLoansPerCard(String cardNo) {
        if ((cardNo == null || cardNo.equals("") || cardNo.matches("\\s+"))) {
            return -1;
        }

        String query = "SELECT COUNT(*) FROM BOOK_LOANS WHERE Date_in IS NULL AND Card_no = " + cardNo + " ; ";

        int loans = -1;
        Statement stmt = DBConnector.instance().createStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            if (null == rs) {
                return -1;
            }
            if (rs.first()) {
                loans = rs.getInt(1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return loans;
    }

    private int getNumCopies(String bookId, String branchId) {
        if ((bookId == null || bookId.equals("") || bookId.matches("\\s+"))) {
            return -1;
        }
        if ((branchId == null || branchId.equals("") || branchId.matches("\\s+"))) {
            return -1;
        }

        String query = "SELECT No_of_copies FROM BOOK_COPIES WHERE Book_id = " + bookId
                + " AND Branch_id = " + branchId + " ; ";

        int copies = -1;
        Statement stmt = DBConnector.instance().createStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            if (null == rs) {
                return -1;
            }
            if (rs.first()) {
                copies = rs.getInt(1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return copies;
    }

    private String getBookTitleById(String bookId) {
        if ((bookId == null || bookId.equals("") || bookId.matches("\\s+"))) {
            return null;
        }
        String query = "SELECT Title FROM BOOK WHERE Book_id = " + bookId + "; ";
        Statement stmt = DBConnector.instance().createStatement();

        ResultSet rs;
        String title = null;

        try {
            rs = stmt.executeQuery(query);
            if (null == rs) {
                return null;
            }
            if (rs.first()) {
                title = rs.getString(1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return title;
    }

    @SuppressWarnings("unused")
    private String getDueDate(String loanId) {
        if ((loanId == null || loanId.equals("") || loanId.matches("\\s+"))) {
            return null;
        }
        String query = "SELECT Due_date FROM BOOK_LOANS WHERE Loan_id = " + loanId + "; ";
        Statement stmt = DBConnector.instance().createStatement();

        ResultSet rs;
        String due = null;

        try {
            rs = stmt.executeQuery(query);
            if (null == rs) {
                return null;
            }
            if (rs.first()) {
                due = rs.getString(1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return due;
    }

    private void notifyFine(String loanId) {
        JOptionPane.showMessageDialog(null, "Borrower have to pay a FINE");
        textFineLoanId.setText(loanId);
        tabbedPane.setSelectedIndex(3);
    }

    /**
     * @param loanId unique key
     * @return 0.0 if no fine, or -1 if paid
     */
    private float getFine(String loanId) {
        String query = "SELECT Fine_amt, Paid FROM FINE ";
        Statement stmt = DBConnector.instance().createStatement();
        float fine = (float) 0.0;

        query += " WHERE Loan_id = " + loanId + "; ";
        ResultSet rs;

        try {
            rs = stmt.executeQuery(query);
            if (rs == null) {
                return (float) 0.0;
            }

            if (!rs.first()) {
                return 0;
            }

            if (rs.getBoolean(2)) {
                return -1;
            }

            fine = rs.getFloat(1);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return fine;
    }

    private float getEstimatedFine(String loanId) {
        String query = "SELECT DATEDIFF(CURDATE(), Due_date) FROM BOOK_LOANS ";
        Statement stmt = DBConnector.instance().createStatement();

        query += " WHERE Loan_id = " + loanId + "; ";
        ResultSet rs;
        float fine = 0;

        try {
            rs = stmt.executeQuery(query);
            if (rs == null) {
                return (float) 0.0;
            }

            if (!rs.first()) {
                return 0;
            }

            int days = rs.getInt(1);
            if (days > 0) {
                fine = (float) (0.25 * rs.getInt(1));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return fine;
    }

    private void setFine(String loanId, int days) {
        String query = "UPDATE FINE SET Fine_amt = ";
        Statement stmt = DBConnector.instance().createStatement();

        float fine = (float) 0.25 * (float) days;
        query += fine;
        query += " WHERE Loan_id = " + loanId + "; ";

        try {
            int count = stmt.executeUpdate(query);
            if (count == 0) {
                insertFine(loanId, fine);
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private void insertFine(String loanId, float fine) {
        String query = "INSERT INTO FINE (Loan_id, Fine_amt) values ( '";
        Statement stmt = DBConnector.instance().createStatement();

        query += loanId + "', '" + fine + "' ) ;";

        try {
            @SuppressWarnings("unused")
            int count = stmt.executeUpdate(query);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private void payFine(String loanId, float amount) {
        if (amount < 0) {
            return;
        }

        String query = "UPDATE FINE SET Paid = TRUE, Fine_amt = ";
        Statement stmt = DBConnector.instance().createStatement();

        query += amount;
        query += " WHERE Loan_id = " + loanId + "; ";

        try {
            stmt.executeUpdate(query);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private String getDueDateFromCurrent() {
        String query = "SELECT DATE_ADD(CURDATE(), INTERVAL 14 DAY);";
        Statement stmt = DBConnector.instance().createStatement();

        ResultSet rs;
        String due = null;

        try {
            rs = stmt.executeQuery(query);
            if (null == rs) {
                return null;
            }
            if (rs.first()) {
                due = rs.getString(1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return due;
    }

    private boolean isReturned(String loanId) {
        String query = "SELECT * FROM BOOK_LOANS "
                + " WHERE Date_in IS NULL AND Loan_id = '" + loanId + "' ;";

        Statement stmt = DBConnector.instance().createStatement();

        ResultSet rs;
        @SuppressWarnings("unused")
        boolean returned = true;

        try {
            rs = stmt.executeQuery(query);
            return null != rs && !rs.first();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return true;
    }

    private void refreshFineSummary(boolean filter) {
        String query = "SELECT Card_no, SUM(Fine_amt) AS 'Total Fine' FROM BOOK_LOANS L, FINE F "
                + " WHERE F.Loan_id = L.Loan_id ";
        if (filter) {
            query += " AND Paid = FALSE ";
        }
        query += " GROUP BY Card_no ;";
        Statement stmt = DBConnector.instance().createStatement();

        ResultSet rs;

        try {
            rs = stmt.executeQuery(query);
            if (null == rs) {
                return;
            }

            ResultsModel model = new ResultsModel();
            model.setResultSet(rs);
            tableFine.setModel(model);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    private boolean checkFine(String cardNo) {

        String query = "SELECT COUNT(*) " +
                "FROM FINE F " +
                "WHERE F.Paid = false AND F.Loan_id IN " +
                "   (SELECT Loan_id FROM BOOK_LOANS L WHERE L.Card_no = " + cardNo + " )";

        Statement stmt = DBConnector.instance().createStatement();
        ResultSet rs;

        try {
            rs = stmt.executeQuery(query);
            if (null == rs) {
                return false;
            }

            if (rs.first()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    System.out.println(count + "");
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
