package com.dn.dbex.domain;

import java.sql.Blob;

public class Photo {
	private String empno;
	private String photo_format;
	private Blob picture;
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	public String getPhoto_format() {
		return photo_format;
	}
	public void setPhoto_format(String photo_format) {
		this.photo_format = photo_format;
	}
	public Blob getPicture() {
		return picture;
	}
	public void setPicture(Blob picture) {
		this.picture = picture;
	}
}
