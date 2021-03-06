package org.jtester.module.dbfit.db.model;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jtester.module.dbfit.db.DatabaseUtils;


import fit.Fixture;

public class DbAutoGeneratedKeyAccessor extends DbParameterAccessor {

	public DbAutoGeneratedKeyAccessor(DbParameterAccessor c) {
		super(c.getName(), DbParameterAccessor.RETURN_VALUE, c.getSqlType(), c.type, c.getPosition());
	}

	private PreparedStatement statement;

	public void bindTo(Fixture f, PreparedStatement cs, int ind) throws SQLException {
		this.statement = cs;
		this.fixture = f;
	}

	public void set(Object value) throws Exception {
		throw new UnsupportedOperationException("Trying to set value of output parameter " + getName());
	}

	public Object get() throws IllegalAccessException, InvocationTargetException {
		ResultSet rs = null;
		try {
			rs = statement.getGeneratedKeys();
			if (rs.next()) {
				return rs.getObject(1);
			} else {
				throw new IllegalAccessException("statement has not generated any keys");
			}
		} catch (SQLException sqle) {
			throw new InvocationTargetException(sqle);
		} finally {
			DatabaseUtils.closeResultSet(rs);
			rs = null;
		}
	}
}
