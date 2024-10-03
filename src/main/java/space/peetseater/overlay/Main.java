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
            bindKey(jPanel, "CLOSE", close, KeyEvent.VK_ESCAPE);
            bindKey(jPanel, "CLOSE", close, KeyEvent.VK_X);

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
            bindKey(jPanel, "CLEAR", clear, KeyEvent.VK_C);

            ActionListener setAnnotationLineColorCommand = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() instanceof JComponent jComponent) {
                        annotatablePane.setAnnotationColor(jComponent.getBackground());
                    }
                }
            };

            // TODO swap to a loop like we did on stream
            JButton red = new JButton("   r   ");
            red.setBackground(Color.RED);
            red.addActionListener(setAnnotationLineColorCommand);
            red.setMnemonic('r');
            jPanel.add(red);
            bindKey(jPanel, "SET_ANNOTATION_COLOR_RED", red, KeyEvent.VK_R);

            JButton cyan = new JButton("   b   ");
            cyan.setBackground(Color.CYAN);
            cyan.addActionListener(setAnnotationLineColorCommand);
            cyan.setMnemonic('b');
            jPanel.add(cyan);
            bindKey(jPanel, "SET_ANNOTATION_COLOR_BLUE", cyan, KeyEvent.VK_B);

            JButton yellow = new JButton("   y   ");
            yellow.setBackground(Color.YELLOW);
            yellow.addActionListener(setAnnotationLineColorCommand);
            yellow.setMnemonic('y');
            jPanel.add(yellow);
            bindKey(jPanel, "SET_ANNOTATION_COLOR_YELLOW", yellow, KeyEvent.VK_Y);

            JButton decreaseLineSize = new JButton("-");
            decreaseLineSize.addActionListener((event) -> {
                annotatablePane.decreaseLineSize();
            });
            decreaseLineSize.setMnemonic(KeyEvent.getExtendedKeyCodeForChar('-'));
            jPanel.add(decreaseLineSize);
            bindKey(jPanel, "DECREASE_LINE_SIZE", decreaseLineSize, KeyEvent.VK_MINUS);

            JButton increaseLineSize = new JButton("+");
            increaseLineSize.addActionListener((event) -> {
                annotatablePane.increaseLineSize();
            });
            // = to avoid pressing shift to get to +
            increaseLineSize.setMnemonic(KeyEvent.getExtendedKeyCodeForChar('='));
            jPanel.add(increaseLineSize);
            bindKey(jPanel, "INCREASE_LINE_SIZE", increaseLineSize, KeyEvent.VK_EQUALS);

            jFrame.pack();
        });
    }

    private static void bindKey(JPanel jPanel, String bindingKey, JButton jButton, int keyEvent) {
        jPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyEvent, 0), bindingKey);
        jPanel.getActionMap().put(bindingKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton.doClick();
            }
        });
    }
}