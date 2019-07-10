import greenfoot.*;
import java.util.List;

public class Can extends Transparencys{
    boolean pause = true;
    private boolean flag = true;
    private int type;
    int Distx, Disty, sx, sy,enx,myx;
    double angle, dam;
    int count = 0;
    int bmcount = 2;
    int count2 = 90;
    boolean shfl = false;
    Actor enemys;
    boolean bomf = false;
    private int anime = 0;
    private int anicount = 1;
    boolean shf=true;
    int ex;
    Enemys ene;
    List enemy;
    public Can(int change, int scx, int scy, double damage){
        type = change;
        sx = scx;
        sy = scy;
        dam = damage;
        if(type==6){
            setImage("Cannon/mybeam1.png");
            GreenfootImage t = getImage();
            t.setTransparency(150);
            setImage(t);
        }else{
            setImage("Cannon/mycan1.png");
        }
        GreenfootImage image = getImage();
        image.scale(sx,sy);
        setImage(image);
    }

    public void act(){
        if(pause){
            switch(type){
                case 1:// Normal
                move(16);
                hittest(true);
                break;

                case 2:// Reflect
                move(13);
                Reflecter();
                hittest(true);
                break;

                case 3:// Missile
                move(7);
                if(count <= 25 && count2 >= 1){
                    List ene1s = getObjectsInRange(240, Enemys.class); 
                    if (!ene1s.isEmpty()) {  
                        enemys = (Actor)ene1s.get(0); 
                        Distx=getX()-enemys.getX();
                        Disty=getY()-enemys.getY();
                        angle = Math.toDegrees(Math.atan2(Disty,Distx))+180;
                        shfl = true;
                    }
                    if(angle - getRotation() > 10){
                        angle = getRotation() + 10;
                    }else if(angle - getRotation() < -10){
                        angle = getRotation() - 10;
                    }
                    if(shfl){
                        setRotation((int)angle);
                    }
                    shfl = false;
                    count2 = 0;
                    count ++;
                }else{
                    count2 ++;
                }
                hittest(true);
                break;

                case 4:// Shotgun
                move(13);
                if(count > 2){
                    sx -= 6;
                    //sy -= 4;
                    if(sx > 0 && sy > 0){
                        for(int i=0;i < 6;i++){
                            Can can = new Can(4, sx, sy, 0.2);
                            getWorld().addObject(can, getX(), getY());
                            can.setRotation(getRotation() + Greenfoot.getRandomNumber(210) - 155);
                        }
                    }
                    flag = false;
                    getWorld().removeObject(this);
                }
                count++;
                hittest(true);
                break;

                case 5:// Burst
                Reflecter();
                if(getX() > 585 || getX() < 30 || getY() > 625 || getY() < 5) {
                    flag = false;
                    getWorld().removeObject(this);
                }
                if(flag){
                    if(bomf){
                        if(count >= 20){
                            if((anime%3) == 0){
                                setImage("Burst1/burst" + anicount +".png");
                                GreenfootImage image = getImage();
                                image.scale(100,100);
                                setImage(image);
                                if(anicount == 10){
                                    flag = false;
                                    getWorld().removeObject(this);
                                }else{
                                    anicount ++;
                                }   
                            }
                            if(flag){
                                Enemys enemys = (Enemys)getOneIntersectingObject(Enemys.class);
                                Actor encan = getOneObjectAtOffset(0,0,Encan1.class);
                                Actor encan2 = getOneObjectAtOffset(0,0,Encan2.class);
                                if(enemys != null|| encan != null|| encan2 != null || getX() > 585 || getX() < 40 || getY() > 625 || getY() < 5) {
                                    if(enemys != null){
                                        enemys.Damage(dam);
                                    }else if(encan != null){
                                        getWorld().removeObject(encan);
                                    }else if(encan2 != null){
                                        getWorld().removeObject(encan2);
                                    }
                                }
                            }
                        }else{
                            try{
                                Actor enemys = getOneIntersectingObject(Enemys.class);
                                setLocation(enemys.getX(),enemys.getY());
                            }catch(Exception e){
                            }
                            count++;
                        }
                    }else{
                        move(7);
                        Actor encan = getOneObjectAtOffset(0,0,Encan.class);
                        Actor enemys = getOneIntersectingObject(Enemys.class);                   
                        if(encan != null || enemys != null){
                            if(encan != null){
                                flag = false;
                                getWorld().removeObject(encan);
                                getWorld().removeObject(this);
                            }else{
                                bomf = true;
                            }
                        }
                    }
                }
                break;

                case 6:
                if(count>1){
                    dam = 0.05;
                    super.OrderTransparence(60);
                    if(count == 5 && flag){
                        getWorld().removeObject(this);
                        flag=false;
                    }
                }else{
                    setLocation((((World1)getWorld()).getMychr()).getX(),(((World1)getWorld()).getMychr()).getY()/2);
                }

                if(flag&&(bmcount%2)==0){
                    getImage().scale((((World1)getWorld()).getMychr()).getY(),20);
                    enemy = getWorld().getObjects(Enemys.class);
                    for(int i=0;i<enemy.size();i++){
                        ene = (Enemys)enemy.get(i);
                        enx = ene.getImage().getWidth()/2;
                        myx = (((World1)getWorld()).getMychr()).getX();
                        if(myx + 10 > ene.getX() - enx && myx - 10 < ene.getX() + enx && (((World1)getWorld()).getMychr()).getY()>ene.getY()){
                                ene.Damage(dam);
                        }
                    }
                }
                bmcount++;
                count++;
                break;
            }
        }
    }

    private void hittest(boolean des){
        if(flag){
            Enemys enemys = (Enemys)getOneIntersectingObject(Enemys.class);
            Actor encan = getOneIntersectingObject(Encan.class);
            if(enemys != null|| encan != null || getX() > 605 || getX() < 55 || getY() > 625 || getY() < 5) {
                if(enemys != null){
                    enemys.Damage(dam);
                }else if(encan != null){
                    getWorld().removeObject(encan);
                }
                flag = false;
                if(des)getWorld().removeObject(this);
            }
        }
    }

    private void hitEnemy(int sc){
        //((World1)getWorld()).getCounter().bumpCount(sc);
        //((World1)getWorld()).enDestroy(true);
    }

    private void Reflecter(){
        if(getX() > 595 || getX() < 65){
            if(getX() > 595){
                setLocation(595,getY());
            }else if(getX() < 65){
                setLocation(65,getY());
            }
            setRotation(-getRotation() + 180);
        }
    }

    public void Pausing(boolean pau){
        pause = pau;
    }
}