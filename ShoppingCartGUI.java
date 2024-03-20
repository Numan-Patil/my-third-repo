import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.UIManager;

public class ShoppingCartGUI extends JFrame {

    private ArrayList<Product> cart;
    private DefaultListModel<String> cartListModel;
    private JList<String> cartList;
    private JButton addToCartButton, removeFromCartButton, checkoutButton;

    public ShoppingCartGUI() {
        super("Shopping Cart");
        cart = new ArrayList<>();
        cartListModel = new DefaultListModel<>();

        // Set Nimbus look and feel for modern appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create GUI components
        JLabel productLabel = new JLabel("Products:");
        JComboBox<Product> productComboBox = new JComboBox<>(createSampleProducts());
        JTextField quantityField = new JTextField(5);
        addToCartButton = new JButton("Add to Cart");
        cartList = new JList<>(cartListModel);
        removeFromCartButton = new JButton("Remove from Cart");
        checkoutButton = new JButton("Checkout");

        // Layout components
        JPanel productPanel = new JPanel();
        productPanel.add(productLabel);
        productPanel.add(productComboBox);
        productPanel.add(quantityField);
        productPanel.add(addToCartButton);

        JPanel cartPanel = new JPanel();
        cartPanel.add(new JScrollPane(cartList));
        cartPanel.add(removeFromCartButton);

        JPanel checkoutPanel = new JPanel();
        checkoutPanel.add(checkoutButton);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(productPanel);
        add(cartPanel);
        add(checkoutPanel);

        // Add action listeners
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add selected product to cart with specified quantity
                try {
                    Product selectedProduct = (Product) productComboBox.getSelectedItem();
                    int quantity = Integer.parseInt(quantityField.getText());
                    if (quantity > 0) {
                        addToCart(selectedProduct, quantity);
                        updateCartList();
                        quantityField.setText(""); // Clear quantity field after adding
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid quantity. Please enter a positive number.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid quantity. Please enter a number.");
                }
            }
        });

        removeFromCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove selected item from cart
                int selectedIndex = cartList.getSelectedIndex();
                if (selectedIndex != -1) {
                    cart.remove(selectedIndex);
                    updateCartList();
                }
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle checkout process (e.g., display total price)
                if (!cart.isEmpty()) {
                    double totalPrice = calculateTotalPrice();
                    JOptionPane.showMessageDialog(null, "Thank You for Shopping ! \n Total Price:  ₹ " + totalPrice);
                } else {
                    JOptionPane.showMessageDialog(null, "Cart is empty!");
                }
            }
        });

        pack();
        setVisible(true);
    }

    private Product[] createSampleProducts() {
        // Replace with your actual product data
        return new Product[]{
                new Product("Shirt", 150.0),
                new Product("Book", 35.0),
                new Product("Phone", 7000.0),
                new Product("MBA degree", 100000.0)
        };
    }

    private void addToCart(Product product, int quantity) {
        cart.add(new Product(product.getName(), product.getPrice(), quantity));
    }

    private void updateCartList() {
        cartListModel.clear();
        for (Product product : cart) {
            cartListModel.addElement(product.toString());
        }
    }

    private double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Product product : cart) {
            totalPrice += product.getPrice() * product.getQuantity();
        }
        return totalPrice;
    }

    public static void main(String[] args) {
        new ShoppingCartGUI();
    }
}

class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price) {
        this(name, price, 1); // Default quantity of 1
    }

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return name + " (x" + quantity + ") - ₹ " + price * quantity;
    }
}