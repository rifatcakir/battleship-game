
package com.battleship.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class UserInfo implements Serializable {

    private UUID id;

    private String username;
}
