package com.nhom9.models;

public class MyPet {
    int photo;
    String name;
    String age;

    //constructor


    public MyPet(int photo, String name, String age) {
        this.photo = photo;
        this.name = name;
        this.age = age;
    }

    //Getter and setter

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
