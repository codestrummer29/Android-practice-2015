package com.example.saahil.jsontest;

/**
 * Created by Saahil on 12/02/16.
 */
//Class made to use multiple data (sample Model)
public class ModelClass {

    private String location;
    private int price;
    private String description;
    private String post_image;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String fb_id;

    public String getFb_id2() {
        return fb_id2;
    }

    public void setFb_id2(String fb_id2) {
        this.fb_id2 = fb_id2;
    }

    private String name;
    private String contact_no;
    private String fb_id2;

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getFb_id() {
        return "https://graph.facebook.com/"+ fb_id +"/picture?type=square";
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String title) {
        this.location = title;
    }

   public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPost_image() {
        return "http://www.abodemart.in/images/"+post_image;
    }

    public void setPost_image(String image) {
        this.post_image = image;
    }


}
