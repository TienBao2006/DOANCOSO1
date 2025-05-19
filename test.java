package trangchu;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import trangchu1.DoanhThuDAO;

public class test extends JFrame implements ActionListener {
	private JPanel cardPanel;
	private CardLayout cardLayout;
    DefaultTableModel tableModel,tableModel1,tableModel2;
    JTable table,table1,table2;
    Connection connection;
    JButton them, xoa, sua , timkiem , themhinhanh,qr;
    JTextField tim,tex,tex1,tex2,tex3,tex4;
    JPanel panel;
    JComboBox<String> days,years,months,nguoidung ;
    JButton qlsanpham, qlkhohang, qlbanhang, qlnhanvien;
//    JButton them, xoa, sua , timkiem , themhinhanh,qr;
    JTextField tensp, soluong, giaban,giagoc,masp,mota,mavach,nhacungcap;
    JComboBox<String> loai,ngay,thang,nam;

    JLabel hinhanh;
	public test() {
		setTitle("Quan ly ban hang");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(false);
		setLayout(new BorderLayout());
		connectDatabase(); 
		cardLayout =new CardLayout();
		cardPanel =new JPanel(cardLayout);
		
		//noi dung 
		JPanel tab1 = doanhthu();
		JPanel tab2 = khohang();
		JPanel tab3 = sanpham();
		JPanel tab4 = qlynhanvien();

		cardPanel.add(tab1,"Doanh thu");
		cardPanel.add(tab2,"Kho hàng");
		cardPanel.add(tab3,"Sản phẩm");
		cardPanel.add(tab4,"Doanh thu3");
		
		JPanel menupanel =new JPanel();
		menupanel.setLayout(new BoxLayout(menupanel, BoxLayout.Y_AXIS));
		menupanel.setBackground(Color.white);
		menupanel.setPreferredSize(new Dimension(190,getHeight()));
		menupanel.setBorder(BorderFactory.createLineBorder(new Color(200, 230, 250),1,true));
		JButton btnTab1= styleButton("Doanh thu", "doanh_thu-removebg-preview.png");
		JButton btnTab2= styleButton("Kho hàng" , "khohang-removebg-preview.png");
		JButton btnTab3= styleButton("Sản phẩm" , "san_pham-removebg-preview.png");
		JButton btnTab4= styleButton("Nhân viên", "image-removebg-preview.png");
		
		
		menuButtons = new JButton[] {btnTab1, btnTab2, btnTab3, btnTab4};
		
		btnTab1.addActionListener(e -> {
		    cardLayout.show(cardPanel, "Doanh thu");
		    highlightButtonBackground(btnTab1);
		});
		btnTab2.addActionListener(e -> {
		    cardLayout.show(cardPanel, "Kho hàng");
		    highlightButtonBackground(btnTab2);
		});
		btnTab3.addActionListener(e -> {
		    cardLayout.show(cardPanel, "Sản phẩm");
		    highlightButtonBackground(btnTab3);
		});
		btnTab4.addActionListener(e -> {
		    cardLayout.show(cardPanel, "Doanh thu3");
		    highlightButtonBackground(btnTab4);
		});
		
		btnTab1.addActionListener(e -> cardLayout.show(cardPanel, "Doanh thu"));
		btnTab2.addActionListener(e -> cardLayout.show(cardPanel, "Kho hàng"));
		btnTab3.addActionListener(e -> cardLayout.show(cardPanel, "Sản phẩm"));
		btnTab4.addActionListener(e -> cardLayout.show(cardPanel, "Doanh thu3"));

		menupanel.add(Box.createRigidArea(new Dimension(0,20)));
		menupanel.add(btnTab1);
		menupanel.add(Box.createVerticalStrut(10));
		menupanel.add(btnTab2);
		menupanel.add(Box.createVerticalStrut(10));
		menupanel.add(btnTab3);
		menupanel.add(Box.createVerticalStrut(10));
		menupanel.add(btnTab4);
		menupanel.add(Box.createVerticalStrut(10));
		
		
		add(menupanel,BorderLayout.WEST);
		add(cardPanel, BorderLayout.CENTER);
		
		setVisible(true);
	}
	private JButton styleButton(String text, String iconPath) {
		ImageIcon icon =new ImageIcon(iconPath);
		Image img=icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		icon =new ImageIcon(img);
		
	JButton button=new JButton(text,icon);
		button.setHorizontalTextPosition(SwingConstants.RIGHT);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setFocusPainted(false);
		button.setForeground(Color.black);
		button.setBackground(Color.white);
		button.setMaximumSize(new Dimension(175,50));
		button.setBorder(BorderFactory.createLineBorder(Color.PINK, 0, true));
		button.setPreferredSize(new Dimension(180,50));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setOpaque(true);
		button.setBorder(new RoundedBorder(20)); 
		button.setMargin(new Insets(10,20,10,20));
		
		return button;
	}
	class RoundedBorder implements Border {
	    private int radius;

	    RoundedBorder(int radius) {
	        this.radius = radius;
	    }

	    public Insets getBorderInsets(Component c) {
	        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
	    }

	    public boolean isBorderOpaque() {
	        return false;
	    }

	    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	        g.setColor(new Color(200, 230, 250));
	        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
	    }
	}
	private JButton[] menuButtons;
	
	private void highlightButtonBackground(JButton selectedButton) {
	    for (JButton btn : menuButtons) {
	        if (btn == selectedButton) {
	            btn.setBackground(new Color(180, 220, 240)); // màu khi chọn
	        } else {
	            btn.setBackground(Color.WHITE); // màu mặc định
	        }
	    }
	}


	private JPanel bangdoanhthu() {
	    JPanel pan = new JPanel(new BorderLayout());
	    pan.setOpaque(false); 
	    String[] thanhphan = {"Ngày", "Vốn", "Doanh Thu", "Lợi Nhuận"};
	    tableModel = new DefaultTableModel(thanhphan, 0);
	    table = new JTable(tableModel) {
	        @Override
	        public boolean isOpaque() {
	            return false;
	        }
	    };

	    DefaultTableCellRenderer center = new DefaultTableCellRenderer();
	    center.setHorizontalAlignment(JLabel.CENTER); 

	    for (int i = 0; i < table.getColumnCount(); i++) {
	        table.getColumnModel().getColumn(i).setCellRenderer(center);
	    }
	    ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
	        .setHorizontalAlignment(JLabel.CENTER);

	    table.setShowGrid(false);
	    table.setRowHeight(40);
	    table.setIntercellSpacing(new Dimension(0, 0));
	    table.setFillsViewportHeight(true);
	    table.setOpaque(false);
	    table.setBackground(new Color(255, 255, 255, 100));

	    JScrollPane tableScro = new JScrollPane(table);
	    tableScro.setOpaque(false);
	    tableScro.getViewport().setOpaque(false);
	    tableScro.setBorder(null);

	    pan.add(tableScro, BorderLayout.CENTER);

	    loadTable2();
	    return pan;
	}


	 private JPanel Bangthongke() {
	        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 0));
	        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	        panel.add(taoBox("14", "Sản phẩm hiện có trong kho", new Color(173, 216, 230)));
	        panel.add(taoBox("18", "Khách từ trước đến nay", new Color(144, 238, 144)));
	        panel.add(taoBox("5", "Nhân viên đang hoạt động", new Color(255, 228, 181)));

	        return panel;
	    }
	 private JPanel taoBox(String so, String ten, Color color) {
	        JPanel box = new JPanel(new BorderLayout());
	        box.setPreferredSize(new Dimension(200, 80));
	        box.setBackground(color);
	        box.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
	        
	        JLabel soLabel = new JLabel(so, SwingConstants.CENTER);
	        soLabel.setFont(new Font("Arial", Font.BOLD, 24));
	        JLabel tenLabel = new JLabel(ten, SwingConstants.CENTER);
	        tenLabel.setFont(new Font("Arial", Font.PLAIN, 14));
	        
	        box.add(soLabel, BorderLayout.CENTER);
	        box.add(tenLabel, BorderLayout.SOUTH);
	        return box;
	    }
		private JPanel doanhthu() {
			JPanel pan=new JPanel();
			pan.setBackground(Color.WHITE);
			pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
			pan.add(Bangthongke());
			pan.add(Dothi());
			pan.add(bangdoanhthu());
			return pan;
		}
		private JPanel Dothi() {
	    	JPanel pan=new JPanel();
	        Map<String, Double> data = DoanhThuDAO.layDoanhThuTheoNgay();

	        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        for (Map.Entry<String, Double> entry : data.entrySet()) {
	            dataset.addValue(entry.getValue(), "Doanh thu", entry.getKey());
	        }

	        JFreeChart chart = ChartFactory.createLineChart(
	                "Doanh thu theo ngày",
	                "Ngày",
	                "VNĐ",
	                dataset
	        );

	        var plot = chart.getCategoryPlot();
	        plot.setBackgroundPaint(new Color(240, 248, 255)); // nền biểu đồ (nhạt xanh)
	        plot.setRangeGridlinePaint(Color.GRAY); // lưới ngang
	        plot.setDomainGridlinesVisible(true);   // bật lưới dọc
	        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);

	        var renderer = plot.getRenderer();
	        renderer.setSeriesPaint(0, new Color(72, 61, 139)); // màu tím đậm cho line
	        renderer.setSeriesStroke(0, new BasicStroke(2.5f)); // nét dày hơn
	        
	        return new ChartPanel(chart);
	    }
//Kho hang
		  private JPanel khohang() {
		        JPanel pan = new JPanel(new BorderLayout());     
		        pan.setBackground(new Color(173, 216, 230)); // LightBlue

		        JLabel lab = new JLabel("Nhập thông tin sản phẩm");
		        lab.setFont(new Font("Segoe UI", Font.BOLD, 16));
		        lab.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		        JPanel searchPanel = new JPanel();
		        searchPanel.setOpaque(false);
		        
		        tim = new JTextField(20);
		        

		        ImageIcon icon = new ImageIcon("image-removebg-preview (2).png");
		        Image ima = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		        timkiem = new JButton(new ImageIcon(ima));
		        timkiem.setPreferredSize(new Dimension(30, 30));
		        timkiem.addActionListener(this);
		        
		        searchPanel.add(tim);
		        searchPanel.add(timkiem);
		        JPanel headerPanel = new JPanel(new BorderLayout());
		        headerPanel.setOpaque(false);
		        headerPanel.add(lab, BorderLayout.WEST);
		        headerPanel.add(searchPanel, BorderLayout.EAST);

		        String[] thanhphan = {"Mã sản phẩm", "Tên sản phẩm", "Loại", "Số lượng", "Giá Bán", "Nhà cung cấp", "Mã vạch", "Mô tả"};
		        tableModel = new DefaultTableModel(thanhphan, 0);
		        table = new JTable(tableModel);
		        table.setRowHeight(30);

		        JScrollPane tablesco = new JScrollPane(table);

		        pan.add(headerPanel, BorderLayout.NORTH);
		        pan.add(tablesco, BorderLayout.CENTER);

		        loadTable();
		        return pan;
		    }
	  
//San Pham	 
	  private JPanel sanpham() {
		
			    JPanel panel = new JPanel(new BorderLayout());
			    
			    JLabel lab = new JLabel("Nhập thông tin sản phẩm");
			    lab.setBounds(10, 10, 200, 30);
			    panel.add(lab,BorderLayout.NORTH);

			    String[] thanhphan = {"Mã sản phẩm", "Tên sản phẩm", "Loại", "Số lượng", "Giá Gốc","Giá Bán", "Nhà cung cấp", "Mã vạch", "Mô tả", "Hình ảnh"};
			    tableModel = new DefaultTableModel(thanhphan, 0);
			    table = new JTable(tableModel);
			    table.setFillsViewportHeight(true);
			    table.addMouseListener(new java.awt.event.MouseAdapter() {
			   
			    	@Override
			        public void mouseClicked(java.awt.event.MouseEvent evt) {
			            int selectedRow = table.getSelectedRow(); // Lấy hàng được chọn
			            if (selectedRow != -1) {
			                masp.setText(tableModel.getValueAt(selectedRow, 0).toString());
			                tensp.setText(tableModel.getValueAt(selectedRow, 1).toString());
			                loai.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
			                soluong.setText(tableModel.getValueAt(selectedRow, 3).toString());
			                giagoc.setText(tableModel.getValueAt(selectedRow, 4).toString());
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
			    panel.add(tablesco,BorderLayout.CENTER);
			 // Tạo JTable và JScrollPane
			   
			    // === TÙY CHỈNH GIAO DIỆN BẢNG ===

			    // Font và kích thước
			    table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
			    table.setRowHeight(32);

			    // Màu nền bảng và header
			    table.setBackground(Color.WHITE);
			    table.getTableHeader().setBackground(new Color(245, 245, 245));

			    // Kẻ viền ô mảnh và tinh tế
			    table.setGridColor(new Color(220, 220, 220)); // Màu viền
			    table.setShowGrid(true);

			    // Căn giữa nội dung
			    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			    for (int i = 0; i < table.getColumnCount(); i++) {
			        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			    }

			    // Căn giữa tiêu đề
			    ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
			        .setHorizontalAlignment(SwingConstants.CENTER);

			    // Tắt chỉnh sửa
			    table.setDefaultEditor(Object.class, null);

			    // Border tổng thể nhẹ cho bảng (nếu cần)
			    tablesco.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

			    JPanel panel2=new JPanel(null);
			    panel2.setPreferredSize(new Dimension(1000,350	));
			    JLabel lblMaSP = new JLabel("Mã sản phẩm:");
			    lblMaSP.setBounds(10, 10, 100, 30);
			    masp = new JTextField();
			    masp.setBounds(120, 10, 200, 30);

			    JLabel lblTenSP = new JLabel("Tên sản phẩm:");
			    lblTenSP.setBounds(10, 50, 100, 30);
			    tensp = new JTextField();
			    tensp.setBounds(120, 50, 200, 30);

			    JLabel lblloai = new JLabel("Loại:");
			    lblloai.setBounds(10, 90, 100, 30);
			    loai = new JComboBox<>(new String[]{"1. Thực phẩm & Đồ uống", "2. Đồ gia dụng & Hóa phẩm", "3. Mỹ phẩm & Chăm sóc cá nhân", "4. Đồ ăn vặt & Bánh kẹo", "5. Sản phẩm cho trẻ em"});
			    loai.setBounds(120, 90, 200, 30);

			    JLabel lblSoLuong = new JLabel("Số lượng:");
			    lblSoLuong.setBounds(10, 130, 100, 30);
			    soluong = new JTextField();
			    soluong.setBounds(120, 130, 200, 30);

			    JLabel lblGiaGoc = new JLabel("Giá gốc:");
			    lblGiaGoc.setBounds(10, 170, 100, 30);
			    giagoc = new JTextField();
			    giagoc.setBounds(120, 170, 200, 30);
			    
			    JLabel lblGiaBan = new JLabel("Giá bán:");
			    lblGiaBan.setBounds(10, 210, 100, 30);
			    giaban = new JTextField();
			    giaban.setBounds(120, 210, 200, 30);

			    JLabel lblnhacungcap = new JLabel("Nhà cung cấp:");
			    lblnhacungcap.setBounds(10, 250, 100, 30);
			    nhacungcap = new JTextField();
			    nhacungcap.setBounds(120, 250, 200, 30);

			    JLabel lblmavach = new JLabel("Mã vạch:");
			    lblmavach.setBounds(10, 290, 100, 30);
			    mavach = new JTextField();
			    mavach.setBounds(120, 290, 200, 30);

			    JLabel lblMoTa = new JLabel("Mô tả:");
			    lblMoTa.setBounds(10, 330, 100, 30);
			    mota = new JTextField();
			    mota.setBounds(120, 330, 200, 30);

			    hinhanh = new JLabel();
			    hinhanh.setBounds(500, 20, 150, 150);
			    hinhanh.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			    them = new JButton("Thêm");
			    them.setBounds(350, 220, 80, 30);
			    xoa = new JButton("Xóa");
			    xoa.setBounds(440, 220, 80, 30);
			    sua = new JButton("Sửa");
			    sua.setBounds(530, 220, 80, 30);
			    qr = new JButton("QRcode");
			    qr.setBounds(620, 220, 100, 30);
			    themhinhanh = new JButton("Thêm Hình Ảnh");
			    themhinhanh.setBounds(500, 180, 150, 30);
			    
			    panel2.add(lblMaSP); panel2.add(masp);
			    panel2.add(lblTenSP); panel2.add(tensp);
			    panel2.add(lblloai); panel2.add(loai);
			    panel2.add(lblSoLuong); panel2.add(soluong);
			    panel2.add(lblGiaGoc); panel2.add(giagoc);
			    panel2.add(lblGiaBan); panel2.add(giaban);
			    panel2.add(lblnhacungcap); panel2.add(nhacungcap);
			    panel2.add(lblmavach); panel2.add(mavach);
			    panel2.add(lblMoTa); panel2.add(mota);
			    panel2.add(them); panel2.add(xoa); panel2.add(sua);panel2.add(themhinhanh);panel2.add(qr);
			    panel2.add(hinhanh);
			    
			    panel.add(panel2,BorderLayout.SOUTH);
			    
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
//Quan ly nhân viên
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
//Load lai csdl  
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
		            double giaGoc = rs.getDouble("giaGoc");
		            String nhaCungCap = rs.getString("nhaCungCap");
		            String maVach = rs.getString("maVach");
		            String moTa = rs.getString("moTa");
		            String hinhAnh = rs.getString("hinhanh");

		            tableModel.addRow(new Object[]{maSP, tenSP, loaiSP, soLuong, giaGoc, nhaCungCap, maVach, moTa, hinhAnh});  
//		            if (hinhAnh != null && !hinhAnh.isEmpty()) {
//		                ImageIcon icon = new ImageIcon(new ImageIcon(hinhAnh).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
//		                hinhanh.setIcon(icon);
//		                hinhAnh = "Đã thêm ảnh";
//		            } else {
//		                  hinhAnh = "";
//		        }}
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
	  private void loadTable2() {
		    try {
		        String query = "SELECT * FROM doanhthu";
		        PreparedStatement stmt = connection.prepareStatement(query);
		        ResultSet rs = stmt.executeQuery();

		        tableModel.setRowCount(0); 
		        while (rs.next()) {     
		            String ngay =rs.getString("ngayMua");
		            String von= rs.getString("von");
		            String doanhthu= rs.getString("doanhthu");
		            String loinhuan= rs.getString("loinhuan");
		            
		            tableModel.addRow(new Object[]{ngay,von,doanhthu,loinhuan});  
		    }} catch (SQLException e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
		    }
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
		    double giaTienDouble = 0;
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
		        String query = "INSERT INTO qlysanpham (maSP, tenSP, loaiSP, soLuong, giaGoc, nhaCungCap, maVach, moTa, hinhanh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
		    double giaGoc = Double.parseDouble(giatien.getText());
		    String nhaCungCap = nhacungcap.getText();
		    String maVach = mavach.getText();
		    String moTa = mota.getText();
		    String hinhAnh = (hinhanh.getToolTipText() != null) ? hinhanh.getToolTipText() : "";

		    try {
		        String query = "UPDATE qlysanpham SET maSP=?, tenSP=?, loaiSP=?, soLuong=?, giaGoc=?, nhaCungCap=?, maVach=?, mota=?, hinhAnh=? WHERE maSP=?";
		        PreparedStatement stmt = connection.prepareStatement(query);
		        stmt.setString(1, maSP);
		        stmt.setString(2, tenSP);
		        stmt.setString(3, loaiSP);
		        stmt.setInt(4, soLuong);
		        stmt.setDouble(5, giaGoc);
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
	  private void Timkiem() {
			String keyword =tim.getText().trim();
			if(keyword.isEmpty()) {
				loadTable();
			}else {
				try {
					tableModel.setRowCount(0);
					  String query = "SELECT * FROM qlysanpham WHERE maSP LIKE ? OR tenSP LIKE ? OR loaiSP LIKE ? OR soLuong LIKE ? OR giaGoc LIKE ? OR nhaCungCap LIKE ? OR maVach LIKE ? OR moTa LIKE ?";    
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
		                    rs.getDouble("giaGoc"),
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
private void connectDatabase() {
		    try {
		        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlycuahang", "root", "2904");
		      
		    } catch (SQLException e) {
		        e.printStackTrace(); 
		        JOptionPane.showMessageDialog(this, "Kết nối cơ sở dữ liệu không thành công: " + e.getMessage());
		    }
}

		@Override
public void actionPerformed(ActionEvent e) {
			if(e.getSource() == timkiem) {
				Timkiem();
			}else if (e.getSource() == them) {
		        themSanPham();
		    } else if (e.getSource() == xoa) {
		        xoaSanPham();
		    } else if (e.getSource() == sua) {
		        suaSanPham();
		    } else if(e.getSource() == timkiem) {
		    	Timkiem();
		    } else if(e.getSource() == themhinhanh) {
		    	chonHinhAnh();

		    }
	}
	

public static void main(String[] args) {
		new test();
	}
	
}


    