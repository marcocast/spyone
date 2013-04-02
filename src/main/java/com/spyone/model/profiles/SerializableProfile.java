package com.spyone.model.profiles;

import java.io.Serializable;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ServerDetails;

public class SerializableProfile implements Serializable {
	
	private static final long serialVersionUID = -7957849274070172271L;
	
	protected String profileName;
	protected String filePath;
	protected String host;
	protected String user;
	protected String password;
   //private Integer port;
   //private String privateKeyLocation;
   //private boolean isPasswordRequired;
   
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
//		sd.setPort(port);
//		sd.setPrivateKeyLocation(privateKeyLocation);
//		sd.setPasswordRequired(isPasswordRequired);
		profile.setServerDetails(sd);
		return profile;
	}

    public String getProfileName() {
        return profileName;
    }
 
    public void setProfileName(String pName) {
    	profileName = pName;
    }
        
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String fpath) {
    	filePath = fpath;
    }
    
    public String getHost() {
        return host;
    }
    
    public void setHost(String fHost) {
    	host = fHost;
    }
    
    public String getUser() {
        return user;
    }
    
    public void setUser(String fUser) {
    	user = fUser;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String fPassword) {
    	password = fPassword;
    }

}
