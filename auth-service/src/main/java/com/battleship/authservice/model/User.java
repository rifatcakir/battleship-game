package com.battleship.authservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users",
        indexes = {
                @Index(name = "username_idx", columnList = "username")
        }
)
@Data
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;
}
