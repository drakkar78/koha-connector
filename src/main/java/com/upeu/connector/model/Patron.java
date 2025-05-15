package com.upeu.connector.model;

import org.json.JSONObject;

/**
 * Representa un usuario (patron) de Koha.
 */
public class Patron {

    private String borrowernumber;
    private String cardnumber;
    private String userid;
    private String surname;
    private String firstname;
    private String categorycode;
    private String branchcode;
    private String email;

    public Patron() {
    }

    public static Patron fromJson(JSONObject json) {
        Patron p = new Patron();
        p.borrowernumber = json.optString("borrowernumber");
        p.cardnumber = json.optString("cardnumber");
        p.userid = json.optString("userid");
        p.surname = json.optString("surname");
        p.firstname = json.optString("firstname");
        p.categorycode = json.optString("categorycode");
        p.branchcode = json.optString("branchcode");
        p.email = json.optString("email");
        return p;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cardnumber", cardnumber);
        json.put("userid", userid);
        json.put("surname", surname);
        json.put("firstname", firstname);
        json.put("categorycode", categorycode);
        json.put("branchcode", branchcode);
        json.put("email", email);
        return json;
    }

    // Getters y setters
    public String getBorrowernumber() {
        return borrowernumber;
    }

    public void setBorrowernumber(String borrowernumber) {
        this.borrowernumber = borrowernumber;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCategorycode() {
        return categorycode;
    }

    public void setCategorycode(String categorycode) {
        this.categorycode = categorycode;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
