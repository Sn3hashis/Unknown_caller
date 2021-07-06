package com.mhvmedia.unknowncaller.Model;
/** Created by AwsmCreators * */
public class ModelCallRates {
    private String countryname;
    private String cost;
    private int countryicon;

    public ModelCallRates() {
    }

    public ModelCallRates(String countryname, String cost, int countryicon) {
        this.countryname = countryname;
        this.cost = cost;
        this.countryicon = countryicon;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getCountryicon() {
        return countryicon;
    }

    public void setCountryicon(int countryicon) {
        this.countryicon = countryicon;
    }
}
