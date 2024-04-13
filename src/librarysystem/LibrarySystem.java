package librarysystem;

import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class LibrarySystem extends JFrame implements LibWindow {
    public final static LibrarySystem INSTANCE = new LibrarySystem();
    private static LibWindow[] allWindows = {
            LibrarySystem.INSTANCE, LoginWindow.INSTANCE,
            AddMember.INSTANCE, AllMemberIdsWindow.INSTANCE,
            AddAuthorWindow.INSTANCE, AllAuthorsWindow.INSTANCE,
            BookWindow.INSTANCE, AllBookIdsWindow.INSTANCE,
            CheckoutBookWindow.INSTANCE, AddBookCopyWindow.INSTANCE
    };
    ControllerInterface ci = new SystemController();
    JPanel mainPanel;
    JMenuBar menuBar;
    JMenu options;
    JMenuItem login, allBookIds, allMemberIds;
    String pathToImage;
    private boolean isInitialized = false;

    private LibrarySystem() {
    }

    public static void hideAllWindows() {
        for (LibWindow frame : allWindows) {
            frame.setVisible(false);
        }
    }

    public void init() {
        formatContentPane();
        setPathToImage();
        insertSplashImage();

        createMenus();
        // pack();
        setSize(660, 500);
        isInitialized = true;
    }

    private void formatContentPane() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 1));
        getContentPane().add(mainPanel);
    }

    private void setPathToImage() {
        String currDirectory = System.getProperty("user.dir");
        System.out.println(currDirectory);
        pathToImage = currDirectory + String.format("%ssrc%slibrarysystem%slibrary.jpg",
                File.separator, File.separator, File.separator);
        System.out.println("##### path to image: "+pathToImage);
    }

    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
        mainPanel.add(new JLabel(image));
    }

    private void createMenus() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createRaisedBevelBorder());

        if (SystemController.getAuth() == null) {
            addLoggedOutMenuItems();
        } else {
            switch (SystemController.getAuth()) {
                case BOTH:
                    addSuperAdminMenuItems();
                    break;
                case ADMIN:
                    addAdminMenuItems();
                    break;
                case LIBRARIAN:
                    addLibrarianMenuItems();
                    break;
                default:
                    addLoggedOutMenuItems();
                    break;
            }
        }

        setJMenuBar(menuBar);
    }

    private void addSuperAdminMenuItems() {
        options = new JMenu("Options");
        menuBar.add(options);

        librarianMenuItems();

        adminMenuItems();

        commonMenuItems();
    }

    private void addAdminMenuItems() {
        options = new JMenu("Options");
        menuBar.add(options);

        adminMenuItems();

        commonMenuItems();
    }

    private void addLibrarianMenuItems() {
        options = new JMenu("Options");
        menuBar.add(options);

        librarianMenuItems();

        commonMenuItems();
    }

    private void commonMenuItems() {
        JMenuItem showAllBooks = new JMenuItem("Show All Books");
        showAllBooks.addActionListener(new AllBookIdsListener());
        options.add(showAllBooks);

        JMenuItem showAllMembers = new JMenuItem("Show All Members");
        showAllMembers.addActionListener(new AllMemberIdsListener());
        options.add(showAllMembers);

        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(e -> {
            SystemController.logout();
            JOptionPane.showMessageDialog(this, "Logged out!");
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.init();
            LibrarySystem.INSTANCE.setVisible(true);
        });
        options.add(logout);
    }

    private void adminMenuItems() {
        JMenuItem addBook = new JMenuItem("Add Book");
        addBook.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            BookWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(BookWindow.INSTANCE);
            BookWindow.INSTANCE.setVisible(true);

        });
        options.add(addBook);

        JMenuItem addBookCopy = new JMenuItem("Add Book Copy");
        addBookCopy.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            AddBookCopyWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(BookWindow.INSTANCE);
            AddBookCopyWindow.INSTANCE.setVisible(true);
        });
        options.add(addBookCopy);

        JMenuItem addMember = new JMenuItem("Add Member");
        addMember.addActionListener(e -> {
            // add member window
            LibrarySystem.hideAllWindows();
            AddMember.INSTANCE.init();
            AddMember.INSTANCE.setVisible(true);
        });
        options.add(addMember);
    }

    private void librarianMenuItems() {
        JMenuItem checkoutBook = new JMenuItem("Checkout Book");
        checkoutBook.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            CheckoutBookWindow.INSTANCE.init();
            CheckoutBookWindow.INSTANCE.setVisible(true);
        });
        options.add(checkoutBook);

//        JMenuItem checkoutRecord = new JMenuItem("Checkout Record");
//        checkoutRecord.addActionListener(e -> {
//            // todo: checkout book window
//        });
        // not required for demo
//        options.add(checkoutRecord);

//        JMenuItem checkoutOverDue = new JMenuItem("Checkout Overdue");
//        checkoutOverDue.addActionListener(e -> {
//            // todo: checkout book window
//        });
        // not required for demo
//        options.add(checkoutOverDue);
    }

    private void addLoggedOutMenuItems() {
        options = new JMenu("Options");
        menuBar.add(options);

        login = new JMenuItem("Login");
        login.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
//            LoginWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
            LoginWindow.INSTANCE.setVisible(true);
        });
        options.add(login);

    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;

    }


    class AllBookIdsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();

            List<String> ids = ci.allBookIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for (String s : ids) {
                sb.append(s + "\n");
            }
            System.out.println(sb);
            AllBookIdsWindow.INSTANCE.setData(sb.toString());
            AllBookIdsWindow.INSTANCE.pack();
            // AllBookIdsWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
            AllBookIdsWindow.INSTANCE.setVisible(true);

        }

    }

    class AllMemberIdsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AllMemberIdsWindow.INSTANCE.init();

            List<String> ids = ci.allMemberIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for (String s : ids) {
                sb.append(s).append("\n");
            }
            System.out.println(sb);
            AllMemberIdsWindow.INSTANCE.setData(sb.toString());
            AllMemberIdsWindow.INSTANCE.pack();
            // AllMemberIdsWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
            AllMemberIdsWindow.INSTANCE.setVisible(true);
        }

    }

}
