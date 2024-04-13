package librarysystem;

import business.BookException;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BookWindow extends JFrame implements LibWindow {
    public static final BookWindow INSTANCE = new BookWindow();
    public static final String ISBN_FORMAT = "\\d{2}-\\d{5}";

    private boolean isInitialized = false;

    private JTextField messageBar = new JTextField();
    private JTextField isbnField;
    private JTextField titleField;
    private JTextField authorsField;
    private JTextField bookCopiesField;

    /* This class is a singleton */
    private BookWindow() {
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
        panel.setBounds(0, 0, 444, 265);

        panel.setLayout(null);

        JLabel addNewBookLabel = new JLabel("Add New Book");
        addNewBookLabel.setBounds(130, 19, 127, 16);
        panel.add(addNewBookLabel);
        addNewBookLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel isbnLabel = new JLabel("ISBN");
        isbnLabel.setBounds(60, 52, 39, 16);
        panel.add(isbnLabel);

        JLabel titleLabel = new JLabel("Book Title");
        titleLabel.setBounds(60, 80, 73, 16);
        panel.add(titleLabel);

        isbnField = new JTextField();
        isbnField.setBounds(155, 47, 102, 26);
        panel.add(isbnField);
        isbnField.setColumns(10);

        titleField = new JTextField();
        titleField.setBounds(155, 75, 187, 26);
        panel.add(titleField);
        titleField.setColumns(10);
        getContentPane().add(panel);

        JLabel lblNewLabel = new JLabel("Checkout Days");
        lblNewLabel.setBounds(60, 108, 102, 16);
        panel.add(lblNewLabel);

        ButtonGroup checkoutDaysGroup = new ButtonGroup();

        JRadioButton radioBtn7Days = new JRadioButton("7 days");
        radioBtn7Days.setSelected(true);
        radioBtn7Days.setBounds(160, 104, 73, 23);
        panel.add(radioBtn7Days);

        JRadioButton radioBtn21Days = new JRadioButton("21 days");
        radioBtn21Days.setBounds(245, 104, 87, 23);
        panel.add(radioBtn21Days);

        // add radio buttons to group
        checkoutDaysGroup.add(radioBtn7Days);
        checkoutDaysGroup.add(radioBtn21Days);

        JLabel authorLabel = new JLabel("Author ID(s)");
        authorLabel.setBounds(60, 136, 83, 16);
        panel.add(authorLabel);

        authorsField = new JTextField();
        authorsField.setToolTipText("Comma separated Author IDs");
        authorsField.setBounds(155, 131, 127, 26);
        panel.add(authorsField);
        authorsField.setColumns(10);

        JLabel bookCopies = new JLabel("Num. Copies");
        bookCopies.setBounds(60, 164, 87, 16);
        panel.add(bookCopies);

        bookCopiesField = new JTextField();
        bookCopiesField.setBounds(155, 159, 49, 26);
        panel.add(bookCopiesField);
        bookCopiesField.setColumns(10);

        JButton addBookBtn = setupAddBookBtn(radioBtn7Days);
        panel.add(addBookBtn);


        JButton backBtn = new JButton("< Back");
        backBtn.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
        backBtn.setBounds(6, 190, 87, 29);
        panel.add(backBtn);


        JButton addAuthorBtn = new JButton("Add");
        addAuthorBtn.setBounds(281, 131, 73, 29);
        panel.add(addAuthorBtn);

        JButton viewAuthorsBtn = new JButton("View");
        viewAuthorsBtn.setBounds(351, 131, 73, 29);
        panel.add(viewAuthorsBtn);

        viewAuthorsBtn.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
//            AllAuthorsWindow.INSTANCE.init();
            AllAuthorsWindow.INSTANCE.setVisible(true);

            ControllerInterface ci = new SystemController();
            List<String> authors = ci.getAllAuthors();
            Collections.sort(authors);
            StringBuilder sb = new StringBuilder();
            for (String s : authors) {
                sb.append(s).append("\n");
            }
            AllAuthorsWindow.INSTANCE.setData(sb.toString());
            AllAuthorsWindow.INSTANCE.pack();
            Util.centerFrameOnDesktop(AllAuthorsWindow.INSTANCE);
            AllAuthorsWindow.INSTANCE.setVisible(true);
        });

        addAuthorBtn.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
//            AddAuthorWindow.INSTANCE.init();
            AddAuthorWindow.INSTANCE.setVisible(true);
        });

        isInitialized(true);
//        setVisible(true);
        setSize(430, 270);
    }

    private JButton setupAddBookBtn(JRadioButton radioBtn7Days) {
        JButton addBookBtn = new JButton("Add Book");
        addBookBtn.addActionListener(e -> {
            ControllerInterface ci = new SystemController();

            try {
                String isbn = isbnField.getText();
                String title = titleField.getText();
                int checkoutDays = radioBtn7Days.isSelected() ? 7 : 21;
                int copies = Integer.parseInt(bookCopiesField.getText());
                List<String> authorIds = Arrays.asList(authorsField.getText().split(","));

                if (isbn.isEmpty() || title.isEmpty()) throw new BookException("Fields should not be empty!");
                if (!isbn.matches(ISBN_FORMAT)) {
                    throw new BookException("Invalid ISBN format. Should be similar to: 12-12345");
                }
                if (copies < 1) throw new BookException("Number of copies must be greater than 1");

                ci.addBook(isbn, title, checkoutDays, copies, authorIds);

                JOptionPane.showMessageDialog(this, "Book added!");
                LibrarySystem.hideAllWindows();
                LibrarySystem.INSTANCE.init();
                LibrarySystem.INSTANCE.setVisible(true);

            } catch (NumberFormatException ne) {
                System.out.println("###### isbn: " + isbnField.getText());
                System.out.println("###### title: " + titleField.getText());
                System.out.println("###### 7 days: " + radioBtn7Days.isSelected());
                System.out.println("###### Copies field: " + bookCopiesField.getText());
                JOptionPane.showMessageDialog(this, "Number of copies must be a number");
            } catch (BookException ex) {
                JOptionPane.showMessageDialog(this, "Failed to add the book. Reason:" + ex.getMessage());
            }
        });
        addBookBtn.setBounds(308, 190, 112, 29);
        return addBookBtn;
    }
}
