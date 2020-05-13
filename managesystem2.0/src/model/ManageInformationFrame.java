package model;

import java.util.HashMap;

import ¹«¸æ.Informtion;

public class ManageInformationFrame {
	
private static HashMap hm = new HashMap<String,Informtion>();
	
	public static void addInformtion(String id,Informtion informtion) {
		hm.put(id, informtion);
	}
	
	public static Informtion getInformtion(String id) {
		return (Informtion)hm.get(id);
	}

}
