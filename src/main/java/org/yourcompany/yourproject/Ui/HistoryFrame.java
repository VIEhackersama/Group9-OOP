package org.yourcompany.yourproject.Ui;

import org.yourcompany.yourproject.Config.UserDataService;
import org.yourcompany.yourproject.Entity.PricePrediction;
import org.yourcompany.yourproject.Entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class HistoryFrame extends javax.swing.JFrame {

    private User loggedInUser;
    private UserDataService userDataService;
    private DefaultTableModel tableModel;

    public HistoryFrame(User user) {
        this.loggedInUser = user;
        this.userDataService = new UserDataService();
        
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Lịch sử dự đoán của: " + user.getName());
        
        // Load dữ liệu ngay khi mở form
        loadHistoryData();
    }

    private void loadHistoryData() {
        // Lấy danh sách từ DB
        List<PricePrediction> list = userDataService.getPredictionsByUserId(loggedInUser.getId());

        // Xóa dữ liệu cũ trên bảng (nếu có)
        tableModel = (DefaultTableModel) tblHistory.getModel();
        tableModel.setRowCount(0);

        // Format tiền tệ cho đẹp (Ví dụ: 5,000,000,000)
        DecimalFormat df = new DecimalFormat("#,###");

        // Đổ dữ liệu vào bảng
        for (PricePrediction p : list) {
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getPredictionDate(),
                p.getProductName(),
                df.format(p.getPredictedPrice()) + " Tỷ VNĐ", // Format giá
                p.getNote()
            });
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHistory = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // Chỉ đóng form này, không tắt app

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("LỊCH SỬ DỰ ĐOÁN GIÁ NHÀ");

        btnBack.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnBack.setText("Quay lại");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose(); // Đóng cửa sổ lịch sử
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 363, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnBack))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        tblHistory.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        tblHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Ngày dự đoán", "Tên sản phẩm", "Giá dự đoán", "Chi tiết"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHistory.setRowHeight(25);
        jScrollPane1.setViewportView(tblHistory);
        if (tblHistory.getColumnModel().getColumnCount() > 0) {
            tblHistory.getColumnModel().getColumn(0).setMaxWidth(50); // Cột ID nhỏ
            tblHistory.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblHistory.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblHistory.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblHistory.getColumnModel().getColumn(4).setPreferredWidth(300);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }

    private javax.swing.JButton btnBack;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblHistory;
}