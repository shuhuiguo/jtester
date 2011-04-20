package org.jtester.module.database.environment.normalise;

import java.sql.SQLException;

public interface TypeNormaliser {
	Object normalise(Object o) throws SQLException;
}
