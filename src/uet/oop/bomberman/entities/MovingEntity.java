package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class MovingEntity extends Entity {
//    protected int board_x;
//    protected int board_y;
    protected int speed;
    protected int left = 0;
    protected int right = 0;
    protected int up = 0;
    protected int down = 0;
    protected int dietime = 0;
    protected final int animate = 12;
    protected boolean dead = false;
    public MovingEntity(int x, int y, Image img, int speed) {

        super(x, y, img);
//        board_x = x;
//        board_y = y;
        this.speed = speed;
    }

    @Override
    public abstract void update();

//    public int getBoard_x() {
//        return board_x;
//    }
//
//    public void setBoard_x(int board_x) {
//        this.board_x = board_x;
//    }
//
//    public int getBoard_y() {
//        return board_y;
//    }
//
//    public void setBoard_y(int board_y) {
//        this.board_y = board_y;
//    }

    public int getboard_index() {
        int x = (this.x)/32 ;
        int y = (this.y)/32 ;
        return y*BombermanGame.WIDTH + x;
    }
    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    //    public boolean canMove (Board board, int x, int y) {
//        if(board.getStillObject(x, y) != null) {
//            if(board.getStillObject(x, y) instanceof Grass) {
//                return true;
//            }
//            else return false;
//        }
//        else return false;
//    }
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDietime() {
        return dietime;
    }

    public void setDietime(int dietime) {
        this.dietime = dietime;
    }

    public HashSet <String> getDynamicMask (int direction, int distance) {
        /*
        direction là số chỉ hướng đi hiện tại (dùng để check xem có đi chuyển được vào hướng đó không).
        1: trạng thái sẽ đi sang trái
        2: trạng thái sẽ đi sang phải
        3: trạng thái sẽ đi lên trên
        4: trạng thái sẽ đi xuống dưới
         */
        int speed_X = 0;
        int speed_Y = 0;
        if(direction == 1) {
            speed_X = -speed * distance;
            speed_Y = 0;
        }
        if(direction == 2) {
            speed_X = speed * distance;
            speed_Y = 0;
        }
        if(direction == 3) {
            speed_X = 0;
            speed_Y = -speed * distance;
        }
        if(direction == 4) {
            speed_X = 0;
            speed_Y = speed * distance;
        }
        Image img = this.img;
        HashSet<String> mask = null;
        if (img != null) {
            mask = new HashSet<String>();
            int W = (int) img.getWidth();
            int H = (int) img.getHeight();

            PixelReader reader = img.getPixelReader();

            int a;
            for (int y = 0; y < H; y++) {
                for (int x = 0; x < W; x++) {
                    final int argb = reader.getArgb(x, y);
                    a = (argb >> 24) & 0xff;
                    if (a != 0) {
                        mask.add((this.getX() + x + speed_X) + "," + (this.getY() + y + speed_Y));
                    }
                }
            }
        }
        return mask;
    }
    public abstract boolean canMove(int direction);

    public abstract void moving();
    public abstract void moveLeft() ;
    public abstract void moveRight();
    public abstract void moveUp();
    public abstract void moveDown();
}
