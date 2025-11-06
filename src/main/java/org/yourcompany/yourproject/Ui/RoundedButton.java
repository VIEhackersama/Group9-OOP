package org.yourcompany.yourproject.Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.GradientPaint;

/**
 * Một JButton tùy chỉnh được vẽ với các góc bo tròn và hiệu ứng 3D.
 * Hỗ trợ hai kiểu: FILLED (chính) và OUTLINE (phụ).
 */
public class RoundedButton extends JButton {

    public static final int STYLE_FILLED = 1;
    public static final int STYLE_OUTLINE = 2;

    private int style;
    private int arc = 25; // Độ cong

    // --- Màu cho kiểu FILLED (Nút chính) - Bảng màu Xanh Lục gợi ý ---
    private Color primaryColor = new Color(26, 188, 156);     // Màu chính (đầu gradient)
    private Color primaryColorBottom = new Color(22, 160, 133); // Màu chính (cuối gradient)
    private Color primaryHover = new Color(30, 200, 170);     // Màu khi di chuột (đầu gradient)
    private Color primaryHoverBottom = new Color(26, 188, 156); // Màu khi di chuột (cuối gradient)
    private Color primaryPressed = new Color(22, 160, 133);    // Màu khi nhấn (màu đặc)
    private Color primaryText = Color.WHITE;                 // Màu chữ

    // --- Màu cho kiểu OUTLINE (Nút phụ) ---
    private Color secondaryColor = new Color(100, 100, 100);
    private Color secondaryHover = new Color(26, 188, 156);   // Đổi màu hover cho đồng bộ
    private Color secondaryPressed = new Color(22, 160, 133); // Đổi màu pressed cho đồng bộ
    private Color secondaryBackground = new Color(245, 245, 245);

    private Shape shape;

    public RoundedButton(String text) {
        this(text, STYLE_FILLED);
    }

    public RoundedButton(String text, int style) {
        super(text);
        this.style = style;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setFont(new Font("Segoe UI", Font.BOLD, 12));
        setPreferredSize(new Dimension(110, 35));

        if (style == STYLE_FILLED) {
            setForeground(primaryText);
        } else {
            setForeground(secondaryColor);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ButtonModel model = getModel();
        int width = getWidth();
        int height = getHeight();

        if (style == STYLE_FILLED) {
            // --- Vẽ kiểu FILLED (với hiệu ứng 3D) ---
            Paint oldPaint = g2.getPaint(); // Lưu lại paint cũ

            if (model.isPressed()) {
                // Khi nhấn: Dùng màu đặc (tạo cảm giác bị ấn phẳng)
                g2.setColor(primaryPressed);
                g2.fillRoundRect(0, 0, width, height, arc, arc);
            } else if (model.isRollover()) {
                // Khi di chuột: Dùng gradient (sáng hơn)
                GradientPaint gp = new GradientPaint(
                        0, 0, primaryHover,         // Bắt đầu từ trên (màu sáng)
                        0, height, primaryHoverBottom); // Kết thúc ở dưới (màu tối hơn)
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, width, height, arc, arc);
            } else {
                // Trạng thái bình thường: Dùng gradient
                GradientPaint gp = new GradientPaint(
                        0, 0, primaryColor,       // Bắt đầu từ trên (màu sáng)
                        0, height, primaryColorBottom); // Kết thúc ở dưới (màu tối hơn)
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, width, height, arc, arc);
            }
            g2.setPaint(oldPaint); // Khôi phục paint cũ

        } else {
            // --- Vẽ kiểu OUTLINE (giữ nguyên logic cũ) ---
            g2.setColor(secondaryBackground);
            g2.fillRoundRect(0, 0, width, height, arc, arc);

            if (model.isPressed()) {
                g2.setColor(secondaryPressed);
            } else if (model.isRollover()) {
                g2.setColor(secondaryHover);
            } else {
                g2.setColor(secondaryColor);
            }
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(1, 1, width - 3, height - 3, arc, arc);

            if (model.isRollover() || model.isPressed()) {
                setForeground(secondaryHover);
            } else {
                setForeground(secondaryColor);
            }
        }

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc);
        }
        return shape.contains(x, y);
    }
}