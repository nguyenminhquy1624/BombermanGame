package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.Board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Advance {
//    Bomber _bomber;
//    Enemy _e;



    public ArrayList<Integer> path = new ArrayList();// đường đi ngắn nhất
    public int height = Board.HEIGHT; // cột dọc
    public int width  = Board.WIDTH;	// cột ngang

    public int[][] node = new int[height*width][4];  //ma trận đỉnh kề
    public int numOfNode = height*width;	 // số lượng đỉnh tối đa
    public int[][] matrix = new int[height][width];  // ma trận đỉnh gồm cái số nguyên âm 0 và nguyên dương. ma trận 2 chiều


    public Advance(){}


    // phần này dùng cho thuật toán BFS
    //gia tri matrix[x][y] la ten cua dinh tuong ung voi vi tri x y
    public static int chooseDirection(Enemy enemy) {

        int ans = enemy.getCurrentDirection();
        Random rd = new Random(System.currentTimeMillis()); //something like srand in C++
        int rand = Math.abs(rd.nextInt()) % 100;
        if(rand < 10) {
            ArrayList <Integer> tmp = new ArrayList<Integer>();
            for(int i = 1; i <= 4; i++) {
                if(enemy.canMove(i)) {
                    tmp.add(new Integer(i));
                }
            }
            if(tmp.size() > 0) {
                Random random = new Random(System.currentTimeMillis()); //something like srand in C++
                int i = Math.abs(random.nextInt()) % tmp.size();
                ans = tmp.get(i).intValue();
            }
        }

        return ans;
    }

    public  void getMatrix(){



        int nameOfVertext=1;
        for ( int i = 0 ; i < height ; i++){
            for ( int j = 0 ; j < width ; j++){
                if (  Board.map[i][j] =='#'  ){
                    matrix[i][j]=0;
                }
                else if ( Board.map[i][j] =='*'){
                    matrix[i][j]=nameOfVertext*(-1);
                    nameOfVertext++;
                }else {
                    this.matrix[i][j]=nameOfVertext;
                    nameOfVertext++;
                }

            }

        }


    }


    /**
     *  cập nhập lại nhưng viên gạch đã phá
     */

//    public void updateDestroy_Brick(){
//        if ( Brick.Xgachvo.isEmpty() ) return;
//
//        for ( int i = 0 ;i < Brick.Xgachvo.size();i++ ){
//            int Xgach = Brick.Xgachvo.get(i);
//            int Ygach = Brick.Ygachvo.get(i);
//            // kiểm tra bị phá chưa
//            if ( matrix [Ygach][Xgach] < 0 ){
//                //  nếu chưa phá ( tức đang âm ) thì cho nó  dương ( tức phá rồi)
//                //System.out.println("Sout AI medium da doi thanh cong :  x = " + Xgach + " y = "+ Ygach + " dinh thu " + matrix [Ygach][Xgach]);
//                matrix [Ygach][Xgach] =  matrix [Ygach][Xgach]*(-1);
//
//            }
//
//        }
//
//    }





    /**
     từ ma trận đỉnh chuyển sang ma trân cạnh kề dùng cho bfs

     */


    public void convertNearNodeMatrix(){

        for ( int i =1 ; i < 12 ; i++){
            for ( int j=1 ;j< 30 ; j++ ){
                if ( this.matrix[i][j] > 0 ){
                    // cùng hàng

                    // bên trái
                    if (this.matrix[i][j-1] > 0 ){
                        this.node[ this.matrix[i][j] ][0]=this.matrix[i][j-1];
                    }else{
                        this.node[this.matrix[i][j]][0]=0; // không có đỉnh kể
                    }
                    //bên phải
                    if (this.matrix[i][j+1] > 0 ){
                        this.node[ this.matrix[i][j] ][1]=this.matrix[i][j+1];
                    }else{
                        this.node[this.matrix[i][j]][1]=0;
                    }

                    //cùng cột
                    //bên trên

                    if (this.matrix[i-1][j] > 0 ){
                        this.node[ this.matrix[i][j] ][2]=this.matrix[i-1][j];
                    }else{
                        this.node[this.matrix[i][j]][2]=0;
                    }

                    //bên dưới
                    if (this.matrix[i+1][j] > 0 ){
                        this.node[ this.matrix[i][j] ][3]=this.matrix[i+1][j];
                    }else{
                        this.node[this.matrix[i][j]][3]=0;
                    }

                }

            }
        }
    }




    /**
     * 	update vị chí của bom dùng để né bom
     * 	chú ý nếu  enemy nằm trong flame của bom thì dừng ngay việc  làm ầm đinh từ enemy đến vị chí dự kiến  kết thúc của flame
     * 	từ ngữ bôi đen hãy hiểu là làm âm đỉnh
     *
     *
     */

    public void updateMatrix(Enemy enemy){
        //this._board.getBombs()


        //update vị chí bom
        int r = Board.getPlayer().getMax_radius(); // bán kính vụ nổi
        // toa độ enemy
        int xe = enemy.getXTile();
        int ye = enemy.getYTile();
        int xb = Board.getPlayer().getXTile();
        int yb = Board.getPlayer().getYTile();

        for ( int i =0 ; i  < Board.getPlayer().getBombs().size() ;i++){
            // tọa độ quả bom đc đặt
            int xt =  Board.getPlayer().getBombs().get(i).getXTile();
            int yt =  Board.getPlayer().getBombs().get(i).getYTile();

            // làm tạm mất đỉnh mà quả bom đang ở ( làm âm nó đi)
            this.matrix[yt][xt] *=-1;
            // làm tạm mất các đỉnh trong vù ảnh hưởng của bom ( âm nó đi)

            // xét ngang
            //phải    && yt!=yb && xt+j != xb
            for ( int j=1 ; j <= r;j++){
                if ( this.matrix[yt][xt+j] > 0 && yt!=ye && xt+j != xe   ){
                    // tức là sẽ dùng việc bôi đen lại nếu enemy trong vùng ảnh hương
                    // dừng việc bôi đen khi găp người
                    this.matrix[yt][xt+j] *=-1;
                }else{
                    break;
                }
            }
            //trai
            for ( int j=1 ; j <= r; j++){
                if ( this.matrix[yt][xt-j] > 0 && yt!=ye && xt-j != xe){
                    // tức là sẽ dùng việc bôi đen lại nếu enemy trong vùng ảnh hương
                    // dừng việc bôi đen khi găp người

                    this.matrix[yt][xt-j] *=-1;

                }else{
                    break;
                }
            }
            // xet dọc
            //dưới
            for ( int j=1 ; j <= r;j++){
                if ( this.matrix[yt+j][xt] > 0  && yt+j != ye && xt!= xe ){
                    // tức là sẽ dùng việc bôi đen lại nếu enemy trong vùng ảnh hương
                    // dừng việc bôi đen khi găp người

                    this.matrix[yt+j][xt] *=-1;
                }else{
                    break;
                }
            }
            //trên
            for ( int j=1 ; j <= r;j++){
                if ( this.matrix[yt-j][xt] > 0 && yt-j != ye && xt != xe){
                    // tức là sẽ dùng việc bôi đen lại nếu enemy trong vùng ảnh hương
                    // dừng việc bôi đen khi găp người

                    this.matrix[yt-j][xt] *=-1;
                }else{
                    break;
                }
            }
        }




    }

    /**
     *
     * @param start đỉnh bắt đầu
     * @param end đỉnh kết thúc
     * @return đỉnh sẽ đi bên cạnh start
     * @throws IllegalStateException
     */



    int bfs( int start , int end  ) throws IllegalStateException { // exception khi que ko còn chỗ
        // tao cai  Queue node
        Queue<Integer> qNode = new LinkedList<Integer>();



        // khai báo
        int [] parent = new int[numOfNode+1];
        boolean [] visted = new boolean[numOfNode+1];




        // sét nhan cho đinh, xét đỉnh cha
        // trường hợp bommer hoặc người chơi trong  khu vực bom tức đỉnh âm thì sẽ đc xử lý ở đây.

        if ( start < 0 ) start *=-1;
        if ( end <0 ) end *=-1;

        visted[start] = false;
        parent[start] = -1; // sét mặc định đỉnh cha
        parent[end]=-1;

        // thêm đỉnh start vào đầu
        qNode.add(start);

        while ( !qNode.isEmpty()){
            // dequeue phanaf tử đầu tiên ra
            int currentNode = qNode.poll();

            // duyệt toàn bộ đỉnh kể với current , nếu chưa visit thì dán cho là visit
            for ( int i = 0 ; i<4 ; i++ ){
                if ( visted[node[currentNode][i]]==false && node[currentNode][i]!=0 ) {
                    // dán nhãn đã thăm
                    visted[node[currentNode][i]]= true;
                    // gán đỉnh cha
                    parent[node[currentNode][i]] = currentNode;
                    // cho vào queue
                    qNode.add(node[currentNode][i]);

                }

            }
        }



        // xuất đường đi ngắn nhất ra
        int p = parent[end];

        // thêm node cuối

        if (p != -1){

            path.add(end);
            path.add(p);
            while ( p!=start){ // chu di den goc
                p = parent[p];
                path.add(p);
            }
//             for ( int i =0 ; i < path.size() ; i++ ){
//                System.out.println(path.get(i)+ " ");
//            }
            // trả về vị chí thứ 2 tức làm đỉnh kết tiếp start
            return path.get(path.size()-2);

        }

        // không tồn tại đường đi thì sẽ cho đứng im
        return -1;
    }




    public int calculateDirection(Enemy enemy) {
        // TODO: cài đặt thuật toán tìm đường đi


        this.getMatrix();
        this.updateMatrix(enemy);
        this.convertNearNodeMatrix();
        // cập nhập liên tục

        int start = this.matrix [enemy.getYTile()][enemy.getXTile()];

        int end = this.matrix[Board.getPlayer().getYTile()][Board.getPlayer().getXTile()];


        int result = this.bfs(start, end);

        if (result == -1 ) {
            enemy.setSpeed(1);
            return chooseDirection(enemy);
        }
        else {
            enemy.setSpeed(2);
            if ( result - start == 1 ) return 2;
            if ( start -  result == 1) return 1;
            if ( start - result > 0 ) return 3;
            if ( start - result < 0 ) return 4;

        }
        enemy.setSpeed(1);
        return chooseDirection(enemy);
    }
}
