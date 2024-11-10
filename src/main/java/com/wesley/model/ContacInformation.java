package com.wesley.model;



import jakarta.persistence.Embeddable;
import lombok.Data;


@Data
@Embeddable
public class ContacInformation {

    private String phone;
    private String email;
    private String website;
    private String facebook;
    private String instagram;
    private String X;
}
