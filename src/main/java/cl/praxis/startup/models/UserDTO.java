package cl.praxis.startup.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String email;
    private String nick;
    private String name;
    private String password;
    private float weight;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDTO(String email, String nick, String name, String password, float weight, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.email = email;
        this.nick = nick;
        this.name = name;
        this.password = password;
        this.weight = weight;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
