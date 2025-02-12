package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Styles {
    public Styles(){

    }
    public void StyleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        button.setContentAreaFilled(false);
        button.setOpaque(false);

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(button.getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, button.getWidth(), button.getHeight(), 20, 20));

                g2.setColor(button.getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int x = (button.getWidth() - fm.stringWidth(button.getText())) / 2;
                int y = (button.getHeight() + fm.getAscent()) / 2 - 2;
                g2.drawString(button.getText(), x, y);

                g2.dispose();
            }
        });
    }

    public void StyleTextField(JTextField textField){
        textField.setPreferredSize(new Dimension(150, 30));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5,5,5,5)
        ));
        textField.setFont(new Font("Segoe UI", Font.BOLD, 16));
    }
}
