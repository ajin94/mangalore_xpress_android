package com.store;

/**
 * Created by akhil on 4/10/18.
 */

public class Product  {
    private String name, description, product_image;
    private int price, mrp;
    private boolean enable;
    private String product_image_urls[];
    private int product_id;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
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

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMrp() {
        return mrp;
    }

    public void setMrp(int mrp) {
        this.mrp = mrp;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String[] getProduct_image_urls() {
        return product_image_urls;
    }

    public void setProduct_image_urls(String[] product_image_urls) {
        this.product_image_urls = product_image_urls;
    }
}
