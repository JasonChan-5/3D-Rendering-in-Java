import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.Math;
import java.util.HashSet;
import java.util.Iterator;

public class donut{

    private static HashSet<int[]> coords = new HashSet<>();
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
        float R1 = 80; //circle radius
        float R2 = 125; //x offset    
        double A = 0;
        double B = 0;
        mThread thread1 = new mThread();
        mThread thread2 = new mThread();
        mThread thread3 = new mThread();
        mThread thread4 = new mThread();
        while (true){
            thread1.r(A, B, R1, R2);
            A += 1;
            B += 1;
            System.out.println("thread 1");
            thread2.d(d);
            System.out.println("thread 2");
            thread3.r(A, B, R1, R2);
            A += 1;
            B += 1;
            System.out.println("thread 3");
            thread4.d(d);
            System.out.println("thread 4");
            // thread3.run(A, B, d, R1, R2);
            // A += 1;
            // B += 1;
            // System.out.println("thread 3");
            // thread4.run(A, B, d, R1, R2);
            // A += 1;
            // B += 1;
            // System.out.println("thread 4");
        }
    }

    private static class WindowClosingListener extends WindowAdapter {
		
        public void windowClosing(WindowEvent e) {
			System.exit(0);
		}

	}

    public static class drawDonut extends JComponent{}

    public static void render(double A, double B, float R1, float R2){
        //rotation math starts here
        for (double i = 0; i < 361; i += 5){ //2d
            for (double p = 0; p < 361; p += 5){ //3d
                int x = Math.round((R2 + R1 * cos(i)) * (cos(B) * cos(p) + sin(A) * sin(B) * sin(p)) - R1 * cos(A) * sin(B) * sin(i));
                int y = Math.round((R2 + R1 * cos(i)) * (cos(p) * sin(B) - cos(B) * sin(A) * sin(p)) + R1 * cos(A) * cos(B) * sin(i));
                int[] a = {x, y};
                coords.add(a);
            }
        }
        try{
            Thread.sleep(95);
        }
        catch(Exception e){

        }
    }

    public static void draw(drawDonut d){
        Graphics2D g = (Graphics2D) d.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 500, 500);
        g.setColor(Color.WHITE);
        g.translate(250, 250);
        Iterator<int[]> i = coords.iterator();
        while (i.hasNext()){
            int[] a = i.next();
            g.drawString(".", a[0], a[1]);
        }
        coords.clear();
    }

    public static float cos(double x){
        return (float) Math.cos(Math.toRadians(x));
    }

    public static float sin(double x){
        return (float) Math.sin(Math.toRadians(x));
    }

}

class mThread extends Thread{
    
    public void run(double A, double B, donut.drawDonut d, float R1, float R2){
        try{
            System.out.println("Running");
        }
        catch (Exception e){

        }
    }

    public void r(double A, double B, float R1, float R2){
        donut.render(A, B, R1, R2);
    }

    public void d(donut.drawDonut d){
        donut.draw(d);
    }
}