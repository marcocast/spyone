package com.spyone.gui.profiles;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import com.spyone.model.profiles.SpyOneProfile;
import com.spyone.model.profiles.da.DiskStoredProfiles;

public class ProfileController implements Initializable {
	
	private DiskStoredProfiles profilesManager = new DiskStoredProfiles();

	@FXML private TableView<SpyOneProfile> tableView;
    @FXML private TableColumn<SpyOneProfile, String> profileNameColumn;
    @FXML private TableColumn<SpyOneProfile, String> filePathColumn;
    @FXML private TableColumn<SpyOneProfile, String> hostColumn;
    @FXML private TableColumn<SpyOneProfile, String> userColumn;
    @FXML private TableColumn<SpyOneProfile, String> passwordColumn;

    @FXML private TextField profileNameField;
    @FXML private TextField filePathField;
    @FXML private TextField hostField;
    @FXML private TextField userField;
    @FXML private TextField passwordField;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	initializeColumnsConfig();
    	populateTableWithExistingProfiles();
	}
    
    private void initializeColumnsConfig() {
    	
    	profileNameColumn.setCellFactory(cellFactoryCast(TextFieldTableCell.forTableColumn()));    	
    	profileNameColumn.setOnEditCommit(
	        new EventHandler<CellEditEvent<SpyOneProfile, String>>() {	   
	            public void handle(CellEditEvent<SpyOneProfile, String> t) {
	            	SpyOneProfile profile = (SpyOneProfile) t.getTableView().getItems()
	            											 .get(t.getTablePosition().getRow());
	            	profile.setProfileName(t.getNewValue());
	                profilesManager.storeProfiles(profile);
	            }
	        }
	    );
    	
    	filePathColumn.setCellFactory(cellFactoryCast(TextFieldTableCell.forTableColumn()));
    	filePathColumn.setOnEditCommit(
	        new EventHandler<CellEditEvent<SpyOneProfile, String>>() {
	            public void handle(CellEditEvent<SpyOneProfile, String> t) {
	            	SpyOneProfile profile = (SpyOneProfile) t.getTableView().getItems()
															 .get(t.getTablePosition().getRow());
					profile.setFilePath(t.getNewValue());
					profilesManager.storeProfiles(profile);
	            }
	        }
	    );

    	hostColumn.setCellFactory(cellFactoryCast(TextFieldTableCell.forTableColumn()));
    	hostColumn.setOnEditCommit(
	        new EventHandler<CellEditEvent<SpyOneProfile, String>>() {
	            public void handle(CellEditEvent<SpyOneProfile, String> t) {
	            	SpyOneProfile profile = (SpyOneProfile) t.getTableView().getItems()
							 								 .get(t.getTablePosition().getRow());
					profile.setHost(t.getNewValue());
					profilesManager.storeProfiles(profile);
	            }
	        }
	    );
    	
    	userColumn.setCellFactory(cellFactoryCast(TextFieldTableCell.forTableColumn()));
    	userColumn.setOnEditCommit(
	        new EventHandler<CellEditEvent<SpyOneProfile, String>>() {
	            public void handle(CellEditEvent<SpyOneProfile, String> t) {
	            	SpyOneProfile profile = (SpyOneProfile) t.getTableView().getItems()
							 								 .get(t.getTablePosition().getRow());
					profile.setUser(t.getNewValue());
					profilesManager.storeProfiles(profile);
	            }
	        }
	    );
    	
    	passwordColumn.setCellFactory(cellFactoryCast(TextFieldTableCell.forTableColumn()));
    	passwordColumn.setOnEditCommit(
	        new EventHandler<CellEditEvent<SpyOneProfile, String>>() {
	            public void handle(CellEditEvent<SpyOneProfile, String> t) {
	            	SpyOneProfile profile = (SpyOneProfile) t.getTableView().getItems()
							 								 .get(t.getTablePosition().getRow());
					profile.setPassword(t.getNewValue());
					profilesManager.storeProfiles(profile);
	            }
	        }
	    );
    }
    
    private void populateTableWithExistingProfiles() {
    	tableView.getItems().addAll(profilesManager.getAllProfiles());
    }
    
    @FXML
    protected void addSpyOneProfile(ActionEvent event) {
    	SpyOneProfile newProfile = new SpyOneProfile(profileNameField.getText(),
			        								filePathField.getText(),
									        		hostField.getText(),
									        		userField.getText(),
									        		passwordField.getText());
        tableView.getItems().add(newProfile);
        profilesManager.storeProfiles(newProfile);
        resetNewProfileFields();
    }

	
	@SuppressWarnings("unchecked")
	private <S, T> Callback<S, T> callbackCast(Callback<?, ?> callbackObject) {
		return (Callback<S, T>) callbackObject;
	}
    
	private Callback<TableColumn<SpyOneProfile, String>, TableCell<SpyOneProfile, String>>
			cellFactoryCast(Callback<?, ?> cellFactoryObject) {
		return this.<TableColumn<SpyOneProfile, String>, TableCell<SpyOneProfile, String>>callbackCast(cellFactoryObject);
	}
    
	private void resetNewProfileFields() {
        profileNameField.setText("");
        filePathField.setText("");
        hostField.setText("");   
        userField.setText("");
        passwordField.setText("");
	}
}

	
