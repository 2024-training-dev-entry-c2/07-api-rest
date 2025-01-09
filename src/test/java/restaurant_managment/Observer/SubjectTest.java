package restaurant_managment.Observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class SubjectTest {

  private Subject subject;
  private IObserver mockObserver1;
  private IObserver mockObserver2;

  @BeforeEach
  public void setUp() {
    subject = new Subject();
    mockObserver1 = mock(IObserver.class);
    mockObserver2 = mock(IObserver.class);
  }

  @Test
  public void testAddObserver() {

    subject.addObserver(mockObserver1);

    subject.notifyObservers();
    verify(mockObserver1).update();
  }

  @Test
  public void testRemoveObserver() {

    subject.addObserver(mockObserver1);
    subject.addObserver(mockObserver2);

    subject.removeObserver(mockObserver1);
    subject.notifyObservers();

    verify(mockObserver1, never()).update();
    verify(mockObserver2).update();
  }

  @Test
  public void testNotifyObservers() {

    subject.addObserver(mockObserver1);
    subject.addObserver(mockObserver2);

    subject.notifyObservers();

    verify(mockObserver1).update();
    verify(mockObserver2).update();
  }
}