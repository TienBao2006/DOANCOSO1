package trangchu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/quanlycuahang";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "2904";

    private JPanel gridPanel;
    private List<Product> cart = new ArrayList<>();

    // --- Lớp Product ---
    static class Product {
        private String tenSP;
        private Double giaTien;
        private String hinhanh;
        private Integer soLuong;
        public Product(String tenSP, double giaTien,String hinhanh,int soLuong) {
            this.tenSP = tenSP;
            this.giaTien = giaTien;
            this.hinhanh=hinhanh;
            this.soLuong=soLuong;
        }

        public String getTenSP() { return tenSP; }
        public double getGiaTien() { return giaTien; }
        public String getHinhAnh() {return hinhanh;}
        public int getSoLuong() { return soLuong; }

    }

    public App() {
        setTitle("Quản lý sản phẩm từ MySQL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("🔄 Làm mới");
        JButton viewCartButton = new JButton("🛒 Xem giỏ hàng");

        topPanel.add(viewCartButton);
        topPanel.add(refreshButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // --- Panel sản phẩm (GridLayout) ---
        gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        // Load sản phẩm lần đầu
        loadProducts();

        // Sự kiện làm mới
        refreshButton.addActionListener(e -> loadProducts());

        // Xem giỏ hàng
        viewCartButton.addActionListener(e -> showCartDialog());
    }

    // --- Load sản phẩm từ MySQL ---
    private void loadProducts() {
        gridPanel.removeAll();

        List<Product> products = getAllProductsFromDatabase();
        for (Product p : products) {
            gridPanel.add(createProductPanel(p));
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private List<Product> getAllProductsFromDatabase() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT tenSP, giaTien,hinhanh,soluong FROM qlysanpham";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String ten = rs.getString("tenSP");
                int gia = rs.getInt("giaTien");
                String hinh=rs.getString("hinhanh");
                int sl=rs.getInt("soLuong");
                productList.add(new Product(ten, gia,hinh,sl));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi CSDL: " + e.getMessage());
        }

        return productList;
    }

    // --- Tạo panel sản phẩm ---
    private JPanel createProductPanel(Product p) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 220));
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(245, 255, 240));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // --- Ảnh sản phẩm ---
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            ImageIcon icon = new ImageIcon(p.getHinhAnh()); // từ file local
            Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            imageLabel.setText("Không có ảnh");
        }

        // --- Tên và giá ---
        JLabel nameLabel = new JLabel(p.getTenSP(), SwingConstants.CENTER);
 
        JLabel priceLabel = new JLabel("Giá: " + p.getGiaTien() + "00 đ", SwingConstants.CENTER);
        JLabel soluong=new JLabel("Còn lại : " + p.getSoLuong());
        JButton addButton = new JButton("Thêm vào giỏ");
        
        addButton.addActionListener(e -> {
            if (p.getSoLuong() > 0) {
                cart.add(new Product(p.getTenSP(), p.getGiaTien(), p.getHinhAnh(), 1)); 
                int newQty = p.getSoLuong() - 1;
                p.soLuong = newQty; // cập nhật số lượng local
                soluong.setText("Còn lại : " + newQty);

                buton(p.getTenSP(), newQty);

                JOptionPane.showMessageDialog(this, "Đã thêm vào giỏ: " + p.getTenSP());
            } else {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã hết hàng!");
            }
        });


        JPanel Panel = new JPanel(new GridLayout(4, 1));
        Panel.add(nameLabel);
        Panel.add(priceLabel);
        Panel.add(addButton);
        Panel.add(soluong);

        panel.add(imageLabel, BorderLayout.NORTH);
        panel.add(Panel, BorderLayout.CENTER);

        return panel;
    }
    private void buton(String tenSP,int newQuantity) {
    	String query ="UPDATE qlysanpham SET soLuong =?  WHERE tenSP = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, newQuantity);
            pstmt.setString(2, tenSP);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật số lượng: " + e.getMessage());
        }
    }
  
    // --- Hiển thị giỏ hàng ---
    private void showCartDialog() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng đang trống!");
            return;
        }

        StringBuilder cartInfo = new StringBuilder();
        int total = 0;

        for (Product p : cart) {
            cartInfo.append(p.getTenSP()).append(" - ").append(p.getGiaTien()).append("đ\n");
            total += p.getGiaTien();
        }
        
        cartInfo.append("\nTổng cộng: ").append(total).append(".000 đ");

        JOptionPane.showMessageDialog(this, cartInfo.toString(), "🛒 Giỏ hàng", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy JDBC Driver MySQL!");
            return;
        }

        SwingUtilities.invokeLater(App::new);
    }
}
