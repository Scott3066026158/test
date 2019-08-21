package com.gaia.autotrade.http.entity;


import java.util.List;

import javax.validation.constraints.Size;

public class User {
	
	// 自增主键
	private int id;
	// 创建时间
	private long gmt_create;
	// 最后修改时间
	private long gmt_modified;
	// 交易ID
	private String traderid;
	// 权限组
	private List<String> privilege;
	// 备注
	private String remark;
	// IP组
	private String ipgroup;
	// 访问Key
	private String accesskey;
	// 加密Key
	private String secretkey;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(long gmt_create) {
		this.gmt_create = gmt_create;
	}
	public long getGmt_modified() {
		return gmt_modified;
	}
	public void setGmt_modified(long gmt_modified) {
		this.gmt_modified = gmt_modified;
	}
	public String getTraderid() {
		return traderid;
	}
	public void setTraderid(String traderid) {
		this.traderid = traderid;
	}
	public List<String> getPrivilege() {
		return privilege;
	}
	public void setPrivilege(List<String> privilege) {
		this.privilege = privilege;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIpgroup() {
		return ipgroup;
	}
	public void setIpgroup(String ipgroup) {
		this.ipgroup = ipgroup;
	}
	public String getAccesskey() {
		return accesskey;
	}
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}
	public String getSecretkey() {
		return secretkey;
	}
	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", gmt_create=" + gmt_create + ", gmt_modified=" + gmt_modified 
				+ ", traderid=" + traderid + ", privilege=" + privilege + ", remark=" + remark + ", ipgroup="
				+ ipgroup + ", accesskey=" + accesskey + ", secretkey=" + secretkey + "]";
	}
}
