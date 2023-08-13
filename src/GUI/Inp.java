package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Inp {

	JFileChooser fc;
	JPanel panel;
	JFrame window;
	JButton button1, button3;
	JLabel lb1, lb3, lb5;
	JTextField tf1;
	boolean check1, check3;
	SortedSet<String> server,wrapper;

	Inp() {

		window = new JFrame("Select Files");
		panel = new JPanel();
		button1 = new JButton("Select");
		button3 = new JButton("Open");
		check1 = false;
		server = new TreeSet<String>();
		wrapper = new TreeSet<String>();

		tf1 = new JTextField("No folder selected");
		lb1 = new JLabel("Select the folder: ");
		lb3 = new JLabel("");
		lb5 = new JLabel("");

		tf1.setBounds(175, 30, 500, 25);
		tf1.setEditable(false);

		lb1.setBounds(20, 30, 250, 25);
		lb3.setBounds(330, 60, 300, 25);
		lb5.setBounds(320, 220, 300, 25);

		button1.setBounds(700, 30, 70, 25);
		button3.setBounds(360, 185, 100, 35);

		panel.add(tf1);
		panel.add(lb1);
		panel.add(lb3);
		panel.add(lb5);
		panel.add(button1);
		panel.add(button3);
		panel.setSize(805, 400);
		panel.setBackground(Color.white);
		panel.setLayout(null);
		window.add(panel);
		window.setBackground(Color.white);
		window.setLayout(null);
		window.setSize(805, 300);
		window.setVisible(true);
		window.setLocationRelativeTo(null);

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser("C:\\Users\\bgsan");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setAcceptAllFileFilterUsed(false);
				int i = fc.showOpenDialog(button1);

				if (i == JFileChooser.APPROVE_OPTION) {
					if (fc.getSelectedFile().equals(null))
						;
					else {
						check1 = false;
						File[] files = fc.getSelectedFile().listFiles();
						for (File f : files)
							if (f.getAbsolutePath().contains("serverOut"))
								check1 = true;
						if (check1 == false) {
							tf1.setText("No folder selected");
							lb3.setText("Please choose the right folder!");
						} else {
							lb3.setText("");
							for (File f : files) {
								if (f.getAbsolutePath().contains("serverOut"))
									server.add(f.getAbsolutePath());
								else if (f.getAbsolutePath().contains("wrapper"))
									wrapper.add(f.getAbsolutePath());
							}
							tf1.setText(fc.getSelectedFile().getAbsolutePath());
							System.out.println(server + " " + wrapper);
						}
					}
				}
			}
		});
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (check1 == true) {
					check3 = true;
					window.dispose();
					try {
						new disp5(server, wrapper);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				else {
					lb5.setText("Please choose the right folder!");
				}
			}
		});

	}

	public static void main(String[] args) {
		new Inp();
	}
}
