package mines;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class MinesController {
	private MinesFX minefx;
	
    @FXML
    private GridPane gp;

    @FXML
    private HBox hbox;

    @FXML
    private TextArea height;

    @FXML
    private TextArea mines;

    @FXML
    private Button reset;

    @FXML
    private TextArea width;
    
    public HBox getHBOX() {
    	return hbox;
    }
    
    public TextArea getHeight() {
    	return height;
    }
    
    public TextArea getWidth() {
    	return width;
    }
    
    public TextArea getMines() {
    	return mines;
    }
    
    public void setMineFX(MinesFX m) {
    	minefx = m;
    }
    
    @FXML
    void setGrid(ActionEvent event) {
    	hbox.getChildren().remove(hbox.getChildren().size()-1);
    	minefx.createGrid(this);
    }
}
