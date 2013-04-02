package com.spyone.model.profiles.da;

import java.util.List;

import com.spyone.model.profiles.SpyOneProfile;

public interface ProfilesDao {

	public List<String> listProfiles();
	
	public List<SpyOneProfile> getAllProfiles();
	
	public List<SpyOneProfile> getProfiles(String... profileNames);
	
	public void storeProfiles(SpyOneProfile... profiles);
	
	public void refreshProfiles();
	
}
