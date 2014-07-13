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
import java.util.*;

/**
 * 
 * @author SANJAY
 * 
 */
public class Ball {
    private int x_pos;
    private int y_pos;
    private int x_speed;
    private int y_speed;
    private int radius;

    private int first_x;
    private int first_y;

    private int maxSpeed;

    private final int X_LEFTOUT = 10;
    private final int X_RIGHTOUT = 370;
    private final int Y_UPOUT = 45;
    private final int Y_DOWNOUT = 370;

    Color color;

    AudioClip out;

    Player player;

    Random rnd = new Random();

    public Ball(int radius, int x, int y, int x_speed, int y_speed, int maxspeed, Color color, AudioClip out,
            Player player) {

        this.radius = radius;

        this.x_pos = x;
        this.y_pos = y;

        this.first_x = x;
        this.first_y = y;

        this.x_speed = x_speed;
        this.y_speed = y_speed;

        this.maxSpeed = maxspeed;

        this.color = color;

        this.out = out;

        this.player = player;
    }

    public void move() {
        x_pos += x_speed;
        y_pos += y_speed;
        isOut();
    }

    public void ballWasHit() {
        x_pos = first_x;
        y_pos = first_y;

        x_speed = (rnd.nextInt()) % maxSpeed;
    }

    public boolean userHit(int mainUserHit_x, int mainUserHit_y) {
        double x = mainUserHit_x - x_pos;
        double y = mainUserHit_y - y_pos;

        double distance = Math.sqrt((x * x) + (y * y));

        if (distance < (radius + 5)) {
            player.addScore(10 * Math.abs(x_speed) + 10);
            return true;
        } else
            return false;
    }

    public boolean isOut() {
        if (x_pos < X_LEFTOUT || x_pos > X_RIGHTOUT || y_pos < Y_UPOUT || y_pos > Y_DOWNOUT) {
            x_pos = first_x;
            y_pos = first_y;

            out.play();

            x_speed = (rnd.nextInt()) % maxSpeed;

            player.looseLife();

            return true;
        } else
            return false;
    }

    public void drawBall(Graphics g) {
        g.setColor(color);
        g.fillOval(x_pos - radius, y_pos - radius, 2 * radius, 2 * radius);
    }
}
