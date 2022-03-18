package com.himark.data;

public class User {
	private String userId; // user_id
	private String userName; // user_name
	private String posId; // pos_id
	private String dutyId; // duty_id
	private String deptId; // dept_id
	private String authorityCode; // authority_code
	
	public User() {
		
	}

	public User(String userId, String userName, String posId, String dutyId, String deptId, String authorityCode) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.posId = posId;
		this.dutyId = dutyId;
		this.deptId = deptId;
		this.authorityCode = authorityCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getDutyId() {
		return dutyId;
	}

	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getAuthorityCode() {
		return authorityCode;
	}

	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", posId=" + posId + ", dutyId=" + dutyId
				+ ", deptId=" + deptId + ", authorityCode=" + authorityCode + "]";
	}
	
}
