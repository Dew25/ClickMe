/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickme;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author jvm
 */
public class App extends JFrame{
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private long delay  = 1000L; 
    private long period = 1000L;
    private int winWidth = 800;
    private int winHeight = 600;
    private int clickMeWidth = 110;
    private int clickMeHeight = 47;
    private JButton clickMe;
    private int level = 1;
    private Random random = new Random();
    public App() {
        try {
            init();
        } catch (Exception e) {
            System.out.println("Ошибка! :)");
        }
    }
    private void init() throws InterruptedException{
        setPreferredSize(new Dimension(winWidth, winHeight));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        clickMe = new JButton("Нажми меня");
        clickMe.setPreferredSize(new Dimension(clickMeWidth,clickMeHeight));
        clickMe.setMinimumSize(clickMe.getPreferredSize());
        clickMe.setMaximumSize(clickMe.getPreferredSize());
        clickMe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level++;
                if(level == 1){
                    clickMe.setText("Ура! Попал!");
                }
                int xSenter = winWidth/2 - clickMeWidth/2; //800/2=400 110/2=55 ==== 435
                int ySenter = winHeight/2 - clickMeHeight/2;
                clickMe.setLocation(xSenter, ySenter);
                executor.shutdown();
            }
        });
        this.getContentPane().add(clickMe);
        
        executor.scheduleAtFixedRate(repeatedTask, 0L, period, TimeUnit.MILLISECONDS);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private TimerTask repeatedTask = new TimerTask() {
        public void run() {
            clickMe.setLocation(getClickMeX(), getClickMeY());
            System.out.println("width: "+ clickMe.getX()+" height: "+clickMe.getY());
        }
    };
    private int getClickMeX(){
        int x = random.nextInt(winWidth + 1);
        if(x > winWidth - clickMe.getWidth()){
            x=winWidth - clickMe.getWidth();
        }
        if(x < 0) x = 0;
        return x;
    }
    private int getClickMeY(){
        int y = random.nextInt(winHeight + 1) - 27;
        if(y > winHeight - clickMe.getHeight()){
            y = winHeight - clickMe.getHeight()*2;
        }
        if(y < 0) y = 27;
        return y;
    }
    
}
