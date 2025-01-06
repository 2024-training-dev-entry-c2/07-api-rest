package restaurant_managment.Observer;

import jakarta.persistence.EntityManager;
import restaurant_managment.Models.CustomerModel;

public class ClientObserver implements IObserver {
  private CustomerModel customer;
  private EntityManager entityManager;

  public ClientObserver(CustomerModel customer, EntityManager entityManager) {
    this.customer = customer;
    this.entityManager = entityManager;
  }

  @Override
  public void update() {
    customer.updateFrecuency(entityManager);
    if (customer.getIsFrequent()) {
    System.out.println("El cliente " + customer.getFirstName() + " " + customer.getLastName() + " es cliente frecuente!!");
    };
  }
}
