package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import main.Reader2;

public class disp5 {

	JFrame window;
	JPanel details;
	JTextArea Summary;
	JTabbedPane tab, subtab1, subtab2;

	public disp5(SortedSet<String> server, SortedSet<String> wrapper) throws IOException {

		window = new JFrame("Parser");
		details = new JPanel();
		Reader2 sum = new Reader2();
		String[] uheader = { "Id", "Time", "Thread Id", "Exception", "Trace" };
		String[] sheader = { "Id", "Time", "Thread Id", "No of occurences", "Exception", "Trace" };
		String[] wheader = { "Time", "Error" };

		tab = new JTabbedPane();
		subtab1 = new JTabbedPane();
		subtab2 = new JTabbedPane();

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 17);

		Summary = new JTextArea(1000, 1000);
		Summary.setLineWrap(true);
		Summary.setWrapStyleWord(true);
		Summary.setFont(font);
		Summary.setLocation(0, 0);

		JScrollPane summaryScroll = new JScrollPane(Summary, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		summaryScroll.setSize(1535, 1080);
		summaryScroll.setLocation(0, 0);

		details.setLayout(null);
		details.add(summaryScroll);
		tab.add("Summary", details);
		sum.display_details(server.first(), wrapper.last(), Summary);

		int i = 0;
		for (String j : server) {

			JMenuBar umenubar, smenubar;
			JButton ubutton, sbutton;
			JTextField[] uinp, sinp;
			JTabbedPane subtab3 = new JTabbedPane();
			Reader2 read = new Reader2();
			JPanel uniquePanel = new JPanel();
			JTextArea uniqueText = new JTextArea();
			DefaultTableModel uniquemodel = new DefaultTableModel(uheader, 0);
			JTable uniquetable = new JTable(uniquemodel) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				};
			};
			JScrollPane utablescroll = new JScrollPane(uniquetable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			JScrollPane uniquescroll = new JScrollPane(uniquePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			umenubar = new JMenuBar();
			smenubar = new JMenuBar();
			uinp = new JTextField[4];
			sinp = new JTextField[5];
			
			for (int z = 0; z < 4; z++) {
				uinp[z] = new JTextField();
			}
			
			umenubar.setLayout(null);
			uinp[0].setBounds(10, 5, 90, 30);
			uinp[1].setBounds(125, 5, 50, 30);
			uinp[2].setBounds(190, 5, 170, 30);
			uinp[3].setBounds(385, 5, 590, 30);
			ubutton = new JButton("Clear");
			ubutton.setPreferredSize(new Dimension(75, 30));
			ubutton.setMaximumSize(new Dimension(75, 30));	
			ubutton.setBounds(990, 5, 75, 30);
			for (int z = 0; z < 4; z++) {
			umenubar.add(uinp[z]);
			}
			umenubar.add(ubutton);
			
			utablescroll.setPreferredSize(new Dimension(1500, 500));
			utablescroll.setMaximumSize(new Dimension(1500, 500));
			utablescroll.setBackground(Color.white);
			utablescroll.setAlignmentX(Component.LEFT_ALIGNMENT);

			umenubar.setAlignmentX(Component.LEFT_ALIGNMENT);
			umenubar.setPreferredSize(new Dimension(1510, 40));
			umenubar.setMaximumSize(new Dimension(1510, 40));

			TableRowSorter<TableModel> urowSorter = new TableRowSorter<>(uniquetable.getModel());
			uniquetable.setRowSorter(urowSorter);
			
			ubutton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int z=0;z<4;z++)
						uinp[z].setText("");
				}
			});
			for (JTextField field : uinp) {
				field.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(4);
						for (int z = 0; z < 4; z++) {
							String text = uinp[z].getText();
							if (text.trim().length() == 0)
								filters.add(RowFilter.regexFilter(".+", z + 1));
							else
								filters.add(RowFilter.regexFilter("(?i)" + text, z + 1));
							RowFilter<Object, Object> serviceFilter = RowFilter.andFilter(filters);
							urowSorter.setRowFilter(serviceFilter);
						}
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(4);
						for (int z = 0; z < 4; z++) {
							String text = uinp[z].getText();
							if (text.trim().length() == 0)
								filters.add(RowFilter.regexFilter(".+", z + 1));
							else
								filters.add(RowFilter.regexFilter("(?i)" + text, z + 1));
							RowFilter<Object, Object> serviceFilter = RowFilter.andFilter(filters);
							urowSorter.setRowFilter(serviceFilter);
						}
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						throw new UnsupportedOperationException("Not supported yet."); // To change body of generated
																						// methods,
																						// choose Tools | Templates.
					}

				});
			}
			uniqueText.setLineWrap(true);
			uniqueText.setEditable(false);
			uniqueText.setWrapStyleWord(true);
			uniqueText.setFont(font);
			uniqueText.setAlignmentX(Component.LEFT_ALIGNMENT);
			uniquetable.putClientProperty("value", i);
			uniquetable.setRowHeight(30);
			uniquetable.getColumnModel().getColumn(0).setPreferredWidth(0);
			uniquetable.getColumnModel().getColumn(0).setMaxWidth(0);
			uniquetable.getColumnModel().getColumn(1).setPreferredWidth(120);
			uniquetable.getColumnModel().getColumn(1).setMaxWidth(120);
			uniquetable.getColumnModel().getColumn(2).setPreferredWidth(70);
			uniquetable.getColumnModel().getColumn(2).setMaxWidth(70);
			uniquetable.getColumnModel().getColumn(3).setPreferredWidth(200);
			uniquetable.getColumnModel().getColumn(3).setMaxWidth(200);
			uniquetable.getColumnModel().getColumn(4).setPreferredWidth(1165);
			uniquetable.getColumnModel().getColumn(4).setMaxWidth(1165);

			uniquetable.setFont(font);
			uniquetable.setBackground(Color.white);
			uniquetable.addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent ev) {
					read.getTraces(uniquetable, uniqueText, uniquescroll);
				}

				@Override
				public void keyTyped(KeyEvent ev) {
					// TODO Auto-generated method stub
				}

				@Override
				public void keyReleased(KeyEvent ev) {
					// TODO Auto-generated method stub
					read.getTraces(uniquetable, uniqueText, uniquescroll);

				}
			});
			uniquetable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					if (me.getClickCount() == 1)
						read.getTraces(uniquetable, uniqueText, uniquescroll);
				}
			});

			uniquePanel.setLayout(new BoxLayout(uniquePanel, BoxLayout.Y_AXIS));
			uniquePanel.setBackground(Color.white);
			uniquePanel.add(umenubar);
			uniquePanel.add(utablescroll);
			uniquePanel.add(uniqueText);

			JPanel samePanel = new JPanel();
			JTextArea sameText = new JTextArea();
			DefaultTableModel samemodel = new DefaultTableModel(sheader, 0);
			JTable sametable = new JTable(samemodel) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				};
			};
			JScrollPane stablescroll = new JScrollPane(sametable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			JScrollPane samescroll = new JScrollPane(samePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			smenubar.setAlignmentX(Component.LEFT_ALIGNMENT);
			smenubar.setPreferredSize(new Dimension(1500, 40));
			smenubar.setMaximumSize(new Dimension(1500, 40));

			stablescroll.setPreferredSize(new Dimension(1500, 500));
			stablescroll.setMaximumSize(new Dimension(1500, 500));
			stablescroll.setBackground(Color.white);
			stablescroll.setAlignmentX(Component.LEFT_ALIGNMENT);

			TableRowSorter<TableModel> srowSorter = new TableRowSorter<>(sametable.getModel());
			sametable.setRowSorter(srowSorter);
			
			for (int z = 0; z < 5; z++) {
				sinp[z] = new JTextField();
			}
			
			smenubar.setLayout(null);
			sinp[0].setBounds(10, 5, 90, 30);
			sinp[1].setBounds(130, 5, 50, 30);
			sinp[2].setBounds(200, 5, 50, 30);
			sinp[3].setBounds(275, 5, 170, 30);
			sinp[4].setBounds(475, 5, 550, 30);
			sbutton = new JButton("Clear");
			sbutton.setPreferredSize(new Dimension(75, 30));
			sbutton.setMaximumSize(new Dimension(75, 30));	
			sbutton.setBounds(1030, 5, 75, 30);
			for (int z = 0; z < 5; z++) {
			smenubar.add(sinp[z]);
			}
			sbutton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int z=0;z<4;z++)
						sinp[z].setText("");
				}
			});
			smenubar.add(sbutton);
			
			for (JTextField field : sinp) {
				field.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(4);
						for (int z = 0; z < 5; z++) {
							String text = sinp[z].getText();
						
							if (text.trim().length() == 0)
								filters.add(RowFilter.regexFilter(".+", z + 1));
							else
								filters.add(RowFilter.regexFilter("(?i)" + text, z + 1));
							RowFilter<Object, Object> serviceFilter = RowFilter.andFilter(filters);
							srowSorter.setRowFilter(serviceFilter);
						}
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(4);
						for (int z = 0; z < 5; z++) {
							String text = sinp[z].getText();
				
							if (text.trim().length() == 0)
								filters.add(RowFilter.regexFilter(".+", z + 1));
							else
								filters.add(RowFilter.regexFilter("(?i)" + text, z + 1));
							RowFilter<Object, Object> serviceFilter = RowFilter.andFilter(filters);
							srowSorter.setRowFilter(serviceFilter);
						}
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						throw new UnsupportedOperationException("Not supported yet.");
					}
				});
			}

			sameText.setLineWrap(true);
			sameText.setEditable(false);
			sameText.setWrapStyleWord(true);
			sameText.setFont(font);
			sameText.setAlignmentX(Component.LEFT_ALIGNMENT);
			sametable.putClientProperty("value", i);
			sametable.setRowHeight(30);
			sametable.getColumnModel().getColumn(0).setPreferredWidth(0);
			sametable.getColumnModel().getColumn(0).setMaxWidth(0);
			sametable.getColumnModel().getColumn(1).setPreferredWidth(120);
			sametable.getColumnModel().getColumn(1).setMaxWidth(120);
			sametable.getColumnModel().getColumn(2).setPreferredWidth(70);
			sametable.getColumnModel().getColumn(2).setMaxWidth(70);
			sametable.getColumnModel().getColumn(3).setPreferredWidth(70);
			sametable.getColumnModel().getColumn(3).setMaxWidth(70);
			sametable.getColumnModel().getColumn(4).setPreferredWidth(200);
			sametable.getColumnModel().getColumn(4).setMaxWidth(200);
			sametable.getColumnModel().getColumn(4).setPreferredWidth(1120);
			sametable.getColumnModel().getColumn(4).setMaxWidth(1120);

			sametable.setFont(font);
			sametable.setBackground(Color.white);
			sametable.addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent ev) {
					read.getTraces_same(sametable, sameText, samescroll);
				}

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
				}

				@Override
				public void keyReleased(KeyEvent ev) {
					// TODO Auto-generated method stub
					read.getTraces_same(sametable, sameText, samescroll);

				}
			});
			sametable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					if (me.getClickCount() == 1) { // to detect doble click events
						read.getTraces_same(sametable, sameText, samescroll);
					}
				}
			});

			samePanel.setLayout(new BoxLayout(samePanel, BoxLayout.Y_AXIS));
			samePanel.setBackground(Color.white);
			samePanel.add(smenubar);
			samePanel.add(stablescroll);
			samePanel.add(sameText);

			read.display(j, uniquemodel, samemodel);
			uniquetable.removeColumn(uniquetable.getColumn("Id"));
			uniquetable.setRowSelectionInterval(0, 0);
			sametable.setRowSelectionInterval(0, 0);
			sametable.removeColumn(sametable.getColumn("Id"));
			
			uniqueText.setEditable(false);
			uniqueText.append("\n\nTraces: \n");
			sameText.setEditable(false);
			sameText.append("\n\nTraces: \n");

			subtab3.add("unique", uniquescroll);
			subtab3.add("same", samescroll);
			subtab1.add(j.substring(j.indexOf("serverOut_") + 10, j.indexOf(".txt")), subtab3);
			i++;
		}

		tab.add("Exceptions", subtab1);

		i = 0;
		for (String j : wrapper) {
			Reader2 read = new Reader2();
			JPanel wrapperPanel = new JPanel();
			DefaultTableModel wrappermodel = new DefaultTableModel(wheader, 0);
			JTable wrappertable = new JTable(wrappermodel) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				};
			};
			JScrollPane wtablescroll, wrapperscroll;
			JMenuBar wmenubar = new JMenuBar();
			JTextField winp = new JTextField();
			JButton wbutton;
			wrappertable.setBounds(300, 150, 900, 900);
			wrappertable.setRowHeight(30);
			wrappertable.setFont(font);
			wrappertable.setBackground(Color.white);
			wtablescroll = new JScrollPane(wrappertable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			wtablescroll.setSize(1000, 500);
			wtablescroll.setLocation(250, 150);
			wtablescroll.setBackground(Color.white);
			wrapperscroll = new JScrollPane(wrapperPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			wrapperscroll.setSize(1525, 1080);
			wrapperscroll.setLocation(0, 0);
			wrapperPanel.setLayout(new BoxLayout(wrapperPanel,BoxLayout.Y_AXIS));
			wrapperPanel.setBackground(Color.white);
			wrapperPanel.add(wmenubar);
			wrapperPanel.add(wtablescroll);
			
			TableRowSorter<TableModel> wrowSorter = new TableRowSorter<>(wrappertable.getModel());
			wrappertable.setRowSorter(wrowSorter);
			
			wmenubar.setPreferredSize(new Dimension(1520,40));
			wmenubar.setMaximumSize(new Dimension(1520,40));
			wmenubar.setAlignmentX(Component.LEFT_ALIGNMENT);
			wmenubar.setLayout(null);
			winp.setBounds(10, 5, 250, 30);
			wbutton = new JButton("Clear");
			wbutton.setPreferredSize(new Dimension(75, 30));
			wbutton.setMaximumSize(new Dimension(75, 30));	
			wbutton.setBounds(300, 5, 75, 30);
			wmenubar.add(winp);
			wmenubar.add(wbutton);
			wtablescroll.setAlignmentX(Component.LEFT_ALIGNMENT);
			
				winp.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
							String text = winp.getText();
							if (text.trim().length() == 0)
								wrowSorter.setRowFilter(null);
							else
								wrowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));	
						}

					@Override
					public void removeUpdate(DocumentEvent e) {
						String text = winp.getText();
						
						if (text.trim().length() == 0)
							wrowSorter.setRowFilter(null);
						else
							wrowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						throw new UnsupportedOperationException("Not supported yet.");
					}
				});
				wbutton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						winp.setText("");
					}
				});
			subtab2.add(j.substring(j.lastIndexOf('\\') + 1, j.indexOf(".log")), wrapperscroll);
			read.display_errors(j, wrappermodel);
			i++;
		}

		tab.add("Errors", subtab2);
		window.setSize(1920, 1080);
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.add(tab);
		window.setBackground(Color.white);

	}
}
