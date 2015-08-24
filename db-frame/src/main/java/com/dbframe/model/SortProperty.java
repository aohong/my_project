package com.dbframe.model;

import com.dbframe.enums.SortType;


public class SortProperty{
	
	private String propertyName;
	private SortType sortType;
	private String columnName;
	
	public SortProperty(String propertyName,SortType sortType){
    	this.propertyName = propertyName;
    	this.columnName = propertyName;
    	this.sortType = sortType;
    }
	
	public String getPropertyName() {
		return propertyName;
	}

	public SortType getSortType() {
		return sortType;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String toString() {
        return "{propertyName:" + propertyName + ", sortType:" + sortType.name() + "}";
    }
}
