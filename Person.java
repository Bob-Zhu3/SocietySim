import java.awt.Color;
import java.awt.*;
import java.util.Random;
import java.lang.Math;

// Improvements:
// 1. Make probability of net worth being cut in half due to unlucky events depend on competence.
// 2. Make wealthy people more likely to encounter lucky events and poor people more likely to encounter unlucky events.

public class Person
{
    Society gp;
    Random rand = new Random();
    double competence;
    double netWorth;
    int x, y;
    double radius;
    double trueRadius = 8;
    int lucky, unlucky;
    public Person(Society gp, double competence, double netWorth, int x, int y, int r, int lucky, int unlucky)
    {
        this.gp = gp;
        this.competence = competence;
        this.netWorth = netWorth;
        this.x = x;
        this.y = y;
        this.radius = r;
        this.lucky = lucky;
        this.unlucky = unlucky;
    }

    public void update()
    {
        for (Event e : gp.events)
        {
            if (this.intersects(e))
            {
                if (e.getType() == 1)
                {
                    // netWorth = Math.round((netWorth + netWorth * competence) * 100.0) / 100.0D;
                    if (rand.nextDouble() < competence)
                    {
                        netWorth = Math.round(netWorth * 2 * 100.0) / 100.0D;
                        radius *= 1.414;
                    }
                    lucky++;
                }
                else
                {
                    // if (rand.nextDouble() > competence)
                    // {
                        netWorth = Math.round(netWorth / 2 * 100.0) / 100.0D;
                        if (radius > 2)
                            radius *= 0.707;
                    // }
                    unlucky++;
                }
            }
        }
    }
    
    // public void update()
    // {
    //     for (Event e : gp.events)
    //     {
    //         if (this.intersects(e))
    //         {
    //             if (rand.nextDouble() < has more money than x% of population)
    //             {
    //                 e.setType(1);
    //                 // netWorth = Math.round((netWorth + netWorth * competence) * 100.0) / 100.0D;
    //                 if (rand.nextDouble() < competence)
    //                 {
    //                     netWorth = Math.round(netWorth * 2 * 100.0) / 100.0D;
    //                     radius *= 1.414;
    //                 }
    //                 lucky++;
    //             }
    //             else
    //             {
    //                 e.setType(2);
    //                 if (rand.nextDouble() > competence)
    //                 {
    //                     netWorth = Math.round(netWorth / 2 * 100.0) / 100.0D;
    //                     if (radius > 2)
    //                         radius *= 0.707;
    //                 }
    //                 unlucky++;
    //             }
    //         }
    //     }
    // }

    public boolean intersects(Event event)
    {
        double distanceX = x - event.x;
        double distanceY = y - event.y;
        double trueRadiusSum = trueRadius + event.radius;
        return distanceX * distanceX + distanceY * distanceY <= trueRadiusSum * trueRadiusSum;
    }

    public boolean mouseIntersects()
    {
        double distanceX = x - gp.mouseH.mouseX;
        double distanceY = y - gp.mouseH.mouseY;
        return distanceX * distanceX + distanceY * distanceY <= (trueRadius) * (trueRadius);
    }

    public void draw(Graphics2D g2)
    {
        if (mouseIntersects())
            g2.setColor(new Color(74,186,255));
        else
            g2.setColor(new Color(135,206,250));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(4));
        g2.drawOval((int)(x - radius), (int)(y - radius), (int)(2 * radius), (int)(2 * radius));
    }

    public void drawStats(Graphics2D g2)
    {
        if (mouseIntersects())
        {
            g2.setColor(Color.darkGray);
            g2.fillRect(x - 39, y - 69, 167, 60);
            g2.setColor(new Color(238, 238, 238));
            g2.fillRect(x - 40, y - 70, 167, 60);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setColor(Color.black);
            g2.setFont(new Font("Segoe UI Historic", Font.PLAIN, 20));
            g2.drawString("Competence: " + competence, x - 35, y - 45);
            g2.drawString("Net Worth: " + netWorth, x - 35, y - 20);
        }
    }

    public double getNetWorth()
    {
        return netWorth;
    }
}