package com.francescocommisso.sitemanager;

import java.util.ArrayList;

public class Site {
    private String name;
    private String formattedName;

    private int totalLots;
    private ArrayList<Lot> lots;

    public Site(String name,int totalLots){
        this.name = getCapitilizedName(name);
        this.totalLots  = totalLots;
        lots = new ArrayList<>();
        for(int i = 0; i<totalLots; i++){
            lots.add(new Lot(i+1,this));
        }
        formattedName = "";

        for(int i = 0; i<this.name.length();i++){
            if (this.name.charAt(i)!=' '){
                formattedName += this.name.charAt(i);
            }
        }
    }

    public String getName(){
        return name;
    }

    public String getFormattedName(){
        return formattedName;
    }

    public String getCapitilizedName(String name){
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : name.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    public int getTotalLots(){
        return totalLots;
    }

    public void setLots(ArrayList<Lot> newLots){
        this.lots = newLots;
    }

    public ArrayList<Lot> getLots() {
        return lots;
    }

    public Lot getLot(int id){
        return lots.get(id);
    }

}
