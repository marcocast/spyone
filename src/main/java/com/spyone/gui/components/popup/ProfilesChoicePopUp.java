package com.spyone.gui.components.popup;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spyone.model.profiles.SpyOneProfile;
import com.spyone.model.profiles.da.DiskStoredProfiles;

@Component
public class ProfilesChoicePopUp {

	@Autowired
	private DiskStoredProfiles profilesManager;

	public Popup showPopUp(List<SpyOneProfile> selectedProfiles) {

		Button done = new Button("Done");

		VBox popUpVBox = new VBox();
		popUpVBox.setPrefWidth(300);
        done.setPrefWidth(300);
        
		popUpVBox.setStyle("-fx-background-color: #000000;");
		profilesManager.refreshProfiles();
		for (SpyOneProfile profile : profilesManager.getAllProfiles()) {
			popUpVBox.getChildren().add(
					buildSelectableProfileLine(profile,
							selectedProfiles));
		}

		popUpVBox.getChildren().add(done);

		final Popup popup = new Popup();
		popup.setAutoFix(false);
		popup.setHideOnEscape(true);
		popup.getContent().addAll(popUpVBox);

		done.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {

				popup.hide();
			}
		});
		return popup;
	}

	/*
	 * this method build a line (hbox) for each profile found in the DB. Also
	 * add the logic to add the checked profile to a list that is then used by
	 * the SearchController. 
	 */
	private Node buildSelectableProfileLine(final SpyOneProfile profile,
			final List<SpyOneProfile> selectedProfiles) {
		HBox hbox = new HBox();
		final CheckBox profileToChoose = new CheckBox(profile.getProfileName());

		if (selectedProfiles.contains(profile)) {
			profileToChoose.setSelected(true);
		}

		hbox.getChildren().add(profileToChoose);
		profileToChoose.selectedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> ov,
							Boolean old_val, Boolean new_val) {
						if (new_val) {
							selectedProfiles.add(profile);
						} else {
							selectedProfiles.remove(profile);
						}
					}
				});
		return hbox;
	}

}