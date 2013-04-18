package com.spyone.gui.search;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Screen;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ProfileBuilder;
import org.grep4j.core.options.Option;
import org.grep4j.core.result.GrepResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spyone.gui.search.service.GrepService;
import com.spyone.model.profiles.SpyOneProfile;

@Component
public class SearchController implements Initializable {

	@FXML
	public Button searchButton;

	@FXML
	public Tab searchTab;

	@FXML
	public Button selectProfilesButton;

	@FXML
	public Button selectOptionsButton;

	@FXML
	private AnchorPane searchPane;

	@FXML
	private TextField textSearch;

	@FXML
	private TextArea searchTextArea;

	@FXML
	public Hyperlink expandLink;

	@FXML
	public HBox expandLinkHBox;

	@Autowired
	ProfilesChoicePopUp profilesChoicePopUp;

	@Autowired
	OptionsChoicePopUp optionsChoicePopUp;

	@Autowired
	GrepService grepService;

	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeLink();

		initializeSearchArea();

		// this is list will be passed and populated in the profilesChoicePopUp
		final List<SpyOneProfile> selectedProfile = new ArrayList<SpyOneProfile>();

		// this is list will be passed and populated in the optionsChoicePopUp
		final List<Option> selectedOptions = new ArrayList<Option>();

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

		// this button display the popup
		if (selectOptionsButton != null) {
			selectOptionsButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {

					final Popup popup = optionsChoicePopUp
							.showPopUp(selectedOptions);
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

					GrepResults grepResults = grepService.getResults(
							textSearch.getText(), profilesToGrep,
							selectedOptions);
					searchTextArea.setText(grepResults.toString());

				}

				private List<Profile> buildProfiles(
						List<SpyOneProfile> selectedProfile) {
					List<Profile> profilesToGrep = new ArrayList<Profile>();
					for (SpyOneProfile spyOneProfile : selectedProfile) {
						if (spyOneProfile.getHost().equalsIgnoreCase(
								"localhost")) {
							Profile profile = ProfileBuilder.newBuilder()
									.name(spyOneProfile.getProfileName())
									.filePath(spyOneProfile.getFilePath())
									.onLocalhost().build();
							profilesToGrep.add(profile);
						} else {
							Profile profile = ProfileBuilder
									.newBuilder()
									.name(spyOneProfile.getProfileName())
									.filePath(spyOneProfile.getFilePath())
									.onRemotehost(spyOneProfile.getHost())
									.credentials(spyOneProfile.getUser(),
											spyOneProfile.getPassword())
									.build();
							profilesToGrep.add(profile);
						}

					}
					return profilesToGrep;
				}
			});
		}
	}

	private void initializeSearchArea() {
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		expandLinkHBox.setPrefWidth(primaryScreenBounds.getWidth());
		searchTextArea.setPrefWidth(primaryScreenBounds.getWidth());
		searchTextArea
				.setPrefHeight((primaryScreenBounds.getHeight() * 70) / 100);
	}

	private void initializeLink() {
		expandLink.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				searchTab.setContent(buildExpandedResult());
			}

			private Node buildExpandedResult() {
				VBox expandedResult = new VBox();
				HBox linkRow = new HBox();
				linkRow.setAlignment(Pos.TOP_RIGHT);
				Hyperlink restoreLink = new Hyperlink();
				restoreLink.setText("Restore");
				restoreLink.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						searchTab.setContent(searchPane);
					}
				});

				linkRow.getChildren().add(restoreLink);

				expandedResult.getChildren().add(linkRow);
				TextArea expandedTextArea = new TextArea();
				expandedTextArea.setText(searchTextArea.getText());
				expandedTextArea.setEditable(searchTextArea.isEditable());

				expandedTextArea.setMinHeight(Double.NEGATIVE_INFINITY);
				expandedTextArea.setMinWidth(Double.NEGATIVE_INFINITY);
				expandedTextArea.setPrefHeight(searchTextArea.getPrefHeight());
				expandedTextArea.setPrefWidth(searchTextArea.getPrefWidth());
				expandedTextArea.setWrapText(searchTextArea.isWrapText());
				VBox.setVgrow(expandedTextArea, Priority.ALWAYS);
				expandedResult.getChildren().add(expandedTextArea);
				return expandedResult;
			}
		});

	}
}
