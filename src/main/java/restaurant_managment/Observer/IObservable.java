package restaurant_managment.Observer;

public interface IObservable {
  void addObserver(IObserver observer);
  void removeObserver(IObserver observer);
  void notifyObservers(String message);
}
