package space.peetseater.overlay;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

class AnnotationLine {
    private final LinkedList<Point> points;
    private final Color color;
    private final float lineSize;

    public AnnotationLine(Color color, float lineSize) {
        this.points = new LinkedList<>();
        this.color = color;
        this.lineSize = lineSize;
    }

    public void drawLine(Graphics2D graphics2D) {
        Iterator<Point> iter = points.iterator();
        Point first = null;
        Point second;
        if (iter.hasNext()) {
            first = iter.next();
        }
        while (iter.hasNext()) {
            second = iter.next();
            graphics2D.setColor(color);
            graphics2D.setStroke(new BasicStroke(lineSize));
            graphics2D.drawLine(first.x, first.y, second.x, second.y);
            first = second;
        }
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void clear() {
        points.clear();
    }
}
