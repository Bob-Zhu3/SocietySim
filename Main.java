import javax.swing.JFrame;

public class Main {
    public static void main(String[] args)
    {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Society");

        Society society = new Society();

        window.add(society);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        society.startGameThread();
    }
}