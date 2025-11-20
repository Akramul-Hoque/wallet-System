package walletSystem.userService.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_permanent_addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermanentAddress{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String road;
    private String post;
    private String city;
}
