package librarysystem;

import business.LibrarySystemException;
import business.SystemController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMember extends JFrame implements LibWindow {
    public static final AddMember INSTANCE = new AddMember();
//    private final JButton addMemberBtn = new JButton("Add Member");
    private boolean isInitialized = false;
    private JPanel panel;
    private JTextField txtFName;
    private JTextField txtLname;
    private JTextField txtStreet;
    private JTextField txtCity;
    private JTextField txtState;
    private JTextField txtZip;
    private JTextField txtTelNum;
    private JTextField txtIDNum;
    private JLabel lblMemberID;
    private JLabel lblNewLabel;
    private JButton addMemberBtn;
    private JButton backBtn;

    public AddMember() {
        init();
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    /**
     * Create the application.
     */
    @Override
    public void init() {
        panel = new JPanel();
        panel.setBounds(100, 100, 579, 511);


        JLabel lblfname = new JLabel("First Name");
        lblfname.setBounds(76, 39, 81, 22);

        txtFName = new JTextField();
        txtFName.setBounds(187, 39, 273, 22);
        txtFName.setColumns(10);
        panel.setLayout(null);
        panel.add(lblfname);
        panel.add(txtFName);

        JLabel lblLname = new JLabel("Last Name");
        lblLname.setBounds(76, 70, 81, 22);
        panel.add(lblLname);

        txtLname = new JTextField();
        txtLname.setBounds(187, 73, 273, 22);
        txtLname.setColumns(10);
        panel.add(txtLname);

        JLabel lblStreet = new JLabel("Street");
        lblStreet.setBounds(76, 104, 75, 22);
        panel.add(lblStreet);

        txtStreet = new JTextField();
        txtStreet.setBounds(187, 107, 273, 22);
        txtStreet.setColumns(10);
        panel.add(txtStreet);

        JLabel lblCity = new JLabel("City");
        lblCity.setBounds(76, 138, 75, 22);
        panel.add(lblCity);

        txtCity = new JTextField();
        txtCity.setBounds(187, 138, 273, 22);
        txtCity.setColumns(10);
        panel.add(txtCity);

        JLabel lblState = new JLabel("State");
        lblState.setBounds(76, 172, 75, 22);
        panel.add(lblState);

        txtState = new JTextField();
        txtState.setBounds(187, 172, 273, 22);
        txtState.setColumns(10);
        panel.add(txtState);

        JLabel lblZip = new JLabel("Zip");
        lblZip.setBounds(76, 206, 81, 22);
        panel.add(lblZip);

        txtZip = new JTextField();
        txtZip.setBounds(187, 206, 273, 22);
        txtZip.setColumns(10);
        panel.add(txtZip);

        JLabel lblTelNumber = new JLabel("Tel-Number");
        lblTelNumber.setBounds(76, 241, 89, 22);
        panel.add(lblTelNumber);

        txtTelNum = new JTextField();
        txtTelNum.setBounds(187, 240, 273, 22);
        txtTelNum.setColumns(10);
        panel.add(txtTelNum);

        txtIDNum = new JTextField();
        txtIDNum.setBounds(187, 285, 273, 22);
        txtIDNum.setEditable(false);
        txtIDNum.setColumns(10);
        panel.add(txtIDNum);
        
        addMemberBtn = new JButton();
        addMemberBtn.setText("Add Member");
        addMemberBtn.setBounds(337, 348, 123, 29);
        addMemberBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
//				if(true) {
                    String memberID = Util.generateRandomizedId();
                    txtIDNum.setText(memberID);
                    SystemController cont = new SystemController();
                    try {
                        cont.addMember(memberID, txtFName.getText(), txtLname.getText(), txtTelNum.getText(),
                                txtStreet.getText(), txtCity.getText(), txtState.getText(), txtZip.getText());

                        JOptionPane.showMessageDialog(addMemberBtn, txtFName.getText() + "'s ID is:" + memberID,
                                "Member added successfully", JOptionPane.INFORMATION_MESSAGE);
                        txtFName.setText("");
                        txtLname.setText("");
                        txtStreet.setText("");
                        txtCity.setText("");
                        txtState.setText("");
                        txtZip.setText("");
                        txtTelNum.setText("");
                        txtIDNum.setText("");
                    } catch (LibrarySystemException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

        addMemberBtn.setForeground(Color.BLACK);
        addMemberBtn.setBackground(Color.CYAN);
        panel.add(addMemberBtn);

        lblMemberID = new JLabel("Member - Id");
        lblMemberID.setBounds(76, 275, 89, 22);
        panel.add(lblMemberID);

        backBtn = new JButton("< Back");
        backBtn.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
        backBtn.setBounds(17, 352, 75, 20);
        panel.add(backBtn);

        getContentPane().add(panel);
        
        lblNewLabel = new JLabel("Add Library Member");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(207, 6, 148, 16);
        panel.add(lblNewLabel);
        isInitialized(true);
//        setVisible(true);
        setSize(600, 500);
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

            JOptionPane.showMessageDialog(addMemberBtn, "Please enter first name.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (lname.isEmpty()) {
            JOptionPane.showMessageDialog(addMemberBtn, "Please enter last name.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (street.isEmpty()) {
            JOptionPane.showMessageDialog(addMemberBtn, "Please enter Street", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (city.isEmpty()) {
            JOptionPane.showMessageDialog(addMemberBtn, "Please enter city.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (state.isEmpty()) {
            JOptionPane.showMessageDialog(addMemberBtn, "Please enter state.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (zip.isEmpty()) {
            JOptionPane.showMessageDialog(addMemberBtn, "Please enter zip.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (zip.length() != 5) {
            JOptionPane.showMessageDialog(addMemberBtn, "Please enter a correct zip length", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!zip.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(addMemberBtn, "Zip code should only include numbers", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (tel.isEmpty()) {
            JOptionPane.showMessageDialog(addMemberBtn, "Please enter your 10 digit tel number", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!tel.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(addMemberBtn, "Telephone number should only include numbers", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (tel.length() != 10) {
            JOptionPane.showMessageDialog(addMemberBtn, "Please enter a valid telephone number.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;

    }


}
