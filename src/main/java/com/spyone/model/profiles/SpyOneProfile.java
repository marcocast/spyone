package com.spyone.model.profiles;


import javafx.beans.property.SimpleStringProperty;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ServerDetails;

public final class SpyOneProfile extends SerializableProfile {

		private static final long serialVersionUID = 5710677228960272139L;
		
		private transient SimpleStringProperty guiProfileName = new SimpleStringProperty("");
		private transient SimpleStringProperty guiFilePath = new SimpleStringProperty("");
		private transient SimpleStringProperty guiHost = new SimpleStringProperty("");
		private transient SimpleStringProperty guiUser = new SimpleStringProperty("");
		private transient SimpleStringProperty guiPassword = new SimpleStringProperty("");
	   //private Integer port;
	   //private String privateKeyLocation;
	   //private boolean isPasswordRequired;
	   
		public SpyOneProfile() {
	        this("", "", "","","");
	    }
		
		public SpyOneProfile(Profile profile) {
			this(profile.getName(), profile.getFilePath(), profile.getServerDetails().getHost(),
				 profile.getServerDetails().getUser(), profile.getServerDetails().getPassword());
		}
	 
	    public SpyOneProfile(String pName, String filePath, String host, String user, String password) {
	    	setGuiProfileName(pName);
	    	setGuiFilePath(filePath);
	    	setGuiHost(host);
	    	setGuiUser(user);
	    	setGuiPassword(password);
	    }
	    
	    public void setGuiProfileName(String pName) {
	    	super.setProfileName(pName);
	    	guiProfileName.set(pName);
	    }
	    
	    public void setGuiFilePath(String fpath) {
	    	super.setFilePath(fpath);
	    	guiFilePath.set(fpath);
	    }
	    
	    public void setGuiHost(String fHost) {
	    	super.setHost(fHost);
	    	guiHost.set(fHost);
	    }
	    
	    public void setGuiUser(String fUser) {
	    	super.setUser(fUser);
	    	guiUser.set(fUser);
	    }
	    
	    public void setGuiPassword(String fPassword) {
	    	super.setPassword(fPassword);
	    	guiPassword.set(fPassword);
	    }
}
