package restaurant_managment.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class OrderModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private ReservationModel reservation;

  @ManyToMany
  private List<DishModel> dishes;

  private String status;

  private Double totalPrice;

  public Double getTotalPrice() {
    if (totalPrice == null) {
      Double total = 0.0;
      for (DishModel dish : dishes) {
        total += dish.getPrice();
      }
      setTotalPrice(total);
      return total;
    }
    return totalPrice;
  }
}
