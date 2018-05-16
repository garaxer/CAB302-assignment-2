package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUIComponents extends JPanel implements ActionListener{


     
     public GUIComponents() {
    	// Initialize the GUI Components
 		initialiseComponents();	 
     }
	
 	private void initialiseComponents()	{
 		//Choose a box layout for the main frame
 		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
 		
 		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.add(Box.createHorizontalGlue());
		p1.add(new JButton("Right Aligned"));
 	     
 	     
 	     
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p2.add(Box.createHorizontalGlue());
		p2.add(new JButton("Center Aligned"));
		p2.add(Box.createHorizontalGlue());
 	     
        
        this.add(p1);
        this.add(Box.createVerticalStrut(20));
        this.add(p2);
 	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
