package org.jtester.module.dbfit.db.enviroment;

import java.sql.SQLException;

public interface TypeNormaliser {
	Object normalise(Object o) throws SQLException;
}
