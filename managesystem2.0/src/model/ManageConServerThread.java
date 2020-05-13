package model;

import java.util.HashMap;

public class ManageConServerThread {
	
	private static HashMap hm = new HashMap<String,ConServerThread>();
	
	public static void addConServerThread(String id,ConServerThread conServerThread) {
		hm.put(id, conServerThread);
	}
	
	public static ConServerThread getConServerThread(String id) {
		return (ConServerThread)hm.get(id);
	}

}
