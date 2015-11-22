package de.philippkatz.maven.plugins;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.maven.plugin.logging.Log;

public class MockLog implements Log {

	private final StringBuilder appender = new StringBuilder();

	public boolean isDebugEnabled() {
		return true;
	}

	public void debug(CharSequence content) {
		append("debug", content);
	}

	public void debug(CharSequence content, Throwable error) {
		append("debug", content, error);
	}

	public void debug(Throwable error) {
		append("debug", error);
	}

	public boolean isInfoEnabled() {
		return true;
	}

	public void info(CharSequence content) {
		append("info", content);
	}

	public void info(CharSequence content, Throwable error) {
		append("info", content, error);
	}

	public void info(Throwable error) {
		append("info", error);
	}

	public boolean isWarnEnabled() {
		return true;
	}

	public void warn(CharSequence content) {
		append("warn", content);
	}

	public void warn(CharSequence content, Throwable error) {
		append("warn", content, error);
	}

	public void warn(Throwable error) {
		append("warn", error);
	}

	public boolean isErrorEnabled() {
		return true;
	}

	public void error(CharSequence content) {
		append("error", content);
	}

	public void error(CharSequence content, Throwable error) {
		append("error", content, error);
	}

	public void error(Throwable error) {
		append("error", error);
	}

	private void append(String prefix, CharSequence content) {
		appender.append("[" + prefix + "] " + content.toString() + "\n");
	}

	private void append(String prefix, Throwable error) {
		String stackTrace = stackTraceToString(error);
		appender.append("[" + prefix + "] " + stackTrace + "\n");
	}

	private void append(String prefix, CharSequence content, Throwable error) {
		String stackTrace = stackTraceToString(error);
		appender.append("[" + prefix + "] " + content.toString() + "\n\n" + stackTrace + "\n");

	}

	private static String stackTraceToString(Throwable error) {
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);
		error.printStackTrace(pWriter);
		return sWriter.toString();
	}

	public String getLog() {
		return appender.toString();
	}

}
