package com.example.sloter.coins;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Sloter on 22/01/2016.
 */
public class Moneda
{

    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right

    private int ySpeed;
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 7;
    private int x = 0;
    private int y = 0;
    private int xSpeed = 5;
    private VistaJuego vistaJuego;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private boolean primera=true;
    public Moneda(VistaJuego vistaJuego, Bitmap bmp) {
        this.vistaJuego = vistaJuego;



        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;



    }

    private void colocaMoneda() {
        Random posx= new Random();
        //x=posx.nextInt((vistaJuego.getWidth()*2)-bmp.getWidth());
        x=(int)(Math.random()*((vistaJuego.getWidth()*1.5)-bmp.getWidth()));
        Random posy= new Random();
        y=posy.nextInt(vistaJuego.getHeight()-bmp.getHeight());
    }

    private void update() {
        if(primera)
        {
            colocaMoneda();
            primera=false;
        }
        currentFrame = ++currentFrame % BMP_COLUMNS;

    }
    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = 3 * height;
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
