package com.ivo.tasevski.quartzschedulerdemo.app;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Accessors(chain = true)
@Setter
@Getter
public class Greeting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String message;
}
