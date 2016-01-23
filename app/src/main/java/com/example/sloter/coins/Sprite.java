package com.example.sloter.coins;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Sloter on 22/01/2016.
 */
public class Sprite {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private int pos = 0;
    private int x = 0;
    private int y = 0;
    private int xSpeed = 5;
    private VistaJuego vistaJuego;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int ySpeed;
    public Sprite(VistaJuego vistaJuego, Bitmap bmp) {
        this.vistaJuego = vistaJuego;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;

        Random rnd = new Random(System.currentTimeMillis());
        xSpeed = 10;
        ySpeed = rnd.nextInt(30) - 5;

        Random posx= new Random();
        x=posx.nextInt(vistaJuego.getWidth()-bmp.getWidth());
        Random posy= new Random();
        y=posy.nextInt(vistaJuego.getHeight()-bmp.getHeight());

    }
    private void update() {
        if (x >= vistaJuego.getWidth() - width - xSpeed || x + xSpeed <= 0) {
            xSpeed = -xSpeed;
        }
        x = x + xSpeed;
        if (y >= vistaJuego.getHeight() - height - ySpeed || y + ySpeed <= 0) {
            ySpeed = -ySpeed;
        }
        y = y + ySpeed;
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }
    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public boolean colision(Rect s1,Rect s2)
    {
        return Rect.intersects(s1,s2);
    }
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public Rect rectangulo()
    {
        return new Rect(this.x,this.y,x+this.width,y+this.height);
    }
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}