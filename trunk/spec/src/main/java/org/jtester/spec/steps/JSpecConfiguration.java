package org.jtester.spec.steps;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.failures.RethrowingFailure;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CasePreservingResolver;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.FreemarkerViewGenerator;
import org.jbehave.core.reporters.PrintStreamStepdocReporter;
import org.jbehave.core.steps.MarkUnmatchedStepsAsPending;
import org.jbehave.core.steps.SilentStepMonitor;

import com.thoughtworks.paranamer.AnnotationParanamer;

public class JSpecConfiguration extends Configuration {

	public JSpecConfiguration() {
		useKeywords(new LocalizedKeywords());
		useStoryControls(new StoryControls());
		useStoryLoader(new LoadFromClasspath());
		useStoryParser(new RegexStoryParser(keywords()));
		useFailureStrategy(new RethrowingFailure());
		useStepCollector(new MarkUnmatchedStepsAsPending());
		useStepMonitor(new SilentStepMonitor());
		useStepdocReporter(new PrintStreamStepdocReporter());
		useViewGenerator(new FreemarkerViewGenerator());
		// case preserving
		useParanamer(new AnnotationParanamer());
		useStoryPathResolver(new CasePreservingResolver());
		usePendingStepStrategy(new FailingUponPendingStep());
		// JTester Spec configurationg
		useStepPatternParser(new JSpecStepPatternParser());
		useStepFinder(new JSpecStepFinder());
	}
}
