package com.yinlian.wssc.web.po;

import java.util.List;

public class City {
    private Integer id;

    private String code;

    private String name;

    private String provincecode;
    
    private List<Area> list; // 城市下面的所有的区 集合

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProvincecode() {
        return provincecode;
    }

    public void setProvincecode(String provincecode) {
        this.provincecode = provincecode == null ? null : provincecode.trim();
    }

	public List<Area> getList() {
		return list;
	}

	public void setList(List<Area> list) {
		this.list = list;
	}
    
}