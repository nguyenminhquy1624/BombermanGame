package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Sound;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class BombermanGame extends Application{

	public static final int WIDTH = 31;
	public static final int HEIGHT = 13;

	private Stage theStage = new Stage();
	public static Canvas canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT+1));
	
	public static GraphicsContext gc = canvas.getGraphicsContext2D();
	public static GraphicsContext gcforplayer = canvas.getGraphicsContext2D();
	public static Scene scene;
	public static Scene sceneTuto;
	public static Board board;
	public static Parent root;
	public static boolean x = true;
	public static float test = -10f;

	public static PlayerController playerController = new PlayerController();
	
	public static int high_score;
    private Text heartText;
    private Text timeText;
    private Text scoreText;
    private Text levelText;
    private List<Text> textList = new ArrayList<Text>();
    
    

	public static void main(String[] args) {
		Application.launch(BombermanGame.class);
	}

	@Override
	public void start(Stage stage) throws IOException {
		
		theStage = stage;
		Menu();
	}

	public void playGame() throws IOException {
		Sound.stop();
		Sound.play("GAME_START");
		
		board = new Board();
		board.score = 0;
		createText();
		
		
		board.loadlevel();
		board.change_level(board.getLevel());

		// Tao root container
		Group root = new Group();
		root.getChildren().add(canvas);
		
		root.getChildren().addAll(textList);
		// Tao scene
		scene = new Scene(root, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT+1), Color.BLACK);

		
		// Them scene vao stage
		theStage.setScene(scene);
		theStage.show();
		
		AnimationTimer timer = new AnimationTimer() {
			
			@Override
			public void handle(long l) {
				board.render();
				board.update();
				update();
				board.CountDown();
				if(board.CountDown() <= 0) {
					stop();
                    try {
						
						EndGame();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
                if(Board.getPlayer().isRemove()) {
                    if(Board.score > high_score) {
                        high_score = Board.score;
                        try {
                            File file = new File("res/levels/high_score.txt");
                            if(!file.exists()) file.createNewFile();
                            FileWriter fw = new FileWriter(file,false);
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(String.valueOf(high_score));
                            bw.close();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    stop();
                    try {
						
						EndGame();
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
                if(Board.getPlayer().isWin()) {
                    Board.score += Board.countDownTime/60;
                    if(board.getLevel() < 3) {
                        Board.countDownTime = Board.default_level_time;
                        board.setLevel(board.getLevel()+1);
                        Board.getPlayer().setWin(false);
                        board.change_level(board.getLevel());
                    }
                    else {
                        if(Board.score > high_score) {
                            high_score = Board.score;
                            try {
                                File file = new File("res/levels/high_score.txt");
                                if(!file.exists()) file.createNewFile();
                                FileWriter fw = new FileWriter(file,false);
                                BufferedWriter bw = new BufferedWriter(fw);
                                bw.write(String.valueOf(high_score));
                                bw.close();
                            }catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        stop();
                        try {
    						
    						EndGame();
    					} catch (IOException e) {
    						e.printStackTrace();
    					}
                    }
                }

			}
		};
		timer.start();

		playerController.control(scene);

	}
	
	@FXML
	public void VolumeGame() {
		if (test == -10f) {
			x = false;
			test = -80f;
			Sound.start();
		} else {
			x = true;
			test = -10f;
			Sound.start();
		}	
	}

	public void Menu() throws IOException {
		
		Sound.play("BG_MUSIC");
		root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		scene = new Scene(root);
		theStage.setScene(scene);
		theStage.show();
	}

	@FXML
	private AnchorPane Pane;

	@FXML
	private Button Play;

	@FXML
	private Button Quit;

	@FXML
	private Button Score;

	@FXML
	private Button Tutorial;

	@FXML
	private Button Volume;

	@FXML
	public void PlayGame(ActionEvent event) throws IOException {
		theStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		playGame();
	}

	@FXML
	public void QuitGame(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Quit Game");
		alert.setHeaderText("Do you want quit game?");
		alert.setContentText("Bạn muốn thoát khỏi game?");
		if (alert.showAndWait().get() == ButtonType.OK) {
			theStage = (Stage) Pane.getScene().getWindow();
			theStage.close();
		}
	}

	@FXML
	public void ScoreGame(ActionEvent event) throws IOException {
		theStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Score();
	}

	public void Score() throws IOException {
		root = FXMLLoader.load(getClass().getResource("HighScore.fxml"));

		sceneTuto = new Scene(root);

		theStage.setScene(sceneTuto);
	}

	@FXML
	void TutorialGame(ActionEvent event) throws IOException {

		theStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Tuto();
	}

	public void Tuto() throws IOException {
		root = FXMLLoader.load(getClass().getResource("Tutorial.fxml"));

		sceneTuto = new Scene(root);

		theStage.setScene(sceneTuto);
	}

	public void EndGame() throws IOException {
		Sound.stop();
		Sound.play("GAME_OVER");
		root = FXMLLoader.load(getClass().getResource("EndGame.fxml"));
		sceneTuto = new Scene(root);
		theStage.setScene(sceneTuto);
		
	}

	@FXML
	private Button Back;
	

	@FXML
	public void BackMenu(ActionEvent event) throws IOException {
		Sound.stop();
		sceneTuto = null;
		theStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Menu();
		
	}
	
	public void createText() {
        scoreText = new Text(0,16,"score: " + String.valueOf(Board.score));
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(new Font(16));
        textList.add(scoreText);

        timeText = new Text(80,16,"time: " + String.valueOf(Board.countDownTime/60));
        timeText.setFill(Color.WHITE);
        timeText.setFont(new Font(16));
        textList.add(timeText);

        levelText = new Text(160,16,"level: " + board.getLevel());
        levelText.setFill(Color.WHITE);
        levelText.setFont(new Font(16));
        textList.add(levelText);

        heartText = new Text(240, 16,"heart: " + Board.getPlayer().getHearts());
        heartText.setFill(Color.WHITE);
        heartText.setFont(new Font(16));
        textList.add(heartText);
    }

    public void update() {
        scoreText.setText("score: " + String.valueOf(Board.score));
        timeText.setText("time: " + String.valueOf(Board.countDownTime/60));
        levelText.setText("level: " + String.valueOf(board.getLevel()));
        heartText.setText("heart: " + String.valueOf(Board.getPlayer().getHearts()));
    }
}
