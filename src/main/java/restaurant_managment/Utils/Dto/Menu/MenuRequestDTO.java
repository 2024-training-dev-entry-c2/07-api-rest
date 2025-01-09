package restaurant_managment.Utils.Dto.Menu;

import java.util.List;

public class MenuRequestDTO {
  private String name;
  private List<Long> dishIds;

  public MenuRequestDTO(String name, List<Long> dishIds) {
    this.name = name;
    this.dishIds = dishIds;
  }

  public MenuRequestDTO() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Long> getDishIds() {
    return dishIds;
  }

  public void setDishIds(List<Long> dishIds) {
    this.dishIds = dishIds;
  }
}