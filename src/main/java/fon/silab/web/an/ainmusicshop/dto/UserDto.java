/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.dto;

import fon.silab.web.an.ainmusicshop.entity.UserEntity.UserRole;

/**
 *
 * @author lj
 */
public class UserDto {
    
    private int userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String adress;
    private String city;
    private UserRole roleUser;
    private String password;
    private String re_password;
    private String zip;
    private int emailConfirmed;
    private String emailToken;

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
    

    public UserDto() {
    }

    public UserDto(int userId, String firstname, String lastname, String email, String phoneNumber, String adress, String city, UserRole roleUser, String password, String zip) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.adress = adress;
        this.city = city;
        this.roleUser = roleUser;
        this.password = password;
        this.zip = zip;
    }

    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
    }

    
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public UserRole getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(UserRole roleUser) {
        this.roleUser = roleUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto{" + "userId=" + userId + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", phoneNumber=" + phoneNumber + ", adress=" + adress + ", city=" + city + ", roleUser=" + roleUser + ", password=" + password + '}';
    }

    public int getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(int emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }
    
    
    
}
