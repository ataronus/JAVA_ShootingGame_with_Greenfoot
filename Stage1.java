import greenfoot.*;

public class Stage1 extends Maps{
    boolean pause = true;
    private static final GreenfootImage bgImage = new GreenfootImage("World/world1.jpg");
    GreenfootImage background = new GreenfootImage(540, 570);
    private int scrollSpeed = -1;
    private GreenfootImage scrollingImage;
    private int scrollPosition = 630;
    GreenfootImage image = new GreenfootImage(540, 570);
    GreenfootImage bg;
    Text txt;
    public int encount = 0;
    public int count = 0;
    public int endes = 0;
    public int bossflag = 0;
    public int enfig = 0;
    private double trans = 0;
    public Stage1(){
        InitImage(570);
        getImage().setTransparency(0);
    }

    public void act(){
        if(pause){
            painting();
            if(bossflag==0){
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
                if(encount > 50){
                    getWorld().addObject(new Stagetitle("StageTitle/STAGE1.png", 3, this),getWorld().getWidth()/2,getWorld().getHeight()/2);
                    encount = -1000;
                }else{
                    encount++;
                }
            }else if(bossflag==2&&endes<60){
                if(enfig < 5){
                    if (encount > 65){
                        if(Greenfoot.getRandomNumber(11) >= 2){
                            for(int i=0; i<Greenfoot.getRandomNumber(2) + 1; i++){
                                getWorld().addObject(new Enemy1(-1,-1, 3, 360, 4, this, 0.1), Greenfoot.getRandomNumber(460) + 85, 15);
                                enfig ++;
                                if(enfig >= 5){
                                    break;
                                }
                            }
                        }else{
                            getWorld().addObject(new Enemy2(this,2.1), Greenfoot.getRandomNumber(460) + 85, 5);
                            enfig ++;
                        }
                        encount = 0;
                    }else{
                        encount ++;
                    }
                }
            }else if(bossflag==2){
                if(enfig != 0){
                }else if(count > 50){
                    getWorld().addObject(new Boss1(this), getWorld().getWidth()/2, 20);
                    bossflag++;
                    count = 0;
                }else{
                    count ++;
                }
            }else if(bossflag==3){
            }else if(bossflag==4){
                if(encount>=120){
                    bossflag++;
                    encount=0;
                }else{encount++;}
            }else if(bossflag==5){
                Clear();
            }else if(bossflag==6){
                if(encount>=200){
                    bossflag++;
                }else{encount++;}
            }else if(bossflag==7){
                trans-=6;
                if(trans<0){
                    trans=0;
                }
                (((World1)getWorld()).getMychr()).OrderTransparence((int)Math.ceil(trans));
                txt.OrderTransparence((int)Math.ceil(trans));
                (((World1)getWorld()).getMychr()).setshab(false);
                getImage().setTransparency((int)Math.ceil(trans));
                if(trans==0){
                    bossflag++;
                }
            }else if(bossflag==8){
                getWorld().removeObject(txt);
                Greenfoot.delay(60);
                (((World1)getWorld()).getMychr()).setLocation(getWorld().getWidth()/2,500);
                ((World1)getWorld()).allflag();
                getWorld().removeObject(this);
            }
        }
    }    

    public void InitImage(int scroll){
        scrollingImage = getScrollingImage(bgImage.getWidth(), bgImage.getHeight());
        background.drawImage(scrollingImage, 0, scroll);  
        setImage(background);
    }

    private void paint(int position){
        bg = getImage();
        bg.drawImage(scrollingImage, 0, position);
        bg.drawImage(scrollingImage, 0, position - scrollingImage.getHeight());
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
        if(scrollPosition >= bgImage.getHeight()){
            scrollPosition = 0;
        }
        scrollPosition -= scrollSpeed;
        bg = getImage();
        bg.drawImage(scrollingImage, 0, scrollPosition);
        bg.drawImage(scrollingImage, 0, scrollPosition - scrollingImage.getHeight());
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
        txt = new Text("STAGE1 CLEAR!", getWorld(), false);
        getWorld().addObject(txt, getWorld().getWidth()/2, getWorld().getHeight()/2);
        getWorld().removeObjects(getWorld().getObjects(Beam.class));
        getWorld().removeObjects(getWorld().getObjects(Can.class));
        bossflag++;
    }

    public void Pausing(boolean pau){
        pause = pau;
    }
}
