package restaurant_managment.Dto.Order;

import restaurant_managment.Dto.Dish.DishResponseDTO;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Models.OrderModel;
import restaurant_managment.Models.ReservationModel;
import restaurant_managment.Repositories.MenuRepository;
import restaurant_managment.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDTOConverter {

  @Autowired
  private ReservationRepository reservationRepository;

  @Autowired
  private MenuRepository menuRepository;

  public OrderModel toOrder(OrderRequestDTO dto) {
    OrderModel order = new OrderModel();
    order.setStatus(dto.getStatus());

    ReservationModel reservation = reservationRepository.findById(dto.getReservationId())
      .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
    order.setReservation(reservation);

    List<DishModel> dishes = dto.getDishIds().stream()
      .map(dishId -> menuRepository.getReferenceById(dishId).getDishes().stream().filter(dish -> dish.getId().equals(dishId)).findFirst().orElse(null))
      .collect(Collectors.toList());
    order.setDishes(dishes);

    return order;
  }

  public OrderResponseDTO toOrderResponseDTO(OrderModel order) {
    OrderResponseDTO dto = new OrderResponseDTO();
    dto.setId(order.getId());
    dto.setReservationId(order.getReservation().getId());
    dto.setStatus(order.getStatus());

    // Convertir los objetos DishModel a DishResponseDTO
    List<DishResponseDTO> dishResponses = order.getDishes().stream()
      .map(dish -> {
        DishResponseDTO dishResponse = new DishResponseDTO();
        dishResponse.setId(dish.getId());
        dishResponse.setName(dish.getName());
        dishResponse.setPrice(dish.getPrice());
        return dishResponse;
      })
      .collect(Collectors.toList());
    dto.setDishes(dishResponses);

    return dto;
  }
}