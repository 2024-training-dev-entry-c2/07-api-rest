package restaurant_managment.Utils.Dto.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Models.OrderModel;
import restaurant_managment.Models.ReservationModel;
import restaurant_managment.Repositories.DishRepository;
import restaurant_managment.Repositories.ReservationRepository;
import restaurant_managment.Utils.Dto.Dish.DishResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDTOConverter {

  private final ReservationRepository reservationRepository;
  private final DishRepository dishRepository;

  @Autowired
  public OrderDTOConverter(ReservationRepository reservationRepository, DishRepository dishRepository) {
    this.reservationRepository = reservationRepository;
    this.dishRepository = dishRepository;
  }

  public OrderModel toOrder(OrderRequestDTO dto) {
    OrderModel order = new OrderModel();
    order.setStatus(dto.getStatus());

    ReservationModel reservation = reservationRepository.findById(dto.getReservationId())
      .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
    order.setReservation(reservation);

    List<DishModel> dishes = dto.getDishIds().stream()
      .map(dishId -> dishRepository.findById(dishId).orElseThrow(() -> new IllegalArgumentException("Dish not found")))
      .collect(Collectors.toList());
    order.setDishes(dishes);

    return order;
  }

  public static OrderResponseDTO toOrderResponseDTO(OrderModel order) {
    OrderResponseDTO dto = new OrderResponseDTO();
    dto.setId(order.getId());
    dto.setReservationId(order.getReservation().getId());
    dto.setStatus(order.getStatus());
    dto.setTotalPrice(order.getTotalPrice());

    List<DishResponseDTO> dishResponses = order.getDishes().stream()
      .map(dish -> {
        DishResponseDTO dishResponse = new DishResponseDTO();
        dishResponse.setId(dish.getId());
        dishResponse.setName(dish.getName());
        dishResponse.setIsPopular(dish.getIsPopular());
        dishResponse.setDescription(dish.getDescription());
        dishResponse.setPrice(dish.getPrice());
        return dishResponse;
      })
      .collect(Collectors.toList());
    dto.setDishes(dishResponses);

    return dto;
  }
}