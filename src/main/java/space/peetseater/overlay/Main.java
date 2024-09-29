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
            jFrame.add(jPanel, BorderLayout.PAGE_END);

            AnnotatablePane annotatablePane = new AnnotatablePane();
            annotatablePane.setBackground(transparent);
            annotatablePane.setAnnotationColor(Color.RED);
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

            Function<JButton, ActionListener> colorSetter = (JButton button) -> new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("SET COLOR " + button.getBackground());
                    annotatablePane.setAnnotationColor(button.getBackground());
                }
            };

            JButton red = new JButton("      ");
            red.setBackground(Color.RED);
            red.addActionListener(colorSetter.apply(red));
            jPanel.add(red);

            JButton blue = new JButton("      ");
            blue.setBackground(Color.BLUE);
            blue.addActionListener(colorSetter.apply(blue));
            jPanel.add(blue);

            JButton yellow = new JButton("      ");
            yellow.setBackground(Color.YELLOW);
            yellow.addActionListener(colorSetter.apply(yellow));
            jPanel.add(yellow);

            jFrame.pack();
        });
    }
}