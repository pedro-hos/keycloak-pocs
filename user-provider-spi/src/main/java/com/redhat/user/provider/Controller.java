package com.redhat.user.provider;

public class Controller {
	
	public Controller() {
		System.out.println(TokenManager.getInstance().getToken());
	}
		
}