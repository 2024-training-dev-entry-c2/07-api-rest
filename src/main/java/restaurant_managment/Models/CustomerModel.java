package restaurant_managment.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
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

  public CustomerModel(Long id, Boolean isFrequent, String firstName, String lastName, String email, String phone) {
    this.id = id;
    this.isFrequent = isFrequent;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
  }

  public CustomerModel(Boolean isFrequent, String firstName, String lastName, String email, String phone) {
    this.isFrequent = isFrequent;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
  }

  public CustomerModel() {
  }

  public CustomerModel updateFrecuency(EntityManager entityManager) {
    Integer totalOrders;

    if (!this.isFrequent)
    {
      totalOrders = entityManager.createQuery("SELECT COUNT(o) FROM OrderModel o WHERE o.reservation.customer.id = :customerId", Long.class)
        .setParameter("customerId", this.id)
        .getSingleResult().intValue();
      verifyTotalOrders(totalOrders);
    }
    return null;
  }

  public void verifyTotalOrders(Integer totalOrders) {
    if (totalOrders >= 10) {
      setIsFrequent(true);
    }
  }
}
