import greenfoot.*;

public class Stage2x2 extends Maps{
    boolean pause = true;
    private static final GreenfootImage bgImage = new GreenfootImage("world2x2.bmp");
    GreenfootImage background = new GreenfootImage(540, 570);
    private int scrollSpeed = 2;
    private GreenfootImage scrollingImage;
    private int scrollPosition = 570;
    GreenfootImage image = new GreenfootImage(540, 570);
    GreenfootImage bg;
    Text txt;
    int scfl=0;
    int scposi = bgImage.getHeight()/3*2;
    int scretupos = 570;
    public int encount = 0;
    public int count = 0;
    public int endes = 0;
    public int bossflag = 0;
    public int enfig = 0;
    private double trans = 0;
    public boolean beam = true;
    public int beamcount = 70;
    public int beamcount2 = 0;
    public int beamX,beamX2,beamx,beamx2;
    public int rambeam = Greenfoot.getRandomNumber(200) + 120;
    boolean mpmvanime=false;
    int myx,myy;
    double mytrans = 255;
    double myimx = 54;
    double myimy = 36;
    double myang,vx,vy;
    public Stage2x2(World1 wo){
        InitImage(scrollPosition);
        getImage().setTransparency(0);
    }

    public void act(){
        if(pause){
            painting();
            if(bossflag==0){
                (((World1)getWorld()).getMychr()).mapmove(false);
                (((World1)getWorld()).getMychr()).setshab(true);
                trans+=10;
                if(trans>255){
                    trans=255;
                }
                (((World1)getWorld()).getMychr()).OrderTransparence((int)Math.ceil(trans));
                getImage().setTransparency((int)Math.ceil(trans));
                if(trans==255){
                    bossflag++;
                }
            }else if(bossflag==1){
                if(encount>=90){
                    bossflag++;
                    encount=0;
                }else{encount++;}
            }else if(bossflag==2){
                if(endes<90){
                    HugeBeams();
                    if (encount > 45 && enfig < 7){
                        if(Greenfoot.getRandomNumber(11) >= 3){
                            for(int i=0; i<Greenfoot.getRandomNumber(2) + 1; i++){
                                getWorld().addObject(new Enemy1(-1,Greenfoot.getRandomNumber(4)+1, 3, 360, 4, this, 0.5), Greenfoot.getRandomNumber(460) + 85, 15);
                                enfig ++;
                                if(enfig >= 8){
                                    break;
                                }
                            }
                        }else{
                            getWorld().addObject(new Enemy2(this,3.6), Greenfoot.getRandomNumber(460) + 85, 5);
                            enfig ++;
                        }
                        encount = 0;
                    }else{
                        encount ++;
                    }
                }else{
                    if(beamcount2 == 0 && beamcount == 0){
                        bossflag++;
                    }else{
                        HugeBeams();
                    }
                }
            }else if(bossflag==3){
                if(enfig != 0){
                }else if(count > 80){
                    getWorld().addObject(new Boss2x2(this), getWorld().getWidth()/2, 30);
                    bossflag++;
                    count = 0;
                }else{
                    count ++;
                }
            }else if(bossflag==4){
            }else if(bossflag==5){
                (((World1)getWorld()).getMychr()).mapmove(true);
                if(encount>=120){
                    myx = ((World1)getWorld()).getMychr().getX();
                    myy = ((World1)getWorld()).getMychr().getY();
                    if(myx<=335 && myx>=325 && myy<=515 && myy>=495){
                        (((World1)getWorld()).getMychr()).setLocation(330,500);
                        scposi = bgImage.getHeight();
                        scretupos = 1710;
                        mpmvanime = true;
                        encount=0;
                    }else{
                        myang = Math.toDegrees(Math.atan2(myy-500,myx-330))+180;
                        vx = Math.cos(Math.toRadians(myang)) * 4;
                        vy = Math.sin(Math.toRadians(myang)) * 4;
                        (((World1)getWorld()).getMychr()).setLocation(myx+(int)vx,myy+(int)vy);
                    }
                }else{encount++;}
            }else if(bossflag==6){
                if(encount>=140){
                    myy = ((World1)getWorld()).getMychr().getY();
                    if(myy<=325 && myy>=315){
                        (((World1)getWorld()).getMychr()).setLocation(330,320);
                        if(mytrans<=0){
                            bossflag++;
                            encount=0;
                        }
                    }else{
                        (((World1)getWorld()).getMychr()).setLocation(330,myy - 3);
                        mytrans -= 4.0;
                        if(mytrans < 0)mytrans=0;
                        (((World1)getWorld()).getMychr()).OrderTransparence((int)Math.ceil(mytrans));
                        myimx -= 0.4;
                        myimy -= 0.1;
                        if(myimx <=1)myimx = 1;
                        if(myimy <=1)myimy = 1;
                        (((World1)getWorld()).getMychr()).getImage().scale((int)Math.ceil(myimx),(int)Math.ceil(myimy));
                    }
                }else{encount++;}
            }else if(bossflag==7){
                Clear();
            }else if(bossflag==8){
                if(encount>=200){
                    bossflag++;
                }else{encount++;}
            }else if(bossflag==9){
                trans-=6;
                if(trans<0){
                    trans=0;
                }
                txt.OrderTransparence((int)Math.ceil(trans));
                getImage().setTransparency((int)Math.ceil(trans));
                if(trans==0){
                    bossflag++;
                }
            }else if(bossflag==10){
                getWorld().removeObject(txt);
                Greenfoot.delay(60);
                (((World1)getWorld()).getMychr()).setLocation(getWorld().getWidth()/2,500);
                (((World1)getWorld()).getMychr()).getImage().scale(54,36);
                ((World1)getWorld()).allflag();
                getWorld().removeObject(this);
            }
        }
    }    

    public void HugeBeams(){
        beamcount ++;
        if(beamcount == rambeam){
            beamX = Greenfoot.getRandomNumber(530)+ 70;
            getWorld().addObject(new Beamsign(), beamX, 45);
            beamx = Greenfoot.getRandomNumber(30) + 60;
            while(true){
                beamX2 = Greenfoot.getRandomNumber(530)+ 70;
                if(beamX2 > beamX + beamx||beamX2 < beamX - beamx){
                    break;
                }
            }
            beamx2 = Greenfoot.getRandomNumber(30) + 60;
            getWorld().addObject(new Beamsign(), beamX2, 45);
        }else if(beamcount >= rambeam + 120 && beam){
            getWorld().removeObjects(getWorld().getObjects(Beamsign.class));
            beam = false;
        }else if(!beam){
            Hugebeam ba = new Hugebeam(20, beamx);
            getWorld().addObject(ba, beamX, 0);
            ba.setRotation(90);
            Hugebeam bb = new Hugebeam(20, beamx2);
            getWorld().addObject(bb, beamX2, 0);
            bb.setRotation(90);
            beamcount2 ++;
        }
        if(beamcount2 >= 35){
            rambeam = Greenfoot.getRandomNumber(200) + 120;
            beamcount = 0;
            beamcount2 = 0;
            beam = true;
        }
    }

    public void InitImage(int scroll){
        scrollingImage = getScrollingImage(bgImage.getWidth(), bgImage.getHeight());
        background.drawImage(scrollingImage, 0, scroll);  
        setImage(background);
    }

    private GreenfootImage getScrollingImage(int width, int height){
        image.scale(width,height);
        for(int x = 0; x < width; x += bgImage.getWidth()) {
            for(int y = 0; y < height; y += bgImage.getHeight()){
                image.drawImage(bgImage, x, y);
            }
        }
        return image;
    }

    public void painting(){
        if(scrollPosition >= scposi){
            scrollPosition = scretupos; 
            if(mpmvanime){
                bossflag++;
                mpmvanime=false;
            }
        }
        scrollPosition += scrollSpeed;
        bg = getImage();
        bg.drawImage(scrollingImage, 0, scrollPosition - scrollingImage.getHeight());
        bg.drawImage(scrollingImage, 0, scrollPosition);
    }

    public void bossDestroy(){   
        bossflag ++;
        encount = 0;
        enfig = 0;
    }

    public void enDestroy(boolean esflag){
        if(esflag){
            endes ++;
        }
        enfig --;
    }

    public void Clear(){
        Greenfoot.setSpeed(50);
        txt = new Text("STAGE2 CLEAR!", getWorld(), false);
        getWorld().addObject(txt, getWorld().getWidth()/2, getWorld().getHeight()/2);
        getWorld().removeObjects(getWorld().getObjects(Beam.class));
        getWorld().removeObjects(getWorld().getObjects(Can.class));
        bossflag++;
    }

    public void Pausing(boolean pau){
        pause = pau;
    }
}