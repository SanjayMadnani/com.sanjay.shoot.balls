/* Copyright (C) 2014, 2015 Sanjay Madnani
 * 
 * This file is free to use: you can redistribute it and/or modify it under the terms of the
 * GPL General Public License V2 as published by the Free Software Foundation, subject to the following conditions:
 * 
 * The above copyright notice should never be changed and should always included wherever this file is used.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.
 * See the GNU General Public License V2 for more details. */
package appletShootBalls;

import java.applet.*;
import java.awt.*;

/**
 * 
 * @author SANJAY
 * 
 */
public class Main extends Applet implements Runnable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int speed;

    boolean isStoped = true;

    private Player player;
    private Ball redball;
    private Ball blueball;

    Thread th;

    AudioClip shotnoise;
    AudioClip hitnoise;
    AudioClip outnoise;

    Font f = new Font("Arial", Font.BOLD, 20);

    Cursor c;

    private Image dbImage = null;
    private Graphics dbg = null;

    public void init() {
        c = new Cursor(Cursor.CROSSHAIR_CURSOR);
        this.setCursor(c);

        setBackground(Color.black);

        setFont(f);

        speed = 15;

        hitnoise = getAudioClip(getCodeBase(), "appletShootBalls/gun.au");
        hitnoise.play();
        hitnoise.stop();
        shotnoise = getAudioClip(getCodeBase(), "appletShootBalls/miss.au");
        shotnoise.play();
        shotnoise.stop();
        outnoise = getAudioClip(getCodeBase(), "appletShootBalls/error.au");
        outnoise.play();
        outnoise.stop();

        player = new Player();
        redball = new Ball(10, 190, 250, 1, -1, 4, Color.red, outnoise, player);
        blueball = new Ball(10, 190, 150, 1, 1, 3, Color.blue, outnoise, player);
    }

    public void start() {
        th = new Thread(this);
        th.start();
    }

    @SuppressWarnings ("deprecation")
    public void stop() {
        th.stop();

    }

    public boolean mouseDown(Event e, int x, int y) {
        if ( !isStoped) {
            if (redball.userHit(x, y)) {
                hitnoise.play();
                redball.ballWasHit();
            }
            if (blueball.userHit(x, y)) {
                hitnoise.play();
                blueball.ballWasHit();
            } else
                shotnoise.play();
        } else if (isStoped && e.clickCount == 2) {
            isStoped = false;
            init();
        }
        return true;
    }

    public void run() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        while (true) {
            if (player.getLives() >= 0 && !(isStoped)) {
                redball.move();
                blueball.move();
            }

            repaint();

            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                System.out.println("Exception Caught in run: " + e.getStackTrace());
            }

            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        }
    }

    public void paint(Graphics g) {
        if (player.getLives() >= 0) {
            g.setColor(Color.yellow);

            g.drawString("Score: " + player.getScore(), 10, 40);
            g.drawString("Lives: " + player.getLives(), 300, 40);

            redball.drawBall(g);
            blueball.drawBall(g);

            if (isStoped) {
                g.setColor(Color.yellow);
                g.drawString("Doubleclick on Applet to start Game!", 40, 200);
            }
        } else if (player.getLives() < 0) {
            g.setColor(Color.yellow);

            g.drawString("Game over!", 130, 100);
            g.drawString("You scored " + player.getScore() + " Points!", 90, 140);

            if (player.getScore() < 300)
                g.drawString("Well, it could be better!", 100, 190);
            else if (player.getScore() < 600 && player.getScore() >= 300)
                g.drawString("That was not so bad", 100, 190);
            else if (player.getScore() < 900 && player.getScore() >= 600)
                g.drawString("That was really good", 100, 190);
            else if (player.getScore() < 1200 && player.getScore() >= 900)
                g.drawString("You seem to be very good!", 90, 190);
            else if (player.getScore() < 1500 && player.getScore() >= 1200)
                g.drawString("That was nearly perfect!", 90, 190);
            else if (player.getScore() >= 1500)
                g.drawString("You are the Champingon!", 100, 190);

            g.drawString("Doubleclick on the Applet, to play again!", 20, 220);

            isStoped = true;
        }
    }

    public void update(Graphics g) {

        dbImage = createImage(this.getSize().width, this.getSize().height);
        dbg = dbImage.getGraphics();

        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        dbg.setColor(getForeground());
        paint(dbg);

        g.drawImage(dbImage, 0, 0, this);

    }
}
