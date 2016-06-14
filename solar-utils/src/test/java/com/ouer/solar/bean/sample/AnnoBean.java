/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean.sample;

import com.ouer.solar.bean.CopyField;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年10月8日 上午11:40:59
 */
public class AnnoBean extends Overload {

    @CopyField
    private int test1;
    @CopyField
    private float test2;

    private String test3;

    @CopyField
    private String test4;

    public int getTest1() {
        return test1;
    }

    public void setTest1(int test1) {
        this.test1 = test1;
    }

    public float getTest2() {
        return test2;
    }

    public void setTest2(float test2) {
        this.test2 = test2;
    }

    public String getTest3() {
        return test3;
    }

    public void setTest3(String test3) {
        this.test3 = test3;
    }

    public String getTest4() {
        return test4;
    }

    public void setTest4(String test4) {
        this.test4 = test4;
    }

}
