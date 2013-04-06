package com.spyone.model.profiles.da;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.spyone.model.profiles.SpyOneProfile;

@Component
public class DiskStoredProfiles implements ProfilesDao, Comparator<SpyOneProfile> {

	private static final String PROFILE_EXTENSION = ".prof";

	Map<String, SpyOneProfile> profiles = new HashMap<String, SpyOneProfile>();
	
	public DiskStoredProfiles(){
		readAllProfilesFromDisk();
	}

	@Override
	public List<String> listProfiles() {
		ArrayList<String> profilesWithOrder = new ArrayList<String>(profiles.keySet());
		Collections.sort(profilesWithOrder);
		return profilesWithOrder;
	}

	@Override
	public List<SpyOneProfile> getAllProfiles() {
		ArrayList<SpyOneProfile> profilesWithOrder = new ArrayList<SpyOneProfile>(profiles.values());
		Collections.sort(profilesWithOrder, this);
		return profilesWithOrder;
	}

	@Override
	public List<SpyOneProfile> getProfiles(String... profileNames) {
		ArrayList<SpyOneProfile> filteredProfiles = new ArrayList<SpyOneProfile>();
		for (String name : profileNames){
			if (profiles.containsKey(name)){
				filteredProfiles.add(profiles.get(name));
			}
		}
		return filteredProfiles;
	}

	@Override
	public void storeProfiles(SpyOneProfile... profiles) {
		for (SpyOneProfile profile : profiles) {
	        try {
	        	FileOutputStream file = new FileOutputStream(profile.getProfileName() + PROFILE_EXTENSION);
	        	ObjectOutputStream profileWriter = new ObjectOutputStream(file);
	        	profileWriter.writeObject(profile);
	        	profileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void refreshProfiles() {
		readAllProfilesFromDisk();
	}
	
	@Override
	public int compare(SpyOneProfile arg0, SpyOneProfile arg1) {
		return arg0.getProfileName().compareTo(arg1.getProfileName());
	}
	
	private void readProfileFromDisk(String name) {
        try {
        	FileInputStream file = new FileInputStream(name + PROFILE_EXTENSION);
        	ObjectInputStream profileReader = new ObjectInputStream(file);
        	SpyOneProfile profile = (SpyOneProfile) profileReader.readObject();
			profileReader.close();
			profiles.put(name, profile);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void readAllProfilesFromDisk() {
		Collection<String> profileNames = findProfilesNamesInDisk();
		for (String profileName : profileNames) readProfileFromDisk(profileName);
	}

	private Collection<String> findProfilesNamesInDisk() {
		File currentDir = new File(System.getProperty("user.dir"));
		String[] existingProfilesInDisk = currentDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.matches(".*\\" + PROFILE_EXTENSION + "$");
			}});
		return removeProfileNameExtension(existingProfilesInDisk);
	}
	
	private ArrayList<String> removeProfileNameExtension(String[] profiles) {
		ArrayList<String> filteredProfiles = new ArrayList<String>();
		for (String profileName : profiles) {
			filteredProfiles.add(profileName.replace(PROFILE_EXTENSION, ""));
		}
		return filteredProfiles;
	}

}
