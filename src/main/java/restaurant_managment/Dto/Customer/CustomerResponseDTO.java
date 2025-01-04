package restaurant_managment.Dto.Customer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerResponseDTO {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private Boolean isFrequent;
}