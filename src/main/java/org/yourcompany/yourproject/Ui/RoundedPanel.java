package org.yourcompany.yourproject.Ui; // Hoặc package khác tùy bạn đặt

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {
    private int cornerRadius = 15; // Bán kính bo góc mặc định
    private Color backgroundColor = Color.WHITE; // Màu nền mặc định

    public RoundedPanel() {
        super();
        setOpaque(false); // Quan trọng: Đặt thành false để cho phép vẽ nền tùy chỉnh
    }

    public RoundedPanel(LayoutManager layout) {
        super(layout);
        setOpaque(false);
    }

    public RoundedPanel(int radius) {
        super();
        this.cornerRadius = radius;
        setOpaque(false);
    }

    public RoundedPanel(LayoutManager layout, int radius) {
        super(layout);
        this.cornerRadius = radius;
        setOpaque(false);
    }

    public RoundedPanel(Color bgColor) {
        super();
        this.backgroundColor = bgColor;
        setOpaque(false);
    }

    public RoundedPanel(LayoutManager layout, Color bgColor) {
        super(layout);
        this.backgroundColor = bgColor;
        setOpaque(false);
    }

    public RoundedPanel(int radius, Color bgColor) {
        super();
        this.cornerRadius = radius;
        this.backgroundColor = bgColor;
        setOpaque(false);
    }

    public RoundedPanel(LayoutManager layout, int radius, Color bgColor) {
        super(layout);
        this.cornerRadius = radius;
        this.backgroundColor = bgColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Vẽ nền bo góc
        g2.setColor(backgroundColor);
        g2.fill(new RoundRectangle2D.Double(0, 0, width - 1, height - 1, cornerRadius, cornerRadius));

        // Vẽ viền (tùy chọn)
        // g2.setColor(Color.LIGHT_GRAY); // Hoặc màu viền bạn muốn
        // g2.draw(new RoundRectangle2D.Double(0, 0, width - 1, height - 1, cornerRadius, cornerRadius));

        g2.dispose();
    }

    // Override setBackground để cập nhật màu nền tùy chỉnh
    @Override
    public void setBackground(Color bg) {
        this.backgroundColor = bg;
        if (isOpaque()) { // Nếu đang là opaque, gọi super để nó vẽ
            super.setBackground(bg);
        }
        repaint(); // Vẽ lại panel
    }

    // Lấy màu nền tùy chỉnh
    @Override
    public Color getBackground() {
        return backgroundColor;
    }
}