import javax.swing.*;
import java.awt.event.ActionEvent;


class Thread_ extends Thread {
    private int priority = 1;
    private int shift;
    private JSlider slider;
    private JSpinner spinner;

    public Thread_(int shift, JSlider slider, JSpinner spinner) {
        this.shift = shift;
        this.slider = slider;
        this.spinner = spinner;
    }

    public void run() {
        while(!Thread.interrupted()) {
            try {
                Thread.sleep(10);
            }
            catch(Exception e) {
                System.out.println(e);
            }

            priority = (int)spinner.getValue();

            setPriority(priority);

            int position = slider.getValue() + shift;

            if (position < 10 )
                position = 10;
            else if (position > 90)
                position = 10;

            slider.setValue(position);
        }
    }

}


 class Task_A {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        
        JPanel panel = new JPanel();

        JButton btn = new JButton("Ok");

        JSlider slider = new JSlider( 0 ,100);

        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        int current = 1;
        int min = 1;
        int max = 9;
        int step = 1;

        SpinnerNumberModel sm1 = new SpinnerNumberModel(current, min, max, step);
        SpinnerNumberModel sm2 = new SpinnerNumberModel(current, min, max, step);

        JSpinner spinner1 = new JSpinner(sm1);
        JSpinner spinner2 = new JSpinner(sm2);

        spinner1.setValue(1);
        spinner2.setValue(1);

        Thread_ th1 = new Thread_(1, slider, spinner1);
        Thread_ th2 = new Thread_(-1, slider, spinner2);

        Thread t1 = new Thread(th1);
        Thread t2 = new Thread(th2);

        btn.addActionListener((ActionEvent e) -> {
                    synchronized (slider) {

                       t1.start();
                       t2.start();
                    }
                });

        panel.add(btn);

        panel.add(slider);
        
        panel.add(spinner1);
        panel.add(spinner2);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}