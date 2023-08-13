package main;

import java.util.ArrayList;

public class excep {
	public String exception, onlyException,time,id;
	public ArrayList<String> traces;

	public excep() {
		exception = "";
		onlyException = "";
		traces = new ArrayList<String>();
		time = "";
		id = "";
	}

	public excep(String x, ArrayList<String> y, String z,String time,String id) {
		exception = x;
		traces = y;
		onlyException = z;
		this.time = time;
		this.id = id;
	}
}
