package de.philippkatz.maven.plugins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Test;

public class TestParserMojoTest {

	@Test
	public void testTestParserMojoWithFailures() {
		TestParserMojo mojo = new TestParserMojo();
		mojo.resultsDirectory = new File(getClass().getResource("/sampleResults-withFailures").getFile());
		MockLog mockLog = new MockLog();
		mojo.setLog(mockLog);
		try {
			mojo.execute();
			fail();
		} catch (MojoExecutionException e) { // should be thrown
			assertEquals("There were test failures.", e.getMessage());
		}
		String logResult = mockLog.getLog();
		assertEquals(7, logResult.split("\n").length);
		assertTrue(logResult.contains("seleniumTestFlow.assertions_on.execute workflow"));
		assertTrue(logResult.contains("seleniumTestFlow.assertions_on.node messages"));
		assertTrue(logResult.contains("seleniumTestFlow.assertions_on.log messages"));
		assertTrue(logResult.contains("Tests run: 9, Failures: 115, Errors: 0, Skipped: 0"));
	}

	@Test
	public void testTestParserMojoWithoutFailures() {
		TestParserMojo mojo = new TestParserMojo();
		mojo.resultsDirectory = new File(getClass().getResource("/sampleResults-fine").getFile());
		MockLog mockLog = new MockLog();
		mojo.setLog(mockLog);
		try {
			mojo.execute();
		} catch (MojoExecutionException e) {
			fail();
		}
		String logResult = mockLog.getLog();
		assertEquals(2, logResult.split("\n").length);
		assertTrue(logResult.contains("Tests run: 9, Failures: 0, Errors: 0, Skipped: 0"));
	}

	@Test
	public void testTestParserMojoWithSkipsShouldNotBeTreatedAsFailures() {
		TestParserMojo mojo = new TestParserMojo();
		mojo.resultsDirectory = new File(getClass().getResource("/sampleResults-skipped").getFile());
		MockLog mockLog = new MockLog();
		mojo.setLog(mockLog);
		try {
			mojo.execute();
		} catch (MojoExecutionException e) {
			fail();
		}
		String logResult = mockLog.getLog();
		assertEquals(2, logResult.split("\n").length);
		assertTrue(logResult.contains("Tests run: 10, Failures: 0, Errors: 0, Skipped: 1"));
	}

	/**
	 * Tests whether recursive collection of result folders works correctly.
	 * 
	 * @throws MojoExecutionException
	 *             Fails the test if anything unexpected goes wrong.
	 */
	@Test
	public void testTestParserMojoWithNestedResultFiles() throws MojoExecutionException {
		File resultsDirectory = new File(getClass().getResource("/sampleResults-nested").getFile());
		List<File> directories = TestParserMojo.collectReportDirectoriesRecursively(resultsDirectory);

		assertEquals(4, directories.size());
	}

}
