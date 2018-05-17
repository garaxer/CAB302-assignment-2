package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class GUIComponents extends JPanel implements ActionListener{

	//change to panel with the button, see slide37 of lecture 9
	public Store store;
     
     public GUIComponents(Store store){
    	 this.store = store;
    	// Initialize the GUI Components
 		initialiseComponents();	 
     }
	
 	private void initialiseComponents()	{
 		//Choose a box layout for the main frame
 		
 		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
 		
 		
 		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.LIGHT_GRAY);
		topPanel.setLayout(new FlowLayout());
		
		topPanel.setSize(100, 100);
		JButton redButton = new JButton("Load in manifests");
		redButton.setBackground(Color.RED);
		redButton.addActionListener(this);
		topPanel.add(redButton);
		
		topPanel.add(new JLabel("Store Capital: "+store.getCapitalString()));
		
		JButton blueButton = new JButton("Load in sales logs");
		blueButton.setBackground(Color.BLUE);
		blueButton.setForeground(Color.WHITE);
		blueButton.addActionListener(this);
		topPanel.add(blueButton);
		topPanel.setSize(getMinimumSize());
		//can just add 3 boxes, one left, one center and one right 
		this.add(topPanel);

		
 		//JPanel p1 = new JPanel();
		String data[][]={ {"1","2","3"},    
		{"4","6","7"},    
		{"8","9","10"}
		};    
		String column[]={"Name","stuff","stuff"};         
		JTable table =new JTable(data,column);      
		JScrollPane scroll = new JScrollPane(table);  
		//p1.add(scroll);
		this.add(scroll);          

 		/*
 		 * 	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
 		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.add(Box.createHorizontalGlue());
		p1.add(new JLabel(store.getCapitalString()));
 	     
     
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p2.add(Box.createHorizontalGlue());
		p2.add(Box.createHorizontalGlue());
		p2.add(new JButton("Center Aligned"));
		//p2.add(Box.createHorizontalGlue());
 	     
        this.add(p1);
        this.add(Box.createVerticalStrut(10));
        this.add(p2);*/
 	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
