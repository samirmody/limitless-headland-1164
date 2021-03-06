package main.java.servlet;

import java.sql.*;

import java.util.Map;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;

class UserData {
	public String status;
	public String user_id;
	public List<Hub> hubs = new ArrayList<Hub>();
	public UserData(String user_id) {
		this.user_id = user_id;
	}
}

class Hub {
	public Integer hub_id;
	public String name;
	public String api_key;
	public Integer pan_id;
	public List<Node> nodes = new ArrayList<Node>();
	public Hub(Integer hub_id, String name, String api_key, Integer pan_id) {
		this.hub_id = hub_id;
		this.name = name;
		this.api_key = api_key;
		this.pan_id = pan_id;
	}
}

class Node {
	public Integer node_id;
	public String name;
	public String address_low;
	public String address_high;
	public String current_value;
	public List<Pin> pins = new ArrayList<Pin> ();
	public Node(Integer node_id, String name, String address_low, String address_high, String current_value) {
		this.node_id = node_id;
		this.name = name;
		this.address_low = address_low;
		this.address_high = address_high;
		this.current_value = current_value;
	}
}

class Pin {
	public Integer pin_id;
	public String name;
	public String type;
	public List<PinData> pin_data = new ArrayList<PinData> ();
	public Pin(Integer pin_id, String name, String type) {
		this.pin_id = pin_id;
		this.name = name;
		this.type = type;
	}
	public List<Tag> tags = new ArrayList<Tag> ();
}

class PinData {
	public Timestamp time;
	public String pin_value;
	public PinData(Timestamp time, String pin_value) {
		this.time = time;
		this.pin_value = pin_value;
	}
}

class Tag {
	public String tag;
	public Tag(String tag) {
		this.tag = tag;
	}
}

class EmbeddedResponse {
	public List<EmbeddedNodes> nodes = new ArrayList<EmbeddedNodes> ();
	public EmbeddedResponse() {}
}

class EmbeddedNodes {
	public String address_low;
	public String address_high;
	public String current_value;
	public String type;
	public EmbeddedNodes(String address_low, String address_high, String current_value, String type) {
		this.address_low = address_low;
		this.address_high = address_high;
		this.current_value = current_value;
		this.type = type;
	}
}