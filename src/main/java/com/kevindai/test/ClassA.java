package com.kevindai.test;

import java.io.Serializable;

/**
 * Created by daiwenkai on 2017/2/10.
 */
public class ClassA implements Serializable {
    private static final long serialVersionUID = 1L;
    //public static final String TEST = "TEST";

    private String name;
    private String pass;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
