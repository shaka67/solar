/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sample;

import com.ouer.solar.file.ProcessedField;
import com.ouer.solar.file.ProcessedType;
import com.ouer.solar.lang.StringableSupport;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月1日 下午2:24:55
 */
@ProcessedType
public class CsvBean extends StringableSupport {

    @ProcessedField(index = 0, title = "姓名")
    private String name;

    @ProcessedField(index = 1, title = "年龄")
    private int age;

    @ProcessedField(index = 2, title = "工作")
    private String work;

    @ProcessedField(index = 3, title = "住址")
    private String home;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    @Override
	public int hashCode() {
    	return toString().hashCode();
    }

    @Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // 到此处类型必相同
        final CsvBean other = (CsvBean) obj;
        return this.toString().equals(other.toString());
    }

}
