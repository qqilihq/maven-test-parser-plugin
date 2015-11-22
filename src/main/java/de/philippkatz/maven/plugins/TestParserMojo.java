package de.philippkatz.maven.plugins;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.surefire.report.ReportTestSuite;
import org.apache.maven.plugins.surefire.report.SurefireReportParser;
import org.apache.maven.reporting.MavenReportException;

/**
 * Goal to parse JUnit-style XML results.
 */
@Mojo(name = "testparser")
public class TestParserMojo extends AbstractMojo {

	/** Directory which contains the reports. */
	@Parameter(property = "testparser.resultsDirectory", required = true)
	private File resultsDirectory;

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
		for (ReportTestSuite report : parsedReports) {
			if (report.getNumberOfErrors() > 0 || report.getNumberOfFailures() > 0) {
				throw new MojoExecutionException(report.getName() + " had errors or failures");
			}
		}
	}
}
