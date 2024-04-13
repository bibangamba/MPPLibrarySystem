package librarysystem;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;

import javax.swing.*;

public class LoginWindow extends JFrame implements LibWindow {
    public static final LoginWindow INSTANCE = new LoginWindow();

    private boolean isInitialized = false;

    private JTextField usernameField;
    private JPasswordField passwordField;

    /* This class is a singleton */
    private LoginWindow() {
        init();
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    /**
     * @wbp.parser.entryPoint
     */
    public void init() {
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 444, 266);

        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("LibrarySystem Login");
        lblNewLabel.setBounds(130, 19, 127, 16);
        panel.add(lblNewLabel);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(60, 77, 62, 16);
        panel.add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(60, 115, 59, 16);
        panel.add(passwordLabel);

        usernameField = new JTextField();
        usernameField.setBounds(130, 72, 187, 26);
        panel.add(usernameField);
        usernameField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 110, 187, 26);
        panel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(176, 148, 79, 29);
        panel.add(loginBtn);
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String pwd = String.valueOf(passwordField.getPassword());
            ControllerInterface ci = new SystemController();
            try {

                System.out.println("###### Username field string obj: "+usernameField.hashCode());

                System.out.println("###### username: "+username+"pwd: "+pwd);
                ci.login(username, pwd);
                JOptionPane.showMessageDialog(this, "Login successful!");
                LibrarySystem.hideAllWindows();
                LibrarySystem.INSTANCE.init();
                LibrarySystem.INSTANCE.setVisible(true);
            } catch (LoginException ex) {
                JOptionPane.showMessageDialog(this, "Login Failed! Reason:" + ex.getMessage());
            }

        });

        JButton backBtn = new JButton("< Back");
        backBtn.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
        backBtn.setBounds(6, 231, 87, 29);
        panel.add(backBtn);

        getContentPane().add(panel);
        isInitialized(true);
//        setVisible(true);
        setSize(420, 300);
    }
}
