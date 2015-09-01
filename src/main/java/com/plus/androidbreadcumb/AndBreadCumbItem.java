package com.plus.androidbreadcumb;

/**
 * Created by ali on 8/8/2015.
 */
public class AndBreadCumbItem {

    private int Id;
    private String diplayText;

    public AndBreadCumbItem(int Id,String displayText)
    {
        this.Id=Id;
        this.diplayText=displayText;
    }

    public String getDiplayText() {
        return diplayText;
    }

    public void setDiplayText(String diplayText) {
        this.diplayText = diplayText;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }



}
