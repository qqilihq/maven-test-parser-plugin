package de.philippkatz.maven.plugins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

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

}
