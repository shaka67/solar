/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean.sample;

import com.ouer.solar.bean.CopyField;
import com.ouer.solar.lang.StringableSupport;

public class Overload extends StringableSupport {

    @CopyField
    String company;

    // not a property setter
    public void setCompany(StringBuilder sb) {
        this.company = sb.toString();
    }

    public String getCompany() {
        return company;
    }
}