/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.access;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月7日 上午2:05:07
 */
public class StrategyDecorator implements AccessStrategy {

    private AccessStrategy strategy;

    public StrategyDecorator(AccessStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String find(long id) {
        return strategy.find(id);
    }

}
