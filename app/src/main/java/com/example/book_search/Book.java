package com.example.book_search;

public class Book implements Comparable<Book> {
    private String title;
    private String body;
    private double rate;
    private double red;
    private double green;
    private double blue;
    private String imgUrl;
    private String foundTtxt;
    private String foundBtxt;

    public Book(String title, String body,
                double rate, double red,
                double green, double blue, String imgUrl) {
        this.title = title;
        this.body = body;
        this.rate = rate;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.imgUrl = imgUrl;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getRed() {
        return red;
    }

    public void setRed(Double red) {
        this.red = red;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(Double green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(Double blue) {
        this.blue = blue;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFoundTtxt() {
        return foundTtxt;
    }

    public void setFoundTtxt(String foundTtxt) {
        this.foundTtxt = foundTtxt;
    }

    public String getFoundBtxt() {
        return foundBtxt;
    }

    public void setFoundBtxt(String foundBtxt) {
        this.foundBtxt = foundBtxt;
    }

    public int compareTo(Book b) {
        if (getTitle() == null || b.getTitle() == null) {
            return 0;
        }
        return getTitle().compareTo(b.getTitle());
    }

}
