package com.spyone.gui.search;

import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.regularExpression;
import static org.grep4j.core.fluent.Dictionary.on;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ProfileBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spyone.model.profiles.SpyOneProfile;

@Component
public class SearchController implements Initializable {

	@FXML
	public Button searchButton;

	@FXML
	public Button selectProfilesButton;

	@FXML
	private AnchorPane searchPane;

	@FXML
	private TextField textSearch;

	@FXML
	private TextArea searchTextArea;

	@Autowired
	ProfilesChoicePopUp profilesChoicePopUp;

	public void initialize(URL arg0, ResourceBundle arg1) {

		// this is list will be passed and populated in the profilesChoicePopUp
		final List<SpyOneProfile> selectedProfile = new ArrayList<SpyOneProfile>();

		// this button display the popup
		if (selectProfilesButton != null) {
			selectProfilesButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {

					final Popup popup = profilesChoicePopUp
							.showPopUp(selectedProfile);
					if (popup.isShowing()) {
						popup.hide();
					} else {
						popup.show(searchPane.getScene().getWindow());
					}
				}
			});
		}

		// this button run the grep and populate the textarea
		if (searchButton != null) {
			searchButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					List<Profile> profilesToGrep = buildProfiles(selectedProfile);
					searchTextArea.setText(grep(
							regularExpression(textSearch.getText()),
							on(profilesToGrep)).toString());

				}

				private List<Profile> buildProfiles(
						List<SpyOneProfile> selectedProfile) {
					List<Profile> profilesToGrep = new ArrayList<Profile>();
					for (SpyOneProfile spyOneProfile : selectedProfile) {
						if(spyOneProfile.getHost().equalsIgnoreCase("localhost")){
							Profile profile = ProfileBuilder.newBuilder()
									.name(spyOneProfile.getProfileName())
									.filePath(spyOneProfile.getFilePath())
									.onLocalhost().build();
							profilesToGrep.add(profile);
						}else{
							Profile profile = ProfileBuilder.newBuilder()
									.name(spyOneProfile.getProfileName())
									.filePath(spyOneProfile.getFilePath())
									.onRemotehost(spyOneProfile.getHost())
									.credentials(spyOneProfile.getUser(),spyOneProfile.getPassword())
									.build();
							profilesToGrep.add(profile);
						}
						
					}
					return profilesToGrep;
				}
			});
		}
	}

}
