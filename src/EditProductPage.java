import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditProductPage {
    JDialog editProductDialog= new JDialog();

    JScrollPane scrollPane;
    JLabel title;
    JPanel header,listPanel;
    RoundedPanel roundedRows;

    EditProductPage(){
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

        for (int i=0; i<=8; i++){
            //roundedRows serve as the oblong that contains each product's details
            roundedRows= new RoundedPanel(60);
            roundedRows.setLayout(new BorderLayout());
            roundedRows.setPreferredSize(new Dimension(500, 60));
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
            JTextField productName= new JTextField(20);
            productName.setFont(MainInventory.PoppinsBold.deriveFont(20f));
            productName.setForeground(AppColors.darkRed);
            productName.setBorder(null);
            productName.setOpaque(false);

            leftSide.add(productName);
            leftSide.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

            //left side is added to indivProducts
            indivProducts.add(leftSide, BorderLayout.WEST);

            //components of the right side
            JTextField quantity= new JTextField(3);
            quantity.setFont(MainInventory.PoppinsRegular.deriveFont(20f));
            quantity.setForeground(AppColors.darkRed);
            quantity.setHorizontalAlignment(JTextField.CENTER);
            quantity.setBorder(null);
            quantity.setOpaque(false);


            //for add and deduct buttons
            JButton add= new JButton("+");
            add.setFont(MainInventory.PoppinsBold.deriveFont(30f));
            add.setForeground(AppColors.darkRed);
            add.setContentAreaFilled(false);
            add.setBorderPainted(false);
            add.setFocusPainted(false);
            add.setOpaque(false);

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

            rightSide.add(trash);
            rightSide.add(deduct);
            rightSide.add(quantity);
            rightSide.add(add);

            indivProducts.add(rightSide, BorderLayout.EAST);

            roundedRows.add(indivProducts);
            listPanel.add(Box.createVerticalStrut(10));
            listPanel.add(roundedRows);
        }

        scrollPane= new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        editProductDialog.add(scrollPane, BorderLayout.CENTER);

        editProductDialog.setVisible(true);
    }

}
