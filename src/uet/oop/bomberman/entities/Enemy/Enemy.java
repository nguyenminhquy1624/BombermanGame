package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity;

import java.util.HashSet;

public abstract class Enemy  extends MovingEntity {

    protected int currentDirection = 1;
    protected int dietime = 0;
    protected boolean detected;
    public Enemy(int x, int y, Image img, int speed) {
        super(x, y, img, speed);
        this.currentDirection = 1;
        this.dietime = 0;
        detected = false;
    }

    public void setDetected(boolean detected) {
        this.detected = detected;
    }

    public boolean isDetected() {
        return detected;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
    }

    @Override
    public abstract void moveLeft();

    @Override
    public abstract void moveRight();

    @Override
    public abstract void update();

    @Override
    public abstract boolean canMove(int direction);

    @Override
    public abstract void moving();

    @Override
    public abstract void moveUp();

    @Override
    public abstract void moveDown();

    public void collidetodie(Entity entity) {
        HashSet<String> check = this.getMask();
        HashSet <String> tmp = entity.getMask();
        check.retainAll(tmp);
        if(check.size() > 200)
            dead = true;
    }
    public boolean collide(Entity entity) {
        HashSet<String> check = this.getMask();
        HashSet <String> tmp = entity.getMask();
        check.retainAll(tmp);
        if(!check.isEmpty())return true;
        else return false;
    }

}
