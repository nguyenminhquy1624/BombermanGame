package uet.oop.bomberman.entities.Bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Sound;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.IntStream;

public class Bomb extends Entity {

    private double timeToExplode = 120;
    private int timeAfter = 30;
    private boolean exploded;
    protected ExplosionDirection[] explosionDirections = new ExplosionDirection[4];
    private int radius;

    @Override
    public Image getImg() {
        return super.getImg();
    }

    @Override
    public void setImg(Image img) {
        super.setImg(img);
    }


    public Bomb(int x, int y,boolean remove,Image img) {

        super(x, y, img);
        Board.map[y][x] = 'B';
        remove = this.remove;
        exploded = false;
        radius = Board.getPlayer().getMax_radius();
    }

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public void setY(int y) {
        super.setY(y);
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    @Override
    public void render(GraphicsContext gc) {
        if(!exploded && !remove) {
            setImg(Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, 60-(int)(timeToExplode)/2, 60).getFxImage());
            super.render(gc);
        }
        else {
            if(!remove) {
                setImg(Sprite.movingSprite(Sprite.bomb_exploded,
                        Sprite.bomb_exploded1, Sprite.bomb_exploded2, timeAfter, 30).getFxImage());
            }
            super.render(gc);
            for(int i = 0;  i< 4; i++) {
                explosionDirections[i].render(gc);
            }
        }

    }

    public void setTimeToExplode(double timeToExplode) {
        this.timeToExplode = timeToExplode;
    }

    @Override
    public void update() {
        if (!remove) {
            if (timeToExplode > 0)
                timeToExplode--;
            else {
                if (!exploded)
                    explosion();
                if (BombermanGame.x) {
                	Sound.play("BOM_EXPLODE");
        		}
                
                if (timeAfter > 0) {
                    timeAfter--;
                    updateExplosions();
                } else {

                    remove = true;
                }
            }
        }
        if (remove && timeAfter == 0) {
            Board.map[y/32][x/32] = ' ';
            BombermanGame.board.getPlayer().removeBombAt(this.x, this.y);
        }
    }
    public void explosion() {
        exploded = true;
        explosionDirections[0] = new ExplosionDirection((x/32), y/32, 1, this.radius);
        explosionDirections[1] = new ExplosionDirection(x/32, y/32, 2, this.radius);
        explosionDirections[2] = new ExplosionDirection(x/32, y/32, 3, this.radius);
        explosionDirections[3] = new ExplosionDirection(x/32, y/32, 4, this.radius);

    }
    public void updateExplosions(){
        ArrayList <Enemy> tmp = BombermanGame.board.getEnemies();
        int n = tmp.size();
        for(int i = 0; i < n; i++) {
            tmp.get(i).collidetodie(this);
        }
        Board.getPlayer().collidetoDie(this);
        for (ExplosionDirection explosion : explosionDirections) {
            explosion.update( timeAfter);
        }
    }
    @Override
    public HashSet<String> getMask() {
        return super.getMask();
    }
}
