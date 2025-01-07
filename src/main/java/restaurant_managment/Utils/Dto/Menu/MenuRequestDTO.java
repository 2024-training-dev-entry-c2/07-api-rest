package restaurant_managment.Utils.Dto.Menu;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuRequestDTO {
  private String name;
  private List<Long> dishIds;
}