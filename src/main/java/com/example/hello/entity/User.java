package com.example.hello.entity;


/**
 * @author zhoukai
 * @date 2020/7/22 15:29
 */
public class User {
    private String id;
    private String name;
    private String passWord;
    private String email;
    private int isAdmin;

    public User(String id, String name, String passWord, String email, int isAdmin) {
        this.id = id;
        this.name = name;
        this.passWord = passWord;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }
}
