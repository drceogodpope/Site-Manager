package com.francescocommisso.sitemanager;

public class Lot {

    public static final int INCOMPLETE = 0;
    public static final int COMPLETE = 1;
    public static final int ERROR = 2;

    int id;
    Site site;
    int status;

    public Lot(int id,Site site){
        this.id = id;
        this.site = site;
        this.status = INCOMPLETE;
    }

    public Lot(int id,int status){
        this.id = id;
        this.status = status;
    }

    public void setStatus(int s){
        status = s;
    }

    public int getStatus(){
        return this.status;
    }

    public int getId(){
        return id;
    }

}
