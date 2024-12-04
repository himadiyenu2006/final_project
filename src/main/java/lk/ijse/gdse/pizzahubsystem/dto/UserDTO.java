package lk.ijse.gdse.pizzahubsystem.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private String user_id;
    private String username;
    private String password;
    private String role;

}
