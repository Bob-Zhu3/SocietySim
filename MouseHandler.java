import java.awt.event.*;
public class MouseHandler implements MouseListener, MouseMotionListener
{
    public int mouseX = -1;
    public int mouseY = -1;
    public int mouseB = -1;
    public boolean mouseDown = false;

    public MouseHandler() {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e)
    {
        mouseDown = true;
        mouseB = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        mouseDown = false;
        mouseB = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}