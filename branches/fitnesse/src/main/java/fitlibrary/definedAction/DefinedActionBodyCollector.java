package fitlibrary.definedAction;

import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;

public class DefinedActionBodyCollector {
	public void parseDefinitions(Tables tables, DefineActionBodyConsumer consumer) {
		parseDefinitionsWithList(tables, consumer);
	}

	private void parseDefinitionsWithList(Tables tables, DefineActionBodyConsumer consumer) {
		TableFactory.useOnLists(true);
		try {
			Tables defineTables = TableFactory.tables();
			for (Table table : tables) {
				if (isHR(table.getLeader()) && !defineTables.isEmpty()) {
					consumer.addAction(defineTables);
					defineTables = TableFactory.tables();
				}
				if (isHR(table.getTrailer())) {
					defineTables.add(table);
					consumer.addAction(defineTables);
					defineTables = TableFactory.tables();
				} else
					defineTables.add(table);
			}
			if (!defineTables.isEmpty())
				consumer.addAction(defineTables);
		} finally {
			TableFactory.pop();
		}
	}

	private boolean isHR(String s) {
		return s != null && (s.contains("<hr>") || s.contains("<hr/>"));
	}

	public interface DefineActionBodyConsumer {
		void addAction(Tables innerTables);
	}
}
