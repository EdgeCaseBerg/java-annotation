package space.peetseater.overlay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame jFrame = new JFrame("Annotation Overlay");
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            // translucent
            jFrame.setUndecorated(true);
            Color transparent = new Color(1f, 1f, 1f, 1/255.0f);
            jFrame.getContentPane().setBackground(transparent);
            jFrame.setBackground(transparent);
            jFrame.setPreferredSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
            jFrame.setVisible(true);
            jFrame.setLayout(new BorderLayout());

            JPanel jPanel = new JPanel(new FlowLayout());
            jPanel.setBackground(transparent);
            jFrame.add(jPanel, BorderLayout.PAGE_END);

            AnnotatablePane annotatablePane = new AnnotatablePane();
            annotatablePane.setBackground(transparent);
            annotatablePane.setAnnotationColor(Color.RED);
            jFrame.add(annotatablePane);

            JButton close = new JButton("X");
            close.setMnemonic('x');
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
            clear.setMnemonic('c');
            jPanel.add(clear);

            Function<JButton, ActionListener> colorSetter = (JButton button) -> new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    annotatablePane.setAnnotationColor(button.getBackground());
                }
            };

            JButton red = new JButton("      ");
            red.setBackground(Color.RED);
            red.addActionListener(colorSetter.apply(red));
            red.setMnemonic('r');
            jPanel.add(red);

            JButton cyan = new JButton("      ");
            cyan.setBackground(Color.CYAN);
            cyan.addActionListener(colorSetter.apply(cyan));
            cyan.setMnemonic('b');
            jPanel.add(cyan);

            JButton yellow = new JButton("      ");
            yellow.setBackground(Color.YELLOW);
            yellow.addActionListener(colorSetter.apply(yellow));
            yellow.setMnemonic('y');
            jPanel.add(yellow);

            jFrame.pack();
        });
    }
}