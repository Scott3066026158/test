package com.gaia.optional.service;

import java.util.List;

public  interface OptionalService {

	List<String> add(String username, String code);

	List<String> query(String username);

	List<String> delete(String username, String code);
	
	List<String> addList(String usernames, String[] codes);

    List<String> delList(String username, String[] code); 

}
