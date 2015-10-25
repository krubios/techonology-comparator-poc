package comparatortechnologyproject;

import java.util.ArrayList;

import comparator.scheduler.Subscriber;

class TreeParent extends TreeObject {
	private ArrayList<TreeObject> children;
	
	public TreeParent(Subscriber subscriber) {
		super(subscriber);
		children = new ArrayList<TreeObject>();
	}
	public void addChild(TreeObject child) {
		children.add(child);
		child.setParent(this);
	}
	public void removeChild(TreeObject child) {
		children.remove(child);
		child.setParent(null);
	}
	public TreeObject[] getChildren() {
		return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
	}
	public boolean hasChildren() {
		return children.size()>0;
	}
}
