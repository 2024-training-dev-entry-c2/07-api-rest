package restaurant_managment.Dto.Customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDTO {
  private String firstName;
  private String lastName;
  private String email;
  private String phone;

}