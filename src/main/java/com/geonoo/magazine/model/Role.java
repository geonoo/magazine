package com.geonoo.magazine.model;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    final private String name;
    public String getName() {
        return name;
    }
    private Role(String name){
        this.name = name;
    }
}
