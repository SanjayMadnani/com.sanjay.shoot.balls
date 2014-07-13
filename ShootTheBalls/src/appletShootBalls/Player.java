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

/**
 * 
 * @author SANJAY
 * 
 */
public class Player {
    private int score;
    private int lives;

    public Player() {
        lives = 10;
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getLives() {
        return lives;
    }

    public void looseLife() {
        lives--;
    }

}
