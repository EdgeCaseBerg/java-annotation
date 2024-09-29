package space.peetseater.overlay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;

public class AnnotatablePane extends JPanel implements MouseMotionListener, MouseListener {

    private final LinkedList<AnnotationLine> lines;

    class AnnotationLine {
        private final Color color;
        private final LinkedList<Point> points;
        public AnnotationLine(Color color) {
            this.color = color;
            points =new LinkedList<>();
        }

        public void drawLine(Graphics2D graphics2D) {
            graphics2D.setColor(color);
            graphics2D.setStroke(new BasicStroke(5));
            Iterator<Point> iter = points.iterator();
            Point first = null;
            Point second = null;
            if (iter.hasNext()) {
                first = iter.next();
            }
            while(iter.hasNext()) {
                second = iter.next();
                if (second != null) {
                    graphics2D.drawLine(first.x, first.y, second.x, second.y);
                }
                first = second;
            }
        }

        public void clear() {
            points.clear();
        }

        public void add(Point point) {
            points.add(point);
        }

        @Override
        public String toString() {
            return "C:%s, P size:%d".formatted(color, points.size());
        }
    }
    boolean shouldClear = false;
    private boolean shouldAdd;
    private Color annotationColor;

    public AnnotatablePane() {
        lines = new LinkedList<AnnotationLine>();
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
            for (AnnotationLine line : lines) {
                line.clear();
            }
        }

        for (AnnotationLine line : lines) {
            line.drawLine(graphics2D);
        }
        graphics2D.setColor(getBackground());
        graphics2D.dispose();
    }

    public void addPointToDraw(Point point) {
        lines.getLast().add(point);
        repaint();
    }

    private void newLine() {
        lines.add(new AnnotationLine(new Color(annotationColor.getRGB())));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        addPointToDraw(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (shouldAdd && e.getButton() == MouseEvent.BUTTON1) {
            addPointToDraw(e.getPoint());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // dont use this. mousePressed is going to fire first and mouse click is not fired during mouse drag
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            shouldAdd = true;
            newLine();
            addPointToDraw(e.getPoint());
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            shouldClear = true;
            repaint();
            shouldAdd = false;
        }
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

    public void setAnnotationColor(Color color) {
        this.annotationColor = color;
    }
}
