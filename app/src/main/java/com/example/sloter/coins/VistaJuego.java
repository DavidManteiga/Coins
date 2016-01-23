package com.example.sloter.coins;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloter on 22/01/2016.
 */

public class VistaJuego extends SurfaceView {
    private List<Sprite> sprites = new ArrayList<Sprite>();

    private List<Integer> malos = new ArrayList<Integer>();

    private List<Alas> objetos = new ArrayList<Alas>();

    private List<Integer> arrobjetos = new ArrayList<Integer>();

    private List<Escudo> escudos = new ArrayList<Escudo>();

    private List<Integer> arrescudo = new ArrayList<Integer>();

    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Sprite2 sprite2;
    private Sprite2 sprite3;
    private List<Moneda> monedas = new ArrayList<Moneda>();
    private Bitmap bmp;
    private int nivel=1;
    private int nivant=1;
    private Tiempo tiempoObjeto;
    private Tiempo tiempoEscudo;
    private boolean hayescudo=false;
    private Sprite2 spactual;
    private int x;
    private int y;


//private int x = 0;
//private int xSpeed = 6;

    public VistaJuego(Context context) {
        super(context);
        cargarMalos();
        cargarObjetos();
        cargarEscudos();

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.principal);
        sprite2 = new Sprite2(this,bmp);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.principalescudo);
        sprite3 = new Sprite2(this,bmp);



        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createSprites();
                createMonedas();
                //
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });

        /*
        bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.moneda);
        moneda=new Moneda(this,bmp2);
        */





        //sprite2.setY(sprite2.getY()+sprite2.getHeight()/2);

//bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//bmp = BitmapFactory.decodeResource(getResources(), R.drawable.princesa);
//sprite = new Sprite(this,bmp);

    }



    private void cargarMalos() {
        malos.add(R.drawable.malo1);
        malos.add(R.drawable.malo2);
        malos.add(R.drawable.malo3);
        malos.add(R.drawable.malo4);
        malos.add(R.drawable.malo5);

    }
    private void cargarObjetos() {
        arrobjetos.add(R.drawable.alas);
    }
    private void cargarEscudos() {
        arrescudo.add(R.drawable.escudo);
    }

    private void createSprites() {
        int mal=(int)(Math.random()*(malos.size()-1));
        int nomb = malos.get(mal);
        sprites.add(createSprite(nomb));
    }
    private void createObjetos() {
        int mal=(int)(Math.random()*(arrobjetos.size()-1));
        int nomb = arrobjetos.get(mal);
        objetos.add(createObjeto(nomb));
    }
    private void createEscudos() {
        int mal=(int)(Math.random()*(arrescudo.size()-1));
        int nomb = arrescudo.get(mal);
        escudos.add(createEscudo(nomb));
    }

    private void createMonedas() {
        int total=nivel*2;
        for(int i=0;i<total;i++)
        {
            monedas.add(createMoneda(R.drawable.moneda));
        }



    }

    private Sprite createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(this,bmp);
    }
    private Moneda createMoneda(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Moneda(this,bmp);
    }
    private Alas createObjeto(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Alas(this,bmp);
    }
    private Escudo createEscudo(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Escudo(this,bmp);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.BLACK);


        Rect s1;
        Rect s2 ;


        Drawable d = getResources().getDrawable(R.drawable.fondo3);
        d.setBounds(0, 0, getWidth(), getHeight());
        d.draw(canvas);
        //setBackgroundDrawable(getResources().getDrawable(R.drawable.images));

        // moneda.onDraw(canvas);
        if(nivel!=nivant)
        {
            nivant++;
            createSprites();
            if(objetos.size()==0)
                createObjetos();

        }
        if((nivel%5==0)&&hayescudo==false)
        {
            if(escudos.size()==0)
                createEscudos();
            //hayescudo=true;
        }


        for (Escudo escudo : escudos) {
            escudo.onDraw(canvas);
            if(hayescudo==false)
                 s2 = sprite2.rectangulo();
            else
                s2 = sprite3.rectangulo();
            s1= escudo.rectangulo();
            tiempoEscudo = new Tiempo();
            tiempoEscudo.Contar();
            boolean col=escudo.colision(s1, s2);
            if(col)
            {
                hayescudo=true;

                borrarEscudo(escudo);

            }



        }

        Sprite spr=null;
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
            if(hayescudo==false)
                s2 = sprite2.rectangulo();
            else
                s2 = sprite3.rectangulo();
            s1= sprite.rectangulo();
            boolean col=sprite.colision(s1, s2);
            if(col)
            {
                if(hayescudo)
                {
                    spr=sprite;

                }
                else
                {
                    //gameOver( canvas);
                }


            }


        }
        if(spr!=null)
            borrarEnemigo(spr);


        for (Alas ala : objetos) {
            ala.onDraw(canvas);

            if(hayescudo==false)
                s2 = sprite2.rectangulo();
            else
                s2 = sprite3.rectangulo();
            s1= ala.rectangulo();
            tiempoObjeto = new Tiempo();
            tiempoObjeto.Contar();
            boolean col=ala.colision(s1, s2);
            if(col)
            {
                hayAlas(ala);

            }



        }

        Moneda mon=null;
        if(monedas.size()>0)
        {
            for (Moneda moneda : monedas) {
                moneda.onDraw(canvas);

                if(hayescudo==false)
                    s2 = sprite2.rectangulo();
                else
                    s2 = sprite3.rectangulo();
                s1= moneda.rectangulo();
                boolean col=moneda.colision(s1, s2);
                if(col)
                {
                    //gameOver( canvas);
                    mon=moneda;



                }


            }
            if(mon!=null)
            {
                borrarMoneda(mon);
            }
        }
        else
        {
            nuevohilo();
            nivel++;
            Paint p=new Paint();
            String text;
            p.setTextSize(canvas.getWidth()/7);
            p.setTextAlign(Paint.Align.CENTER);
            text = "Nivel " + nivel;
            canvas.drawText(text, canvas.getWidth() / 2, canvas.getHeight() / 4, p);

        }



        if((tiempoObjeto!=null)&&(tiempoObjeto.getSegundos()>5))
        {

                sprite2.setVeldiv(20);

        }
        if((tiempoEscudo!=null)&&(tiempoEscudo.getSegundos()>5))
        {

            hayescudo=false;

        }

        if(hayescudo==false)
        {

            sprite2.onDraw(canvas);

        }
        else {

            sprite3.onDraw(canvas);

        }

    }

    private void cambiarcoordenadas() {

        sprite3.setX(sprite2.getX());
        sprite3.setY(sprite2.getY());
    }
    private void cambiarcoordenadas2() {

        sprite2.setX(sprite3.getX());
        sprite2.setY(sprite3.getY());
    }

    private void borrarEscudo(Escudo escudo) {
        escudos.remove(escudo);
    }
    private void borrarEnemigo(Sprite sprite) {
        sprites.remove(sprite);

    }
    private void hayAlas(Alas ala) {
        sprite2.setVeldiv(40);
        objetos.remove(ala);




    }

    private void nuevohilo() {
        createMonedas();
    }

    private void borrarMoneda(Moneda moneda) {

        monedas.remove(moneda);
    }



    private void gameOver(Canvas canvas) {
        /*
        Paint p=new Paint();
        String text;
        p.setTextSize(canvas.getWidth()/7);
        p.setTextAlign(Paint.Align.CENTER);
        text = "game over";
        canvas.drawText(text,canvas.getWidth()/2,canvas.getHeight()/4,p);
        */

        Intent intent = new Intent(this.getContext(), GameOver.class);
        this.getContext().startActivity(intent);


    }

    public boolean onTouchEvent( MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){

            if(hayescudo)
            {
                mover(sprite3, event);
                posiciona(sprite2,event);

            }
            else
            {
                mover(sprite2,event);
                posiciona(sprite3, event);
            }

            /*
            int actX= sprite2.getX();
            //Toast.makeText(getContext(),"hola "+actX,Toast.LENGTH_SHORT).show();
            // event.getButtonState();


            float y=event.getY();
            float x=event.getX();

            int resX=sprite2.getWidth()/2;
            int resY=sprite2.getHeight()/2;

            sprite2.obtenerCoor(x-resX,y-resY);
*/




        }
        return true;
    }

    private void posiciona(Sprite2 sprite, MotionEvent event) {

        int actX= sprite.getX();
        //Toast.makeText(getContext(),"hola "+actX,Toast.LENGTH_SHORT).show();
        // event.getButtonState();


        float y=event.getY();
        float x=event.getX();

        int resX=sprite.getWidth()/2;
        int resY=sprite.getHeight()/2;

        sprite.posiciona(x-resX,y-resY);

    }

    private void mover(Sprite2 sprite, MotionEvent event) {


        int actX= sprite.getX();
        //Toast.makeText(getContext(),"hola "+actX,Toast.LENGTH_SHORT).show();
        // event.getButtonState();


        float y=event.getY();
        float x=event.getX();

        int resX=sprite.getWidth()/2;
        int resY=sprite.getHeight()/2;

        sprite.obtenerCoor(x-resX,y-resY);



    }


}
