import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GameScene extends Scene {
    private final staticThing leftImage;
    private final staticThing rightImage;
    private final staticThing imagebleu;
    private final staticThing heartImage;
    private final staticThing heartImage1;
    private final staticThing heartImage2;
    private final staticThing bull,bull1,bull2,bull3,bull4,bull5,bull6;
    private Camera camera;
    private Hero hero;
    private Foe enemie;
    private Foe enemie1;
    private Pane pane;
    private Text text;
    private Text text_bulls;
    private Font font;
    private ArrayList<staticThing> bulls;

    private int numberOfLives = 3;
    private int s=0;
    private static double now_time = 0;
    private double invincibility = 0;
    private int count=1;
    private int count_enemie=0;
    private int count_enemie1=0;
    private boolean b = true;
    private int index = 3;
    private int bulls_max = 2;
    private double time;

    staticThing getHeart = new staticThing(((Math.random() * 300) + 100), 100, "heart.png");

    private ArrayList<Foe> enemies;

    private final double InitialSpeed=1;

    public GameScene(Pane pane, double width, double height, boolean depthBuffer,
                     Camera camera, Hero hero, Foe enemie, Foe enemie1){
        super(pane, width, height, depthBuffer);
        this.camera= camera;
        this.pane  = pane;
        this.enemie = enemie;
        this.enemie1 = enemie1;
        this.imagebleu = new staticThing(0,-100,"imagebleu.png");
        this.leftImage = new staticThing(0, 0,"desert.png");
        this.rightImage = new staticThing(800, 0, "desert.png");
        this.heartImage  = new staticThing(20,20,"heart.png");
        this.heartImage1 = new staticThing(60,20,"heart.png");
        this.heartImage2 = new staticThing(100,20,"heart.png");
        this.bull        = new staticThing(300,150,"bull.png");
        this.bull1        = new staticThing(300,150,"bull.png");
        this.bull2        = new staticThing(300,150,"bull.png");
        this.bull3        = new staticThing(300,150,"bull.png");
        this.bull4        = new staticThing(300,150,"bull.png");
        this.bull5        = new staticThing(300,150,"bull.png");
        this.bull6        = new staticThing(300,150,"bull.png");

        bulls = new ArrayList<>();
        bulls.add(0,bull);
        bulls.add(1,bull1);
        bulls.add(2,bull2);
        bulls.add(3,bull3);
        bulls.add(4,bull4);
        bulls.add(5,bull5);
        bulls.add(6,bull6);

        pane.getChildren().add(imagebleu.getImageView());
        pane.getChildren().addAll(leftImage.getImageView(),rightImage.getImageView(), hero.getImageView());
        pane.getChildren().add(heartImage.getImageView());
        pane.getChildren().add(heartImage1.getImageView());
        pane.getChildren().add(heartImage2.getImageView());

        this.hero =hero;
        this.hero.setSpeedx(InitialSpeed);

        enemies= new ArrayList<>();
        enemies.add(enemie);
        enemies.add(enemie1);
        pane.getChildren().add(enemie.getImageView());
        pane.getChildren().add(enemie1.getImageView());

        this.text = new Text();
        pane.getChildren().add(text);
        this.font = new Font(30);
        this.text_bulls = new Text();
        pane.getChildren().add(text_bulls);

        this.setOnMouseClicked(event -> hero.jump());
        this.setOnKeyPressed(keyEvent -> {
            String key=keyEvent.getCode().toString();
            if(key.equals("Z")){
                hero.jump();
            }
            if(key.equals("D")){
                hero.speed_var(1);
                hero.forcex_var(100);
                hero.setFrameChange(false);
            }
            if(key.equals("Q")){
                hero.speed_var(-1);
                hero.forcex_var(-100);
                hero.setFrameChange(true);
            }
            if(key.equals("F")){
                boolean m = true;
                hero.setShoot(m);
                hero.resetZ(50);
                for(int i = 0; i<=bulls_max; i++){
                    if(pane.getChildren().contains(bulls.get(i).getImageView()) == false){
                        pane.getChildren().add(bulls.get(i).getImageView());
                        bulls.get(i).setSpeed(5);
                        break;
                    }
                }
            }
        });
    }

    public void setNumberOfLives(int number){
        switch (number){
            case 0 : pane.getChildren().removeAll(heartImage.getImageView());
            case 1 : pane.getChildren().removeAll(heartImage1.getImageView());
            case 2 : pane.getChildren().removeAll(heartImage2.getImageView());
        }
    }

    public void addNumberOfLives(int number){
        if(number == 2 ){
            if(pane.getChildren().contains(heartImage1.getImageView()) == false){
                pane.getChildren().add(heartImage1.getImageView());
            }
        }
        if(number == 3 ){
            if(pane.getChildren().contains(heartImage2.getImageView()) == false){
                pane.getChildren().add(heartImage2.getImageView());
            }
        }
    }
    public void update(double time){
        this.time = time;
        text.setX(200);
        text.setY(60);
        text.setText(toString());
        text.fontProperty().setValue(font);

        rightImage.getImageView().setY(-camera.getY());
        leftImage.getImageView().setY(-camera.getY());

        leftImage.getImageView().setX(- (camera.getX())%800);
        rightImage.getImageView().setX(- (camera.getX())%800+800);
        
        hero.getImageView().setX(hero.getX()-camera.getX());
        hero.getImageView().setY(hero.getY()-camera.getY());

        if(enemie.getX()-camera.getX()+1000+2000*count_enemie<0){
            count_enemie+=1;
            if(pane.getChildren().contains(enemie.getImageView()) == false){
                pane.getChildren().add(enemie.getImageView());
            }
        }
        enemie.getImageView().setX(enemie.getX()-camera.getX()+2000*count_enemie);
        enemie.getImageView().setY(enemie.getY()-camera.getY());
        enemie.setTime(count_enemie);

        if(enemie1.getX()-camera.getX()+1500+3000*count_enemie1<0){
            count_enemie1+=1;
            if(pane.getChildren().contains(enemie1.getImageView()) == false){
                pane.getChildren().add(enemie1.getImageView());
            }
        }
        enemie1.getImageView().setX(enemie1.getX()-camera.getX()+3000*count_enemie1);
        enemie1.getImageView().setY(enemie1.getY()-camera.getY());
        enemie1.setTime1(count_enemie1);

        if(pane.getChildren().contains(enemie.getImageView()) == true){
            if (hero.Intersect(enemie.hitbox_enemie)){
                if( now_time == 0 ) {
                    invincibility = hero.isInvincible(true);
                    this.now_time = time;
                    numberOfLives -= 1;
                    setNumberOfLives(numberOfLives);
                }
                if( time - now_time > invincibility){
                    now_time = 0;
                }
            }
        }
        if(pane.getChildren().contains(enemie1.getImageView()) == true){
            if (hero.Intersect(enemie1.hitbox_enemie1)){
                if( now_time == 0 ) {
                    invincibility = hero.isInvincible(true);
                    this.now_time = time;
                    numberOfLives -= 1;
                    setNumberOfLives(numberOfLives);
                }
                if( time - now_time > invincibility){
                    now_time = 0;
                }
            }
        }
        if(b == true){
            if (count_enemie%10 == 5) {
                pane.getChildren().add(getHeart.getImageView());
                b = false;
            }
        }
        if(b == false){
            getHeart.update(time,camera.getX(),hero,camera);
        }
        if(hero.Intersect(getHeart.hitbox_heart)){
            if(pane.getChildren().contains(getHeart.getImageView())){
                numberOfLives+=1;
                if(numberOfLives == 3){numberOfLives = 3;}
                addNumberOfLives(numberOfLives);
                pane.getChildren().removeAll(getHeart.getImageView());
            }
        }
        for(int i=0;i<=bulls_max;i++) {
            bulls.get(i).update(time, 0, hero, camera);
            bulls.get(i).getImageView().setX(bulls.get(i).getX_bull() + hero.getX() - camera.getX() + 55);
            bulls.get(i).setXbull(bulls.get(i).getX_bull() + hero.getX() + 55);
            if (pane.getChildren().contains(bulls.get(i).getImageView()) == false) {
                bulls.get(i).getImageView().setY(hero.getY() - camera.getY() + 35);
                bulls.get(i).setYbull(hero.getY() + 35);
            }
            if (bulls.get(i).getX_bull() > 1000) {
                if (pane.getChildren().contains(bulls.get(i).getImageView()) == true) {
                    pane.getChildren().remove(bulls.get(i).getImageView());
                    bulls.get(i).setSpeed(0);
                    bulls.get(i).setX_bull(0);
                    System.out.println(index);
                }
            }
            if (pane.getChildren().contains(bulls.get(i).getImageView()) == true) {
                if (enemie.Intersect_enemie(bulls.get(i).hitbox_bull)) {
                    pane.getChildren().remove(enemie.getImageView());
                    pane.getChildren().remove(bulls.get(i).getImageView());
                }
                if (enemie1.Intersect_enemie1(bulls.get(i).hitbox_bull)) {
                    pane.getChildren().remove(enemie1.getImageView());
                    pane.getChildren().remove(bulls.get(i).getImageView());
                }
            }
            if (pane.getChildren().contains(bulls.get(i).getImageView()) == true){
                this.index -= 1;
                if(this.index<0){this.index = 0;}
            }
        }

        text_bulls.setX(400);
        text_bulls.setY(60);
        text_bulls.setText(toString_bull());
        text_bulls.fontProperty().setValue(font);

        this.index = bulls_max+1;

        if((count_enemie-1)%5 == 0){
            pane.getChildren().remove(getHeart.getImageView());
            b = true;
        }
    }

    public double getNumberOfLives(){
        return numberOfLives;
    }

    @Override
    public String toString() {
        return "score"+":"+(int) this.time;
    }

    public String toString_bull() {
        return "bull"+":"+this.index;
    }
}
