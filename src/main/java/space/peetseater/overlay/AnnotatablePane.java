package space.peetseater.overlay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

public class AnnotatablePane extends JPanel implements MouseMotionListener, MouseListener {

    private final LinkedList<AnnotationLine> lines;
    private Color nextLineColor;
    private boolean clearFlag;
    private boolean startNewLine;
    private int lineSize;

    public AnnotatablePane() {
        lines = new LinkedList<>();
        lineSize = 5;
        addMouseMotionListener(this);
        addMouseListener(this);
        setOpaque(false);
        clearFlag = false;
        nextLineColor = Color.RED;
        startNewLine = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g.create();

        if (clearFlag) {
            clearFlag = false;
            lines.clear();
        }

        for (AnnotationLine line : lines) {
            line.drawLine(graphics2D);
        }

        graphics2D.setColor(getBackground());
        graphics2D.dispose();
    }

    private void newLine() {
        lines.add(new AnnotationLine(nextLineColor, lineSize));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (lines.isEmpty()) {
            newLine();
        }
        if (startNewLine) {
            startNewLine = false;
            newLine();
        }
        lines.getLast().addPoint(e.getPoint());
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void setClearFlag(boolean flag) {
        this.clearFlag = flag;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (MouseEvent.BUTTON3 == e.getButton()) {
            this.clearFlag = true;
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        startNewLine = true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void increaseLineSize() {
        lineSize++;
    }

    public void decreaseLineSize() {
        lineSize = Math.max(0, lineSize - 1);
    }

    public void setAnnotationColor(Color color) {
        this.nextLineColor = color;
    }
}
