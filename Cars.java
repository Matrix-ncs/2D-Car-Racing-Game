
package cars;


import javax.swing.JFrame;


public class Cars extends JFrame{


    public static void main(String[] args) {
        JFrame app=new JFrame();
        work e=new work();
        app.add(e);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setSize(500, 740);
        app.setVisible(true);
    }
    
}
