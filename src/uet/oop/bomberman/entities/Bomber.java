package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.entities.Item.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.HashSet;

public class Bomber extends MovingEntity {

    public static final int ability_time = 600; //thá»�i gian tá»‘i Ä‘a cĂ³ thĂª sá»­ dá»¥ng Ä‘Æ°á»£c siĂªu nÄƒng lá»±c sau khi Äƒn váº­t pháº©m
    private ArrayList <Bomb> bombs;
    private int hearts; // Sá»‘ máº¡ng chÆ¡i
    private int Fpass; // Thá»�i gian cĂ²n láº¡i cĂ³ kháº£ nÄƒng khĂ¡ng lá»­a
    private int Wpass; //Thá»�i gian cĂ²n láº¡i cĂ³ kháº£ nÄƒng Ä‘i xuyĂªn tÆ°á»�ng
    private int Speed_up; // Thá»�i gian cĂ²n láº¡i cĂ³ kháº£ nÄƒng cháº¡y nhanh
    private int max_radius; //BĂ¡n kĂ­nh bom
    private int F_item;// Thá»�i gian cĂ²n láº¡i cĂ³ kháº£ nÄƒng tÄƒng bĂ¡n kĂ­nh bom
    private int Bomb_limit; //LÆ°á»£ng bom nhiá»�u nháº¥t Ä‘Æ°á»£c Ä‘áº·t
    private boolean win;
    private int immortal;
    public Bomber(int x, int y, Image img, int speed) {
        super(x, y, img, speed);
        bombs = new ArrayList<Bomb>();
        hearts = 1;
        Fpass = 0;
        Speed_up = 0;
        max_radius = 2;
        F_item = 0;
        Bomb_limit = 1;
        win = false;
        immortal = 180;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public ArrayList <Bomb> getBombs() {
        return bombs;
    }

    public boolean addBomb(Bomb bomb) {
        int n = bombs.size();
        boolean check = true;
        if(BombermanGame.board.getEntityAt(bomb.getX(), bomb.getY()) != null ||
        BombermanGame.board.getstillObjectAt(bomb.getX(), bomb.getY()) != null)
            check = false;
        for(int i = 0; i < n; i++) {
            Bomb tmp = bombs.get(i);
            if(bomb.getX() == tmp.getX() && bomb.getY() == tmp.getY()) {
                check = false;
                break;
            }
        }
        if(check == true) {
            bombs.add(bomb);
        }
        return check;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public int getFpass() {
        return Fpass;
    }

    public void setFpass(int fpass) {
        Fpass = fpass;
    }

    public int getMax_radius() {
        return max_radius;
    }

    public void setMax_radius(int max_radius) {
        this.max_radius = max_radius;
    }

    public void removeBombAt(int x, int y) {
        int n = bombs.size();
        boolean check = true;
        for(int i = 0; i < n; i++) {
            Bomb tmp = bombs.get(i);
            if(x == tmp.getX() && y == tmp.getY()) {
               bombs.remove(i);
               Board.map[y/32][x/32] = ' ';
               break;
            }
        }
    }
    public Bomb getBombAt(int x, int y) {
        int n = bombs.size();
        boolean check = true;
        for(int i = 0; i < n; i++) {
            Bomb tmp = bombs.get(i);
            if(x == tmp.getX() && y == tmp.getY()) {
               return tmp;
            }
        }
        return null;
    }
    public ArrayList<Bomb> getbombs() {
        return bombs;
    }
    @Override
    public void update() {
        collide();
        for(int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();
        }
        if(!dead) {
            if(Speed_up > 0) {
                setSpeed(4);
                Speed_up--;
            }
            else setSpeed(2);

            if (Fpass > 0) Fpass--;
            if(Wpass > 0) Wpass--;
            if(immortal > 0) immortal--;
            if(Wpass <= 0 && Board.map[y/32][x/32] == '*') Wpass = 60;
            if(F_item > 0)
            {
                max_radius = 3;
                F_item--;
            }
            else max_radius = 2;
            moving();
            placebomb();
        }
        else {
            if(Fpass > 0) Fpass = 0;
            if(F_item > 0) F_item = 0;
            if(Speed_up > 0) Speed_up = 0;
            if(Wpass > 0) Wpass = 0;
            if(Bomb_limit > 1) Bomb_limit = 1;
            this.setImg(Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, dietime, 60).getFxImage());
            if(dietime < animate) dietime++;
            else {

                if(hearts > 0) {
                	if (BombermanGame.x) {
                		Sound.play("PLAYER_DEAD");
					}
                    
                    dead = false;
                    immortal = 180;
                    hearts--;
                    setX(32);
                    setY(32);
                }
                else {
                    dead = true;
//                    Sound.play("GAME_OVER");
                    setRemove(true);
                }
//                for(int i = 0; i < Board.HEIGHT; i++) {
//                    for(int j = 0; j < Board.WIDTH; j++) {
//                        System.out.print(Board.map[i][j]);
//                    }
//                    System.out.print("\n");
//                }
//                System.out.print("\n");
            }
        }
    }

    @Override
    public void moving() {
        if(BombermanGame.playerController.left) {
            this.moveLeft();
        }
        if(BombermanGame.playerController.right) {
            this.moveRight();
        }
        if(BombermanGame.playerController.up) {
            this.moveUp();
        }
        if (BombermanGame.playerController.down) {
            this.moveDown();
        }
    }
    public void placebomb() {
        if (BombermanGame.playerController.space && bombs.size() < Bomb_limit) {

            int x = (this.x)/32;
            int y = (this.y)/32;
//            if(img == Sprite.player_right.getFxImage() || img == Sprite.player_right_1.getFxImage() || img == Sprite.player_right_2.getFxImage()) {
//                if(this.x - x*32 > 23)x++;
//                if(this.y - y*32 > 23)y++;
//            }
//            else if(img == Sprite.player_right.getFxImage() || img == Sprite.player_right_1.getFxImage() || img == Sprite.player_right_2.getFxImage()){
//                if(this.x - x*32 > 23)x++;
//                if(this.y - y*32 > 23)y++;
//            }
//            else {
//                if(this.x - x*32 > 16)x++;
//                if(this.y - y*32 > 16)y++;
//            }
//            addBomb(new Bomb(x, y,false,Sprite.bomb.getFxImage()))
            if(Board.map[y][x] == ' ') {
                this.bombs.add(new Bomb(x, y, false, Sprite.bomb.getFxImage()));
                if (BombermanGame.x) {
                	Sound.play("BOM_SET");
				}
                Board.map[y][x] = 'B';
//                System.out.println("BOMB SET!!!");
            }
        }
    }
    @Override
    public boolean canMove(int direction) {
        boolean ans = true;
        if(direction == 1) {
            ans = (BombermanGame.board.getstillObjectAt(x-speed, y+1) == null
                    && BombermanGame.board.getstillObjectAt(x-speed,y+ Sprite.SCALED_SIZE-3) == null);
            int n = bombs.size();
            for(int i = 0; i < n; i++) {
                Bomb tmp = bombs.get(i);
                if(!this.collide(tmp)) {
                    if(this.x/32 != tmp.getX()/32 || this.y/32 != tmp.getY()/32) {
                        if ((x - 2 * speed >= tmp.getX() && y + 1 >= tmp.getY()) &&
                                (x - 2 * speed < tmp.getX() + Sprite.SCALED_SIZE && y + 1 < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        } else if ((x - speed * 2 >= tmp.getX() && y + Sprite.SCALED_SIZE - 3 >= tmp.getY()) &&
                                (x - speed * 2 < tmp.getX() + Sprite.SCALED_SIZE && y + Sprite.SCALED_SIZE - 3 < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                    }
                }
            }
            if(ans) {
                if(Wpass <= 0 && BombermanGame.board.getEntityAt(x-speed, y+1) != null
                        && BombermanGame.board.getEntityAt(x-speed,y+ Sprite.SCALED_SIZE-3) != null)
                    ans = false;
            }

        }
        if(direction == 2) {
            int size = 20;
            if(right < animate/3) size = 20;
            else if(right > animate*2/3 && right < animate) size = 22;
            else size = 24;
            ans = (BombermanGame.board.getstillObjectAt(x + size + speed, y+1) == null
                    && BombermanGame.board.getstillObjectAt(x + size + speed,y+Sprite.SCALED_SIZE-3) == null);
            int n = bombs.size();
            for(int i = 0; i < n; i++) {
                Bomb tmp = bombs.get(i);
                if(!this.collide(tmp)) {
                    if(this.x/32 != tmp.getX()/32 || this.y/32 != tmp.getY()/32) {
                        if((x+ size + speed*2 >= tmp.getX() && y+ 1 >= tmp.getY()) &&
                                (x + size + speed*2 < tmp.getX() + Sprite.SCALED_SIZE && y+ 1 < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                        else if((x + size + speed*2 >= tmp.getX() && y+ Sprite.SCALED_SIZE-3 >= tmp.getY()) &&
                                (x + size + speed*2 < tmp.getX() + Sprite.SCALED_SIZE && y+ Sprite.SCALED_SIZE-3 < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                    }
                }

            }
            if(ans){
                if(Wpass <= 0 &&
                        BombermanGame.board.getEntityAt(x + size + speed, y+1) != null
                        && BombermanGame.board.getEntityAt(x + size + speed,y+Sprite.SCALED_SIZE-3) != null)
                    ans = false;
            }
        }
        if(direction == 3) {
            int size = 20;
            if(up < animate/3) size = 20;
            else if(up > animate*2/3 && up < animate) size = 22;
            else size = 20;
            ans = (BombermanGame.board.getstillObjectAt(x, y - speed) == null
                    && BombermanGame.board.getstillObjectAt(x + size,y - speed) == null);
            int n = bombs.size();
            for(int i = 0; i < n; i++) {
                Bomb tmp = bombs.get(i);
                if(!collide(tmp)) {
                    if(this.x/32 != tmp.getX()/32 || this.y/32 != tmp.getY()/32) {
                        if((x >= tmp.getX() && y - 2*speed >= tmp.getY()) &&
                                (x < tmp.getX() + Sprite.SCALED_SIZE && y - 2*speed < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                        else if((x + size >= tmp.getX() && y - 2*speed >= tmp.getY()) &&
                                (x + size < tmp.getX() + Sprite.SCALED_SIZE && y - 2*speed < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                    }
                }

            }
            if(ans) {
                if(Wpass <= 0 &&
                        BombermanGame.board.getEntityAt(x, y - speed) != null
                        && BombermanGame.board.getEntityAt(x + size,y - speed) != null)
                    ans = false;
            }
        }
        if(direction == 4) {
            int size = 22;
            if(down > animate*2/3 && down < animate) size = 20;
            else size = 20;
            ans = (BombermanGame.board.getstillObjectAt(x, y + 30 + speed) == null
                    && BombermanGame.board.getstillObjectAt(x + size,y + 30 + speed) == null);
            int n = bombs.size();
            for(int i = 0; i < n; i++) {
                Bomb tmp = bombs.get(i);
                if(!collide(tmp)) {
                    if(this.x/32 != tmp.getX()/32 || this.y/32 != tmp.getY()/32) {
                        if((x >= tmp.getX() && y + 30 + 2*speed >= tmp.getY()) &&
                                (x < tmp.getX() + Sprite.SCALED_SIZE && y + 30 + 2*speed < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                        else if((x + size  >= tmp.getX() && y + 30 + 2*speed >= tmp.getY()) &&
                                (x + size  < tmp.getX() + Sprite.SCALED_SIZE && y+ 30 + 2*speed < tmp.getY() + Sprite.SCALED_SIZE)) {
                            ans = false;
                            break;
                        }
                    }
                }
            }
            if(ans) {
                if (Wpass <= 0 &&
                        BombermanGame.board.getEntityAt(x, y + 30 + speed) != null
                        && BombermanGame.board.getEntityAt(x + size,y + 30 + speed) != null)
                    ans = false;
            }
        }
        return ans;
    }

    @Override
    public void moveLeft() {
        right = 0;
        up = 0;
        down = 0;
        img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, left, animate).getFxImage();
        if(left < animate) left++;
        else left = 1;
        if(canMove(1))
        x -= speed;
    }

    @Override
    public void moveRight() {
        left = 0;
        up = 0;
        down = 0;
        img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, right, animate).getFxImage();
        if(right < animate) right++;
        else right = 1;
        if(canMove(2))
        x += speed;
    }

    @Override
    public void moveUp() {
        left = 0;
        right = 0;
        down = 0;
        img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, up, animate).getFxImage();
        if(up < animate) up++;
        else up = 1;
        if(canMove(3))
        y -= speed;
    }

    @Override
    public void moveDown() {
        left = 0;
        right = 0;
        up = 0;
        img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, down, animate).getFxImage();
        if(left < animate) left++;
        else left = 1;
        if(canMove(4))
        y += speed;
    }
    public void collidetoDie(Entity entity) {
        HashSet <String> check = this.getMask();
        HashSet <String> tmp = entity.getMask();
        check.retainAll(tmp);
        if(!check.isEmpty() && immortal <= 0)
            dead = true;
    }

    public void collideWithItem(Item item) {
        HashSet <String> check = this.getMask();
        HashSet <String> tmp = item.getMask();
        check.retainAll(tmp);
        if(!check.isEmpty()) {
            if(item instanceof FlamePass) {
                Fpass = ability_time;
                item.setActive(true);
                if (BombermanGame.x) {
                	Sound.play("POWER_UP");
				}
                
            }
            if(item instanceof Portal) {
                if(BombermanGame.board.getEnemies().size() <= 0) {
                    item.setActive(true);
                    win = true;
                    if (BombermanGame.x) {
                    	Sound.play("POWER_UP");
    				}
                }

            }
            if(item instanceof BombItem) {
                Bomb_limit++;
                item.setActive(true);
                if (BombermanGame.x) {
                	Sound.play("POWER_UP");
				}
            }
            if(item instanceof FlameItem) {
                F_item = ability_time;
                item.setActive(true);
                if (BombermanGame.x) {
                	Sound.play("POWER_UP");
				}
            }
            if(item instanceof SpeedItem) {
                Speed_up = ability_time;
                item.setActive(true);
                if (BombermanGame.x) {
                	Sound.play("POWER_UP");
				}
            }
            if(item instanceof WallPass) {
                Wpass = ability_time;
                item.setActive(true);
                if (BombermanGame.x) {
                	Sound.play("POWER_UP");
				}
            }
            if(item instanceof HeartItem) {
                this.hearts++;
                item.setActive(true);
                if (BombermanGame.x) {
                	Sound.play("POWER_UP");
				}
            }
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);

        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).render(gc);
        }
    }
    public void collide() {
        for(int i = 0; i < BombermanGame.board.getItems().size(); i++) {
            collideWithItem(BombermanGame.board.getItems().get(i));
        }

        for (int i = 0; i < BombermanGame.board.getEnemies().size(); i++) {
            collidetoDie(BombermanGame.board.getEnemies().get(i));
        }
    }

    public boolean collide(Entity entity) {
        HashSet<String> check = this.getMask();
        HashSet <String> tmp = entity.getMask();
        check.retainAll(tmp);
        if(!check.isEmpty())return true;
        else return false;
    }
}
