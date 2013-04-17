package com.spyone.gui.search;

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

	
	public Popup showPopUp(List<SpyOneProfile> selectedProfile){
		
        Button done = new Button("Done");

        VBox popUpVBox = new VBox();
        popUpVBox.setStyle("-fx-background-color: #000000;");
        for (SpyOneProfile profile : profilesManager.getAllProfiles()) {
        	popUpVBox.getChildren().add(buildSelectableProfileLine(profile,selectedProfile));            
		}

        popUpVBox.getChildren().add(done);
        
        final Popup popup = new Popup();
        popup.setAutoFix(true);
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

	private Node buildSelectableProfileLine(final SpyOneProfile profile, final List<SpyOneProfile> selectedProfile) {
		HBox hbox = new HBox();
		final CheckBox profileToChoose = new CheckBox(profile.getProfileName());
		hbox.getChildren().add(profileToChoose);
		profileToChoose.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val) {
	        	selectedProfile.add(profile);	        
	        }
	    });
		return hbox;
	}
   


}