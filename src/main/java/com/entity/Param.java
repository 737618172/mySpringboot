package com.entity;


import java.util.ArrayList;

public class Param {

    private Long id;

    private String name;

    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static void main(String[] args) {
        int i =1;
        while(true)
            new String(""+  i++);
    }
//
//    public static void a(){
//        b();
//    }
//    public static void b(){
//        a();
//    }

}
