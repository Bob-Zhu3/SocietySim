import java.awt.Color;
import java.awt.*;
import java.util.Random;

public class Event
{
    Society gp;
    Random rand = new Random();
    int x, y;
    int radius = 5;
    int type;
    public Event(Society gp, int type, int x, int y)
    {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int t)
    {
        type = t;
    }

    public void update()
    {
        x = rand.nextInt(729) + 31;
        y = rand.nextInt(729) + 31;
    }

    public void draw(Graphics2D g2)
    {
        if (type == 1)
            g2.setColor(new Color(6, 16, 124, 200));
        else if (type == 2)
            g2.setColor(new Color(212, 0, 18, 200));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillOval((int)(x - radius), (int)(y - radius), (int)(2 * radius), (int)(2 * radius));
    }
}