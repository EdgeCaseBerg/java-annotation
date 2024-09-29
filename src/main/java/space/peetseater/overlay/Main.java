package space.peetseater.overlay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame jFrame = new JFrame("Annotation Overlay");
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            // translucent
            jFrame.setUndecorated(true);
            Color transparent = new Color(1f, 1f, 1f, 10/255.0f);
            jFrame.getContentPane().setBackground(transparent);
            jFrame.setBackground(transparent);
            jFrame.setPreferredSize(new Dimension(1080, 720));
            jFrame.setAlwaysOnTop(true);
            jFrame.setVisible(true);
            jFrame.setLayout(new BorderLayout());

            JPanel jPanel = new JPanel(new FlowLayout());
            jFrame.add(jPanel, BorderLayout.PAGE_END);

            AnnotatablePane annotatablePane = new AnnotatablePane();
            annotatablePane.setBackground(transparent);
            jFrame.add(annotatablePane);

            JButton close = new JButton("X");
            jPanel.add(close);
            close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            JButton clear = new JButton("Clear");
            clear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    annotatablePane.setClearFlag(true);
                    annotatablePane.repaint();
                }
            });
            jPanel.add(clear);

            jFrame.pack();
        });
    }
}