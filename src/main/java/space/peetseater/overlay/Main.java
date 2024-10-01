package space.peetseater.overlay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

            ActionListener setAnnotationLineColorCommand = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() instanceof JComponent jComponent) {
                        annotatablePane.setAnnotationColor(jComponent.getBackground());
                    }
                }
            };

            // TODO swap to a loop like we did on stream
            JButton red = new JButton("      ");
            red.setBackground(Color.RED);
            red.addActionListener(setAnnotationLineColorCommand);
            red.setMnemonic('r');
            jPanel.add(red);

            JButton cyan = new JButton("      ");
            cyan.setBackground(Color.CYAN);
            cyan.addActionListener(setAnnotationLineColorCommand);
            cyan.setMnemonic('b');
            jPanel.add(cyan);

            JButton yellow = new JButton("      ");
            yellow.setBackground(Color.YELLOW);
            yellow.addActionListener(setAnnotationLineColorCommand);
            yellow.setMnemonic('y');
            jPanel.add(yellow);

            JButton decreaseLineSize = new JButton("-");
            decreaseLineSize.addActionListener((event) -> {
                annotatablePane.decreaseLineSize();
            });
            decreaseLineSize.setMnemonic(KeyEvent.getExtendedKeyCodeForChar('-'));
            jPanel.add(decreaseLineSize);

            JButton increaseLineSize = new JButton("+");
            increaseLineSize.addActionListener((event) -> {
                annotatablePane.increaseLineSize();
            });
            // = to avoid pressing shift to get to +
            increaseLineSize.setMnemonic(KeyEvent.getExtendedKeyCodeForChar('='));
            jPanel.add(increaseLineSize);


            jFrame.pack();
        });
    }
}