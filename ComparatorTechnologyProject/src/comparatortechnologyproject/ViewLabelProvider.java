package comparatortechnologyproject;

import java.net.URL;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

class ViewLabelProvider extends LabelProvider {

	public String getText(Object obj) {
		return obj.toString();
	}
	@SuppressWarnings("deprecation")
	public Image getImage(Object obj) {
		URL url = Activator.getDefault().find(new Path("icons/subscriberStation.gif"));
		if (obj instanceof TreeParent){
			url = Activator.getDefault().find(new Path("icons/baseStation.gif"));
		}
		
		ImageDescriptor id = ImageDescriptor.createFromURL(url);
		return id.createImage();
	}
}
