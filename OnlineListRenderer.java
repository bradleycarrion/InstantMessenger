import java.awt.Component;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;


public class OnlineListRenderer extends DefaultListCellRenderer {
	private Map<String, ImageIcon> imageMap;
	
	public OnlineListRenderer(Map<String,ImageIcon> imageMap)  {
		super();
		this.imageMap = imageMap;
	}
	
	@Override
	public Component getListCellRendererComponent(
	     JList list, Object value, int index,
	     boolean isSelected, boolean cellHasFocus) {

	     JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	     label.setIcon(imageMap.get((String) value));
	     label.setHorizontalTextPosition(JLabel.RIGHT);
         return label;
	    }
	
}
