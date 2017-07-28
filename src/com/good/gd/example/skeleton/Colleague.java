package com.good.gd.example.skeleton;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by developer01 on 19/06/2017.
 */
public class Colleague implements Parcelable{

    private String title;
    private String name;
    private String mobile;
    private String location;
    private String altContactNumber;
    private String homePhone;
    private String landline;
    private String internalLandline;
    private String postalAddress;
    private String emailAddress;
    private String lineManager;
    private String secretary;
    private String jobTitle;
    private String department;
    private String businessUnit;
    private String division;
    private String userID;
    private String country;
    private String costCentre;
    private String website;
    private String addtnL;
    private String AXISGroup;
    private String MWOTGroup;
    private String lineManagerDetailsLink;
    private Bitmap profileImage;

    public Colleague(String name, String jobTitle, String department, String location) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.location = location;
        this.department = department;
    }

    public Colleague(String name, String jobTitle, String department, String location, String landline, String mobile, String emailAddress) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.location = location;
        this.department = department;
        this.landline = landline;
        this.mobile = mobile;
        this.emailAddress = emailAddress;
    }

    public Colleague(String title, String name, String mobile, String atlContact, String homePhone, String internalLandline, String landLine, String postalAddress, String emailAddress,
                     String lineManager, String secretary, String jobTitle, String department, String businessUnit, String division, String userID,
                     String country, String costCentre, String website, String addLocation, String axisgroup, String mwotGroup, String lineManagerLink) {

        this.title = title;
        this.name = name;
        this.mobile = mobile;
        this.altContactNumber = atlContact;
        this.homePhone = homePhone;
        this.internalLandline = internalLandline;
        this.landline = landLine;
        this.postalAddress = postalAddress;
        this.emailAddress = emailAddress;
        this.lineManager = lineManager;
        this.secretary = secretary;
        this.jobTitle = jobTitle;
        this.department = department;
        this.businessUnit = businessUnit;
        this.division = division;
        this.userID = userID;
        this.country = country;
        this.costCentre = costCentre;
        this.website = website;
        this.addtnL = addLocation;
        this.AXISGroup = axisgroup;
        this.MWOTGroup = mwotGroup;
        this.lineManagerDetailsLink = lineManagerLink;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setAlternateContactNumber(String altContactNumber) {
        this.altContactNumber = altContactNumber;
    }

    public String getAlternateContactNumber() {
        return altContactNumber;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getLandline() {
        return landline;
    }

    public void setInternalLandline(String internalLandline) {
        this.internalLandline = internalLandline;
    }

    public String getInternalLandline() {
        return internalLandline;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setLineManager(String lineManager) {
        this.lineManager = lineManager;
    }

    public String getLineManager() {
        return lineManager;
    }

    public void setSecretary(String secretary) {
        this.secretary = secretary;
    }

    public String getSecretary() {
        return secretary;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDivision() {
        return division;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCostCentre(String costCentre) {
        this.costCentre = costCentre;
    }

    public String getCostCentre() {
        return costCentre;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setAddLocation(String addtnL) {
        this.addtnL = addtnL;
    }

    public String getAddLocation() {
        return addtnL;
    }

    public void setAXISGroup(String AXISGroup) {
        this.AXISGroup = AXISGroup;
    }

    public String getAXISGroup() {
        return AXISGroup;
    }

    public void setMWOTGroup(String MWOTGroup) {
        this.MWOTGroup = MWOTGroup;
    }

    public String getMWOTGroup() {
        return MWOTGroup;
    }

    public void setLineManagerLink(String managerLink) {
        this.lineManagerDetailsLink = managerLink;
    }

    public String getLineManagerLink() {
        return lineManagerDetailsLink;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public Colleague(Parcel in){
        // the order needs to be the same as in writeToParcel() method
        this.title = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
        this.altContactNumber = in.readString();
        this.homePhone = in.readString();
        this.internalLandline = in.readString();
        this.landline = in.readString();
        this.postalAddress = in.readString();
        this.emailAddress = in.readString();
        this.lineManager = in.readString();
        this.secretary = in.readString();
        this.jobTitle = in.readString();
        this.department = in.readString();
        this.businessUnit = in.readString();
        this.division = in.readString();
        this.userID = in.readString();
        this.country = in.readString();
        this.costCentre = in.readString();
        this.website = in.readString();
        this.addtnL = in.readString();
        this.AXISGroup = in.readString();
        this.MWOTGroup = in.readString();
        this.lineManagerDetailsLink = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeString(altContactNumber);
        dest.writeString(homePhone);
        dest.writeString(internalLandline);
        dest.writeString(landline);
        dest.writeString(postalAddress);
        dest.writeString(emailAddress);
        dest.writeString(lineManager);
        dest.writeString(secretary);
        dest.writeString(jobTitle);
        dest.writeString(department);
        dest.writeString(businessUnit);
        dest.writeString(division);
        dest.writeString(userID);
        dest.writeString(country);
        dest.writeString(costCentre);
        dest.writeString(website);
        dest.writeString(addtnL);
        dest.writeString(AXISGroup);
        dest.writeString(MWOTGroup);
        dest.writeString(lineManagerDetailsLink);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Colleague createFromParcel(Parcel in) {
            return new Colleague(in);
        }

        public Colleague[] newArray(int size) {
            return new Colleague[size];
        }
    };

}
