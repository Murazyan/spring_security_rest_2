package com.example.spring_security_rest_2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles_rest")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id")
    protected int id;

    @Column(name = "name", unique = true)
    private String name;

    @Column
    @JsonIgnore
    private LocalDateTime created ;

    @Column
    @JsonIgnore
    private LocalDateTime updated;

    @PrePersist
    public void onCreate(){
        this.created = LocalDateTime.now();
        this.updated = created;
    }


    @PreUpdate
    public void onUpdate(){
        this.updated = LocalDateTime.now();
    }

    public Role(String name){
        this.name = name;
    }

}
