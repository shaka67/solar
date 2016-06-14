/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class Date2LongTypeHandler implements TypeHandler<Long> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Long parameter,
			JdbcType jdbcType) throws SQLException {
		ps.setObject(i, parameter);
	}

	@Override
	public Long getResult(ResultSet rs, String columnName) throws SQLException {
		final Date date = rs.getTimestamp(columnName);
		if (date == null) {
            return 0L;
        }
		return date.getTime();
	}

	@Override
	public Long getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		// 存储过程还是算了
		return null;
	}

	@Override
	public Long getResult(ResultSet rs, int columnIndex) throws SQLException {
		final Date date = rs.getTimestamp(columnIndex);
		if (date == null) {
            return 0L;
        }
		return date.getTime();
	}

}
