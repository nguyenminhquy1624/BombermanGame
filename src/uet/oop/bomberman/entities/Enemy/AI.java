package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class AI {

    public static int chooseDirection(Enemy enemy) //thĂªm kháº£ nÄƒng nĂ© bom á»Ÿ má»©c cÆ¡ báº£n.
    {

        int ans = enemy.getCurrentDirection();
//        Random rd = new Random(System.currentTimeMillis()); //something like srand in C++
//        int rand = Math.abs(rd.nextInt()) % 100;
//        if(true) {
//
//        }
        ArrayList <Integer> tmp = new ArrayList<Integer>();
        boolean danger = false;
        for(int i = 1; i <= 4; i++) {
            if(enemy.canMove(i)) {
                int x1 = enemy.getX()/32;
                int y1 = enemy.getY()/32;
                boolean danger_1 = false;
                for(int j = 0; j < Board.getPlayer().getMax_radius(); j++) {
                    if(i == 1) x1--;
                    if(i == 2) x1++;
                    if(i == 3) y1--;
                    if(i == 4) y1++;

                    if(x1 >= 0 && y1 >= 0 && y1 < 13 && x1 < 31) {
                        if(Board.map[y1][x1] == 'B') {
                            danger_1 = true;
                            danger = true;
                            break;
                        }
                        else if(Board.map[y1][x1] == '*' || Board.map[y1][x1] == '#') {
                            break;
                        }
                    }
                    else break;
                }
                if(!danger_1)
                tmp.add(new Integer(i));
            }
        }
//
        if(danger && !(enemy instanceof Balloon)) enemy.setSpeed(2);
        else  enemy.setSpeed(1);
        if(tmp.size() > 0) {

            Random random = new Random(System.currentTimeMillis()); //something like srand in C++
            int i = Math.abs(random.nextInt()) % tmp.size();
            ans = tmp.get(i).intValue();
        }

        return ans;
    }
    private static int chase_player(Enemy enemy) throws IllegalStateException{
        ArrayList <Integer> path = new ArrayList<Integer>();
        boolean [] visited = new boolean[BombermanGame.HEIGHT* BombermanGame.WIDTH];
        int [] parent = new int[BombermanGame.HEIGHT* BombermanGame.WIDTH];


        for(int i = 0; i < 403; i++) {
            visited[i] = false;
        }
        int start = enemy.getboard_index();
        int end = Board.getPlayer().getboard_index();
        parent[start] = -1;
        parent[end] = -1;

        Queue <Integer> bfs = new LinkedList<Integer>();
        bfs.add(start);

        visited[start] = true;
        while (!bfs.isEmpty()) {
            int tmp = bfs.poll();
            if(tmp == end) break;
            int x = tmp%BombermanGame.WIDTH;
            int y = tmp/BombermanGame.WIDTH;
            if(x-1 >= 0 && y >= 0 && x-1 < 31 && y < 13) {
                boolean check = visited[y*BombermanGame.WIDTH + x-1] == false && Board.map[y][x-1] != '#' && Board.map[y][x-1] != 'B';
                if(!(enemy instanceof Kodoria)) check = check && Board.map[y][x-1] != '*';
                if(check){
//                    && Board.map[y][x-1] != 'x'  && Board.map[y][x-1] != 'b'
//                            && Board.map[y][x-1] != 'f'  && Board.map[y][x-1] != 's'
                    bfs.add(y*BombermanGame.WIDTH + x-1);
                    visited[y*BombermanGame.WIDTH + x-1] = true;
                    parent[y*BombermanGame.WIDTH + x-1] = tmp;
                    if(y*BombermanGame.WIDTH + x-1 == end) break;
                }
            }
            if(x+1 >= 0 && y >= 0 && x+1 < 31 && y < 13) {
                boolean check = visited[y*BombermanGame.WIDTH + x+1] == false && Board.map[y][x+1] != '#' && Board.map[y][x+1] != 'B';
                if(!(enemy instanceof Kodoria)) check = check && Board.map[y][x+1] != '*';
                if(check){
//                    && Board.map[y][x-1] != 'x'  && Board.map[y][x-1] != 'b'
//                            && Board.map[y][x-1] != 'f'  && Board.map[y][x-1] != 's'
                    bfs.add(y*BombermanGame.WIDTH + x+1);
                    visited[y*BombermanGame.WIDTH + x+1] = true;
                    parent[y*BombermanGame.WIDTH + x+1] = tmp;
                    if(y*BombermanGame.WIDTH + x+1 == end) break;
                }
            }
            if(x >= 0 && y-1 >= 0 && x < 31 && y-1 < 13) {
                boolean check = visited[(y-1)*BombermanGame.WIDTH + x] == false && Board.map[y-1][x] != '#' && Board.map[y-1][x] != 'B';
                if(!(enemy instanceof Kodoria)) check = check && Board.map[y-1][x] != '*';
                if(check){
//                      && Board.map[y][x-1] != 'x'  && Board.map[y][x-1] != 'b'
//                            && Board.map[y][x-1] != 'f'  && Board.map[y][x-1] != 's'
                    bfs.add((y-1)*BombermanGame.WIDTH + x);
                    visited[(y-1)*BombermanGame.WIDTH + x] = true;
                    parent[(y-1)*BombermanGame.WIDTH + x] = tmp;
                    if((y-1)*BombermanGame.WIDTH + x == end) break;
                }
            }
            if(x >= 0 && y+1 >= 0 && x < 31 && y+1 < 13) {
                boolean check = visited[(y+1)*BombermanGame.WIDTH + x] == false && Board.map[y+1][x] != '#' && Board.map[y+1][x] != 'B';
                if(!(enemy instanceof Kodoria)) check = check && Board.map[y+1][x] != '*';
                if(check){
//                   && Board.map[y][x-1] != 'x'  && Board.map[y][x-1] != 'b'
//                            && Board.map[y][x-1] != 'f'  && Board.map[y][x-1] != 's'
                    bfs.add((y+1)*BombermanGame.WIDTH + x);
                    visited[(y+1)*BombermanGame.WIDTH + x] = true;
                    parent[(y+1)*BombermanGame.WIDTH + x] = tmp;
                    if((y+1)*BombermanGame.WIDTH + x == end) break;
                }
            }
        }
        int p = parent[end];

        // thĂªm node cuá»‘i

        if (p != -1) {
            enemy.setDetected(true);
            path.add(end);
            path.add(p);
            while (p != start) { // chu di den goc
                p = parent[p];
                path.add(p);
            }
            return path.get(path.size()-2);
        }
        enemy.setDetected(false);
        return -1;
    }

    public static int chooseDirectionAdvance(Enemy enemy) {
        int start = enemy.getboard_index();
        int end = Board.getPlayer().getboard_index();
        int distance = (int)Math.sqrt((enemy.getXTile()-Board.getPlayer().getXTile())*(enemy.getXTile()-Board.getPlayer().getXTile()) +
                (enemy.getYTile()-Board.getPlayer().getYTile())*(enemy.getYTile()-Board.getPlayer().getYTile()));
        int result = chase_player(enemy);

        if (result == -1 ) {
            enemy.setSpeed(1);
            return chooseDirection(enemy);
            //random.nextInt(4);
        }




        if ( result - start == 1) {
            if(enemy instanceof Oneal) {
                if(distance < 6) {
                    enemy.setSpeed(2);
                    return 2;
                }
                else {
                    enemy.setSpeed(1);
                    return chooseDirection(enemy);
                }
            }
            else if(enemy instanceof Kodoria) {
                if(distance < 6) {
                    enemy.setSpeed(2);
                    return 2;
                }
                else {
                    enemy.setSpeed(1);
                    return chooseDirection(enemy);
                }
            }
            else {
                if(!(enemy instanceof Doll))
                enemy.setSpeed(2);
                return 2;
            }
        }// ben phai
        if ( start -  result == 1) {
            if(enemy instanceof Oneal) {
                if(distance < 6) {
                    enemy.setSpeed(2);
                    return 1;
                }
                else {
                    enemy.setSpeed(1);
                    return chooseDirection(enemy);
                }
            }
            else if(enemy instanceof Kodoria) {
                if(distance < 6) {
                    enemy.setSpeed(2);
                    return 1;
                }
                else {
                    enemy.setSpeed(1);
                    return chooseDirection(enemy);
                }
            }
            else {
                if(!(enemy instanceof Doll))
                enemy.setSpeed(2);
                return 1;
            }
             // ssang trai
        }
        if ( start > result ) {
            if(enemy instanceof Oneal) {
                if(distance < 6) {
                    enemy.setSpeed(2);
                    return 3;
                }
                else {
                    enemy.setSpeed(1);
                    return chooseDirection(enemy);
                }
            }
            else if(enemy instanceof Kodoria) {
                if(distance < 6) {
                    enemy.setSpeed(2);
                    return 3;
                }
                else {
                    enemy.setSpeed(1);
                    return chooseDirection(enemy);
                }
            }
            else {
                if(!(enemy instanceof Doll))
                enemy.setSpeed(2);
                return 3; // len tren
            }
        }
        if ( start < result ) {
            if(enemy instanceof Oneal) {
                if(distance < 6) {
                    enemy.setSpeed(2);
                    return 4;
                }
                else {
                    enemy.setSpeed(1);
                    return chooseDirection(enemy);
                }
            }
            else if(enemy instanceof Kodoria) {
                if(distance < 6) {
                    enemy.setSpeed(2);
                    return 4;
                }
                else {
                    enemy.setSpeed(1);
                    return chooseDirection(enemy);
                }
            }
            else {
                if(!(enemy instanceof Doll))
                enemy.setSpeed(2);
                return 4; // duoi
            }
        }
//        enemy.setDetected(false);
        enemy.setSpeed(1);
        return chooseDirection(enemy);
    }
}
