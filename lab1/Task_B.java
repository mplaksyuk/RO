import javax.swing.*;
import java.awt.event.ActionEvent;


class Thread_ extends Thread {
    private int shift;
    private JSlider slider;

    public Thread_(int shift, JSlider slider, int priority) {
        this.shift = shift;
        this.slider = slider;
        setPriority(priority);
    }

    public void run() {
        while(!interrupted()) {
            try {
                Thread.sleep(10);
            }
            catch(Exception e) {
                Thread.currentThread().interrupt();
                System.out.println(e);
            }

            int position = slider.getValue() + shift;

            if (position < 10 )
                position = 10;
            else if (position > 90)
                position = 90;

            slider.setValue(position);
        }
    }

}


class Task_B {

    private static Thread t1, t2;
    public static boolean simafon = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);

        JSlider slider = new JSlider( 0 ,100);

        JLabel label = new JLabel("", SwingConstants.CENTER);

        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel panel_1 = new JPanel();
        JButton start1 = new JButton("Start1");
        JButton stop1 = new JButton("Stop1");
        stop1.setEnabled(false);
        
        panel_1.add(start1);
        panel_1.add(stop1);

        JPanel panel_2 = new JPanel();
        JButton start2 = new JButton("Start2");
        JButton stop2 = new JButton("Stop2");
        stop2.setEnabled(false);

        panel_2.add(start2);
        panel_2.add(stop2);

        Thread_ th1 = new Thread_(1, slider, 1);
        Thread_ th2 = new Thread_(-1, slider, 10);

        
        start1.addActionListener((ActionEvent e) -> {
            if(!simafon) {
                t1 = new Thread(th1);
                t1.start();
                start1.setEnabled(false);
                stop1.setEnabled(true);
                simafon = true;
            }
            else {
                label.setText("Thread Busy"); 
            }
        });

        start2.addActionListener((ActionEvent e) -> {
            if(!simafon) {
                t2 = new Thread(th2);
                t2.start();
                start2.setEnabled(false);
                stop2.setEnabled(true);
                simafon = true;
            }
            else {
               label.setText("Thread Busy"); 
            }
        });

        stop1.addActionListener((ActionEvent e) -> {
            start1.setEnabled(true);
            stop1.setEnabled(false);
            t1.interrupt();
            simafon = false;
            label.setText(""); 
        });

        stop2.addActionListener((ActionEvent e) -> {
            start2.setEnabled(true);
            stop2.setEnabled(false);
            t2.interrupt();
            simafon = false;
            label.setText(""); 
        });
        
        
        panel.add(slider);
        panel.add(label);
        panel.add(panel_1);
        panel.add(panel_2);
        
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
