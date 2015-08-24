package com.dbframe.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbframe.enums.JoinType;
import com.dbframe.enums.MatchType;

public class SqlFilter {

	private List<FilterProperty> filters = new ArrayList<FilterProperty>();
	private List<SqlFilter> filtersGroup = new ArrayList<SqlFilter>();
	private Map<Integer,JoinType> groupJoinType = new HashMap<Integer,JoinType>();
	
	private SqlFilter(){}
	
	
	public SqlFilter and(SqlFilter filter){
		if(filter!=null && !(filter.filters.isEmpty() && filter.filtersGroup.isEmpty())){
			this.filtersGroup.add(filter);
		}
		return this;
	}
	
	public SqlFilter or(SqlFilter filter){
		if(filter!=null && !(filter.filters.isEmpty() && filter.filtersGroup.isEmpty())){
			this.groupJoinType.put(this.filtersGroup.size(), JoinType.OR);
			this.filtersGroup.add(filter);
		}
		return this;
	}
	
	public JoinType getGroupJoinType(int index){
		JoinType type = groupJoinType.get(index);
		if(type==null){
			type=JoinType.AND;
		}
		return type;
	}
	
	public static SqlFilter init(String name,Object value,MatchType matchType){
		SqlFilter instance =  new SqlFilter();
		instance.filters.add(new FilterProperty(name,value,matchType));
		return instance;
	}
	
	public static SqlFilter init(String name,Object value){
		return init(name,value,MatchType.EQ);
	}
	
	public static SqlFilter init(Map<String,Object> map){
		return init(map,true);
	}
	
	public static SqlFilter init(Map<String,Object> map,boolean allowEmpty){
		SqlFilter instance =  new SqlFilter();
		for(Map.Entry<String, Object> entry : map.entrySet()){
			if(allowEmpty){
				instance.and(entry.getKey(), entry.getValue());
			}else{
				instance.addIfNotEmpty(entry.getKey(), entry.getValue());
			}
		}
		return instance;
	}
	
	
	public static SqlFilter init(){
		SqlFilter instance =  new SqlFilter();
		return instance;
	}
	

	public SqlFilter or(String name,Object value){
        this.filters.add(new FilterProperty(name,value,MatchType.EQ,JoinType.OR));
        return this;
    }
	
	public SqlFilter or(String name,Object value,MatchType matchType){
        this.filters.add(new FilterProperty(name,value,matchType,JoinType.OR));
        return this;
    }
	
	public SqlFilter and(String name,Object value){
        this.and(name,value,MatchType.EQ);
        return this;
    }
	
	public SqlFilter and(String name,Object value,MatchType matchType){
		this.filters.add(new FilterProperty(name,value,matchType));
		return this;
	}
	
	
	
	public SqlFilter addIfNotEmpty(String name,Object value){
	    return this.addIfNotEmpty(name,value,MatchType.EQ,JoinType.AND);
	}
	
	public SqlFilter addIfNotEmpty(String name,Object value,MatchType matchType){
	    return this.addIfNotEmpty(name,value,matchType,JoinType.AND);
	}
	
	public SqlFilter addIfNotEmpty(String name,Object value,JoinType joinType){
        return this.addIfNotEmpty(name,value,MatchType.EQ,joinType);
    }
    
    public SqlFilter addIfNotEmpty(String name,Object value,MatchType matchType,JoinType joinType){
        if(value!=null && !"".equals(value)){
            switch(joinType){
                case AND:
                    this.and(name, value, matchType);
                    break;
                case OR:
                    this.or(name, value, matchType);
            }
        }
        return this;
    }
	
	
	public SqlFilter addAll(SqlFilter filter){
		for(FilterProperty fp : filter.getList()){
			this.filters.add(fp);
		}
		int i=0;
		for(SqlFilter filters : filter.getGroupList()){
			JoinType  type = filter.getGroupJoinType(i);
			if(type!=null){
				this.groupJoinType.put(filtersGroup.size(),type);
			}
			this.filtersGroup.add(filters);
			i++;
		}
			
		return this;
	}
	
	
	
	
	
	
	public List<FilterProperty> getList(){
		return this.filters;
	}
	public List<SqlFilter> getGroupList(){
		return this.filtersGroup;
	}

	
	
	

	public Map<String,Object> toMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		Object paramValue = null;
		String paramName;
		
		for(FilterProperty propertyFilter : this.filters){
			paramName = propertyFilter.getPropertyName();
			paramValue = propertyFilter.getPropertyValue();
			map.put(paramName, paramValue);
		}
		
		return map;
	}
}
