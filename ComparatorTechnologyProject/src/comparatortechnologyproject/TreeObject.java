package comparatortechnologyproject;

import comparator.scheduler.Subscriber;
import comparatortechnologyproject.TreeParent;

class TreeObject {
	
	private Subscriber subscriber;
	private TreeParent parent;
	
	public TreeObject(Subscriber subscriber) {
		this.subscriber = subscriber;
	}
	public String getName() {
		return subscriber.getName();
	}
	
	public Subscriber getSubscriber(){
		return subscriber;
	}
	public void setParent(TreeParent treeParent) {
		this.parent = treeParent;
	}
	public TreeParent getParent() {
		return parent;
	}
	public String toString() {
		return getName();
	}
}