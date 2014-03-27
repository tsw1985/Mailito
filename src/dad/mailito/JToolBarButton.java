package dad.mailito;

import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class JToolBarButton  extends JButton{
	
public JToolBarButton(String toolTip , Icon icon, ActionListener actionListener){
		
		super();
		
		setIcon(icon);
		setOpaque(false);
		setBorderPainted(false);
		setFocusable(false);
		setToolTipText(toolTip);
		setSize(100,100);
		addActionListener(actionListener);
		
		
	}

}
