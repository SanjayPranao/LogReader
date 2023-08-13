package main;

import java.awt.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Reader2 {
	ArrayList<Integer> unique;
	ArrayList<Integer> allset;
	ArrayList<excep> list2;
	ArrayList<ArrayList<Integer>> sets;

	public Reader2() {
		list2 = new ArrayList<excep>();
		unique = new ArrayList<Integer>();
		allset = new ArrayList<Integer>();
		sets = new ArrayList<ArrayList<Integer>>();
	}

	public void display(String server, DefaultTableModel model, DefaultTableModel model1) throws IOException {
		String filePath = server;
		File file = new File(filePath);
		BufferedReader fileReader = new BufferedReader(new FileReader(file));// using BufferedReader instead of Scanner
		String line = "";
		boolean check = true;
		Pattern pattern = Pattern.compile("[a-zA-Z]+\\.[a-zA-Z\\.]+\\.[a-zA-Z]+Exception");// using regular expression
		Pattern time = Pattern.compile("[0-9]+\\:[0-9]+\\:[0-9]+\\:[0-9]+");
		Pattern threadid = Pattern.compile("\\[[0-9]+\\]");
		excep c = new excep();

		while (check) {
			line = fileReader.readLine();
			if (line == null)
				check = false;
			else {
				// to find the exceptions in the log file
				Matcher matcher = pattern.matcher(line);
				Matcher match_time = time.matcher(line);
				Matcher match_id = threadid.matcher(line);
				if (matcher.find() && !line.contains("ExceptionSorter") && !line.contains("\s\tat")
						&& !line.contains("\tat")) {
					if (c.exception == null && list2.size() == 0)
						;
					else {
						list2.add(
								new excep(c.exception, new ArrayList<String>(c.traces), c.onlyException, c.time, c.id));
						c.exception = null;
						c.time = null;
						c.traces.clear();
						c.id = null;
					}
					c.exception = line.replaceAll("\\[[^()]*\\]\\:\s", "").replaceFirst("\\|", "");
					c.onlyException = matcher.group(0);
					if (match_time.find())
						c.time = match_time.group(0);
					if (match_id.find())
						c.id = match_id.group().substring(1, match_id.group().length() - 1);
				}
				if (line.contains("\s\tat") || line.contains("\tat"))// For finding the stack lines after the exception
				{
					String trace = line.replaceAll("\\[[^()]*\\]\\:", "").replace("\s\t", "").replace("\t", "");
					c.traces.add(trace);
				}
			}
		}
		fileReader.close();
		list2.add(c);

		for (excep d : list2) {
			int occ = 0;
			for (excep e : list2) {
				if (!e.equals(d) && e.traces.size() != 0 && (d.traces.size() == e.traces.size())) {
					if (d.exception.equals(e.exception)) {
						boolean check2 = true;
						for (int k = 0; k < e.traces.size(); k++) {
							if (!d.traces.get(k).equals(e.traces.get(k))) {
								check2 = false;
								break;
							}
						}
						if (check2 == true)
							occ++;
					}
				}
			}
			if (occ == 0)
				unique.add(list2.indexOf(d));
			else {
				ArrayList<Integer> temp = new ArrayList<Integer>();
				for (excep e : list2) {
					if (e.traces.size() != 0 && (d.traces.size() == e.traces.size())
							&& !allset.contains(list2.indexOf(e))) {
						if (d.exception.equals(e.exception)) {
							boolean check2 = true;
							for (int k = 0; k < e.traces.size(); k++) {
								if (!d.traces.get(k).equals(e.traces.get(k))) {
									check2 = false;
									break;
								}
							}
							if (check2 == true) {
								temp.add(list2.indexOf(e));
								allset.add(list2.indexOf(e));
							}
						}
					}
				}
				if (temp.size() != 0)
					sets.add(temp);
			}
		}
		for (int a = unique.size() - 1; a > -1; a--) {
			if (list2.get(unique.get(a)).time==null) {
				continue;
			} else if (list2.get(unique.get(a)).traces.size() == 0) {
				model.addRow(new Object[] { (a), list2.get(unique.get(a)).time, list2.get(unique.get(a)).id,
						list2.get(unique.get(a)).onlyException, list2.get(unique.get(a)).exception });
			} else {
				model.addRow(new Object[] { (a), list2.get(unique.get(a)).time, list2.get(unique.get(a)).id,
						list2.get(unique.get(a)).onlyException, list2.get(unique.get(a)).traces.get(0) });
			}

		}
		for (int a = sets.size() - 1; a > -1; a--) {
			if (list2.get(sets.get(a).get(0)).traces.size() == 0) {
				model1.addRow(new Object[] { (a), list2.get(sets.get(a).get(sets.get(a).size() - 1)).time,
						list2.get(sets.get(a).get(0)).id, sets.get(a).size(),
						list2.get(sets.get(a).get(0)).onlyException, list2.get(sets.get(a).get(0)).exception });
			} else {
				model1.addRow(new Object[] { (a), list2.get(sets.get(a).get(sets.get(a).size() - 1)).time,
						list2.get(sets.get(a).get(0)).id, sets.get(a).size(),
						list2.get(sets.get(a).get(0)).onlyException, list2.get(sets.get(a).get(0)).traces.get(0) });
			}
		}
	}

	public void display_details(String server, String wrapper, JTextArea textArea) throws IOException {
		String filePath = server;
		File file = new File(filePath);
		BufferedReader fileReader = new BufferedReader(new FileReader(file));// using BufferedReader instead of Scanner
		String line = "";
		boolean check = true;
		Set<String> users = new HashSet<String>();
		SortedSet<String> details = new TreeSet<String>();
		while (check) {
			line = fileReader.readLine();
			if (line == null)
				check = false;
			else {

				// For finding info about he Database
				if (line.contains("Build Number"))
					details.add(line.substring(line.indexOf("Build"), line.length() - 1) + "\n");
				else if (line.contains("Build Processor Architecture"))
					details.add(line.substring(line.indexOf("Build"), line.length() - 1) + " bit\n");
				else if (line.contains("database ::"))
					details.add("DB Name:" + line.substring(line.indexOf("database") + 11, line.length() - 1) + "\n");
				else if (line.contains("DB Version"))
					details.add("DB Version: " + line.replaceAll("\\[[^()]*\\]\\:\s", "").substring(25, 30) + "\n");
				else if (line.contains("DB Architecture"))
					details.add("DB Architecture: " + line.replaceAll("\\[[^()]*\\]\\:\s", "").substring(25, 27)
							+ " bit\n");
				// finding number of users who logged in
				if (line.contains("\tLoginId")) {
					users.add(line.substring(line.indexOf("LoginId") + 14, line.length()));
				}
			}
		}
		fileReader.close();
		int a = 0;
		for (String data : details)
			textArea.append((a++ + 1) + ") " + data);

		filePath = wrapper;// reading wrapper log to find start time
		file = new File(filePath);
		fileReader = new BufferedReader(new FileReader(file));
		line = "";
		Pattern pattern = Pattern.compile("[0-9]+\\/[0-9]+\\/[0-9]+\s[0-9]+:[0-9]+:[0-9]+");
		ArrayList<String> time = new ArrayList<String>();
		check = true;
		while (check) {
			line = fileReader.readLine();
			if (line == null)
				check = false;
			else if (line.contains("Server started in")) {
				Matcher matcher = pattern.matcher(line);
				matcher.find();
				time.add(matcher.group(0));
			}
		}

		textArea.append((a++ + 1) + ") Last Product Start Date and Time: " + time.get(time.size() - 1));
		textArea.append("\n" + (a++ + 1) + ") Total number of users: " + users.size());
		textArea.setEditable(false);
	}

	public void display_errors(String wrapper, DefaultTableModel model) throws IOException {
		String filePath = wrapper;
		File file = new File(filePath);
		BufferedReader fileReader = new BufferedReader(new FileReader(file));// using BufferedReader instead of Scanner
		String line = "";
		Pattern time = Pattern.compile("[0-9]+/[0-9]+/[0-9]+\s[0-9]+\\:[0-9]+\\:[0-9]+");
		Matcher matchTime;
		boolean check = true;
		ArrayList<error> errors = new ArrayList<error>();
		do {
			line = fileReader.readLine();
			if (line != null) {
				matchTime = time.matcher(line);
				if (line.contains("ERROR\s\s")) {
					matchTime.find();
					errors.add(new error(line.substring(line.lastIndexOf('|') + 2), matchTime.group()));
				}
			} else
				check = false;
		} while (check);
		for (error e : errors) {
			model.addRow(new Object[] { e.time, e.error });
		}
		fileReader.close();
	}

	public void getTraces(JTable table, JTextArea textArea, JScrollPane scroll) {
		int row = table.getSelectedRow();
		int a = (int) table.getModel().getValueAt(table.convertRowIndexToModel(row), 0);
		textArea.setText(null);
		textArea.append("\nTraces:\n\n");
		if (list2.get(unique.get(a)).traces.size() == 0) {
			textArea.append(list2.get(unique.get(a)).exception + "\n");

		} else {

			for (String s : list2.get(unique.get(a)).traces) {
				textArea.append(s + "\n");
			}
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				scroll.getViewport().setViewPosition(new Point(0, 0));
			}
		});
	}

	public void getTraces_same(JTable table, JTextArea textArea, JScrollPane scroll) {
		int row = table.getSelectedRow();
		int a = (int) table.getModel().getValueAt(table.convertRowIndexToModel(row), 0);
		textArea.setText(null);
		textArea.append("\nTraces:\n\n");
		if (list2.get(sets.get(a).get(0)).traces.size() == 0) {
			textArea.append(list2.get(sets.get(a).get(0)).exception + "\n");
		} else {
			for (String s : list2.get(sets.get(a).get(0)).traces) {
				textArea.append(s + "\n");
			}
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				scroll.getViewport().setViewPosition(new Point(0, 0));
			}
		});
	}
}
