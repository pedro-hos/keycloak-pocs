package com.redhat.resteasyclient;

public class Main {

	public static void main(String[] args) {
		System.out.println(TokenManager.getInstance().getToken());
	}

}
