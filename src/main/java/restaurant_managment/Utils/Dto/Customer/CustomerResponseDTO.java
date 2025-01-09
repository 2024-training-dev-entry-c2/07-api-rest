package restaurant_managment.Utils.Dto.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private Boolean isFrequent;

}