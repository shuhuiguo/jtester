package org.jtester;

import org.jtester.IAssertion;
import org.jtester.database.JTesterFitnesse;
import org.jtester.module.database.dbop.DBOperator;
import org.jtester.module.database.dbop.IDBOperator;

public interface IDatabase extends IAssertion {

	final JTesterFitnesse fit = new JTesterFitnesse();

	final IDBOperator db = new DBOperator();
}
