import java.awt.*;
import javax.swing.*;
import java.awt.geom.Line2D;

class MyCanvas extends JComponent{
    public void paint(Graphics g){
        g.drawLine(0,1,2,5);
        g.drawLine(0,1,6,3);
        g.drawLine(2,5,6,3);
    }
}

public class grafico{
    public static void main(String[] args) {
        JFrame window = new JFrame("Poligno");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0,0,200,200);
        window.setSize(200,200);
        window.setLocation(-15,15);
        window.getContentPane().add(new MyCanvas());
        window.setVisible(true);
    }
}
