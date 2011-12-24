package org.jtester;

import org.jtester.IAssertion;
import org.jtester.database.JTesterFitnesse;
import org.jtester.database.operator.DBOperator;
import org.jtester.database.operator.IDBOperator;

public interface IDatabase extends IAssertion {

	final JTesterFitnesse fit = new JTesterFitnesse();

	final IDBOperator db = new DBOperator();
}
