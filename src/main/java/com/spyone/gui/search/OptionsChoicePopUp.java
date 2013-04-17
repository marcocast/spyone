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

import org.grep4j.core.options.Option;
import org.springframework.stereotype.Component;

@Component
public class OptionsChoicePopUp {

	
	public Popup showPopUp(List<Option> selectedOptions){
				
		selectedOptions.clear();
		
        Button done = new Button("Done");

        VBox popUpVBox = new VBox();
        popUpVBox.setStyle("-fx-background-color: #000000;");
       
        popUpVBox.getChildren().add(buildSelectableOptionLine(Option.countMatches(),selectedOptions));            
        popUpVBox.getChildren().add(buildSelectableOptionLine(Option.ignoreCase(),selectedOptions));  
        popUpVBox.getChildren().add(buildSelectableOptionLine(Option.invertMatch(),selectedOptions));  
        popUpVBox.getChildren().add(buildSelectableOptionLine(Option.filesMatching(),selectedOptions));  
        popUpVBox.getChildren().add(buildSelectableOptionLine(Option.lineNumber(),selectedOptions));  

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
	
	/*
	 * this method build a line (hbox) for each profile found in the DB. 
	 * Also add the logic to add the checked profile to a list that is then used by the SearchController.
	 * TODO: if this is reopened, it should keep checked the checkboxes previously selected
	 */
	private Node buildSelectableOptionLine(final Option option, final List<Option> selectedOptions) {
		HBox hbox = new HBox();
		final CheckBox optionToChoose = new CheckBox(option.getOptionCommand());
		hbox.getChildren().add(optionToChoose);
		optionToChoose.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val) {
	        	selectedOptions.add(option);	        
	        }
	    });
		return hbox;
	}
   


}