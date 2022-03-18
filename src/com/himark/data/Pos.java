package com.himark.data;

public class Pos {
	private String posId; // pos_id
	private String posName; // pos_name
	
	public Pos() {
		
	}

	public Pos(String posId, String posName) {
		super();
		this.posId = posId;
		this.posName = posName;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	@Override
	public String toString() {
		return "Pos [posId=" + posId + ", posName=" + posName + "]";
	}
	
}
