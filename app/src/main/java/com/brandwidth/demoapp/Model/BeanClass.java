package com.brandwidth.demoapp.Model;


public class BeanClass
{
    private String id;
    private String name;
    private String path;



    private String rating;

    public BeanClass(){}


    public BeanClass(String _id, String _name, String _path, String _rate)
    {
        this.id = _id;
        this.name = _name;
        this.path = _path;
        this.rating = _rate;
    }



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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
