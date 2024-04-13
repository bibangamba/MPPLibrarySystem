package librarysystem;

import business.BookException;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;

public class AddBookCopyWindow extends JFrame implements LibWindow {
    public static final AddBookCopyWindow INSTANCE = new AddBookCopyWindow();
    public static final String ISBN_FORMAT = "\\d{2}-\\d{5}";

    private boolean isInitialized = false;

    private JTextField isbnField;
    private JTextField bookCopiesField;

    /* This class is a singleton */
    private AddBookCopyWindow() {
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
        panel.setBounds(0, 0, 375, 189);
        panel.setLayout(null);

        JLabel addNewBookLabel = new JLabel("Add Book Copy");
        addNewBookLabel.setBounds(114, 19, 127, 16);
        panel.add(addNewBookLabel);
        addNewBookLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel isbnLabel = new JLabel("ISBN");
        isbnLabel.setBounds(60, 52, 39, 16);
        panel.add(isbnLabel);

        isbnField = new JTextField();
        isbnField.setBounds(155, 47, 102, 26);
        isbnField.setColumns(10);
        panel.add(isbnField);

        JLabel bookCopies = new JLabel("Num. Copies");
        bookCopies.setBounds(60, 80, 87, 16);
        panel.add(bookCopies);

        bookCopiesField = new JTextField();
        bookCopiesField.setBounds(155, 75, 49, 26);
        panel.add(bookCopiesField);
        bookCopiesField.setColumns(10);

        JButton addBookBtn = setupAddBookCopyBtn();
        panel.add(addBookBtn);


        JButton backBtn = new JButton("< Back");
        backBtn.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
        backBtn.setBounds(6, 123, 87, 29);
        panel.add(backBtn);

        getContentPane().add(panel);
        isInitialized(true);
//        setVisible(true);
        setSize(375, 210);
    }

    private JButton setupAddBookCopyBtn() {
        JButton addBookCopyBtn = new JButton("Add Copy");
        addBookCopyBtn.addActionListener(e -> {
            ControllerInterface ci = new SystemController();

            try {
                String isbn = isbnField.getText();
                int copies = Integer.parseInt(bookCopiesField.getText());

                if (isbn.isEmpty()) throw new BookException("Fields should not be empty!");
                if (!isbn.matches(ISBN_FORMAT)) {
                    throw new BookException("Invalid ISBN format. Should be similar to: 12-12345");
                }
                if (copies < 1) throw new BookException("Number of copies must be greater than 1");

                int lastCopyNum = ci.addBookCopy(isbn, copies);

                JOptionPane.showMessageDialog(this, "Book copy added! Last copyNum is: "+lastCopyNum);
                isbnField.setText("");
                bookCopiesField.setText("");

            } catch (NumberFormatException ne) {
                JOptionPane.showMessageDialog(this, "Number of copies must be a number");
            } catch (BookException ex) {
                JOptionPane.showMessageDialog(this, "Failed to add the book. Reason:" + ex.getMessage());
            }
        });
        addBookCopyBtn.setBounds(238, 123, 112, 29);
        return addBookCopyBtn;
    }
}
