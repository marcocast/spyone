package com.spyone.services;

import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.regularExpression;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.fluent.Dictionary.options;

import java.util.List;

import org.grep4j.core.model.Profile;
import org.grep4j.core.options.Option;
import org.grep4j.core.result.GrepResults;
import org.springframework.stereotype.Service;

@Service
public class GrepService {
	
	public GrepResults getResults(String text, List<Profile> profiles, List<Option> options){
		
		GrepResults grepResults = grep(
				regularExpression(text),
				on(profiles),
				options(options));
		return grepResults;
	}

}
