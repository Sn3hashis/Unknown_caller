package com.mhvmedia.unknowncaller.Model;
/** Created by AwsmCreators * */
public class User {
    private Double credits;
    String number;
    String refer_code;
    Boolean refer_taken;

    public User() {
    }

    public User(Double credits, String number, String refer_code, Boolean refer_taken) {
        this.credits = credits;
        this.number = number;
        this.refer_code = refer_code;
        this.refer_taken = refer_taken;
    }

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRefer_code() {
        return refer_code;
    }

    public void setRefer_code(String refer_code) {
        this.refer_code = refer_code;
    }

    public Boolean getRefer_taken() {
        return refer_taken;
    }

    public void setRefer_taken(Boolean refer_taken) {
        this.refer_taken = refer_taken;
    }
}