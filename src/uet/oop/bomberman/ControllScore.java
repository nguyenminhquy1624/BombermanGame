package uet.oop.bomberman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ControllScore implements Initializable{
	
	private static ArrayList<Integer> high_score = new ArrayList<>();
	
	public ControllScore() throws IOException {
		 String url = "C:\\Users\\MINH QUY\\Documents\\JAVA_ECLIPSE\\OOP\\Bomberman\\res\\levels\\high_score.txt";

	        File file = new File(url);
	        BufferedReader reader = new BufferedReader(new FileReader(file));
	            String line = reader.readLine();
	            while (line != null) {
	            	Integer x = Integer.parseInt(line);
	            	high_score.add(x);
	                line = reader.readLine();
	            }
	            high_score.sort((o1, o2) -> o2 - o1);
	}

	BombermanGame bGame = new BombermanGame();
		@FXML
	    private Button Back;

	    @FXML
	    private Label sc1;

	    @FXML
	    private Label sc2;

	    @FXML
	    private Label sc3;

	    @FXML
	    private Label sc4;

	    @FXML
	    private Label sc5;

	    @FXML
	    private Label sc6;

	    @FXML
	    void BackMenu(ActionEvent event) throws IOException {
	    	bGame.BackMenu(event);
	    }
		@Override
		public void initialize(URL location, ResourceBundle resources) {
		sc1.setText(String.valueOf(high_score.get(0)));
		sc2.setText(String.valueOf(high_score.get(1)));
		sc3.setText(String.valueOf(high_score.get(2)));
		sc4.setText(String.valueOf(high_score.get(3)));
		sc5.setText(String.valueOf(high_score.get(4)));
		sc6.setText(String.valueOf(high_score.get(5)));
		}
}
