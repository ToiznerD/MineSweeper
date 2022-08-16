package mines;

import java.io.IOException;

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MinesFX extends Application {
	private Mines mines;
	private GridPane gp;
	private Stage stg;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		HBox root = new HBox();
		stg = primaryStage;
		MinesController controller;
		// Regular commands
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Mines.fxml"));
			root = loader.load();
			controller = loader.getController();
			controller.setMineFX(this);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		createGrid(controller);
		Scene scene = new Scene(root);
		stg.setScene(scene);
		stg.show();

	}

	public void createGrid(MinesController controller) {
		/* Create a GridPane for the buttons based on width, height & mines amount.
		 * Add the GridPane to the main stage.
		 */
		HBox hbox = controller.getHBOX(); //Parent pane
		gp = new GridPane();
		gp.setVgap(1);
		gp.setHgap(1);

		// Get preferences
		int width = Integer.parseInt(controller.getWidth().getText());
		int height = Integer.parseInt(controller.getHeight().getText());
		int mines = Integer.parseInt(controller.getMines().getText());

		// Set constraints
		for (int i = 0; i < width; i++)
			gp.getColumnConstraints().add(new ColumnConstraints(40));
		for (int i = 0; i < height; i++)
			gp.getRowConstraints().add(new RowConstraints(40));

		// Create grid buttons
		createButtons(gp, width, height, mines);
		
		//Add to Parent pane & fit the window size
		hbox.getChildren().add(gp);
		hbox.autosize();
		stg.sizeToScene();
	}

	private void createButtons(GridPane gp, int width, int height, int minesAmt) {
		/* Create new mines board[height][width] & minesAmt mines on board
		 * Create buttons board
		 * add all buttons to gp
		 */
		mines = new Mines(height, width, minesAmt);
		Button[][] buttons = new Button[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				buttons[i][j] = new Button();
				
				//Set button preferences and add to gp
				buttons[i][j].setText(this.mines.get(i, j));
				buttons[i][j].setFont(Font.font(null, FontWeight.BOLD, 16));
				buttons[i][j].setOnMouseClicked(new Click(i, j, buttons));
				buttons[i][j].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				gp.add(buttons[i][j], j, i);
			}
		}
	}

	private void updateBoard(Button[][] b) {
		// Update all the buttons text
		// used after a button is opened
		for (int i = 0; i < b.length; i++)
			for (int j = 0; j < b[0].length; j++)
				b[i][j].setText(mines.get(i, j));
	}

	public class Click implements EventHandler<MouseEvent> {
		private int i, j;
		Button[][] buttons;

		public Click(int i, int j, Button[][] b) {
			this.i = i;
			this.j = j;
			buttons = b;
		}

		@Override
		public void handle(MouseEvent event) {
			/* Right click on button - toggle flag
			 * Left click on button - open spot
			 */
			if (MouseButton.SECONDARY.equals(event.getButton())) {
				mines.toggleFlag(i, j);
				updateBoard(buttons);
			} else if (MouseButton.PRIMARY.equals(event.getButton())) {
				if (!mines.open(i, j))
					mines.setShowAll(true);
				updateBoard(buttons);
			}
		}

	}

}
