import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
public class Ene1 extends Enemy{   
    private boolean deadflag=true,storing;
    private int pat,shpat,intime,count = 0,shcount = 100,rad = 0,anime = 0,anicount = 1,a=0,x,sizx=20,sizy=20,Distx,Disty,myx,myy;
    double dam=1,moving,HP,ag=90,vx,vy,mx,my,si,moag,carvmo,i=0,angle;
    Maps map;
    List b,encans;
    GreenfootImage[] images = new GreenfootImage[13];
    Mychr g;
    Encan1 inencan;
    public Ene1(boolean st){
        storing = st;
        getImage().setTransparency(0);
        for(int i=1;i<=13;i++){
            images[i-1] = new GreenfootImage("Enemy/Ene1/ene1-" + i +".png");
        }
    }

    public void act(){
        if(!storing&&pause){
            if((anime%11) == 0){
                setImage(images[anicount-1]);
                if(anicount == 13){
                    anicount = 1;
                }else{
                    anicount ++;
                }
            }
            anime ++;
            move();
            shot();
            if(HP <= 0){
                ((A)getWorld()).getCounter().bumpCount(30);
                map.enDestroy(true);
                store();
            }else{
                if(getX() > 596 || getX() < 64 || getY() > 600 || getY() < 2){
                    map.enDestroy(false);
                    store();
                }
            }
        }
    }

    public void move(){
        if(!getWorld().getObjects(Mychr.class).isEmpty()){
            switch(pat){
                case 0:
                setLocation(getX(), (int)(getY() + moving));
                break;

                case 1:
                if(intime > count){
                    setLocation(getX(), getY() + 4);
                }

                break;
                case 2:
                if(intime > count){
                    setLocation(getX(), getY() + 3);
                }else{
                    setLocation(getX(), getY() - 3);
                }

                break;

                case 3:
                if(intime > count){
                    setLocation(getX(), getY() + 2);
                    if(count >= 15){
                        setLocation(getX() - 2, getY());
                    }
                }
                break;

                case 4:
                if(intime > count){
                    setLocation(getX(), getY() + 2);
                    if(count >= 15){
                        setLocation(getX() + 2, getY());
                    }
                }
                break; 

                case 5:
                if(a==0){
                    x = getX();
                }
                si = Math.toDegrees(Math.sin(i)) * 0.7;
                setLocation(x + (int)si, getY() + 3);
                if((a%1)==0){
                    i+= 0.1;
                }
                a++;
                break;

                case 6:
                if(a==0){
                    x = getX();
                }
                si = Math.toDegrees(Math.cos(i)) * 0.8;
                setLocation(x + (int)si, getY() + 3);
                if((a%1)==0){
                    i+= 0.1;
                }
                a++;
                break;

                case 7:
                if(count < 120){
                    turn(Greenfoot.getRandomNumber(2) + 1);
                }
                move(4);
                break;

                case 8:
                if(count < 120){
                    turn(-(Greenfoot.getRandomNumber(2) + 1));
                }
                move(4);
                break;

                case 9://Left turn
                if(count >= 50 && count <= carvmo/moag+50){
                    mx = Math.cos(Math.toRadians(ag)) * moving * 1.3;
                    my = Math.sin(Math.toRadians(ag)) * moving * 1.3;
                    ag += moag;
                }else if(count > carvmo/moag+50){
                    mx = Math.cos(Math.toRadians(ag)) * moving * 1.3;
                    my = Math.sin(Math.toRadians(ag)) * moving * 1.3;
                }
                setLocation(getX() + (int)(mx), getY() + (int)(my));
                break;

                case 10://Right turn
                if(count >= 50 && count <= carvmo/moag+50){
                    mx = Math.cos(Math.toRadians(ag)) * moving * 1.3;
                    my = Math.sin(Math.toRadians(ag)) * moving * 1.3;
                    ag -= moag;
                }else if(count > carvmo/moag+50){
                    mx = Math.cos(Math.toRadians(ag)) * moving * 1.3;
                    my = Math.sin(Math.toRadians(ag)) * moving * 1.3;
                }
                setLocation(getX() + (int)(mx), getY() + (int)(my));
                break;
            }
            count ++;       
        }
    }

    public void shot(){
        switch(shpat){
            case 0:
            break;
            case 1:
            if(shcount > 80){
                g=((A)getWorld()).getMychr();
                myx=getX();
                myy=getY();
                Distx=myx-g.getX();
                Disty=myy-g.getY();
                angle=Math.toDegrees(Math.atan2(Disty,Distx))+180;
                inEncan(getX(),getY(),(int)angle);
                shcount = 0;
            }
            break;

            case 2:
            if(shcount > 100){
                b=getWorld().getObjects(Mychr.class);
                g =(Mychr)b.get(0);
                myx=getX();
                myy=getY();
                Distx=myx-g.getX();
                Disty=myy-g.getY();
                angle=Math.toDegrees(Math.atan2(Disty,Distx))+180;
                inEncan(getX(),getY(),(int)angle);
                inEncan(getX(),getY(),(int)angle + 20);
                inEncan(getX(),getY(),(int)angle - 20);
                shcount = -30;
            }
            break;

            case 3:
            if(shcount > 80){
                shcount = 70;
                inEncan(getX(),getY(),90 + rad * 30);
                rad ++;
            }
            break;

            case 4:
            if(shcount > 90){
                shcount = 0;
                for(int i=0; i<9; i++){
                    inEncan(getX(),getY(),90 + rad * 40);
                    rad ++;
                }
            }
            break;
        }
        shcount ++;
    }

    public void Damage(double dam){
        HP -= dam;
    }

    public void inmap(double hp, Maps m, int inx, int iny, int mo, int sh, double move, double carve, double caag){
        getImage().setTransparency(255);
        map = m;
        HP = hp;
        anime = 0;
        anicount = 1;
        a = -1;
        count=0;
        shcount = 50;
        if(mo == -1){
            pat = Greenfoot.getRandomNumber(11);
        }else{
            pat = mo;
        }
        if(sh == -1){
            shpat = Greenfoot.getRandomNumber(5); 
        }else{
            shpat = sh;
        }
        moving = move;
        carvmo = carve;
        moag = caag;
        vx = move;
        vy = move;
        mx = 0;
        my = move;
        intime = Greenfoot.getRandomNumber(4) * 10 + 50;
        if(pat == 7){
            setRotation(60);
        }else if(pat == 8){
            setRotation(-60);
        }
        getImage().scale(sizx,sizy);
        storing = false;
        setLocation(inx, iny);
    }

    public void store(){
        getImage().setTransparency(0);
        setLocation(1,629);
        //getImage().scale(40,40);
        storing = true;
    }

    public boolean getStoring(){
        return storing;
    }

    public void inEncan(int canx, int cany, int deg){
        encans = getWorld().getObjects(Encan1.class);
        for(int i=0;i<encans.size();i++){
            inencan = (Encan1)(encans.get(i));
            if(inencan.getStoring()){
                inencan.inmap(canx,cany,1,1,6);
                inencan.setRotation(deg);
                break;
            }
        }
    }

    public double getDamage(){
        return dam;
    }
}
