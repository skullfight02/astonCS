package uk.ac.aston.oop.javafx.assessed;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uk.ac.aston.oop.javafx.assessed.model.Database;
import uk.ac.aston.oop.javafx.assessed.model.Item;

public class ListController {

	@FXML
	private ListView<Item> listItems;

	private final Database model;

	public ListController(Database model) {
		this.model = model;
	}

	@FXML
	public void initialize() {
		listItems.setItems(model.itemsProperty());
	}

	@FXML
	public void shufflePressed() {
		FXCollections.shuffle(model.itemsProperty());
	}

	@FXML
	public void quitPressed() {
		listItems.getScene().getWindow().hide();
	}

	@FXML
	public void removePressed() {
		int selectedIdx = listItems.getSelectionModel().getSelectedIndex();
		if (selectedIdx >= 0) {
			final Item selectedItem = listItems.getSelectionModel().getSelectedItem();

			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RemoveItem.fxml"));
			final RemoveController controller = new RemoveController(selectedItem);
			loader.setController(controller);
			try {
				final Parent parent = (Parent) loader.load();

				final Stage removeStage = new Stage();
				removeStage.initModality(Modality.APPLICATION_MODAL);
				removeStage.setScene(new Scene(parent, 250, 200));
				removeStage.showAndWait();

				if (controller.isConfirmed()) {
					model.itemsProperty().remove(selectedIdx);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	@FXML
	public void addPressed() {
		System.out.println("sth");
		// 4: create the FXMLLoader and store it in local variable "loader"
		final FXMLLoader loader = new FXMLLoader();
		// 5: set the location of the FXML file in the loader
		loader.setLocation(getClass().getResource("AddItem.fxml"));
		// 6: create the RemoveController while passing the selected item
		final CreateCDController controller = new CreateCDController();
		// 7: set the controller in the loader
		loader.setController(controller);
		try {
			// 8: load the FXML file and store the root object in a variable
			final Parent root = (Parent) loader.load();
			// 9: create a "new Stage()" and store it in local variable "stage"
			final Stage addStage = new Stage();
			// 10: set the modality of the Stage
			addStage.initModality(Modality.APPLICATION_MODAL);
			// 11: create a new Scene with root object (250px x 200px)
			// 12: set the Scene of the Stage
			addStage.setScene(new Scene(root, 250, 250));
			addStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
