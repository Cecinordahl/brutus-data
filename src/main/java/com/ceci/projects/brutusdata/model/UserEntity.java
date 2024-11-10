package com.ceci.projects.brutusdata.model;
import jakarta.persistence.*;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String address;
    private String ccnumber;

    // TODO remove comments
    // Method to return a masked credit card number for display purposes
    // The @Transient annotation ensures getMaskedCcnumber is not persisted to the database.
    @Transient
    public String getMaskedCcnumber() {
        return "**** **** **** " + ccnumber.substring(ccnumber.length() - 4);
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCcnumber() {
        return ccnumber;
    }

    public void setCcnumber(String ccnumber) {
        this.ccnumber = ccnumber;
    }
}