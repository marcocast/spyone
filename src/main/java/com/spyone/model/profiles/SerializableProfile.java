package com.spyone.model.profiles;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ServerDetails;

@EqualsAndHashCode
@Getter
@Setter
public class SerializableProfile implements Serializable {
	
	private static final long serialVersionUID = -7957849274070172271L;
	
	protected String profileName;
	protected String filePath;
	protected String host;
	protected String user;
	protected String password;
   
	public SerializableProfile() {
        this("", "", "","","");
    }
 
    public SerializableProfile(String pName, String filePath, String host, String user, String password) {
    	setProfileName(pName);
    	setFilePath(filePath);
    	setHost(host);
    	setUser(user);
    	setPassword(password);
    }
    
	public SerializableProfile(Profile profile) {
		profileName = profile.getName();
		filePath = profile.getFilePath();
		ServerDetails sd = profile.getServerDetails();
		host = sd.getHost();
		user = sd.getUser();
		password = sd.getPassword();
	}
	
	public Profile getGrep4JProfile(){
		Profile profile = new Profile(profileName, filePath);
		ServerDetails sd = new ServerDetails(host);
		sd.setUser(user);
		sd.setPassword(password);
		profile.setServerDetails(sd);
		return profile;
	}

}
