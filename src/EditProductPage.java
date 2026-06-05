import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EditProductPage extends JFrame{
    private MainInventory parentFrame;

    JDialog editProductDialog= new JDialog();

    JScrollPane scrollPane;
    JLabel title;
    JPanel header,listPanel, bottomPanel;
    RoundedButton btnSave;

    List <ProductsDatabase.Product> products= ProductsDatabase.loadProducts(); //outer.inner var= new outer.inner();
    List<JTextField> nameFields = new ArrayList<>(); //for update checking
    List<JTextField> quantityFields = new ArrayList<>(); //for update checking
    Set<String> markedForDeletion = new HashSet<>(); //for accurate deleting

    EditProductPage(MainInventory parent){
        this.parentFrame=parent;

        editProductDialog.setSize(600, 400);
        editProductDialog.setLocationRelativeTo(null);
        editProductDialog.setLayout(new BorderLayout());

        title= new JLabel("EDIT PRODUCT");
        title.setForeground(Color.white);
        title.setFont(MainInventory.LazyDog.deriveFont(40f));
        title.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        header= new JPanel();
        header.setBackground(AppColors.pinkishOrange);
        header.add(title);

        editProductDialog.add(header, BorderLayout.NORTH);

        listPanel= new JPanel();
        listPanel.setBackground(AppColors.pinkishOrange);
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (ProductsDatabase.Product p: products){
            RoundedPanel roundedRows; //fixed delete bug by moving this inside the loop (no more overwriting)
            //roundedRows serve as the oblong that contains each product's details
            roundedRows= new RoundedPanel(60);
            roundedRows.setLayout(new BorderLayout());
            roundedRows.setPreferredSize(new Dimension(500, 60));
            roundedRows.setMaximumSize(new Dimension(550, 60));
            //roundedRows.setMinimumSize(new Dimension(500, 60));
            roundedRows.setBackground(AppColors.lightPinkishOrange);

            //indivProducts groups each product's details and buttons
            JPanel indivProducts= new JPanel(new BorderLayout());
            indivProducts.setOpaque(false);

            //leftSide contains the product name, while the right side contains the quantity and adjustment buttons
            JPanel leftSide= new JPanel(new FlowLayout(FlowLayout.LEFT));
            leftSide.setOpaque(false);

            JPanel rightSide= new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            rightSide.setOpaque(false);

            //component of the left side
            JTextField productName= new JTextField(p.name, 15);
            productName.setFont(MainInventory.PoppinsBold.deriveFont(20f));
            productName.setForeground(AppColors.darkRed);
            //productName.setHorizontalAlignment(JTextField.CENTER);
            productName.setBorder(null);
            productName.setOpaque(false);

            leftSide.add(productName);
            leftSide.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

            //left side is added to indivProducts
            indivProducts.add(leftSide, BorderLayout.WEST);

            //components of the right side
            JTextField quantity= new JTextField(String.valueOf(p.quantity), 4);
            quantity.setFont(MainInventory.PoppinsRegular.deriveFont(20f));
            quantity.setForeground(AppColors.darkRed);
            quantity.setHorizontalAlignment(JTextField.CENTER);
            quantity.setBorder(null);
            quantity.setOpaque(false);

            nameFields.add(productName);
            quantityFields.add(quantity);

            //for add and deduct buttons
            JButton btnAdd= new JButton("+");
            btnAdd.setFont(MainInventory.PoppinsBold.deriveFont(30f));
            btnAdd.setForeground(AppColors.darkRed);
            btnAdd.setContentAreaFilled(false);
            btnAdd.setBorderPainted(false);
            btnAdd.setFocusPainted(false);
            btnAdd.setOpaque(false);

            JButton deduct= new JButton("-");
            deduct.setFont(MainInventory.PoppinsBold.deriveFont(30f));
            deduct.setForeground(AppColors.darkRed);
            deduct.setContentAreaFilled(false);
            deduct.setBorderPainted(false);
            deduct.setFocusPainted(false);
            deduct.setOpaque(false);
            //end of add and deduct buttons

            //for trash icon
            ImageIcon icon = new ImageIcon("trashbin.png");
            Image img = icon.getImage();
            Image resizedImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImg);
            JButton trash = new JButton(resizedIcon);

            trash.setContentAreaFilled(false);
            trash.setBorderPainted(false);
            trash.setFocusPainted(false);
            trash.setOpaque(false);
            //end of trash icon

            btnAdd.addActionListener((e ->{
                int current= Integer.parseInt(quantity.getText());
                quantity.setText(String.valueOf(current+1));
            }));

            deduct.addActionListener(e->{
                if(Integer.parseInt(quantity.getText()) > 0){
                    int current= Integer.parseInt(quantity.getText());
                    quantity.setText(String.valueOf(current-1));
                }else{
                    JOptionPane.showMessageDialog(this, "Quantity cannot be negative", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            });

            trash.addActionListener(e ->{
                    if (markedForDeletion.contains(p.name)) {
                        // clicking trash again = undo
                        markedForDeletion.remove(p.name);
                        roundedRows.setBackground(AppColors.lightPinkishOrange); // visual feedback
                    } else {
                        markedForDeletion.add(p.name);
                        roundedRows.setBackground(Color.lightGray);   // visual feedback
                    }
            });

            rightSide.add(trash);
            rightSide.add(deduct);
            rightSide.add(quantity);
            rightSide.add(btnAdd);

            indivProducts.add(rightSide, BorderLayout.EAST);

            roundedRows.add(indivProducts);
            listPanel.add(Box.createVerticalStrut(10));
            listPanel.add(roundedRows);
        }

        scrollPane= new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        editProductDialog.add(scrollPane, BorderLayout.CENTER);

        bottomPanel= new JPanel();
        bottomPanel.setBackground(AppColors.pinkishOrange);

        btnSave= new RoundedButton("Save Changes", 60);
        btnSave.setBackground(AppColors.pinkishOrange);
        btnSave.setPreferredSize(new Dimension(150, 40));
        btnSave.setForeground(AppColors.darkRed);
        btnSave.setFont(MainInventory.PoppinsRegular.deriveFont(15f));
        bottomPanel.add(btnSave);

        editProductDialog.add(bottomPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e->{
            for (String name : markedForDeletion) {
                ProductsDatabase.deleteProduct(name);
            }

            for (int i=0; i<products.size(); i++){
                String newName= nameFields.get(i).getText().trim();
                int newQty= Integer.parseInt(quantityFields.get(i).getText().trim());

                ProductsDatabase.Product updated= new ProductsDatabase.Product(newName, newQty, products.get(i).imagePath);
                ProductsDatabase.updateProduct(products.get(i).name, updated);
            }
            parentFrame.loadProductCards();
            editProductDialog.dispose();
        });

        editProductDialog.setModal(true);
        editProductDialog.setVisible(true);
    }
}
