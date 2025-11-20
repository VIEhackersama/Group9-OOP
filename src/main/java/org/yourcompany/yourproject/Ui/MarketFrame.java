package org.yourcompany.yourproject.Ui;

import org.yourcompany.yourproject.Config.HouseMongoController;
import org.yourcompany.yourproject.Config.HouseCsvController;
import org.yourcompany.yourproject.Entity.House;
import org.yourcompany.yourproject.Entity.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.Objects;

public class MarketFrame extends javax.swing.JFrame {
    
    private User loggedInUser;
    private List<House> allHouses; 
    private DefaultListModel<House> houseListModel; 
    private JLabel emptyListLabel;
    private JScrollPane listScrollPane;
    
    /**
     * Creates new form MarketFrame
     */
    public MarketFrame(User user) {
        this.loggedInUser = user;
        initComponents(); 
        this.setLocationRelativeTo(null);

        // Nối biến logic của mình với biến giao diện của NetBeans
        // Nếu thiếu dòng này -> Lỗi NullPointerException
        this.listScrollPane = jScrollPane1; 

        // Ẩn panel phụ
        extraDetailsPanel.setVisible(false);
        
        //Setup các thứ khác
        lblWelcome.setText("Xin chào, " + loggedInUser.getName());
        lblEmail.setText(loggedInUser.getEmail());

        houseListModel = new DefaultListModel<>();
        jListHouses.setModel(houseListModel);

        emptyListLabel = new JLabel("Không tìm thấy nhà nào!", SwingConstants.CENTER);
        emptyListLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        emptyListLabel.setForeground(Color.GRAY);

        addListeners();        
        loadHouseData();
    }
    
    private void loadHouseData() {
        /* HouseMongoController controller = new HouseMongoController();
        try {
            // GỌI MONGODB
            allHouses = controller.loadHousesFromDb();

            // Kiểm tra null/rỗng
            if (allHouses == null) {
                allHouses = new ArrayList<>(); // Tránh lỗi null pointer
                throw new Exception("Không thể kết nối hoặc lấy dữ liệu từ MongoDB.");
            }
            if (allHouses.isEmpty()) {
                logger.warning("MongoDB trả về danh sách nhà trống.");
            }
        */
        HouseCsvController csvController = new HouseCsvController();
        try {
            String csvPath = Objects.requireNonNull(getClass().getResource("/muanhadat_data_adjusted.csv")).getPath();
            
            // Fix lỗi đường dẫn trên Windows (xóa dấu / ở đầu nếu có)
            if (System.getProperty("os.name").startsWith("Windows") && csvPath.startsWith("/")) {
                csvPath = csvPath.substring(1);
            }
            
            allHouses = csvController.loadHousesFromCsv(csvPath);

            // Tạo bộ lọc duy nhất cho Phường và Hướng
            Set<String> allPhuong = new java.util.HashSet<>();
            Set<String> allHuong = new java.util.HashSet<>();

            for (House house : allHouses) {
                if (house.getDirection() != null && !house.getDirection().isEmpty()) {
                    allHuong.add(house.getDirection());
                }
                if (house.getAddress() != null && !house.getAddress().isEmpty()) {
                    allPhuong.add(house.getAddress());
                }
            }
            
            // Chuyển Set sang List để xử lý thứ tự
            List<String> listPhuong = new java.util.ArrayList<>(allPhuong);
            List<String> listHuong = new java.util.ArrayList<>(allHuong);

            boolean huongHasUnknown = listHuong.remove("Không xác định");
            java.util.Collections.sort(listPhuong);
            java.util.Collections.sort(listHuong);
            listPhuong.add(0, "Tất cả");
            listHuong.add(0, "Tất cả");
            if (huongHasUnknown) {
                listHuong.add("Không xác định");
            }

            cmbPhuong.setModel(new DefaultComboBoxModel<>(listPhuong.toArray(new String[0])));
            cmbHuong.setModel(new DefaultComboBoxModel<>(listHuong.toArray(new String[0])));

            // Hiển thị danh sách lần đầu
            updateFilteredList();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm này sẽ được gọi khi thay đổi ComboBox
    private void updateFilteredList() {
        String selectedPhuong = (String) cmbPhuong.getSelectedItem();
        String selectedHuong = (String) cmbHuong.getSelectedItem();

        houseListModel.clear(); // Xóa danh sách cũ trên giao diện
        
        if (allHouses == null) return;

        for (House house : allHouses) {
            // Điều kiện 1: Phường
            boolean phuongMatch = selectedPhuong.equals("Tất cả") || house.getAddress().equals(selectedPhuong);
            
            // Điều kiện 2: Hướng
            boolean huongMatch = selectedHuong.equals("Tất cả") || house.getDirection().equals(selectedHuong);

            // Nếu thỏa mãn cả 2 thì thêm vào danh sách
            if (phuongMatch && huongMatch) {
                houseListModel.addElement(house);
            }
        }
        
        // Xử lý hiển thị: Nếu rỗng thì hiện Label, có dữ liệu thì hiện List
        if (houseListModel.isEmpty()) {
            listScrollPane.setViewportView(emptyListLabel);
        } else {
            listScrollPane.setViewportView(jListHouses);
        }
        
        // Xóa lựa chọn cũ và xóa trắng các ô nhập liệu
        jListHouses.clearSelection();
        clearDetailFields();
    }
    
    private void addListeners() {
        // ListSelectionListener e
        jListHouses.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                House h = jListHouses.getSelectedValue();
                // gọi getSelectedValue(): JList hỏi Selection Model dòng nào đang được chọn. JList chạy sang Data Model cho  xin vật thể ở vị trí đó rồi trả vật thể đó về cho biến h
                if (h != null) {
                    //
                    txtAddress.setText(h.getAddress());
                    txtPrice.setText(String.valueOf(h.getPrice()));
                    txtArea.setText(String.valueOf(h.getArea()));
                    txtStreet.setText(String.valueOf(h.getStreetInFrontOfHouse()));
                    txtWidth.setText(String.valueOf(h.getWidth()));
                    
                    // --- Đổ dữ liệu vào Panel Phụ (10 trường) ---
                    txtHeight.setText(String.valueOf(h.getHeight()));
                    txtFloor.setText(String.valueOf(h.getFloorNumber()));
                    txtBedroom.setText(String.valueOf(h.getBedroomNumber()));
                    txtBathroom.setText(String.valueOf(h.getBathroomNumber()));
                    txtDirection.setText(h.getDirection());
                    
                    txtLaw.setText(String.valueOf(h.getLaw()));
                    txtDistance.setText(String.format("%.2f", h.getDistance_center()));
                    txtDensity.setText(String.format("%.2f", h.getRoom_density()));
                    txtBathPerBed.setText(String.format("%.2f", h.getBath_per_bed()));
                    txtRatio.setText(String.format("%.2f", h.getWide_ratio()));
                } else {
                    clearDetailFields();
                }
            }
        });

        cmbPhuong.addActionListener(e -> updateFilteredList());
        cmbHuong.addActionListener(e -> updateFilteredList());
        
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }
    
    private void clearDetailFields() {
        txtAddress.setText("");
        txtPrice.setText("");
        txtArea.setText("");
        txtStreet.setText("");
        txtWidth.setText("");
        txtHeight.setText("");
        txtFloor.setText("");
        txtBedroom.setText("");
        txtBathroom.setText("");
        txtDirection.setText("");
        txtLaw.setText("");
        txtDistance.setText("");
        txtDensity.setText("");
        txtBathPerBed.setText("");
        txtRatio.setText("");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cmbPhuong = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListHouses = new javax.swing.JList<>();
        jLabel10 = new javax.swing.JLabel();
        cmbHuong = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        lblEmail = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        txtArea = new javax.swing.JTextField();
        txtStreet = new javax.swing.JTextField();
        txtWidth = new javax.swing.JTextField();
        btnToggleDetails = new javax.swing.JButton();
        extraDetailsPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtHeight = new javax.swing.JTextField();
        txtFloor = new javax.swing.JTextField();
        txtBedroom = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtBathroom = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtDirection = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtLaw = new javax.swing.JTextField();
        txtDistance = new javax.swing.JTextField();
        txtDensity = new javax.swing.JTextField();
        txtBathPerBed = new javax.swing.JTextField();
        txtRatio = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Phường");

        cmbPhuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbPhuong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jScrollPane1.setViewportView(jListHouses);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Hướng");

        cmbHuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbHuong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbHuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbPhuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(122, 122, 122))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmbPhuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cmbHuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        lblEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblEmail.setText("email");

        lblWelcome.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblWelcome.setText("Xin chào, tên");

        btnLogout.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLogout.setText("Đăng xuất");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(lblWelcome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblEmail)
                .addGap(18, 18, 18)
                .addComponent(btnLogout)
                .addGap(11, 11, 11))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(lblWelcome)
                    .addComponent(btnLogout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jLabel1.setText("Địa chỉ:");

        jLabel2.setText("Giá (tỷ):");

        jLabel3.setText("Diện tích (m2):");

        jLabel4.setText("Mặt tiền:");

        jLabel5.setText("Đường trước nhà:");

        txtAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddressActionPerformed(evt);
            }
        });

        btnToggleDetails.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnToggleDetails.setText("txt");
        btnToggleDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToggleDetailsActionPerformed(evt);
            }
        });

        extraDetailsPanel.setBackground(new java.awt.Color(51, 255, 51));

        jLabel6.setText("Chiều dài (m):");

        jLabel7.setText("Số tầng:");

        jLabel8.setText("Số phòng ngủ:");

        txtFloor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFloorActionPerformed(evt);
            }
        });

        txtBedroom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBedroomActionPerformed(evt);
            }
        });

        jLabel11.setText("Số phòng tắm:");

        jLabel12.setText("Hướng nhà:");

        jLabel13.setText("Pháp lý:");

        jLabel14.setText("Cách trung tâm (km):");

        jLabel15.setText("Mật độ phòng:");

        jLabel16.setText("Tỷ lệ Tắm/Ngủ:");

        jLabel17.setText("Tỷ lệ Rộng/Dài:");

        txtLaw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLawActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout extraDetailsPanelLayout = new javax.swing.GroupLayout(extraDetailsPanel);
        extraDetailsPanel.setLayout(extraDetailsPanelLayout);
        extraDetailsPanelLayout.setHorizontalGroup(
            extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(extraDetailsPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(extraDetailsPanelLayout.createSequentialGroup()
                        .addGroup(extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(37, 37, 37)
                        .addGroup(extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtHeight, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(txtFloor)
                            .addComponent(txtBedroom)))
                    .addGroup(extraDetailsPanelLayout.createSequentialGroup()
                        .addGroup(extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(37, 37, 37)
                        .addGroup(extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBathroom)
                            .addComponent(txtDirection))))
                .addGap(57, 57, 57)
                .addGroup(extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel13))
                .addGap(34, 34, 34)
                .addGroup(extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtLaw, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(txtDistance)
                            .addComponent(txtRatio))
                        .addComponent(txtDensity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtBathPerBed, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        extraDetailsPanelLayout.setVerticalGroup(
            extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(extraDetailsPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtLaw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtFloor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtBedroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtDensity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtBathroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtBathPerBed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(extraDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtDirection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txtRatio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(extraDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(172, 172, 172)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(65, 65, 65)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtStreet, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(280, 280, 280)
                                .addComponent(btnToggleDetails)))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 29, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(14, 14, 14)
                        .addComponent(btnToggleDetails)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(extraDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnToggleDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToggleDetailsActionPerformed
        // TODO add your handling code here:
        boolean isVisible = extraDetailsPanel.isVisible();
        extraDetailsPanel.setVisible(!isVisible);
        btnToggleDetails.setText(isVisible ? "Hiển thị thêm chi tiết" : "Ẩn bớt chi tiết");
    }//GEN-LAST:event_btnToggleDetailsActionPerformed

    private void txtBedroomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBedroomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBedroomActionPerformed

    private void txtLawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLawActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLawActionPerformed

    private void txtAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddressActionPerformed

    private void txtFloorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFloorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFloorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnToggleDetails;
    private javax.swing.JComboBox<String> cmbHuong;
    private javax.swing.JComboBox<String> cmbPhuong;
    private javax.swing.JPanel extraDetailsPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<House> jListHouses;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtArea;
    private javax.swing.JTextField txtBathPerBed;
    private javax.swing.JTextField txtBathroom;
    private javax.swing.JTextField txtBedroom;
    private javax.swing.JTextField txtDensity;
    private javax.swing.JTextField txtDirection;
    private javax.swing.JTextField txtDistance;
    private javax.swing.JTextField txtFloor;
    private javax.swing.JTextField txtHeight;
    private javax.swing.JTextField txtLaw;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtRatio;
    private javax.swing.JTextField txtStreet;
    private javax.swing.JTextField txtWidth;
    // End of variables declaration//GEN-END:variables
}
