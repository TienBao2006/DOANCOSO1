package trangchu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.lang.ModuleLayer.Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class Quanlysanpham extends JFrame implements ActionListener {
    JButton qlsanpham, qlkhohang, qlbanhang, qlnhanvien;
    JPanel panel;
    JButton them, xoa, sua , timkiem , themhinhanh,qr;
    JTextField tensp, soluong, giatien,masp,mota,mavach,nhacungcap,tim;
    JComboBox<String> loai,ngay,thang,nam;
    DefaultTableModel tableModel;
    JTable table;
    Connection connection;
    JLabel hinhanh;
    public Quanlysanpham() {
        this.setSize(1000, 750);
        this.setTitle("Quản lý cửa hàng tạp hóa");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        connectDatabase();
        panel = new JPanel(new BorderLayout()); 
        trang(); 
        this.add(panel, BorderLayout.CENTER); 
        this.setVisible(true);
    }
    
private void connectDatabase() {
    try {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlycuahang", "root", "2904");
        System.out.println("Kết nối thành công!");
    } catch (SQLException e) {
        e.printStackTrace(); // In chi tiết lỗi ra console
        JOptionPane.showMessageDialog(this, "Kết nối cơ sở dữ liệu không thành công: " + e.getMessage());
    }
}


public void trang() {
	JPanel panel =new JPanel(new BorderLayout());
	panel.setBackground(new Color(WIDTH));
	
	JPanel pan=new JPanel();
	pan.setPreferredSize(new Dimension(100,100));
	JLabel lab=new JLabel("Quan ly cua hang tap hoa");
	pan.add(lab,BorderLayout.NORTH);
	
    JPanel pane1 = new JPanel();
    pane1.setLayout(new GridLayout(10, 1, 10, 10));

    qlsanpham = new JButton("Quản lý sản phẩm");
    qlbanhang = new JButton("Quản lý bán hàng");
    qlkhohang = new JButton("Quản lý kho hàng");
    qlnhanvien = new JButton("Quản lý nhân viên");
    
    
    pane1.add(qlsanpham);
    pane1.add(qlkhohang);

    
    panel.add(pane1,BorderLayout.CENTER);
    panel.add(pan,BorderLayout.NORTH);
    this.add(panel, BorderLayout.WEST);

    
    qlsanpham.addActionListener(e -> switchPanel(thongtin()));
    qlkhohang.addActionListener(e -> switchPanel(khohang()));

}

private void switchPanel(JPanel newPanel) {
    panel.removeAll(); // Xóa nội dung cũ
    panel.add(newPanel, BorderLayout.CENTER); // Thêm nội dung mới
    panel.revalidate(); // Cập nhật giao diện
    panel.repaint();
}


private JPanel thongtin() {
    JPanel panel = new JPanel(null);
    
    JLabel lab = new JLabel("Nhập thông tin sản phẩm");
    lab.setBounds(10, 10, 200, 30);
    panel.add(lab);

    String[] thanhphan = {"Mã sản phẩm", "Tên sản phẩm", "Loại", "Số lượng", "Giá Bán", "Nhà cung cấp", "Mã vạch", "Mô tả", "Hình ảnh"};
    tableModel = new DefaultTableModel(thanhphan, 0);
    table = new JTable(tableModel);
    table.addMouseListener(new java.awt.event.MouseAdapter() {
     
    	@Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int selectedRow = table.getSelectedRow(); // Lấy hàng được chọn
            if (selectedRow != -1) {
                masp.setText(tableModel.getValueAt(selectedRow, 0).toString());
                tensp.setText(tableModel.getValueAt(selectedRow, 1).toString());
                loai.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
                soluong.setText(tableModel.getValueAt(selectedRow, 3).toString());
                giatien.setText(tableModel.getValueAt(selectedRow, 4).toString());
                nhacungcap.setText(tableModel.getValueAt(selectedRow, 5).toString());
                mavach.setText(tableModel.getValueAt(selectedRow, 6).toString());
                mota.setText(tableModel.getValueAt(selectedRow, 7).toString());

                String imagePath = tableModel.getValueAt(selectedRow, 8) != null ? tableModel.getValueAt(selectedRow, 8).toString() : "";
                if (!imagePath.isEmpty()) {
                    ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                    hinhanh.setIcon(icon);
                    hinhanh.setToolTipText(imagePath); 
                } else {
                    hinhanh.setIcon(null); 
                }
            }
        }
    });

    
    JScrollPane tablesco = new JScrollPane(table);
    tablesco.setBounds(10, 50, 830, 200);
    panel.add(tablesco);
    
    JLabel lblMaSP = new JLabel("Mã sản phẩm:");
    lblMaSP.setBounds(10, 270, 100, 30);
    masp = new JTextField();
    masp.setBounds(120, 270, 200, 30);
    
    JLabel lblTenSP = new JLabel("Tên sản phẩm:");
    lblTenSP.setBounds(10, 310, 100, 30);
    tensp = new JTextField();
    tensp.setBounds(120, 310, 200, 30);
    
    JLabel lblloai = new JLabel("Loại:");
    lblloai.setBounds(10, 350, 100, 30);
    loai =new JComboBox<String>(new String[] {"1. Thực phẩm & Đồ uống","2. Đồ gia dụng & Hóa phẩm","3. Mỹ phẩm & Chăm sóc cá nhân","4. Đồ ăn vặt & Bánh kẹo","5. Sản phẩm cho trẻ em"});
    loai.setBounds(120, 350, 200, 30);
    
    JLabel lblSoLuong = new JLabel("Số lượng:");
    lblSoLuong.setBounds(10, 390, 100, 30);
    soluong = new JTextField();
    soluong.setBounds(120, 390, 200, 30);
    
    JLabel lblGiaTien = new JLabel("Giá tiền:");
    lblGiaTien.setBounds(10, 430, 100, 30);
    giatien = new JTextField();
    giatien.setBounds(120, 430, 200, 30);
    
    JLabel lblnhacungcap = new JLabel("Nhà cung cấp:");
    lblnhacungcap.setBounds(10, 470, 100, 30);
    nhacungcap = new JTextField();
    nhacungcap.setBounds(120, 470, 200, 30);
    
    JLabel lblmavach = new JLabel("Mã vạch:");
    lblmavach.setBounds(10, 510, 100, 30);
    mavach = new JTextField();
    mavach.setBounds(120, 510, 200, 30);
    
    JLabel lblMoTa = new JLabel("Mô tả:");
    lblMoTa.setBounds(10, 550, 100, 30);
    mota = new JTextField();
    mota.setBounds(120, 550, 200, 30);
    
    hinhanh=new JLabel();
    hinhanh.setBounds(500,300,150,150);
    hinhanh.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
    them = new JButton("Thêm");
    them.setBounds(10, 600, 80, 30);
    xoa = new JButton("Xóa");
    xoa.setBounds(100, 600, 80, 30);
    sua = new JButton("Sửa");
    sua.setBounds(190, 600, 80, 30);
    qr=new JButton("QRcode");
    qr.setBounds(270,600,80,30);
    themhinhanh=new JButton("Thêm Hình Ảnh");
    themhinhanh.setBounds(500,600,150,30);
    
    panel.add(lblMaSP); panel.add(masp);
    panel.add(lblTenSP); panel.add(tensp);
    panel.add(lblloai); panel.add(loai);
    panel.add(lblSoLuong); panel.add(soluong);
    panel.add(lblGiaTien); panel.add(giatien);
    panel.add(lblnhacungcap); panel.add(nhacungcap);
    panel.add(lblmavach); panel.add(mavach);
    panel.add(lblMoTa); panel.add(mota);
    panel.add(them); panel.add(xoa); panel.add(sua);panel.add(themhinhanh);panel.add(qr);
    panel.add(hinhanh);
    
    them.addActionListener(this);
    xoa.addActionListener(this);
    sua.addActionListener(this);
    themhinhanh.addActionListener(this);
    qr.addActionListener(this);
    loadTable();
    return panel;
}
private void chonHinhAnh() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Chọn Hình Ảnh");
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));

    int returnValue = fileChooser.showOpenDialog(this);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
        String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();

        // Hiển thị ảnh trên giao diện
        ImageIcon icon = new ImageIcon(new ImageIcon(selectedFilePath).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        hinhanh.setIcon(icon);
        hinhanh.setToolTipText(selectedFilePath); // Lưu đường dẫn

        // Lưu đường dẫn ảnh vào CSDL
        luuHinhAnhVaoDatabase(selectedFilePath);
    }
}

private void luuHinhAnhVaoDatabase(String filePath) {
    String maSP = masp.getText(); 
    if (maSP.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sản phẩm trước khi thêm ảnh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        String query = "UPDATE qlysanpham SET hinhanh = ? WHERE maSP = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, filePath);
        stmt.setString(2, maSP);

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật hình ảnh thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm với mã: " + maSP, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi lưu hình ảnh: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}


private void generateQRCode() {
	String data = "Mã sản phẩm: " + masp.getText() + "\nTên sản phẩm: " + tensp.getText() +
		    "\nLoại: " + loai.getSelectedItem() + "\nSố lượng: " + soluong.getText() + 
		    "\nGiá tiền: " + giatien.getText() + " VNĐ"+ "\nNhà cung cấp: " + nhacungcap.getText() + 
		    "\nMã vạch: " + mavach.getText() + "\nMô tả: " + mota.getText() + "\n"  ;
	

    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    try {
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(qrImage)), "QR Code", JOptionPane.PLAIN_MESSAGE);
    } catch (WriterException e) {
        e.printStackTrace();
    }
}
private JPanel khohang() { 
	JPanel pan=new JPanel(null);     
     JLabel lab = new JLabel("Nhập thông tin sản phẩm");
     lab.setBounds(10, 10, 200, 30);
     pan.add(lab);
     
     String[] thanhphan = {"Mã sản phẩm", "Tên sản phẩm", "Loại", "Số lượng", "Giá Bán", "Nhà cung cấp", "Mã vạch", "Mô tả"};
     tableModel = new DefaultTableModel(thanhphan, 0);
     table = new JTable(tableModel);

     JScrollPane tablesco = new JScrollPane(table);
     tablesco.setBounds(10, 100, 830, 400);
     pan.add(tablesco);
     
     ImageIcon icon =new ImageIcon("image-removebg-preview (2).png");
     Image ima=icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
     tim=new JTextField();
     tim.setBounds(570,30,200,30);
     
     timkiem=new JButton(new ImageIcon(ima));
     timkiem.setBounds(780,30,30,30);
     timkiem.addActionListener(this);
  
     pan.add(timkiem);
     pan.add(tim);
     loadTable();
	return pan;
}

private void themSanPham() {
    String maSP = masp.getText();
    String tenSP = tensp.getText();
    String loaiSP = (String) loai.getSelectedItem();
    String soLuong = soluong.getText();
    String giaTien = giatien.getText();
    String nhaCungCap = nhacungcap.getText();
    String maVach = mavach.getText();
    String moTa = mota.getText();


    if (maSP.isEmpty() || tenSP.isEmpty() || soLuong.isEmpty() || giaTien.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    int soLuongInt = 0;
    double giaTienDouble = 0.000;
    try {
        soLuongInt = Integer.parseInt(soLuong);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Số lượng phải là một số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        giaTienDouble = Double.parseDouble(giaTien);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Giá tiền phải là một số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Lấy đường dẫn hình ảnh nếu có
    String hinhAnh = (hinhanh.getIcon() != null) ? hinhanh.getToolTipText() : null;

    try {
        String query = "INSERT INTO qlysanpham (maSP, tenSP, loaiSP, soLuong, giaTien, nhaCungCap, maVach, moTa, hinhanh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, maSP);
        stmt.setString(2, tenSP);
        stmt.setString(3, loaiSP);
        stmt.setInt(4, soLuongInt);
        stmt.setDouble(5, giaTienDouble);
        stmt.setString(6, nhaCungCap);
        stmt.setString(7, maVach);
        stmt.setString(8, moTa);
        stmt.setString(9, hinhAnh);  
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

        
        tableModel.addRow(new Object[]{maSP, tenSP, loaiSP, soLuongInt, giaTienDouble, nhaCungCap, maVach, moTa, hinhAnh});
        masp.setText(""); 
        tensp.setText(""); 
        soluong.setText(""); 
        giatien.setText(""); 
        nhacungcap.setText("");
        mavach.setText(""); 
        mota.setText(""); 
        hinhanh.setIcon(null); 
        hinhanh.setToolTipText(null);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi thêm vào database: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    loadTable();
}



private void xoaSanPham() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String maSP = tableModel.getValueAt(selectedRow, 0).toString();

    int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        try {
            String query = "DELETE FROM qlysanpham WHERE maSP = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, maSP);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}


private void suaSanPham() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String maSPCu = tableModel.getValueAt(selectedRow, 0).toString();
    String maSP = masp.getText(); 
    String tenSP = tensp.getText();
    String loaiSP = (String) loai.getSelectedItem();
    int soLuong = Integer.parseInt(soluong.getText());
    double giaTien = Double.parseDouble(giatien.getText());
    String nhaCungCap = nhacungcap.getText();
    String maVach = mavach.getText();
    String moTa = mota.getText();
    String hinhAnh = (hinhanh.getToolTipText() != null) ? hinhanh.getToolTipText() : "";

    try {
        String query = "UPDATE qlysanpham SET maSP=?, tenSP=?, loaiSP=?, soLuong=?, giaTien=?, nhaCungCap=?, maVach=?, mota=?, hinhAnh=? WHERE maSP=?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, maSP);
        stmt.setString(2, tenSP);
        stmt.setString(3, loaiSP);
        stmt.setInt(4, soLuong);
        stmt.setDouble(5, giaTien);
        stmt.setString(6, nhaCungCap);
        stmt.setString(7, maVach);
        stmt.setString(8, moTa);
        stmt.setString(9, hinhAnh);
        stmt.setString(10, maSPCu); 

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!");
            loadTable(); 
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi cập nhật sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}



private void loadTable() {
    try {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        String query = "SELECT * FROM qlysanpham";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            tableModel.addRow(new Object[]{
                rs.getString("maSP"),
                rs.getString("tenSP"),
                rs.getString("loaiSP"),
                rs.getInt("soLuong"),
                rs.getDouble("giaTien"),
                rs.getString("nhaCungCap"),
                rs.getString("maVach"),
                rs.getString("moTa"),
                rs.getString("hinhanh")
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}


private void Timkiem() {
	String keyword =tim.getText().trim();
	if(keyword.isEmpty()) {
		loadTable();
	}else {
		try {
			tableModel.setRowCount(0);
			  String query = "SELECT * FROM qlysanpham WHERE maSP LIKE ? OR tenSP LIKE ? OR loaiSP LIKE ? OR soLuong LIKE ? OR giaTien LIKE ? OR nhaCungCap LIKE ? OR maVach LIKE ? OR moTa LIKE ?";    
			  PreparedStatement preparedStatement = connection.prepareStatement(query);
      
				preparedStatement.setString(1, "%" + keyword + "%");
                preparedStatement.setString(2, "%" + keyword + "%");
                preparedStatement.setString(3, "%" + keyword + "%");
                preparedStatement.setString(4, "%" + keyword + "%");
                preparedStatement.setString(5, "%" + keyword + "%");
                preparedStatement.setString(6, "%" + keyword + "%");
                preparedStatement.setString(7, "%" + keyword + "%");
                preparedStatement.setString(8, "%" + keyword + "%");
        ResultSet rs = preparedStatement.executeQuery();
		 
        while (rs.next()) {
        	tableModel.addRow(new Object[] {
        			rs.getString("maSP"),
                    rs.getString("tenSP"),
                    rs.getString("loaiSP"),
                    rs.getInt("soLuong"),
                    rs.getDouble("giaTien"),
                    rs.getString("nhaCungCap"),
                    rs.getString("maVach"),
                    rs.getString("moTa")
        	});
        	if(tableModel.getRowCount()==0) {
        		JOptionPane.showMessageDialog(this,"Không tìm thấy kết quả !"+keyword,"Tìm kiếm",JOptionPane.INFORMATION_MESSAGE );
        		
        	}
        }
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin "+ e.getMessage());;
		}
	}
}


@Override
public  void actionPerformed(ActionEvent e) {
    if (e.getSource() == them) {
        themSanPham();
    } else if (e.getSource() == xoa) {
        xoaSanPham();
    } else if (e.getSource() == sua) {
        suaSanPham();
    } else if(e.getSource() == timkiem) {
    	Timkiem();
    } else if(e.getSource() == themhinhanh) {
    	chonHinhAnh();

    }else if(e.getSource()==qr) {
    	generateQRCode();
    }
}


public static void main(String[] args) {
    new Quanlysanpham();
}
}