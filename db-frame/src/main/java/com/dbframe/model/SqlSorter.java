package com.dbframe.model;

import java.util.ArrayList;
import java.util.List;

import com.dbframe.enums.SortType;

/**
 * 排序属性
 * 
 * 可以是单个属性排序，也可以是多个属性排序
 * 
 * @author allan
 *
 */
public class SqlSorter {


	private List<SortProperty> children = new ArrayList<SortProperty>();
	
	private SqlSorter() {}



	public static SqlSorter init(String propertyName, SortType sortType) {
		SqlSorter sortProperties = new SqlSorter();
		return sortProperties.add(propertyName, sortType);
	}
	
	public static SqlSorter init(){
		return new SqlSorter();
	}

	public SqlSorter add(String propertyName) {
        this.children.add(new SortProperty(propertyName, SortType.ASC));
        return this;
    }
	
	public SqlSorter add(String propertyName, SortType sortType) {
		this.children.add(new SortProperty(propertyName, sortType));
		return this;
	}
	
	public SqlSorter addAll(SqlSorter sortProperties){
		for(SortProperty sp : sortProperties.getList())
			this.children.add(sp);
		
		return this;
	}
	
	public List<SortProperty> getList(){
		return this.children;
	}


	

}
