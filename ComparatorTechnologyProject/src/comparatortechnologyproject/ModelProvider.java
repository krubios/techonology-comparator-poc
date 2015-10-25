package comparatortechnologyproject;

import java.util.ArrayList;
import java.util.List;

import comparator.scheduler.Subscriber;

public enum ModelProvider {
	INSTANCE;

  private List<Subscriber> subscriber;

  private ModelProvider() {
	subscriber = new ArrayList<Subscriber>();
    // Image here some fancy database access to read the persons and to
    // put them into the model
  }

  public List<Subscriber> getSubscriber() {
    return subscriber;
  }
  
  public void clearAll(){
	  subscriber.clear();
  }
  
  public void setSubscriber(Subscriber subs){
	  subscriber.add(subs);
  }

}
