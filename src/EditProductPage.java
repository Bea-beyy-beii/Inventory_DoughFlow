import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class EditProductPage {
    JDialog editProductDialog;
    JScrollPane scrollPane;
    JLabel title;
    JPanel header,listPanel;
    Color deepPink = new Color(199, 21, 133);

    EditProductPage(){
        editProductDialog= new JDialog();
        editProductDialog.setSize(600, 400);
        editProductDialog.setLocationRelativeTo(null);
        editProductDialog.setLayout(new BorderLayout());

        title= new JLabel("EDIT PRODUCT");
        title.setForeground(Color.pink);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        header= new JPanel();
        header.add(title);

        editProductDialog.add(header, BorderLayout.NORTH);

        listPanel= new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        listPanel.setBackground(new Color(255, 220, 220));

        for (int i=0; i<=8; i++){
            JPanel indivProducts= new JPanel(new BorderLayout());
            indivProducts.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            indivProducts.setBackground(new Color(255, 220, 220));

            JPanel leftSide= new JPanel(new FlowLayout(FlowLayout.LEFT));
            leftSide.setBackground(new Color(255, 220, 220));

            JPanel rightSide= new JPanel(new FlowLayout(FlowLayout.RIGHT));
            rightSide.setBackground(new Color(255, 220, 220));

            JTextField productName= new JTextField(20);
            productName.setFont(new Font("Arial", Font.PLAIN, 15));
            leftSide.add(productName);
            leftSide.setBorder(BorderFactory.createEmptyBorder(15,0,5,0));

            indivProducts.add(leftSide, BorderLayout.WEST);

            JTextField quantity= new JTextField(3);
            quantity.setFont(new Font("Arial", Font.PLAIN, 15));

            //for add and deduct buttons
            JButton add= new JButton("+");
            add.setFont(new Font("Arial", Font.BOLD, 20));
            add.setForeground(deepPink);
            add.setContentAreaFilled(false);
            add.setBorderPainted(false);
            add.setFocusPainted(false);
            add.setOpaque(false);

            JButton deduct= new JButton("-");
            deduct.setFont(new Font("Arial", Font.BOLD, 30));
            deduct.setForeground(deepPink);
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

            listPanel.add(indivProducts);
        }

        scrollPane= new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        editProductDialog.add(scrollPane, BorderLayout.CENTER);

        editProductDialog.setVisible(true);
    }

}
