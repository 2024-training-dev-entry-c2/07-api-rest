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
import restaurant_managment.Observer.IObservable;
import restaurant_managment.Observer.IObserver;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class CustomerModel implements IObservable {

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
  private List<IObserver> observers = new ArrayList<>();

  @Transient
  private Integer totalOrders;

  public void updateFrecuency(EntityManager entityManager) {
    totalOrders = entityManager.createQuery("SELECT COUNT(o) FROM OrderModel o WHERE o.reservation.customer.id = :customerId", Long.class)
      .setParameter("customerId", this.id)
      .getSingleResult().intValue();

    if (totalOrders >= 10 && !this.isFrequent) {
      setIsFrequent(true);
      notifyObservers("Customer " + this.firstName + " has become frequent.");
    }
  }

  @Override
  public void addObserver(IObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(IObserver observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers(String message) {
    for (IObserver observer : observers) {
      observer.update(message);
    }
  }
}
