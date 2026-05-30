import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainInventory extends JFrame implements ActionListener{

    JPanel top, middle, productsPanel, mainContent;
    JLabel inventory, doughflow;
    JTextField search;
    JButton editProd, addProd;
    JScrollPane scrollPane;

    MainInventory() {

        setTitle("Inventory Page");
        setSize(925, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // TOP PANEL: inventory and doughflow labels
        // inner layout: border (inventory-west, doughflow-east)
        // outer layout: top panel added to north of inventory page

        top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inventory = new JLabel("INVENTORY");
        inventory.setFont(new Font("Arial", Font.BOLD, 50));
        inventory.setForeground(Color.pink);

        doughflow = new JLabel("DOUGH FLOW");
        doughflow.setFont(new Font("Arial", Font.BOLD, 30));
        doughflow.setForeground(Color.pink);

        top.add(inventory, BorderLayout.WEST);
        top.add(doughflow, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);


        // MAIN CONTENTS: middle panel and products panel
        // inner layout: border (middle to north, products to scroll pane then center)
        // outer layout: added to the center of inventory page


        /*
        Inner layout of middle panel: border
        Components:
        -left panel in FlowLayout (west): search bar
        -right panel in FlowLayout to right (east): add, edit

        Middle panel added to Main Content North
        */

        middle = new JPanel(new BorderLayout());
        middle.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel leftSide = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel rightSide = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        search = new JTextField("SEARCH BAR", 20);

        editProd = new JButton("EDIT PRODUCT");
        addProd = new JButton("+ ADD NEW PRODUCT");

        leftSide.add(search);

        rightSide.add(editProd);
        rightSide.add(addProd);

        middle.add(leftSide, BorderLayout.WEST);
        middle.add(rightSide, BorderLayout.EAST);


        /*
        Inner layout of products panel: grid
        Components:
        -cards (image, name, quantity)

        Products panel added to SCROLL PANE then added to Main Content Center
        */

        productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(0, 4, 20, 20));
        productsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        //PRODUCT CARDS
        for (int i = 1; i <= 8; i++) {

            JPanel card = new JPanel();
            card.setPreferredSize(new Dimension(150, 150));
            card.setBackground(new Color(255, 220, 220));

            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

            JLabel image = new JLabel("IMAGE");
            image.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel name = new JLabel("Product Name");
            name.setFont(new Font("Arial", Font.BOLD, 18));
            name.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel amount = new JLabel("Amount");
            amount.setFont(new Font("Arial", Font.BOLD, 18));
            amount.setAlignmentX(Component.CENTER_ALIGNMENT);

            card.add(Box.createVerticalStrut(20));
            card.add(image);
            card.add(Box.createVerticalStrut(10));
            card.add(name);
            card.add(amount);

            productsPanel.add(card);
        }


        // ================= SCROLLPANE =================
        scrollPane = new JScrollPane(productsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        // ================= MAIN CONTENT =================
        mainContent = new JPanel(new BorderLayout());

        mainContent.add(middle, BorderLayout.NORTH);
        mainContent.add(scrollPane, BorderLayout.CENTER);

        add(mainContent, BorderLayout.CENTER);


        setVisible(true);

        addProd.addActionListener(this);
        editProd.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(addProd)){
            new AddProductPage();
        }

        if (e.getSource().equals(editProd)){
            new EditProductPage();
        }
    }

    public static void main(String[] args) {
        new MainInventory();
    }
}