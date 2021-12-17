package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Item.Item;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.HashSet;

public class Brick extends Entity {
    int broke = 0;
    private Item itemhidden;
    @Override
    public Image getImg() {
        return super.getImg();
    }

    @Override
    public void setImg(Image img) {
        super.setImg(img);
    }

    public Brick(int x, int y, Image img, Item itemhidden) {
        super(x, y, img);
        this.itemhidden = itemhidden;
    }

    public Item getItemhidden() {
        return itemhidden;
    }

    public void setItemhidden(Item itemhidden) {
        this.itemhidden = itemhidden;
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

    @Override
    public void render(GraphicsContext gc) {

        super.render(gc);
    }

    @Override
    public void update() {
//        collidetobroken();
        if(remove) {
            this.setImg(Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2,broke, 30).getFxImage());

            if(broke < 30) broke++;
            else {
                BombermanGame.board.removeEntityAt(this.x, this.y);
                Board.map[y/32][x/32] = ' ';
                if(itemhidden != null) {
                    BombermanGame.board.getItems().add(itemhidden);
                }
            }
        }
    }

    @Override
    public HashSet<String> getMask() {
        return super.getMask();
    }

//    public void collidetobroken() {
//        HashSet <String> check = this.getMask();
//        ArrayList <Bomb> tmp = Board.getPlayer().getBombs();
//        int n = tmp.size();
//        for(int i = 0; i < n; i++) {
//            if(tmp.get(i).isExploded()) {
//                HashSet <String> temp = tmp.get(i).getMask();
//                temp.retainAll(check);
//                if(temp.size() > 0) {
//                    remove = true;
//                    break;
//                }
//            }
//
//        }
//    }
}
