package librarysystem;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextPane;

import business.LibrarySystemException;
import business.SystemController;

import javax.swing.JTextField;

public class CheckoutBookWindow extends JFrame implements LibWindow {
	private CheckoutBookWindow() {}
	public static final CheckoutBookWindow INSTANCE = new CheckoutBookWindow();
	private boolean isInitialized = false;
	
	private JFrame frame;
	private JTextField LibraryMemberId;
	private JTextField BookID;
	private final JButton saveButton = new JButton("Save");
	
	public boolean isInitialized() {
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        isInitialized = val;
    }

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CheckoutBookWindow window = new CheckoutBookWindow();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
//	public CheckoutBookWindow() {
//		init();
//	}

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
		panel.setLayout(null);;
		
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
        setVisible(true);
        setSize(420, 300);
        
//        JButton saveButton = new JButton("Save");
        saveButton.setBounds(6, 149, 117, 29);
        saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateForm()) {
//				if(true) {
					String memberID = LibraryMemberId.getText();
					String bookID = BookID.getText();
					SystemController cont = new SystemController();
					try {
						cont.CreateCheckoutBook(bookID, memberID);

						JOptionPane.showMessageDialog(saveButton, "Book with id = " + bookID + "has been checked out to member with id = " + memberID ,
								"checkout created successfully", JOptionPane.INFORMATION_MESSAGE);
						LibraryMemberId.setText("");
						BookID.setText("");
					} catch (LibrarySystemException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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
        backBtn.setBounds(6, 231, 87, 29);
        panel.add(backBtn);
        
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