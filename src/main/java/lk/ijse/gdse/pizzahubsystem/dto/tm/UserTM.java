package lk.ijse.gdse.pizzahubsystem.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTM {
    private String user_id;
    private String username;
    private String password;
    private String email;
    private String role;
}
