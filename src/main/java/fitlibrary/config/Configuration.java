package fitlibrary.config;

import fitlibrary.annotation.NullaryAction;
import fitlibrary.annotation.ShowSelectedActions;
import fitlibrary.annotation.SimpleAction;

@ShowSelectedActions
public interface Configuration {
	@NullaryAction(tooltip = "Retain sensible unicode characters when converting an action name to a Java method name. Set through fixturing code.")
	boolean keepingUniCode();

	boolean isAddTimings();

	@SimpleAction(wiki = "|''<i>add timings</i>''|true or false|", tooltip = "Specify whether timing information is to be added to reported tables. False by default.")
	void addTimings(boolean addTimings);
}
