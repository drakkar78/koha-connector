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
        Patron patron = new Patron();
        patron.setBorrowernumber(json.optString("borrowernumber", null));
        patron.setCardnumber(json.optString("cardnumber", null));
        patron.setUserid(json.optString("userid", null));
        patron.setSurname(json.optString("surname", null));
        patron.setFirstname(json.optString("firstname", null));
        patron.setCategorycode(json.optString("categorycode", null));
        patron.setBranchcode(json.optString("branchcode", null));
        patron.setEmail(json.optString("email", null));
        return patron;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        if (cardnumber != null)    json.put("cardnumber", cardnumber);
        if (userid != null)        json.put("userid", userid);
        if (surname != null)       json.put("surname", surname);
        if (firstname != null)     json.put("firstname", firstname);
        if (categorycode != null)  json.put("categorycode", categorycode);
        if (branchcode != null)    json.put("branchcode", branchcode);
        if (email != null)         json.put("email", email);
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
