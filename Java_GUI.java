
import javax.swing.JFrame;
import javax.swing.JButton;				
import javax.swing.JLabel;			
import javax.swing.JTextArea;			
import javax.swing.Icon;				
import javax.swing.ImageIcon;			
import javax.swing.SwingConstants;		
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;				
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

class Participant {

	private String buttonName;
	private String fullName;
	private String tutorialGroup;
	private String imageFile;

	public Participant() {

	}

	public Participant(String buttonName, String fullName, String tutorialGroup, String imageFile) {
		this.buttonName = buttonName;
		this.fullName = fullName;
		this.tutorialGroup = tutorialGroup;
		this.imageFile = imageFile;
	}

	public Participant(Participant p) {
		this(p.buttonName, p.fullName, p.tutorialGroup, p.imageFile);
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setTutorialGroup(String tutorialGroup) {
		this.tutorialGroup = tutorialGroup;
	}
	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	public String getButtonName() {
		return buttonName;
	}

	public String getFullName() {
		return fullName;
	}

	public String getTutorialGroup() {
		return tutorialGroup;
	}

	public String getImageFile() {
		return imageFile;
	}

	public String toString() {
		return String.format("Hi! I am participant%s%nMy name is %s%nI am from tutorial group: %s%n", buttonName, fullName, tutorialGroup);
	}
}

class QnaRoom extends JFrame {

	// Array of text area
	private final JTextArea [] jta = {new JTextArea (7,65), new JTextArea (7,65)};

	// Array of button
	private final JButton [] jbArray;

	// Array store default image file for button
	private final String [] defaultImagesFile = {"Pictures/default.png", "Pictures/default2.png", "Pictures/host.png", "Pictures/host2.png", "Pictures/brush.png", "Pictures/brush2.png"}; 

	// Store participants in an Array List 
	private ArrayList<Participant> participants = new ArrayList<Participant>();

	
	public QnaRoom(ArrayList<Participant> participants) {

		super("Q & A Room");
		setLayout(new FlowLayout());

		// Text Area
		for (int i = 0; i < jta.length; i++){
			jta[i].setSize(50, 50);
			jta[i].setFont (new Font ("Arial", Font.PLAIN, 14));
			jta[i].setForeground (Color.WHITE);
			
			// Participant text area
			if (i == 0) {
				jta[i].setBackground (Color.BLACK);
				add(jta[i]);
				add(new JLabel("Host Area", SwingConstants.CENTER));
			}

			// Host text area
			else {
				jta[i].setBackground (Color.BLUE);
				add(jta[i]);
			}
		}

		// Add participants
		this.participants = participants;

		jbArray = new JButton [participants.size() + 2];
		Icon ic;

		// Add participants button from the array
		for (int i = 0; i < participants.size(); i++) {	
			
			ic = new ImageIcon (defaultImagesFile[0]);
			
			jbArray [i] = new JButton (ic);
			jbArray [i].setText(participants.get(i).getButtonName());
			jbArray [i].setFont(new Font("Arial", Font.PLAIN, 18));
			jbArray [i].setBackground (Color.YELLOW);
			jbArray [i].setForeground (Color.RED);

			ic = new ImageIcon (defaultImagesFile[1]);
			jbArray [i].setRolloverIcon(ic);
			
			add (jbArray [i]);
		}

		// Add host and clear button
		for (int i = 0; i < 2; i++) {
			
			int index = participants.size() + i;

			if (i == 0)
				ic = new ImageIcon (defaultImagesFile[2]);
			else
				ic = new ImageIcon (defaultImagesFile[4]);

			jbArray [index] = new JButton (ic);
			jbArray [index].setFont(new Font("Arial", Font.PLAIN, 18));

			if (i == 0) {
				jbArray [index].setText(" Host ");
				jbArray [index].setBackground (Color.GREEN);
				jbArray [index].setForeground (Color.BLUE);
				
				ic = new ImageIcon (defaultImagesFile[3]);
				jbArray [index].setRolloverIcon(ic);
			}
				
			else {
				jbArray [index].setText(" Clear ");
				jbArray [index].setBackground (Color.CYAN);
				jbArray [index].setForeground (Color.BLACK);

				ic = new ImageIcon (defaultImagesFile[5]);
				jbArray [index].setRolloverIcon(ic);
			}		

			add (jbArray [index]);
		}
		

		// Register Event - Participant Button
		for (int i = 0; i < jbArray.length - 2; i++) 
			jbArray [i].addActionListener(e -> SubmitQuestion(e));

		// Register Event - Host Button
		jbArray [jbArray.length - 2].addActionListener(e -> SubmitAnswer());	

		// Register Event - Clear Button
		jbArray [jbArray.length - 1].addActionListener(e -> Clear());
	}

	private void SubmitQuestion(ActionEvent e) {

		String s = "";
		Icon ic = new ImageIcon ();
		int index = 0;

		while (s.equals("")){
			if (e.getSource() == jbArray [index]){
				s = participants.get(index).toString() + jta[0].getText();

				ic = new ImageIcon (defaultImagesFile[1]);
				jbArray [index].setIcon(ic);

				ic = new ImageIcon (participants.get(index).getImageFile());
			}

			index ++;
		}

		JOptionPane.showMessageDialog(null, s, "Welcome to Chat Room", JOptionPane.INFORMATION_MESSAGE, ic);

		ic = new ImageIcon (defaultImagesFile[0]);
		jbArray [index-1].setIcon(ic);
	}

	private void SubmitAnswer() {

		String s = jta[1].getText();

		Icon ic = new ImageIcon (defaultImagesFile[3]);
		jbArray [jbArray.length - 2].setIcon(ic);

		ic = new ImageIcon ("Pictures/host_profile.png"); 

		JOptionPane.showMessageDialog(null, s, "I am the host", JOptionPane.INFORMATION_MESSAGE, ic);

		ic = new ImageIcon (defaultImagesFile[2]);
		jbArray [jbArray.length - 2].setIcon(ic);
	}

	private void Clear() {

		for (int i = 0; i < jta.length; i++){
			jta[i].setText("");
		}
	}
}


class Java_GUI {
	public static void main(String[] args) {

		ArrayList<Participant> participants = new ArrayList<Participant>();
		
		Participant p1 = new Participant(" One ", "Master Tigress", "T01", "Pictures/tigress.jpg");
		Participant p2 = new Participant(" Two ", "Master Viper", "T03", "Pictures/viper.jpg");
		Participant p3 = new Participant(" Three ", "Master Monkey", "T05", "Pictures/monkey.jpg");
		Participant p4 = new Participant(" Four ", "Master Mantis", "T06", "Pictures/mantis.jpg");

		participants.add(p1);
		participants.add(p2);
		participants.add(p3);
		participants.add(p4);

		QnaRoom qna = new QnaRoom(participants);
		
		qna.setSize(750, 480);
		qna.setVisible(true);
		qna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}



