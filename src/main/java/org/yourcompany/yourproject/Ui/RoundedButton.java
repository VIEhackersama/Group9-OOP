package org.yourcompany.yourproject.Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.GradientPaint;

/**
 * Một JButton tùy chỉnh được vẽ với các góc bo tròn và hiệu ứng 3D gradient.
 */
public class RoundedButton extends JButton {

    private int arc = 25; // Độ cong

    // --- Màu sắc cho nút (Bảng màu Xanh Lục) ---
    private Color primaryColor = new Color(26, 188, 156);     // Màu chính (đầu gradient)
    private Color primaryColorBottom = new Color(22, 160, 133); // Màu chính (cuối gradient)
    private Color primaryHover = new Color(30, 200, 170);     // Màu khi di chuột (đầu gradient)
    private Color primaryHoverBottom = new Color(26, 188, 156); // Màu khi di chuột (cuối gradient)
    private Color primaryPressed = new Color(22, 160, 133);    // Màu khi nhấn (màu đặc)
    private Color primaryText = Color.WHITE;                 // Màu chữ

    public RoundedButton(String text) {
        super(text);

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setFont(new Font("Segoe UI", Font.BOLD, 12));
        setPreferredSize(new Dimension(110, 35));

        // Mặc định luôn là chữ trắng
        setForeground(primaryText);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ButtonModel model = getModel();
        int width = getWidth();
        int height = getHeight();

        Paint oldPaint = g2.getPaint(); // Lưu lại paint cũ

        if (model.isPressed()) {
            // Khi nhấn
            g2.setColor(primaryPressed);
            g2.fillRoundRect(0, 0, width, height, arc, arc);
        } else if (model.isRollover()) {
            // Khi di chuột
            GradientPaint gp = new GradientPaint(
                    0, 0, primaryHover,
                    0, height, primaryHoverBottom);
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, width, height, arc, arc);
        } else {
            // Trạng thái bình thường
            GradientPaint gp = new GradientPaint(
                    0, 0, primaryColor,
                    0, height, primaryColorBottom);
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, width, height, arc, arc);
        }
        g2.setPaint(oldPaint); // Khôi phục paint cũ

        g2.dispose();
        super.paintComponent(g);
    }

    /**
     * Phương thức contains
     * Tạo một hình chữ nhật bo tròn mới mỗi lần
     * để kiểm tra va chạm.
     */
    @Override
    public boolean contains(int x, int y) {
        return new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc).contains(x, y);
    }
}