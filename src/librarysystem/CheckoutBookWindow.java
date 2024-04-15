package librarysystem;

import business.LibrarySystemException;
import business.SystemController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class CheckoutBookWindow extends JFrame implements LibWindow {
    public static final CheckoutBookWindow INSTANCE = new CheckoutBookWindow();
    private final JButton saveButton = new JButton("Save");
    private boolean isInitialized = false;
    private JFrame frame;
    private JTextField LibraryMemberId;
    private JTextField BookID;
    private JTable table;
    private String[] columnNames = {
		"member Name",
		"book Name",
		"copy Number",
        "checkout Date",
        "checkout Due"
        };
    Object[][] data;
    private DefaultTableModel tableModel;
    /**
     * Create the application.
     */
    public CheckoutBookWindow() {
        init();
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    /**
     * Initialize the contents of the frame.
     */
    public void init() {
        JPanel panel = new JPanel();
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        panel.setBounds(0, 6, 450, 278);
//		frame.getContentPane().add(panel);
        panel.setLayout(null);
        ;

        JLabel labelMemberId = new JLabel("Member ID");
        labelMemberId.setBounds(6, 53, 78, 29);
        panel.add(labelMemberId);

        getContentPane().add(panel);


        LibraryMemberId = new JTextField();
        LibraryMemberId.setBounds(101, 54, 130, 26);
        panel.add(LibraryMemberId);
        LibraryMemberId.setColumns(10);

        JLabel labelBookId = new JLabel("Book ID");
        labelBookId.setBounds(6, 92, 78, 29);
        panel.add(labelBookId);

        BookID = new JTextField();
        BookID.setColumns(10);
        BookID.setBounds(101, 93, 130, 26);
        panel.add(BookID);
        isInitialized(true);
//        setVisible(true);
        setSize(551, 372);

//        JButton saveButton = new JButton("Save");
        saveButton.setBounds(189, 151, 117, 29);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
//				if(true) {
                    String memberID = LibraryMemberId.getText();
                    String bookID = BookID.getText();
                    SystemController cont = new SystemController();
                    try {
                        int copyNum = cont.checkoutBook(bookID, memberID);

                        JOptionPane.showMessageDialog(saveButton, "Book with id = " + bookID + " (book copy number: " + copyNum + " ) has been checked out to member with id = " + memberID,
                                "checkout created successfully", JOptionPane.INFORMATION_MESSAGE);
                        LibraryMemberId.setText("");
                        BookID.setText("");
                        data = cont.getCheckoutData();
                        tableModel.setRowCount(0);
                        cont.tableModelSetRow(tableModel, data);
                        tableModel.fireTableDataChanged();
                    } catch (LibrarySystemException e1) {
                        // TODO Auto-generated catch block
//                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(saveButton, e1.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        panel.add(saveButton);

        JButton backBtn = new JButton("< Back");
        backBtn.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
        backBtn.setBounds(-3, 151, 87, 29);
        panel.add(backBtn);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 212, 545, 126);
        panel.add(scrollPane);
        

        SystemController cont = new SystemController();
        data = cont.getCheckoutData();
        System.out.println(data.length);
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnNames);
        cont.tableModelSetRow(tableModel, data);
        table = new JTable(tableModel);
        
        scrollPane.setViewportView(table);
        
        JLabel lblNewLabel = new JLabel("Checkout Book");
        lblNewLabel.setBounds(227, 17, 105, 16);
        panel.add(lblNewLabel);

    }

    private boolean validateForm() {
        String libraryMemberId = LibraryMemberId.getText();
        String bookID = BookID.getText();


        if (libraryMemberId.isEmpty()) {

            JOptionPane.showMessageDialog(saveButton, "Please enter Member id.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (bookID.isEmpty()) {
            JOptionPane.showMessageDialog(saveButton, "Please enter Book id.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;

    }
}



