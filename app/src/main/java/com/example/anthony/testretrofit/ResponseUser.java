package com.example.anthony.testretrofit;

/**
 * Created by Anthony on 10/12/2016.
 */

public class ResponseUser {

    String username;
    String email;
    String token;

    public ResponseUser(String username, String email, String token) {
        this.username = username;
        this.email = email;
        this.token = token;
    }
}
