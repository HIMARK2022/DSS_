package com.himark.data;

public class Manager {
	private String managerId; // manager_id
	private String approvalTarget; // approval_target
	private String classifyTarget; // classify_target
	private String approvalStart; // approval_start
	private String approvalFinish; // approval_finish
	
	public Manager() {
		
	}

	public Manager(String managerId, String approvalTarget, String classifyTarget, String approvalStart, String approvalFinish) {
		super();
		this.managerId = managerId;
		this.approvalTarget = approvalTarget;
		this.classifyTarget = classifyTarget;
		this.approvalStart = approvalStart;
		this.approvalFinish = approvalFinish;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getApprovalTarget() {
		return approvalTarget;
	}

	public void setApprovalTarget(String approvalTarget) {
		this.approvalTarget = approvalTarget;
	}

	public String getClassifyTarget() {
		return classifyTarget;
	}

	public void setClassifyTarget(String classifyTarget) {
		this.classifyTarget = classifyTarget;
	}

	public String getApprovalStart() {
		return approvalStart;
	}

	public void setApprovalStart(String approvalStart) {
		this.approvalStart = approvalStart;
	}

	public String getApprovalFinish() {
		return approvalFinish;
	}

	public void setApprovalFinish(String approvalFinish) {
		this.approvalFinish = approvalFinish;
	}

	@Override
	public String toString() {
		return "Manager [managerId=" + managerId + ", approvalTarget=" + approvalTarget + ", classifyTarget="
				+ classifyTarget + ", approvalStart=" + approvalStart + ", approvalFinish=" + approvalFinish + "]";
	}
	
}
