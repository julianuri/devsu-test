package com.devsu.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String phone;
    private String address;
}
