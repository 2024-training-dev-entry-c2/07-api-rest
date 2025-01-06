package restaurant_managment.Models;

import jakarta.persistence.Column;
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
@Table(name = "customers")
public class CustomerModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Boolean isFrequent = false;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;

  @Transient
  private Integer totalOrders;

  public void updateFrecuency(EntityManager entityManager) {
    totalOrders = entityManager.createQuery("SELECT COUNT(o) FROM OrderModel o WHERE o.reservation.customer.id = :customerId", Long.class)
      .setParameter("customerId", this.id)
      .getSingleResult().intValue();

    if (totalOrders >= 10 && !this.isFrequent) {
      setIsFrequent(true);
    }
  }
}
