package space.peetseater.overlay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;

public class AnnotatablePane extends JPanel implements MouseMotionListener, MouseListener {


    private LinkedList<Point> points;
    boolean shouldClear = false;
    private boolean shouldAdd;

    public AnnotatablePane() {
        points = new LinkedList<Point>();
        addMouseListener(this);
        addMouseMotionListener(this);
        shouldAdd = false;
        setOpaque(false); //important or else panel gains color over time
    }

    public void setClearFlag(boolean flag) {
        this.shouldClear = flag;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g.create();
        if (shouldClear) {
            shouldClear = false;
            points.clear();
        }
        Iterator<Point> iter = points.iterator();
        Point first = null;
        Point second = null;
        if (iter.hasNext()) {
            first = iter.next();
        }
        while(iter.hasNext()) {
            second = iter.next();
            if (second != null) {
                graphics2D.setColor(Color.RED);
                graphics2D.setStroke(new BasicStroke(5));
                graphics2D.drawLine(first.x, first.y, second.x, second.y);
            }
            first = second;
        }
        graphics2D.setColor(getBackground());
        graphics2D.dispose();
    }

    public void addPointToDraw(Point point) {
        points.add(point);
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        addPointToDraw(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (shouldAdd) {
            addPointToDraw(e.getPoint());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            addPointToDraw(e.getPoint());
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            shouldClear = true;
            repaint();
        }
        shouldAdd = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        shouldAdd = false;
        if (e.getButton() == MouseEvent.BUTTON3) {
            shouldClear = true;
            repaint();
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        shouldAdd = false;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        shouldAdd = false;
    }
}
