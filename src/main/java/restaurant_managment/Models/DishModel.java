package restaurant_managment.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dishes")
public class DishModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Boolean isPopular;

  private Boolean isAvailable;

  private String name;

  private Double price;

  private String description;

  @Transient
  private Integer totalOrders;

  public void updatePopularity(EntityManager entityManager) {
    totalOrders = entityManager.createQuery("SELECT COUNT(o) FROM OrderModel o JOIN o.dishes d WHERE d.id = :dishId", Long.class)
      .setParameter("dishId", this.id)
      .getSingleResult().intValue();

    if (totalOrders >= 100 && !this.isPopular) {
      this.isPopular = true;
    }
  }
}