package uet.oop.bomberman;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.management.StringValueExp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ControllerEnd implements Initializable{

	BombermanGame bGame = new BombermanGame();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		EndScore.setText(String.valueOf(Board.score));
		LabelLW.setText(String.valueOf("YOU WIN"));
		//sc1.setText(String.valueOf(BombermanGame.high_score.get(0)));
	}
	
	@FXML
    private Button Back;

    @FXML
    private Label EndScore;

    @FXML
    private Button Score;
    
    @FXML
    private Label LabelLW;
    
    

    @FXML
    void BackMenu(ActionEvent event) throws IOException {
    	bGame.BackMenu(event);
    }

    @FXML
    void ScoreGame(ActionEvent event) throws IOException {
    	bGame.ScoreGame(event);
    }

}
