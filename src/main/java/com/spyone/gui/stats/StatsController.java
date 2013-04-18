package com.spyone.gui.stats;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ProfileBuilder;
import org.grep4j.core.options.Option;
import org.grep4j.core.result.GrepResult;
import org.grep4j.core.result.GrepResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spyone.gui.components.popup.OptionsChoicePopUp;
import com.spyone.gui.components.popup.ProfilesChoicePopUp;
import com.spyone.model.profiles.SpyOneProfile;
import com.spyone.services.GrepService;

@Component
public class StatsController implements Initializable {

	@FXML
	public Button statsButton;

	@FXML
	public Tab statsTab;

	@FXML
	public Button selectProfilesButton;

	@FXML
	public Button selectOptionsButton;

	@FXML
	private AnchorPane statsPane;

	@FXML
	private PieChart pieChart;

	@FXML
	private BarChart barChart;

	@FXML
	private TextField textstats;

	@Autowired
	ProfilesChoicePopUp profilesChoicePopUp;

	@Autowired
	OptionsChoicePopUp optionsChoicePopUp;

	@Autowired
	GrepService grepService;

	public void initialize(URL arg0, ResourceBundle arg1) {

		// this is list will be passed and populated in the profilesChoicePopUp
		final List<SpyOneProfile> selectedProfile = new ArrayList<SpyOneProfile>();

		// this is list will be passed and populated in the optionsChoicePopUp
		final List<Option> selectedOptions = new ArrayList<Option>();

		pieChart.setTitle("Search result");
		pieChart.autosize();

		barChart.autosize();

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
						popup.show(statsPane.getScene().getWindow());

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
						popup.show(statsPane.getScene().getWindow());
					}
				}
			});
		}

		// this button run the grep and populate the textarea
		if (statsButton != null) {
			statsButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {

					List<Profile> profilesToGrep = buildProfiles(selectedProfile);

					GrepResults grepResults = grepService.getResults(
							textstats.getText(), profilesToGrep,
							selectedOptions);

					pieChart.getData().addAll(buildGrepPieChart(grepResults));

					barChart.getData().addAll(buildGrepBarChart(grepResults));

				}

				private Object buildGrepBarChart(GrepResults grepResults) {
					XYChart.Series series1 = new XYChart.Series();
					series1.setName("Total lines");
					for (GrepResult grepResult : grepResults) {
						series1.getData().add(
								new XYChart.Data(grepResult.getFileName(), grepResult
										.totalLines()));
					}
					return series1;
				}

				private ObservableList buildGrepPieChart(GrepResults grepResults) {
					ObservableList grepData = FXCollections
							.observableArrayList();

					for (GrepResult grepResult : grepResults) {
						grepData.add(new PieChart.Data(
								grepResult.getFileName(), grepResult
										.totalLines()));
					}

					return grepData;
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

}
