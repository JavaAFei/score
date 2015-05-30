package com.ghy.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;;


@Entity
@Table(name="t_show_option")
public class ShowOption {
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@Column(length=32)
	private String id;
	@Column(length=128)
	private String columnName;
	@Column(length=1)
	private String isShow;
	@Column(length=3)
	private String sort;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	} 
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
}
