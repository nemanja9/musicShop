/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 *
 * @author lj
 */
@Entity(name = "users")
@Table(name = "users")
public class UserEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private int userId;

    @Column(name = "firstname", nullable = false, length = 55)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 55)
    private String lastname;

    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 25)
    private String phoneNumber;

    @Column(name = "adress", nullable = false, length = 256)
    private String adress;

    @Column(name = "city", nullable = false, length = 256)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role",  length = 256, nullable = false)
    private UserRole roleUser;

    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Transient
    @Column(name = "re_password", insertable = false, updatable = false)
    private String re_password;

    public UserEntity() {
    }

    public UserEntity(int userId, String firstname, String lastname, String email, String phoneNumber, String adress, String city, UserRole role, String password) {
        super();
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.adress = adress;
        this.city = city;
        this.roleUser = role;
        this.password = password;
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

    public UserRole getRole() {
        return roleUser;
    }

    public void setRole(UserRole role) {
        this.roleUser = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.userId);
        hash = 59 * hash + Objects.hashCode(this.firstname);
        hash = 59 * hash + Objects.hashCode(this.lastname);
        hash = 59 * hash + Objects.hashCode(this.email);
        hash = 59 * hash + Objects.hashCode(this.phoneNumber);
        hash = 59 * hash + Objects.hashCode(this.adress);
        hash = 59 * hash + Objects.hashCode(this.city);
        hash = 59 * hash + Objects.hashCode(this.roleUser);
        hash = 59 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserEntity other = (UserEntity) obj;
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserEntity{" + "userId=" + userId + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", phoneNumber=" + phoneNumber + ", adress=" + adress + ", city=" + city + ", role=" + roleUser + ", password=" + password + '}';
    }

    
    
    public enum UserRole{
        ROLE_USER,
        ROLE_ADMIN
    }
}

