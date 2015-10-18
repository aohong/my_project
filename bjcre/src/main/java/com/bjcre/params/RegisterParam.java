/**
 * 
 */
package com.bjcre.params;

/**
 * @author 彩色照片
 *
 */
public class RegisterParam {
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "RegisterParam [userName=" + username + ", password=" + password + "]";
	}
	
	
	
	
	
	
}
