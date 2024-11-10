package com.wesley.dto;

import com.wesley.model.Restaurant;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class RestauranteDTO{
       private String title;

       private String description;

       @Column(name = "image_url", length = 1000)
       List<String> images;

       private Long id;

}
