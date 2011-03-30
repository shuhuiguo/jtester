package fitlibrary.batch;

import java.io.IOException;

import fitlibrary.batch.FitLibraryRunner;

public class RunFitLibraryRunner {

	public static void main(String[] args) throws IOException, InterruptedException {
		String[] arguments = { 
				"-suiteName", "FitLibrary.SpecifiCations.GlobalActionsProvided",
				"-fitNesseDiry", "fitnesse",
				"-resultsDiry", "runnerResults",
				"-showPasses", "false"};
		FitLibraryRunner.main(arguments);
	}
}
