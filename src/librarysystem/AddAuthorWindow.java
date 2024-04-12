package librarysystem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.LibrarySystemException;
import business.SystemController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class AddAuthorWindow extends JFrame implements LibWindow {
	  public static final AddAuthorWindow INSTANCE = new AddAuthorWindow();
	  JPanel panel;
	    private boolean isInitialized = false;
	    public boolean isInitialized() {
	        return isInitialized;
	    }

	    public void isInitialized(boolean val) {
	        isInitialized = val;
	    }
	    private AddAuthorWindow() {
//	    	init();
	    	
	    }
	    private JTextField txtFName;
		private JTextField txtLname;
		private JTextField txtStreet;
		private JTextField txtCity;
		private JTextField txtState;
		private JTextField txtZip;
		private JTextField txtTelNum;
		private final JButton btnAddAuthor = new JButton("Add Author");
		private JLabel lblBio;
		private JTextArea txtBio;


	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
////		EventQueue.invokeLater(new Runnable() {
////			public void run() {
////				try {
//					AddAuthorWindow window = new AddAuthorWindow();
////					window.frame.setVisible(true);
////				} catch (Exception e) {
////					e.printStackTrace();
////				}
////			}
////		});
//	}

	/**
	 * Create the application.
	 */


	/**
	 * Initialize the contents of the frame.
	 */
	public void init() {
getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 593, 598);
		getContentPane().add(panel);
		JLabel lblfname = new JLabel("First Name");
		lblfname.setBounds(76, 24, 138, 22);

		txtFName = new JTextField();
		txtFName.setBounds(273, 24, 273, 22);
		txtFName.setColumns(10);
		panel.setLayout(null);
		panel.add(lblfname);
		panel.add(txtFName);

		JLabel lblLname = new JLabel("Last Name");
		lblLname.setBounds(76, 70, 138, 22);
		panel.add(lblLname);

		txtLname = new JTextField();
		txtLname.setBounds(273, 70, 273, 22);
		txtLname.setColumns(10);
		panel.add(txtLname);

		JLabel lblStreet = new JLabel("Street");
		lblStreet.setBounds(76, 116, 138, 22);
		panel.add(lblStreet);

		txtStreet = new JTextField();
		txtStreet.setBounds(273, 116, 273, 22);
		txtStreet.setColumns(10);
		panel.add(txtStreet);

		JLabel lblCity = new JLabel("City");
		lblCity.setBounds(76, 162, 138, 22);
		panel.add(lblCity);

		txtCity = new JTextField();
		txtCity.setBounds(273, 162, 273, 22);
		txtCity.setColumns(10);
		panel.add(txtCity);

		JLabel lblState = new JLabel("State");
		lblState.setBounds(76, 208, 138, 22);
		panel.add(lblState);

		txtState = new JTextField();
		txtState.setBounds(273, 208, 273, 22);
		txtState.setColumns(10);
		panel.add(txtState);

		JLabel lblZip = new JLabel("Zip");
		lblZip.setBounds(76, 254, 138, 22);
		panel.add(lblZip);

		txtZip = new JTextField();
		txtZip.setBounds(273, 254, 273, 22);
		txtZip.setColumns(10);
		panel.add(txtZip);

		JLabel lblTelNumber = new JLabel("Tel-Number");
		lblTelNumber.setBounds(76, 300, 138, 22);
		panel.add(lblTelNumber);

		txtTelNum = new JTextField();
		txtTelNum.setBounds(273, 300, 273, 22);
		txtTelNum.setColumns(10);
		panel.add(txtTelNum);
		btnAddAuthor.setBounds(144, 528, 273, 46);
		btnAddAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateForm()) {
//				if(true) {
					
				
					SystemController cont = new SystemController();
					try {
						cont.addAuthor(txtFName.getText(), txtLname.getText(), txtTelNum.getText(),
								txtStreet.getText(), txtCity.getText(), txtState.getText(), txtZip.getText(),txtBio.getText());

						JOptionPane.showMessageDialog(btnAddAuthor, "Author added successfully",
								"Success", JOptionPane.INFORMATION_MESSAGE);
						txtFName.setText("");
						txtLname.setText("");
						txtStreet.setText("");
						txtCity.setText("");
						txtState.setText("");
						txtZip.setText("");
						txtTelNum.setText("");
						txtBio.setText("");
					} catch (LibrarySystemException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		btnAddAuthor.setForeground(Color.BLACK);
		btnAddAuthor.setBackground(Color.CYAN);
		panel.add(btnAddAuthor);

		lblBio = new JLabel("Bio");
		lblBio.setBounds(76, 345, 107, 22);
		panel.add(lblBio);
		
		JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            BookWindow.INSTANCE.setVisible(true);
        });
        backBtn.setBounds(0, 0, 75, 20);
        panel.add(backBtn);
		
		 getContentPane().add(panel);
		 
		 JScrollPane scrollPane = new JScrollPane();
		 scrollPane.setBounds(273, 344, 273, 134);
		 panel.add(scrollPane);
		 
		 txtBio = new JTextArea();
		 scrollPane.setViewportView(txtBio);
	     isInitialized(true);
	     setVisible(true);
	     setSize(629, 659);
	}
	private boolean validateForm() {
		String fname = txtFName.getText();
		String lname = txtLname.getText();
		String city = txtCity.getText();
		String street = txtStreet.getText();
		String state = txtState.getText();
		String zip = txtZip.getText();
		String tel = txtTelNum.getText();

		if (fname.isEmpty()) {

			JOptionPane.showMessageDialog(btnAddAuthor, "Please enter first name.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (lname.isEmpty()) {
			JOptionPane.showMessageDialog(btnAddAuthor, "Please enter last name.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (street.isEmpty()) {
			JOptionPane.showMessageDialog(btnAddAuthor, "Please enter Street", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (city.isEmpty()) {
			JOptionPane.showMessageDialog(btnAddAuthor, "Please enter city.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (state.isEmpty()) {
			JOptionPane.showMessageDialog(btnAddAuthor, "Please enter state.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (zip.isEmpty()) {
			JOptionPane.showMessageDialog(btnAddAuthor, "Please enter zip.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (zip.length() != 5) {
			JOptionPane.showMessageDialog(btnAddAuthor, "Please enter a correct zip length", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (!zip.matches("[0-9]+")) {
			JOptionPane.showMessageDialog(btnAddAuthor, "Zip code should only include numbers", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (tel.isEmpty()) {
			JOptionPane.showMessageDialog(btnAddAuthor, "Please enter your 10 digit tel number", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (!tel.matches("[0-9]+")) {
			JOptionPane.showMessageDialog(btnAddAuthor, "Telephone number should only include numbers", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (tel.length() != 10) {
			JOptionPane.showMessageDialog(btnAddAuthor, "Please enter a valid telephone number.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
		
	}
	
}
