package librarysystem;

import business.LibrarySystemException;
import business.SystemController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAuthorWindow extends JFrame implements LibWindow {
    public static final AddAuthorWindow INSTANCE = new AddAuthorWindow();
    private final JButton btnAddAuthor = new JButton("Add Author");
    JPanel panel;
    private boolean isInitialized = false;
    private JTextField txtFName;
    private JTextField txtLname;
    private JTextField txtStreet;
    private JTextField txtCity;
    private JTextField txtState;
    private JTextField txtZip;
    private JTextField txtTelNum;
    private JLabel lblBio;
    private JTextArea txtBio;
    private JLabel addAuthorLabel;

    private AddAuthorWindow() {
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
        getContentPane().setLayout(null);

        panel = new JPanel();
        panel.setBounds(10, 11, 593, 518);
        getContentPane().add(panel);
        JLabel lblfname = new JLabel("First Name");
        lblfname.setBounds(76, 48, 89, 22);

        txtFName = new JTextField();
        txtFName.setBounds(189, 48, 273, 22);
        txtFName.setColumns(10);
        panel.setLayout(null);
        panel.add(lblfname);
        panel.add(txtFName);

        JLabel lblLname = new JLabel("Last Name");
        lblLname.setBounds(76, 82, 75, 22);
        panel.add(lblLname);

        txtLname = new JTextField();
        txtLname.setBounds(189, 82, 273, 22);
        txtLname.setColumns(10);
        panel.add(txtLname);

        JLabel lblStreet = new JLabel("Street");
        lblStreet.setBounds(76, 116, 138, 22);
        panel.add(lblStreet);

        txtStreet = new JTextField();
        txtStreet.setBounds(189, 116, 273, 22);
        txtStreet.setColumns(10);
        panel.add(txtStreet);

        JLabel lblCity = new JLabel("City");
        lblCity.setBounds(76, 150, 138, 22);
        panel.add(lblCity);

        txtCity = new JTextField();
        txtCity.setBounds(189, 150, 273, 22);
        txtCity.setColumns(10);
        panel.add(txtCity);

        JLabel lblState = new JLabel("State");
        lblState.setBounds(76, 184, 138, 22);
        panel.add(lblState);

        txtState = new JTextField();
        txtState.setBounds(189, 184, 273, 22);
        txtState.setColumns(10);
        panel.add(txtState);

        JLabel lblZip = new JLabel("Zip");
        lblZip.setBounds(76, 218, 138, 22);
        panel.add(lblZip);

        txtZip = new JTextField();
        txtZip.setBounds(189, 218, 273, 22);
        txtZip.setColumns(10);
        panel.add(txtZip);

        JLabel lblTelNumber = new JLabel("Tel-Number");
        lblTelNumber.setBounds(76, 252, 138, 22);
        panel.add(lblTelNumber);

        txtTelNum = new JTextField();
        txtTelNum.setBounds(189, 252, 273, 22);
        txtTelNum.setColumns(10);
        panel.add(txtTelNum);
        btnAddAuthor.setBounds(346, 470, 116, 29);
        btnAddAuthor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
//				if(true) {


                    SystemController cont = new SystemController();
                    try {
                        cont.addAuthor(txtFName.getText(), txtLname.getText(), txtTelNum.getText(),
                                txtStreet.getText(), txtCity.getText(), txtState.getText(), txtZip.getText(), txtBio.getText());

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
        lblBio.setBounds(76, 295, 107, 22);
        panel.add(lblBio);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            BookWindow.INSTANCE.setVisible(true);
        });
        backBtn.setBounds(6, 474, 75, 20);
        panel.add(backBtn);

        getContentPane().add(panel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(189, 286, 273, 134);
        panel.add(scrollPane);

        txtBio = new JTextArea();
        scrollPane.setViewportView(txtBio);
        
        addAuthorLabel = new JLabel("Add Author");
        addAuthorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addAuthorLabel.setBounds(208, 6, 152, 16);
        panel.add(addAuthorLabel);
        isInitialized(true);
//	     setVisible(true);
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
