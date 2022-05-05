import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.Math;
import java.util.HashMap;

public class donut{

    public static void main(String args[]){
        JFrame f = new JFrame("donut.java");
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.addWindowListener(new WindowClosingListener());
        f.setPreferredSize(new Dimension(500, 500));
        f.setMinimumSize(new Dimension(500, 500));
        f.setMaximumSize(new Dimension(500, 500));
        drawDonut d = new drawDonut();
        d.setDoubleBuffered(false);
        f.add(d);
        f.setVisible(true);
        //donut variables
        float R1 = 35; //circle radius
        float R2 = 125; //x offset
        double A = 0;
        double B = 0;
        while (true){
            render(A, B, d, R1, R2);
            f.setVisible(true);
            // try {
            //     Thread.sleep(3000);
            // }
            // catch(Exception e){}
            A += 1;
            B += 1;
        }
    }

    private static class WindowClosingListener extends WindowAdapter {
		
        public void windowClosing(WindowEvent e) {
			System.exit(0);
		}

	}

    public static class drawDonut extends JComponent{}

    public static void render(double A, double B, drawDonut d, float R1, float R2){
        Graphics2D g = (Graphics2D) d.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 500, 500);
        g.setColor(Color.WHITE);
        g.translate(250, 250);
        //rotation math starts here
        HashMap<Integer[], Integer> torus = new HashMap<>();
        for (double i = 0; i < 361; i += 2){ //2d
            for (double p = 0; p < 361; p += 2){ //3d
                Integer[] coords = 
                    {Math.round((R2 + R1 * cos(i)) * (cos(B) * cos(p) + sin(A) * sin(B) * sin(p)) - R1 * cos(A) * sin(B) * sin(i)),
                    Math.round((R2 + R1 * cos(i)) * (cos(p) * sin(B) - cos(B) * sin(A) * sin(p)) + R1 * cos(A) * cos(B) * sin(i))};
                int z = Math.round(cos(A) * (R2 + R1 * cos(i)) * sin(p) + R1 * sin(A) * sin(i));
                if (torus.get(coords) != null){
                    if (torus.get(coords) < z){
                        torus.put(coords, z);
                    } 
                }
                else{
                    torus.put(coords, z);
                }
            }
        }
        //
        for (Integer[] x: torus.keySet()){
            g.drawString("@", x[0], x[1]);
        }
        d.update(g);
    }

    public static float cos(double x){
        return (float) Math.cos(Math.toRadians(x));
    }

    public static float sin(double x){
        return (float) Math.sin(Math.toRadians(x));
    }

}
//p = phi
// (R2 + R1 * cos(i)) * (cos(B) * cos(p) + sin(A) * sin(B) * sin(p)) - R1 * cos(A) * sin(B) * sin(i) x
// (R2 + R1 * cos(i)) * (cos(p) * sin(B) - cos(B) * sin(A) * sin(p)) + R1 * cos(A) * cos(B) * sin(i) y
// cos(A) * (R2 + R1 * cos(i)) * sin(phi) + R1 * sin(A) * sin(i) z