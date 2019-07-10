import greenfoot.*;
import java.awt.Color;
import java.util.List;
public class World1 extends World{

    int allflag;
    boolean pause = true;
    boolean paupress = false;
    boolean nowpau = false;
    Text pautxt;
    Mychr myc;
    Counter count;
    HPCounter hpcount;
    GreenfootImage background;
    Maps map;
    public World1(){
        super(660, 630, 1);
        setBackground("StageTitle/Title.jpg");
    }

    public void act(){
        if(allflag==0){
            if(Greenfoot.isKeyDown("enter")){
                background = new GreenfootImage(getWidth(), getHeight());
                background.setColor(Color.black);
                background.fill();
                setBackground(background);
                Greenfoot.delay(80);
                InitGame();
                allflag++;
            }
        }else if(allflag==2){
            map = new Stage2x1();
            addObject(map,getWidth()/2,getHeight()/2);
            allflag++;
        }else if(allflag==4){
            map = new Stage2x2(this);
            addObject(map,getWidth()/2,getHeight()/2);
            allflag++;
        }

        if(allflag!=0&&Greenfoot.isKeyDown("a")){
            if(!paupress){
                paupress = true;
                pause = !pause;
                List pauss = getObjects(Pause.class);
                for(int i = 0;i < pauss.size();i++){
                    ((Pause)pauss.get(i)).Pausing(pause);
                }
                if(!nowpau){
                    pautxt = new Text("PAUSE", this, false);
                    addObject(pautxt,getWidth()/2,getHeight()/2);
                }else{
                    removeObject(pautxt);
                }
                nowpau = !nowpau;
            }
        }else{
            paupress = false;
        }
    }

    private void InitGame(){
        myc = new Mychr(1,this);
        count = new Counter(0,20);
        hpcount = new HPCounter();
        map = new Stage1();
        setPaintOrder(Counters.class,Margin.class,Transparencys.class,Mychr.class,Can.class,
            Enemys.class,Beam.class,Encan.class,Cure.class,Maps.class);
        addObject(new Margin(getWidth(),30), getWidth()/2, 615);
        addObject(new Margin(60, getHeight()), 630, getHeight()/2);
        addObject(new Margin(60, getHeight()), 30, getHeight()/2);
        addObject(new Margin(getWidth(),30), getWidth()/2, 15);
        addObject(count, 80, 615);
        addObject(hpcount, 450, 615);
        for(int i = 0; i < 15; i++){
            addObject(new HPs(),470 + i * 8, 615);
        }
        addObject(myc, getWidth()/2, 500);
        myc.Transparence();
        addObject(map,getWidth()/2,getHeight()/2);
    }

    public void Gameover(){
        Text txt = new Text("GAME OVER", this, true);
        addObject(txt, getWidth()/2, getHeight()/2);
        removeObjects(getObjects(Encan.class));
        Greenfoot.delay(160);
        removeObjects(getObjects(Enemys.class));
        Greenfoot.delay(2);
        removeObjects(getObjects(Mychr.class));
        Greenfoot.delay(2);
        removeObjects(getObjects(Actor.class));
        Greenfoot.delay(60);
        allflag=0;
        setBackground("StageTitle/Title.jpg");
    }

    public void allflag(){
        allflag++;
    }

    public Mychr getMychr(){
        return myc;
    }

    public HPCounter getHPCounter(){
        return hpcount;
    }

    public Counter getCounter(){
        return count;
    }

    public Maps getStage(){
        return map;
    }
}
