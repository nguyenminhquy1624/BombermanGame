package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Sound;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.HashSet;

public class Balloon extends Enemy {
    private int time = 0;
    public Balloon(int x, int y, Image img, int speed) {
        super(x, y, img, speed);
        currentDirection = 1;
        time = 0;

    }


    @Override
    public void update() {
        if(!dead) {
//            if(x%32 == 0 && y%32 == 0) {
//                Board.map[board_y][board_x] = ' ';
//                board_x = x/32;
//                board_y = y/32;
//                Board.map[board_y][board_x] = '1';
//            }
            moving();
        }
        else {
//            if(x%32 == 0 && y%32 == 0) {
//                Board.map[board_y][board_x] = ' ';
//                board_x = x/32;
//                board_y = y/32;
//                Board.map[board_y][board_x] = '1';
//            }
            this.setImg(Sprite.balloom_dead.getFxImage());
            if(dietime < 30) dietime++;
            else {
                //Board.map[y/32][x/32] = ' ';
            	if (BombermanGame.x) {
            		Sound.play("PLAYER_DEAD");
				}
                
                Board.removeEnemyAt(this.x, this.y);
                Board.score += 20;
            }

        }
    }

    @Override
    public boolean canMove(int direction) {
        boolean ans = true;
//        HashSet <String> check = this.getDynamicMask(direction, 1);
//        ArrayList <Entity> temp = Board.getStillObject();
//        int n = temp.size();
//        for(int i = 0; i < n; i++) {
//            Entity tmp = temp.get(i);
//            if(tmp instanceof Wall) {
//                HashSet <String> tmp_set = tmp.getMask();
//                tmp_set.retainAll(check);
//                if(!tmp_set.isEmpty()) {
//                    ans = false;
//                    break;
//                }
//            }
//        }
        if(direction == 1) {
            if(y%32 == 0) {
                ans = (BombermanGame.board.getstillObjectAt(x-speed, y) == null
                        && BombermanGame.board.getstillObjectAt(x-speed,y+ Sprite.SCALED_SIZE-1) == null &&
                        BombermanGame.board.getEntityAt(x-speed, y) == null
                        && BombermanGame.board.getEntityAt(x-speed,y+ Sprite.SCALED_SIZE-1) == null);
                ArrayList <Bomb> bombs = Board.getPlayer().getBombs();
                int n = bombs.size();
                for(int i = 0; i < n; i++) {
                    Bomb tmp = bombs.get(i);
                    if(!collide(tmp)) {
                        if((x- speed >= tmp.getX() && y>= tmp.getY()) &&
                                (x-speed < tmp.getX() + Sprite.SCALED_SIZE && y < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                        else if((x-speed >= tmp.getX() && y+ Sprite.SCALED_SIZE-1 >= tmp.getY()) &&
                                (x-speed < tmp.getX() + Sprite.SCALED_SIZE && y+ Sprite.SCALED_SIZE-1 < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                    }
                }
            }
            else ans = false;


        }
        if(direction == 2) {
            if(y%32 == 0) {
                ans = (BombermanGame.board.getstillObjectAt(x + Sprite.SCALED_SIZE-1 + speed, y) == null
                        && BombermanGame.board.getstillObjectAt(x + Sprite.SCALED_SIZE-1 + speed,y+Sprite.SCALED_SIZE-1) == null &&
                        BombermanGame.board.getEntityAt(x + Sprite.SCALED_SIZE-1 + speed, y) == null
                        && BombermanGame.board.getEntityAt(x + Sprite.SCALED_SIZE-1 + speed,y+Sprite.SCALED_SIZE-1) == null);
                ArrayList <Bomb> bombs = Board.getPlayer().getBombs();
                int n = bombs.size();
                for(int i = 0; i < n; i++) {
                    Bomb tmp = bombs.get(i);
                    if(!collide(tmp)) {
                        if((x+ Sprite.SCALED_SIZE - 1 + speed >= tmp.getX() && y >= tmp.getY()) &&
                                (x + Sprite.SCALED_SIZE - 1 + speed < tmp.getX() + Sprite.SCALED_SIZE && y < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                        else if((x + Sprite.SCALED_SIZE - 1 + speed >= tmp.getX() && y+ Sprite.SCALED_SIZE-1 >= tmp.getY()) &&
                                (x + Sprite.SCALED_SIZE - 1 + speed < tmp.getX() + Sprite.SCALED_SIZE && y+ Sprite.SCALED_SIZE-1 < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                    }

                }
            }
            else ans = false;
        }
        if(direction == 3) {
            if(x%32 == 0) {
                ans = (BombermanGame.board.getstillObjectAt(x, y - speed) == null
                        && BombermanGame.board.getstillObjectAt(x + Sprite.SCALED_SIZE-1,y - speed) == null &&
                        BombermanGame.board.getEntityAt(x, y - speed) == null
                        && BombermanGame.board.getEntityAt(x + Sprite.SCALED_SIZE-1,y - speed) == null);
                ArrayList <Bomb> bombs = Board.getPlayer().getBombs();

                int n = bombs.size();
                for(int i = 0; i < n; i++) {
                    Bomb tmp = bombs.get(i);
                    if(!collide(tmp)) {
                        if((x >= tmp.getX() && y - speed >= tmp.getY()) &&
                                (x < tmp.getX() + Sprite.SCALED_SIZE && y - speed < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                        else if((x + Sprite.SCALED_SIZE-1 >= tmp.getX() && y - speed >= tmp.getY()) &&
                                (x + Sprite.SCALED_SIZE-1 < tmp.getX() + Sprite.SCALED_SIZE && y - speed < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                    }

                }
            }
            else ans = false;
        }
        if(direction == 4) {
            if(x%32 == 0) {
                ans = (BombermanGame.board.getstillObjectAt(x, y + Sprite.SCALED_SIZE-1 + speed) == null
                        && BombermanGame.board.getstillObjectAt(x + Sprite.SCALED_SIZE-1,y + Sprite.SCALED_SIZE-1 + speed) == null &&
                        BombermanGame.board.getEntityAt(x, y + Sprite.SCALED_SIZE-1 + speed) == null
                        && BombermanGame.board.getEntityAt(x + Sprite.SCALED_SIZE-1,y + Sprite.SCALED_SIZE-1 + speed) == null);
                ArrayList <Bomb> bombs = Board.getPlayer().getBombs();
                int n = bombs.size();
                for(int i = 0; i < n; i++) {
                    Bomb tmp = bombs.get(i);
                    if(!collide(tmp)) {
                        if((x >= tmp.getX() && y + 31 + speed >= tmp.getY()) &&
                                (x < tmp.getX() + Sprite.SCALED_SIZE && y + 31 + speed < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                        else if((x + Sprite.SCALED_SIZE-1  >= tmp.getX() && y + 31 + speed >= tmp.getY()) &&
                                (x + Sprite.SCALED_SIZE-1  < tmp.getX() + Sprite.SCALED_SIZE && y+ 31 + speed < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                    }
                }
            }
           else ans = false;

        }
        return ans;
    }

    @Override
    public void moving() {
        if(x%32 == 0 && y%32 == 0)
        currentDirection = AI.chooseDirection(this);
        //else currentDirection = AI.chooseDirection(this);
        switch (currentDirection) {
            case 1:
                this.moveLeft();
                break;
            case 2:
                this.moveRight();
                break;
            case 3:
                this.moveUp();
                break;
            case 4:
                this.moveDown();
                break;
        }
    }

    @Override
    public void moveLeft() {
        currentDirection = 1;
        right = 0;
        up = 0;
        down = 0;
        img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, left, animate).getFxImage();
        if(left < animate) left++;
        else left = 1;
        if(canMove(1))
            x -= speed;
    }

    @Override
    public void moveRight() {
        currentDirection = 2;
        left = 0;
        up = 0;
        down = 0;
        img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, right, animate).getFxImage();
        if(right < animate) right++;
        else right = 1;
        if(canMove(2))
            x += speed;
    }

    @Override
    public void moveUp() {
        currentDirection = 3;
        left = 0;
        right = 0;
        down = 0;
        img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, up, animate).getFxImage();
        if(up < animate) up++;
        else up = 1;
        if(canMove(3))
            y -= speed;
    }

    @Override
    public void moveDown() {
        currentDirection = 4;
        left = 0;
        right = 0;
        up = 0;
        img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_left3, down, animate).getFxImage();
        if(down < animate) down++;
        else down = 1;
        if(canMove(4))
            y += speed;
    }

}
