package lk.ijse.gdse.pizzahubsystem.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerTM {
    private String customer_id;
    private String name;
    private String contact;
    private String email;
    private String address;

}
