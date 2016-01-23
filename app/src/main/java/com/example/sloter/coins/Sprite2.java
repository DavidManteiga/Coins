package com.example.sloter.coins;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Sloter on 22/01/2016.
 */
public class Sprite2 {
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private int x = 0;
    private int y ;
    private double recorreY = 0;
    private double recorreX = 0;
    int disX;
    int disY;
    private int x2 = 0;
    private int y2 = 0;
    private int xSpeed = 5;
    private int ySpeed;
    private VistaJuego vistaJuego;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int direction = 2;
    private boolean primera=true;
    private int veldiv=20;
    public Sprite2(VistaJuego vistaJuego, Bitmap bmp) {
        this.vistaJuego = vistaJuego;

        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;

    }
    private void update()
    {

        if(primera)
        {
            this.x=(vistaJuego.getWidth()/2)-(this.getWidth()/2);

            this.y=(vistaJuego.getHeight()/2)-(this.getHeight()/2);
            primera=false;
        }

        int distX;
        int distY;

        if(x<(int)x2)
        {
            distX=x2-x;
            if(distX>this.getWidth()/10)
                x+=recorreX;
            else
                x=x2;

        }
        if(x>(int)x2)
        {
            distX=x-x2;
            if(distX>this.getWidth()/10)
                x-=recorreX;
            else
                x=x2;
        }
        if(y<(int)y2)
        {
            distY=y2-y;
            if(distY>this.getHeight()/10)
                y+=recorreY;
            else
                y=y2;
        }
        if(y>(int)y2)
        {
            distY=y-y2;
            if(distY>this.getHeight()/10)
                y-=recorreY;
            else
                y=y2;
        }


        currentFrame = ++currentFrame % BMP_COLUMNS;

        //Log.e("Sprite","x:"+this.getX()+" y:"+this.getY()+"/////x2:"+x2+"y2:"+y2);
        //Log.e("vistajuego","width:"+vistaJuego.getWidth()+" heigth:"+vistaJuego.getHeight());

    }
    public void onDraw(Canvas canvas) {

        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);


    }

    public void obtenerCoor(float x3, float y3)
    {




        x2=(int)x3;
        y2=(int)y3;

        disX=x2-x;
        disY=y2-y;
        if(x2<x)
            disX=x-x2;
        if(y2<y)
            disY=y-y2;

        double vel=1;

        double h=Math.hypot(disX, disY);
        vel=h/veldiv;
        recorreX=disX/vel;
        recorreY= disY/vel;

    }
    public void posiciona(float x3, float y3)
    {
        x2=(int)x3;
        y2=(int)y3;
        x=x2;
        y=y2;
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVeldiv() {
        return veldiv;
    }

    public void setVeldiv(int veldiv) {
        this.veldiv = veldiv;
    }

    public Rect rectangulo()
    {
        return new Rect(this.x,this.y,x+this.width,y+this.height);
    }

    private int getAnimationRow() {





        if(((x<x2)&&(y<y2))||((x>x2)&&(y<y2)))
        {
            direction=2;
            if(x<x2)
            {
                if (y < y2)
                {
                    if(!((x2-x)<(y2-y)))
                        direction = 3;

                }
            }
            else
            {
                if (y < y2)
                {
                    if(!((x-x2)<(y2-y)))
                        direction = 1;


                }
            }
        }

        if(((x<x2)&&(y>y2))||((x>x2)&&(y>y2)))
        {
            direction=0;
            if(x>x2)
            {
                if (y > y2)
                {
                    if(!((x-x2)<(y-y2)))
                        direction = 1;

                }




            }
            else
            {
                if (y2 < y)
                {
                    if(!((x2-x)<(y-y2)))
                        direction = 3;


                }
            }
        }





        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

}

