package com.wesley.dto;


import com.wesley.model.Restaurant;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class RestauranteDTO{
       private String name;

       private String description;

       @Column(name = "image_url", length = 1000)
       List<String> image;

       private Long id;

}
