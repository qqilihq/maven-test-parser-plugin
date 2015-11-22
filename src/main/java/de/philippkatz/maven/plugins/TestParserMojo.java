package de.philippkatz.maven.plugins;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.surefire.report.ReportTestCase;
import org.apache.maven.plugins.surefire.report.ReportTestSuite;
import org.apache.maven.plugins.surefire.report.SurefireReportParser;
import org.apache.maven.reporting.MavenReportException;

/**
 * Goal to parse JUnit-style XML results.
 * 
 * @author Philipp Katz
 */
@Mojo(name = "testparser")
public class TestParserMojo extends AbstractMojo {

	/** Directory which contains the reports. */
	@Parameter(property = "testparser.resultsDirectory", required = true)
	File resultsDirectory;

	@Override
	public void execute() throws MojoExecutionException {
		if (!resultsDirectory.isDirectory()) {
			throw new MojoExecutionException(resultsDirectory + " is not a directory");
		}
		getLog().debug("Checking test results in " + resultsDirectory);
		List<File> reportDirectories = Collections.singletonList(resultsDirectory);
		SurefireReportParser parser = new SurefireReportParser(reportDirectories, Locale.getDefault());
		List<ReportTestSuite> parsedReports;
		try {
			parsedReports = parser.parseXMLReportFiles();
		} catch (MavenReportException e) {
			throw new MojoExecutionException("Could not parse XML reports", e);
		}
		if (parsedReports.isEmpty()) {
			getLog().warn("Did not find any XML reports in " + resultsDirectory);
		}
		List<String> failures = new ArrayList<>();
		List<String> errors = new ArrayList<>();
		for (ReportTestSuite testSuite : parsedReports) {
			if (testSuite.getNumberOfErrors() > 0) {
				errors.add(testSuite.getFullClassName());
			}
			for (ReportTestCase testCase : testSuite.getTestCases()) {
				if (testCase.hasFailure()) {
					failures.add(testCase.getFullName());
				}
			}
		}
		if (!errors.isEmpty()) {
			getLog().info("Errors:");
			logLines(errors);
		}
		if (!failures.isEmpty()) {
			getLog().info("Failures:");
			logLines(failures);
		}
		Map<String, String> summary = parser.getSummary(parsedReports);
		getLog().info(String.format("Tests run: %s, Failures: %s, Errors: %s, Skipped: %s",
				summary.get("totalTests"), 
				summary.get("totalFailures"), 
				summary.get("totalErrors"),
				summary.get("totalSkipped")));
		if (!errors.isEmpty() && !failures.isEmpty()) {
			throw new MojoExecutionException("There were test errors and failures.");
		}
		if (!errors.isEmpty()) {
			throw new MojoExecutionException("There were test errors.");
		}
		if (!failures.isEmpty()) {
			throw new MojoExecutionException("There were test failures.");
		}
	}

	private void logLines(List<String> lines) {
		for (String line : lines) {
			getLog().info(line);
		}
		getLog().info("");
	}

}
