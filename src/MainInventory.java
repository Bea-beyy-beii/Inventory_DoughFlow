import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MainInventory extends JPanel implements ActionListener, DocumentListener{
    //external fonts used in the program
    public static Font LazyDog;
    public static Font PoppinsBold;
    public static Font PoppinsRegular;

    public static Font loadFont(String path) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, MainInventory.class.getResourceAsStream(path));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            return font;

        } catch (FontFormatException | IOException e) {
        }

        return new Font("SansSerif", Font.PLAIN, 12); // Default Font if error
    }

    JPanel top, middle, productsPanel, mainContent, overallPanel;
    JLabel inventory, doughflow;
    JTextField searchBox;
    RoundedButton editProd, addProd;
    RoundedPanel searchPanel;
    JScrollPane scrollPane;

    MainInventory() {

        //setTitle("Inventory Page");
        setSize(925, 650);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // TOP PANEL: inventory and doughflow labels
        // inner layout: border (inventory-west, doughflow-east)
        // outer layout: top panel added to north of inventory page

        top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inventory = new JLabel("INVENTORY");
        inventory.setFont(MainInventory.LazyDog.deriveFont(60f));
        inventory.setForeground(AppColors.darkRed);

        doughflow = new JLabel("DOUGH FLOW");
        doughflow.setFont(MainInventory.LazyDog.deriveFont(40f));
        doughflow.setForeground(AppColors.grayRed);

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

        searchPanel= new RoundedPanel(60);
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setPreferredSize(new Dimension(320, 50));
        searchPanel.setBackground(AppColors.pinkishOrange);

        searchBox = new JTextField(20);
        searchBox.setForeground(AppColors.darkRed);
        searchBox.setFont(new Font("PoppinsRegular", Font.PLAIN, 15));
        searchBox.setOpaque(false);
        searchBox.setBorder(null);

        ImageIcon searchIcon= new ImageIcon("search2.png");
        Image searchImg= searchIcon.getImage();
        Image resizedSearchImg= searchImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon resizedSearchIcon= new ImageIcon(resizedSearchImg);
        JButton searchIconBtn= new JButton(resizedSearchIcon);
        searchIconBtn.setContentAreaFilled(false);
        searchIconBtn.setBorderPainted(false);
        searchIconBtn.setFocusPainted(false);
        searchIconBtn.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 0));

        searchPanel.add(searchBox, BorderLayout.WEST);
        searchPanel.add(searchIconBtn, BorderLayout.EAST);

        leftSide.add(searchPanel);


        editProd = new RoundedButton("EDIT PRODUCT", 60);
        editProd.setPreferredSize(new Dimension(180, 50));
        editProd.setForeground(AppColors.darkRed);
        editProd.setBackground(AppColors.pinkishOrange);
        editProd.setFont(new Font("PoppinsBold", Font.PLAIN, 15));

        addProd = new RoundedButton("ADD PRODUCT", 60);
        addProd.setPreferredSize(new Dimension(180, 50));
        addProd.setForeground(AppColors.darkRed);
        addProd.setBackground(AppColors.pinkishOrange);
        addProd.setFont(new Font("PoppinsBold", Font.PLAIN, 15));

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


        loadProductCards();


        // ================= SCROLLPANE =================
        scrollPane = new JScrollPane(productsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        // ================= MAIN CONTENT =================
        mainContent = new JPanel(new BorderLayout());

        mainContent.add(middle, BorderLayout.NORTH);
        mainContent.add(scrollPane, BorderLayout.CENTER);

        add(mainContent, BorderLayout.CENTER);

        //final panel

        overallPanel= new JPanel(new BorderLayout());
        overallPanel.add(top, BorderLayout.NORTH);
        overallPanel.add(mainContent, BorderLayout.CENTER);

        setVisible(true);

        addProd.addActionListener(this);
        editProd.addActionListener(this);

        searchBox.getDocument().addDocumentListener(this);

    }

    public void loadProductCards() {
        displayProducts(ProductsDatabase.loadProducts());
    }

    public void displayProducts(List<ProductsDatabase.Product> products) {
        productsPanel.removeAll();

        if (products.isEmpty()) {
            JLabel empty = new JLabel("No products found");
            empty.setForeground(AppColors.darkRed);
            empty.setFont(new Font("PoppinsRegular", Font.PLAIN, 20));
            empty.setHorizontalAlignment(SwingConstants.CENTER);
            productsPanel.setLayout(new BorderLayout());
            productsPanel.add(empty, BorderLayout.CENTER);
        } else {
            productsPanel.setLayout(new GridLayout(0, 4, 0, 0));
            for (ProductsDatabase.Product p : products) {
                RoundedPanel card = createProductCard(p);
                JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
                wrapper.add(card);
                wrapper.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                productsPanel.add(wrapper);
            }
        }

        productsPanel.revalidate();
        productsPanel.repaint();
    }

    // Build one product card from a Product object
    private RoundedPanel createProductCard(ProductsDatabase.Product product) {
        RoundedPanel card = new RoundedPanel(50);
        card.setPreferredSize(new Dimension(180, 180));
        //card.setMaximumSize(new Dimension(180, 180));
        card.setMinimumSize(new Dimension(180, 180));
        card.setBackground(AppColors.lightPinkishOrange);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        // Show image or a default placeholder label
        if (!product.imagePath.equals("DEFAULT")) {
            ImageIcon icon = new ImageIcon(product.imagePath);
            Image scaled = icon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            JLabel image = new JLabel(new ImageIcon(scaled));
            image.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
            image.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(Box.createVerticalStrut(10));
            card.add(image);
        } else {
            ImageIcon icon = new ImageIcon("cake_default.png");
            Image scaled = icon.getImage().getScaledInstance(100, 90, Image.SCALE_SMOOTH);
            JLabel image = new JLabel(new ImageIcon(scaled));
            image.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
            image.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(Box.createVerticalStrut(10));
            card.add(image);
        }

        JLabel name = new JLabel(product.name);
        name.setFont(MainInventory.LazyDog.deriveFont(15f));
        name.setForeground(AppColors.darkRed);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel amount = new JLabel("Stocks: " + product.quantity);
        amount.setFont(MainInventory.PoppinsRegular.deriveFont(15f));
        amount.setForeground(AppColors.darkRed);
        amount.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(10));
        card.add(Box.createHorizontalStrut(0));
        card.add(name);
        card.add(amount);
        card.setDrawBorder(false);

        return card;
    }

    public void filterProducts(){
        String query= searchBox.getText().trim().toLowerCase();
        List<ProductsDatabase.Product> all = ProductsDatabase.loadProducts();

        List<ProductsDatabase.Product> filtered = new ArrayList<>();
        for (ProductsDatabase.Product p : all) {
            if (p.name.toLowerCase().contains(query)) {
                filtered.add(p);
            }
        }

        displayProducts(filtered);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(addProd)){
            new AddProductPage(this);
        }

        if (e.getSource().equals(editProd)){
            List<ProductsDatabase.Product> products = ProductsDatabase.loadProducts();

            if (!products.isEmpty()){
                new EditProductPage(this);
            }else{
                JOptionPane.showMessageDialog(this, "No existing products yet!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        filterProducts();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        filterProducts();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        filterProducts();
    }

    public static void main(String[] args) {
        LazyDog = loadFont("/Fonts/LazyDog.ttf");
        PoppinsBold = loadFont("/Fonts/Poppins-Bold.ttf");
        PoppinsRegular = loadFont("/Fonts/Poppins-Regular.ttf");

        new MainInventory();
    }
}