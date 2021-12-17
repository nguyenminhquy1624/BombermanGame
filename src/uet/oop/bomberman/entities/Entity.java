package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.graphics.Sprite;
import javafx.geometry.Rectangle2D;

import java.util.HashSet;


public abstract class Entity {
    //Tá»�a Ä‘á»™ X tĂ­nh tá»« gĂ³c trĂ¡i trĂªn trong Canvas
    protected int x;

    //Tá»�a Ä‘á»™ Y tĂ­nh tá»« gĂ³c trĂ¡i trĂªn trong Canvas
    protected int y;
    protected boolean remove;
    protected Image img;

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Entity() {

    }

    //Khá»Ÿi táº¡o Ä‘á»‘i tÆ°á»£ng, chuyá»ƒn tá»« tá»�a Ä‘á»™ Ä‘Æ¡n vá»‹ sang tá»�a Ä‘á»™ trong canvas
    public Entity( int x, int y, Image img) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.img = img;
        remove = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXTile() {
        return (x)/32;
    }
    public int getYTile() {
        return (y)/32;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y+32);
    }
    public abstract void update();

    public HashSet <String> getMask() {

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
                        mask.add((this.getX()  + x) + "," + (this.getY() + y));
                    }
                }
            }
        }
        return mask;
    }

}
