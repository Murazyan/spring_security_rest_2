package com.example.spring_security_rest_2.model;

import com.example.spring_security_rest_2.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users_rest")
@EqualsAndHashCode(of = {"id"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id")
    protected int id;

    @Column
    @NotBlank(message = "User name is required")
    private String name;

    @Column
    @NotBlank(message = "User surname is required")
    private String surname;

    @Email(message = "Invalid email format")
    @Column(name = "email", unique = true)
    private String email;


    @Column
    @JsonIgnore
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;


    @Column
//    @JsonIgnore
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime created;

    @Column
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonIgnore
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime passwordChanged;

    @Column
//    @JsonIgnore
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles_rest",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;



    @PrePersist
    public void onCreate() {
        this.created = LocalDateTime.now();
        this.updated = created;
        this.passwordChanged = created;
    }

    @PreUpdate
    public void onUpdate() {
        this.updated = LocalDateTime.now();
    }



    public boolean isAdmin(){
        return this.roles.stream()
                .map(Role::getName)
                .anyMatch(role->role.equalsIgnoreCase("admin"));
    }

}


