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
public class ClickMe extends JFrame{
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private long delay  = 0; 
    private long period = 2000L;
    private int winWidth = 800;
    private int winHeight = 600;
    private int buttonClickMeWidth = 110;
    private int buttonClickMeHeight = 47;
    private JButton clickMe;
    private int level = 1;
    private String title;
    private Random random = new Random();
    public ClickMe() {
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
        title = "Уровень "+level+ ". Подготовительный.";
        setTitle(title);
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        clickMe = new JButton("Нажми меня");
        clickMe.setPreferredSize(new Dimension(buttonClickMeWidth,buttonClickMeHeight));
        clickMe.setMinimumSize(clickMe.getPreferredSize());
        clickMe.setMaximumSize(clickMe.getPreferredSize());
        clickMe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCleckMeTitle("А еще раз?");
                level++;
                System.out.println("Levet = "+level);
                period -=80;
                System.out.println("period = "+period);
                switch (level) {
                    case 2:
                        setNewTitle("Уровень "+level+ ". Ускоренный.");
                        break;
                    case 3:
                        setNewTitle("Уровень "+level+ ". Игровой.");
                        break;
                    case 4:
                        setNewTitle("Уровень "+level+ ". Мастерский.");
                        break;
                    default:
                        setNewTitle("Уровень "+level+ ". Да ты просто виртуоз!.");
                        setCleckMeTitle("Mолодец!");
                        stop();
                        return;
                }
                int xSenter = winWidth/2 - buttonClickMeWidth/2; //800/2=400 110/2=55 ==== 435
                int ySenter = winHeight/2 - buttonClickMeHeight/2;
                clickMe.setLocation(xSenter, ySenter);
                start(period);
                System.out.println("");
            }
        });
        this.getContentPane().add(clickMe);
        start(period);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void setCleckMeTitle(String newTitle){
        clickMe.setText(newTitle);
    }
    private void start(long period){
        executor.scheduleAtFixedRate(repeatedTask, delay, period, TimeUnit.MILLISECONDS);
    }
    private void stop(){
        executor.shutdown();
    }
    private TimerTask repeatedTask = new TimerTask() {
        @Override
        public void run() {
            clickMe.setLocation(getClickMeX(), getClickMeY());
//            System.out.println("width: "+ clickMe.getX()+" height: "+clickMe.getY());
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
    private  void setNewTitle(String text){
        this.setTitle(text);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClickMe().setVisible(true);
            }
        });
    }
}
