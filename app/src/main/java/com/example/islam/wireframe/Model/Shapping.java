package com.example.islam.wireframe.Model;

import java.io.Serializable;

/**
 * Created by islam on 24/12/2016.
 */

public class Shapping implements Serializable {

    private String shappingCode;
    private String shappingStatuse;
    private String shappingreason;
    private String shappingRelation;
    private String status;
    private String relation;
    private String reason;
    private String Costumername;
    private String Costumerid;
    private String Costumeraddress;
    private String Costumerphone;
    private byte[] CostumerSingniture;
    private float Costumerlat;
    private float Costumerlng;
    private String UserId;
    private String Username;
    private String Userpassword;
    private String Userphone;
    private String Userkind;
    private String UserAddress;
    private String Sync;
    private byte[] image;


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    private String Date;

    public String getSync() {
        return Sync;
    }

    public void setSync(String sync) {
        Sync = sync;
    }

    public String getShappingRelation() {
        return shappingRelation;
    }

    public void setShappingRelation(String shappingRelation) {
        this.shappingRelation = shappingRelation;
    }

    public String getShappingStatuse() {
        return shappingStatuse;
    }

    public void setShappingStatuse(String shappingStatuse) {
        this.shappingStatuse = shappingStatuse;
    }

    public String getShappingreason() {
        return shappingreason;
    }

    public void setShappingreason(String shappingreason) {
        this.shappingreason = shappingreason;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }

    public String getCostumername() {
        return Costumername;
    }

    public void setCostumername(String costumername) {
        Costumername = costumername;
    }

    public String getCostumerid() {
        return Costumerid;
    }

    public void setCostumerid(String costumerid) {
        Costumerid = costumerid;
    }

    public String getCostumeraddress() {
        return Costumeraddress;
    }

    public void setCostumeraddress(String costumeraddress) {
        Costumeraddress = costumeraddress;
    }

    public String getCostumerphone() {
        return Costumerphone;
    }

    public void setCostumerphone(String costumerphone) {
        Costumerphone = costumerphone;
    }

    public byte[] getCostumerSingniture() {
        return CostumerSingniture;
    }

    public void setCostumersingniture(byte[] costumerSingniture) {
        CostumerSingniture = costumerSingniture;
    }

    public float getCostumerlat() {
        return Costumerlat;
    }

    public void setCostumerlat(float costumerlat) {
        Costumerlat = costumerlat;
    }

    public float getCostumerlng() {
        return Costumerlng;
    }

    public void setCostumerlng(float costumerlng) {
        Costumerlng = costumerlng;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserpassword() {
        return Userpassword;
    }

    public void setUserpassword(String userpassword) {
        Userpassword = userpassword;
    }

    public String getUserphone() {
        return Userphone;
    }

    public void setUserphone(String userphone) {
        Userphone = userphone;
    }

    public String getUserkind() {
        return Userkind;
    }

    public void setUserkind(String userkind) {
        Userkind = userkind;
    }

    public String getShappingCode() {
        return shappingCode;
    }

    public void setShappingCode(String shappingCode) {
        this.shappingCode = shappingCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
