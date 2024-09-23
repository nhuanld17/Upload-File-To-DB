package com.example.Upload_File_To_DB.message;

 /* ResponseFile: contains name, url, type, size */
public class ResponeFile {
	
	private String id;
	private String name;
	private String url;
	private String type;
	private long size;
	
	public ResponeFile(String id, String name, String url, String type, long size) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.type = type;
		this.size = size;
	}
	 
	 public String getId() {
		 return id;
	 }
	 
	 public void setId(String id) {
		 this.id = id;
	 }
	 
	 public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
