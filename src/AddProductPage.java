import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddProductPage extends JFrame implements ActionListener, KeyListener {
    private MainInventory parentFrame;
    private String selectedImagePath= null;

    JDialog addProductDialog= new JDialog();

    JLabel title, productName, initQuantity, uploadPic;
    RoundedTextField enterProductName, enterInitQuantity;
    JButton uploadSamplePic, done;
    JPanel productPanel, quantityPanel, uploadPanel, donePanel;

    AddProductPage(MainInventory parent){
        this.parentFrame= parent;

        addProductDialog.setSize(400, 600);
        addProductDialog.setLocationRelativeTo(null);
        addProductDialog.setLayout(new GridLayout(8,1));
        addProductDialog.getContentPane().setBackground(AppColors.pinkishOrange);

        title= new JLabel("ADD NEW PRODUCT");
        title.setForeground(Color.white);
        title.setFont(MainInventory.LazyDog.deriveFont(35f));
        title.setBorder(BorderFactory.createEmptyBorder(40, 40, 10, 30));

        productName= new JLabel("Product Name:");
        productName.setForeground(AppColors.darkRed);
        productName.setFont(new Font("Canva Sans", Font.BOLD, 20));
        productName.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        initQuantity= new JLabel("Initial Quantity:");
        initQuantity.setForeground(AppColors.darkRed);
        initQuantity.setFont(new Font("Canva Sans", Font.BOLD, 20));
        initQuantity.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        uploadPic= new JLabel("Upload Picture :");
        uploadPic.setForeground(AppColors.darkRed);
        uploadPic.setFont(new Font("Canva Sans", Font.BOLD, 20));
        uploadPic.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        //this part implements the rounding of the add product txtfield
        productPanel= new JPanel(new FlowLayout(FlowLayout.CENTER));
        productPanel.setBackground(AppColors.pinkishOrange);

        enterProductName= new RoundedTextField(60);
        enterProductName.setPreferredSize(new Dimension(350, 60));
        enterProductName.setFont(new Font("Canva Sans", Font.PLAIN, 20));
        enterProductName.setHorizontalAlignment(JTextField.CENTER);
        enterProductName.setForeground(AppColors.darkRed);
        enterProductName.setBackground(AppColors.lightPinkishOrange);

        productPanel.add(enterProductName);

        //this part implements the rounding of the init quantity txtfield
        quantityPanel= new JPanel(new FlowLayout(FlowLayout.CENTER));
        quantityPanel.setBackground(AppColors.pinkishOrange);

        enterInitQuantity= new RoundedTextField(60);
        enterInitQuantity.setPreferredSize(new Dimension(350, 60));
        enterInitQuantity.setFont(new Font("Canva Sans", Font.PLAIN, 20));
        enterInitQuantity.setHorizontalAlignment(JTextField.CENTER);
        enterInitQuantity.setForeground(AppColors.darkRed);
        enterInitQuantity.setBackground(AppColors.lightPinkishOrange);

        quantityPanel.add(enterInitQuantity);

        //this part implements the upload pic btn
        uploadPanel= new JPanel(new FlowLayout(FlowLayout.CENTER));
        uploadPanel.setBackground(AppColors.pinkishOrange);

        uploadSamplePic= new RoundedButton("UPLOAD SAMPLE PIC", 60);
        uploadSamplePic.setPreferredSize(new Dimension(350, 60));
        uploadSamplePic.setForeground(AppColors.grayRed);
        uploadSamplePic.setBackground(AppColors.lightPinkishOrange);
        uploadSamplePic.addActionListener(this);

        uploadPanel.add(uploadSamplePic);

        //this part implements the done btn
        donePanel= new JPanel(new FlowLayout(FlowLayout.CENTER));
        donePanel.setBackground(AppColors.pinkishOrange);

        done= new RoundedButton("DONE", 60);
        done.setPreferredSize(new Dimension(350, 60));
        done.setFont(new Font("Canva Sans", Font.BOLD, 20));
        done.setForeground(AppColors.darkRed);
        done.setBackground(AppColors.lightPinkishOrange);
        done.addActionListener(this);
        done.addKeyListener(this);

        donePanel.add(done);
        //end of elements

        enterProductName.addKeyListener(this);
        enterInitQuantity.addKeyListener(this);

        addProductDialog.add(title);
        addProductDialog.add(productName);
        addProductDialog.add(productPanel);
        addProductDialog.add(initQuantity);
        addProductDialog.add(quantityPanel);
        addProductDialog.add(uploadPic);
        addProductDialog.add(uploadPanel);
        addProductDialog.add(donePanel);

        addProductDialog.setModal(true);
        addProductDialog.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(done)) {
            String name = enterProductName.getText().trim();
            String qtyText = enterInitQuantity.getText().trim();

            if (!name.isEmpty() && !qtyText.isEmpty()) {
                try {
                    int qty = Integer.parseInt(qtyText);

                    // Use the stored image path, or "DEFAULT" if none selected
                    String imgPath = (selectedImagePath != null) ? selectedImagePath : "DEFAULT";

                    ProductsDatabase.addProduct(new ProductsDatabase.Product(name, qty, imgPath));

                    addProductDialog.dispose();
                    parentFrame.loadProductCards(); // Refresh the main inventory view

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Quantity must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please complete all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == uploadSamplePic) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(addProductDialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedImagePath = fileChooser.getSelectedFile().getAbsolutePath();
                uploadSamplePic.setText("Image Selected"); // Visual feedback
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER) {
            String name = enterProductName.getText().trim();
            String qtyText = enterInitQuantity.getText().trim();

            if (!name.isEmpty() && !qtyText.isEmpty()) {
                try {
                    int qty = Integer.parseInt(qtyText);

                    // Use the stored image path, or "DEFAULT" if none selected
                    String imgPath = (selectedImagePath != null) ? selectedImagePath : "DEFAULT";

                    ProductsDatabase.addProduct(new ProductsDatabase.Product(name, qty, imgPath));

                    addProductDialog.dispose();
                    parentFrame.loadProductCards(); // Refresh the main inventory view

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Quantity must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please complete all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
