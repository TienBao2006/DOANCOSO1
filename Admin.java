package trangchu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Admin extends JFrame implements ActionListener {
    JButton qlsanpham, qlkhohang, qlbanhang, qlnhanvien;
    DefaultTableModel tableModel,tableModel1;
    JTable table,table1;
    Connection connection;
    JButton them, xoa, sua , timkiem , themhinhanh;
    JTextField tim,tex,tex1,tex2,tex3,tex4;
    JPanel panel;
    JComboBox<String> days,years,months,nguoidung ;
	public Admin() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1200,700);
		this.setLocationRelativeTo(null);
		connectDatabase();
	 panel =new JPanel(new BorderLayout());
		trangchu();
		this.add(panel,BorderLayout.CENTER);
		this.setVisible(true);
	}
	public void trangchu() {
		JPanel panel =new JPanel(new BorderLayout());
		panel.setBackground(new Color(WIDTH));
		
		JPanel pan=new JPanel();
		pan.setPreferredSize(new Dimension(100,100));
//		JLabel lab=new JLabel("Quan ly cua hang tap hoa");
//		pan.add(lab,BorderLayout.NORTH);
		
	    JPanel pane1 = new JPanel();
	    pane1.setLayout(new GridLayout(5, 1, 5, 5));

	    qlbanhang = new JButton("Quản lý bán hàng");
	    qlkhohang = new JButton("Quản lý kho hàng");
	    qlnhanvien = new JButton("Quản lý nhân viên");
	    qlbanhang.setContentAreaFilled(false);
	    qlnhanvien.setContentAreaFilled(false);
	    qlkhohang.setContentAreaFilled(false);
	    pane1.add(qlbanhang);
	    pane1.add(qlkhohang);
	    pane1.add(qlnhanvien);
	    qlbanhang.addActionListener(this);
	    qlkhohang.addActionListener(this);
	    qlnhanvien.addActionListener(this);
	    
	    panel.add(pane1,BorderLayout.CENTER);
	    panel.add(pan,BorderLayout.NORTH);
	    this.add(panel, BorderLayout.WEST);

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
	private JPanel qlynhanvien() {
		JPanel pan =new JPanel();
		pan.setLayout(null);
		JLabel lab=new JLabel("Thông tin nhân viên ");
		lab.setFont(new Font("A",Font.BOLD,20));
		lab.setBounds(10,10,300,40);
		JLabel lab1=new JLabel("Họ và tên");
		tex=new JTextField();
		tex.setBounds(150,100,175,30);
		lab1.setBounds(30,100,80,30);
		JLabel lab2=new JLabel("Ngày sinh");
		lab2.setBounds(30,150,80,30);
		String[] day =ngay(31);
		days =new JComboBox(day);
		days.setBounds(150, 150, 50, 30);
	    String[] month = ngay(12);
	    months = new JComboBox(month);
	    months.setBounds(205, 150, 50, 30);
	    String[] year = nam(2025);
	    years = new JComboBox(year);
	    years.setBounds(260,150, 60, 30);
	    JLabel lab3 =new JLabel("Que quan");
	     tex1=new JTextField();
		tex1.setBounds(150,200,175,30);
	    lab3.setBounds(30,200,80,30);
	    JLabel lab4 =new JLabel("So dien thoai");
	    lab4.setBounds(30,250,100,30);
	     tex2=new JTextField();
		tex2.setBounds(150,250,175,30);
	    JLabel lab5 = new JLabel("Tai khoan");
	    lab5.setBounds(30,300,80,30);
	     tex3=new JTextField();
		tex3.setBounds(150,300,175,30);
	    JLabel lab6 = new JLabel("Mat khau");
	    lab6.setBounds(30,350,80,30);
	     tex4=new JTextField();
		tex4.setBounds(150,350,175,30);
		JLabel lab7 =new JLabel("Người dùng");
		lab7.setBounds(30,400,100,30);
		nguoidung =new JComboBox<String>(new String[] { "Nhân viên bán hàng","Nhân viên quản lý kho"});
		nguoidung.setBounds(150,400,175,30);
		them =new JButton("Them");
		them.setBounds(30,450,80,30);
		xoa =new JButton("Xoa");
		xoa.setBounds(130,450,80,30);
		sua =new JButton("Sua");
		sua.setBounds(230,450,80,30);
		
		tableModel1 =new DefaultTableModel(new String[] {
			"Họ và tên","Ngày sinh","Địa chỉ","Số điện thoại","Tài khoản","Mật khẩu","Người dùng"
		},0){
    	    @Override
    	    public boolean isCellEditable(int row, int column) {
    	        return false;  
    	    }
    	};
		
		table1=new JTable(tableModel1);
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table1.getSelectionModel().addListSelectionListener(e->{
			int selectedRow=table1.getSelectedRow();
			
			if(selectedRow !=-1) {
				tex.setText(tableModel1.getValueAt(selectedRow, 0).toString());
				tex1.setText(tableModel1.getValueAt(selectedRow, 2).toString());
				tex2.setText(tableModel1.getValueAt(selectedRow, 3).toString());
				tex3.setText(tableModel1.getValueAt(selectedRow, 4).toString());
				tex4.setText(tableModel1.getValueAt(selectedRow, 5).toString());
				String ngaysinh=tableModel1.getValueAt(selectedRow, 1).toString();
				String[] data=ngaysinh.split("/");
				if(data.length==3){
					String ngay =data[0];
					String thang =data[1];
					String nam=data[2];
					days.setSelectedItem(ngay);
					months.setSelectedItem(thang);
					years.setSelectedItem(nam);
				}
				String nguoidung1=tableModel1.getValueAt(selectedRow, 6).toString();
				nguoidung.setSelectedItem(nguoidung1);
				}
		});
		table1.getColumnModel().getColumn(0).setPreferredWidth(150); 
    	table1.getColumnModel().getColumn(1).setPreferredWidth(100);  
    	table1.getColumnModel().getColumn(2).setPreferredWidth(150);  
    	table1.getColumnModel().getColumn(3).setPreferredWidth(100); 
    	table1.getColumnModel().getColumn(4).setPreferredWidth(100);  
    	table1.getColumnModel().getColumn(5).setPreferredWidth(80);
    	table1.getColumnModel().getColumn(6).setPreferredWidth(180);
		JScrollPane scro=new JScrollPane(table1);
		scro.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scro.setSize(800,500);
		scro.setBounds(400,95,600,500);
		pan.add(scro);
		lab1.setFont(new Font("A",Font.BOLD,14));
		lab2.setFont(new Font("A",Font.BOLD,14));
		lab3.setFont(new Font("A",Font.BOLD,14));
		lab4.setFont(new Font("A",Font.BOLD,14));
		lab5.setFont(new Font("A",Font.BOLD,14));
		lab6.setFont(new Font("A",Font.BOLD,14));
		lab7.setFont(new Font("A",Font.BOLD,14));
	    pan.add(them);
	    pan.add(xoa);
	    pan.add(sua);
	    them.setContentAreaFilled(false);
	    xoa.setContentAreaFilled(false);
	    sua.setContentAreaFilled(false);
		them.addActionListener(this);
	    xoa.addActionListener(this);
	    sua.addActionListener(this);
	    pan.add(tex);
	    pan.add(tex1);
	    pan.add(tex2);
	    pan.add(tex3);
	    pan.add(tex4);
	    pan.add(lab);
	    pan.add(lab2);
	    pan.add(lab1);
	    pan.add(lab3);
	    pan.add(lab4);
	    pan.add(lab5);
	    pan.add(lab6);
	    pan.add(lab7);
	    pan.add(days);
	    pan.add(months);
	    pan.add(years);
	    pan.add(nguoidung);
	    loadTable1();
	
		return pan;
	}
	public String[] ngay(int a) {
		String[] day =new String[a];
		for(int  i= 1;i<=a;i++) {
			day[i-1] =String.valueOf(i);
		}
		return day;
	}	
	public String[] nam(int a) {
		String[] day =new String[a];
		for(int  i= 1;i<=a;i++) {
			day[a-i] =String.valueOf(i);
		}
		return day;
	}
	private void loadTable() {
	    try {
	        String query = "SELECT * FROM qlysanpham";
	        PreparedStatement stmt = connection.prepareStatement(query);
	        ResultSet rs = stmt.executeQuery();

	        tableModel.setRowCount(0); 
	        while (rs.next()) {     
	            String maSP = rs.getString("maSP");
	            String tenSP = rs.getString("tenSP");
	            String loaiSP = rs.getString("loaiSP");
	            int soLuong = rs.getInt("soLuong");
	            double giaTien = rs.getDouble("giaTien");
	            String nhaCungCap = rs.getString("nhaCungCap");
	            String maVach = rs.getString("maVach");
	            String moTa = rs.getString("moTa");
	            String hinhAnh = rs.getString("hinhanh");

	            tableModel.addRow(new Object[]{maSP, tenSP, loaiSP, soLuong, giaTien, nhaCungCap, maVach, moTa, hinhAnh});  
//	            if (hinhAnh != null && !hinhAnh.isEmpty()) {
//	                ImageIcon icon = new ImageIcon(new ImageIcon(hinhAnh).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
//	                hinhanh.setIcon(icon);
//	                hinhAnh = "Đã thêm ảnh";
//	            } else {
//	                  hinhAnh = "";
//	        }}
	    }} catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
	    }
	}
	private void loadTable1() {
	    try {
	        String query = "SELECT * FROM dangki";
	        PreparedStatement stmt = connection.prepareStatement(query);
	        ResultSet rs = stmt.executeQuery();

	        tableModel1.setRowCount(0); 
	        while (rs.next()) {     
	            String hoten = rs.getString("hovaten");
	            String ngaysinh = rs.getString("ngaysinh");
	            String diachi = rs.getString("diachi");
	            String sdt = rs.getString("sdt");
	            String taikhoan = rs.getString("taikhoan");
	            String matkhau = rs.getString("matkhau");
	            String nguoidung=rs.getString("nguoidung");
	            
	            tableModel1.addRow(new Object[]{hoten, ngaysinh, diachi, sdt, taikhoan, matkhau,nguoidung});  
	    }} catch (SQLException e) {
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
	private void themtk() {
		if(tex.getText().trim().isEmpty() || 
			tex1.getText().trim().isEmpty() ||
			tex2.getText().trim().isEmpty() ||
			tex3.getText().trim().isEmpty() ||
			tex4.getText().trim().isEmpty() ) {
	JOptionPane.showMessageDialog(this,"Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE );
		return;
		}
		    String day = (String) days.getSelectedItem();
	        String month = (String) months.getSelectedItem();
	        String year = (String) years.getSelectedItem();
	        String nguoidung2=(String) nguoidung.getSelectedItem();
	        if (day.length() == 1) {
	            day = "0" + day;
	        }
	        if (month.length() == 1) {
	            month = "0" + month;
	        }
	        String ngaysinh = year+ "-" + month + "-" +  day;
	        String query = "INSERT INTO dangki (hovaten,ngaysinh,diachi, sdt, taikhoan, matkhau,nguoidung) VALUES (?,?,?,?, ?, ?, ?)";
	        try {
	        	if(connection == null || connection.isClosed()) {
	        		JOptionPane.showMessageDialog(this, "Loi ket noi","Error", JOptionPane.ERROR_MESSAGE);
	        	return;
	        	}
	        	PreparedStatement pre=connection.prepareStatement(query);
	        	pre.setString(1,tex.getText());
	        	pre.setString(2,ngaysinh);
	        	pre.setString(3,tex1.getText());
	        	pre.setString(4,tex2.getText());
	        	pre.setString(5,tex3.getText());
	        	pre.setString(6,tex4.getText());
	        	pre.setString(7,nguoidung2);
	        	int result =pre.executeUpdate();
	        	if(result >0) {
	        		JOptionPane.showMessageDialog(this, "Them thanh cong");
	        		tex.setText("");
	        		tex1.setText("");
	        		tex2.setText("");
	        		tex3.setText("");
	        		tex4.setText("");
	        		days.setSelectedIndex(0);
	                months.setSelectedIndex(0);
	                years.setSelectedIndex(0);
	                nguoidung.setSelectedIndex(result);
	                loadTable1();
	}else {
		JOptionPane.showMessageDialog(this,"Khong luu duoc du lieu");
	}
	}catch(SQLException e){
		JOptionPane.showMessageDialog(this, "Khong luu duoc du lieu");
		e.printStackTrace();
	}
	        
	}
	private void Xoatk() {
		int select =table1.getSelectedRow();
		if(select ==-1) {
			JOptionPane.showMessageDialog(this, "VUi long chon thong tin can xoa");
			return;
		}
		String hovaten=tableModel1.getValueAt(select, 0).toString();
		
		try {
			String query = "DELETE FROM dangki WHERE hovaten = ?";
			PreparedStatement stmt =connection.prepareStatement(query);
			stmt.setString(1, hovaten);

			int xoa =stmt.executeUpdate();
			if(xoa>0) {
				tableModel1.removeRow(select);
				JOptionPane.showMessageDialog(this, "Xoa thanh cong");
			}else {
				JOptionPane.showMessageDialog(this, "Khong xoa duoc");
			}
        	int result =stmt.executeUpdate();
        	
        		tex.setText("");
        		tex1.setText("");
        		tex2.setText("");
        		tex3.setText("");
        		tex4.setText("");
        		days.setSelectedIndex(0);
                months.setSelectedIndex(0);
                years.setSelectedIndex(0);
                nguoidung.setSelectedIndex(result);
                loadTable1();

		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Loi ket noi");
		}
	}
	
	private void connectDatabase() {
	    try {
	        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlycuahang", "root", "2904");
	        System.out.println("Kết nối thành công!");
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	        JOptionPane.showMessageDialog(this, "Kết nối cơ sở dữ liệu không thành công: " + e.getMessage());
	    }
	}
	private void remove(JPanel newPanel) {
		panel.removeAll();
		panel.add(newPanel);
		panel.revalidate();
		panel.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == timkiem) {
			Timkiem();
		}else if(e.getSource() == qlkhohang) {
			remove(khohang());
		}else if(e.getSource() == qlnhanvien) {
			remove(qlynhanvien());
		}else if(e.getSource() == them) {
			themtk();
		}else if(e.getSource() == xoa) {
			Xoatk();
		}
	}
	
	public static void main(String[] args) {
		new Admin();
	}

}
