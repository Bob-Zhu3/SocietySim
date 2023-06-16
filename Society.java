import java.awt.Color;
import java.awt.Dimension;
import java.awt.*;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Society extends JPanel implements Runnable
{
    final int originalTileSize = 16;
    final int scale = 3;
    
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 17;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    int population = 500;
    int eventNum = 100;
    // double sum = 0;
    // double mean;
    // double variance = 0;
    // double sd = 0;

    int FPS = 8;
    int counter = 0;
    boolean running = false;
    int ticks = 40;

    MouseHandler mouseH = new MouseHandler();
    Thread gameThread;
    Random rand = new Random();
    ArrayList<Person> people = new ArrayList<Person>();
    ArrayList<Event> events = new ArrayList<Event>();
    Font arial_30 = new Font("Agency FB", Font.BOLD, 30);

    public Society()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);

        for (int i = 0; i < population; i++)
            people.add(new Person(this, Math.round((rand.nextGaussian() * 0.1 + 0.5) * 100.0) / 100.0D, 10, rand.nextInt(729) + 31, rand.nextInt(729) + 31, 8, 0, 0));
        for (int i = 0; i < eventNum; i++)
            events.add(new Event(this, 1, rand.nextInt(729) + 31, rand.nextInt(729) + 31));
        for (int i = 0; i < eventNum; i++)
            events.add(new Event(this, 2, rand.nextInt(729) + 31, rand.nextInt(729) + 31));
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run()
    {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null)
        {
            repaint();
            if (mouseHover() && mouseH.mouseB == 1)
                running = true;
            if (running)
            {
                counter++;
                update(counter == 1);
                if (counter == ticks)
                {
                    counter = 0;
                    running = false;
                }
            }
            try
            {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if (remainingTime < 0)
                    remainingTime = 0;

                Thread.sleep((long)remainingTime);

                nextDrawTime += drawInterval;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            if (counter == ticks - 1)
                exportData();
        }
    }

    public void update(boolean newRun)
    {
        for (Person p : people)
        {
            if (newRun)
            {
                p.competence = Math.round((rand.nextGaussian() * 0.1 + 0.5) * 100.0) / 100.0D;
                p.netWorth = 100;
                p.x = rand.nextInt(729) + 31;
                p.y = rand.nextInt(729) + 31;
                p.radius = 8;
            }
            p.update();
        }        
        for (Event e : events)
            e.update();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawRect(20, 20, 750, 750);
        if (mouseHover())
            g2.setColor(new Color(99, 157, 255));
        else
            g2.setColor(new Color(186, 207, 243));
        g2.fillRect(800, 200, 100, 40);
        g2.setFont(arial_30);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);
        g2.drawString("RUN", 830, 230);
        for (Person p : people)
            p.draw(g2);
        for (Event e : events)
            e.draw(g2);
        for (Person p : people)
            p.drawStats(g2);
        g2.dispose();
    }

    public boolean mouseHover()
    {
        return mouseH.mouseX >= 800 && mouseH.mouseX <= 900 && mouseH.mouseY >= 200 && mouseH.mouseY <= 240;
    }

    public void exportData()
    {
        Collections.sort(people, new NetWorthSorter());
        double sum = 0;
        double topSum = 0;
        int count = 0;
        for (Person p : people)
        {
            System.out.println(p.competence + "   " + p.netWorth);
            sum += p.netWorth;
            if (count < population / 5)
                topSum += p.netWorth;
            count++;
        }

        System.out.println();
        System.out.println("Total Net Worth: " + Math.round(sum * 100.0) / 100.0D);
        System.out.println("Top 20% Net Worth: " + Math.round(topSum * 100.0) / 100.0D);
        System.out.println("The richest 20% of people hold " + (Math.round((topSum / sum * 100.0) * 100.0) / 100.0D) + "% of all the money.");
        System.out.println();
    }
}