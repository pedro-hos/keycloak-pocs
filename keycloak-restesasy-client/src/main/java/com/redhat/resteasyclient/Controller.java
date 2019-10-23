package com.redhat.resteasyclient;

public class Controller {
	
	public Controller() {
		System.out.println(TokenManager.getInstance().getToken());
	}
		
}