import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddProductPage extends JFrame implements ActionListener{
    JDialog addProductDialog= new JDialog();
    JLabel title, productName, initQuantity, uploadPic;
    JTextField enterProductName, enterInitQuantity;
    JButton uploadSamplePic, done;

    AddProductPage(){
        addProductDialog.setSize(400, 600);
        addProductDialog.setLocationRelativeTo(null);
        addProductDialog.setLayout(new GridLayout(8,1));

        title= new JLabel("ADD NEW PRODUCT");
        title.setForeground(Color.pink);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 30));

        productName= new JLabel("Product Name:");
        productName.setForeground(Color.pink);
        productName.setFont(new Font("Arial", Font.PLAIN, 20));

        initQuantity= new JLabel("Initial Quantity:");
        initQuantity.setForeground(Color.pink);
        initQuantity.setFont(new Font("Arial", Font.PLAIN, 20));

        uploadPic= new JLabel("Upload Picture:");
        uploadPic.setForeground(Color.pink);
        uploadPic.setFont(new Font("Arial", Font.PLAIN, 20));

        enterProductName= new JTextField(40);
        enterProductName.setFont(new Font("Arial", Font.PLAIN, 20));

        enterInitQuantity= new JTextField(40);
        enterInitQuantity.setFont(new Font("Arial", Font.PLAIN, 20));

        uploadSamplePic= new JButton("UPLOAD SAMPLE PIC");
        uploadSamplePic.addActionListener(this);

        done= new JButton("DONE");
        done.setForeground(Color.pink);
        done.addActionListener(this);

        addProductDialog.add(title);
        addProductDialog.add(productName);
        addProductDialog.add(enterProductName);
        addProductDialog.add(initQuantity);
        addProductDialog.add(enterInitQuantity);
        addProductDialog.add(uploadPic);
        addProductDialog.add(uploadSamplePic);
        addProductDialog.add(done);

        addProductDialog.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == uploadSamplePic){

            JFileChooser fileChooser = new JFileChooser();

            int result = fileChooser.showOpenDialog(addProductDialog);

            if(result == JFileChooser.APPROVE_OPTION){
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                JOptionPane.showMessageDialog(addProductDialog, "Selected File:\n" + filePath);
            }
        }

        if (e.getSource().equals(done)){
            if (!enterProductName.getText().trim().isEmpty() && !enterInitQuantity.getText().trim().isEmpty()){
                addProductDialog.dispose();
            }else{
                JOptionPane.showMessageDialog(this, "Please complete all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
