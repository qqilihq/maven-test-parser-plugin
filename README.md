Test Parser Plugin for Maven
============================

[![Run Status](https://api.shippable.com/projects/58d7cb2ca8fea50500f12d34/badge?branch=master)](https://app.shippable.com/github/qqilihq/maven-test-parser-plugin)
[![Coverage Badge](https://api.shippable.com/projects/58d7cb2ca8fea50500f12d34/coverageBadge?branch=master)](https://app.shippable.com/github/qqilihq/maven-test-parser-plugin)

About
-----

This [Maven][3] plugin solves the following issue which I described in [this][2] Stack Overflow question:

> I'm running a Maven build workflow which involves running a 3rd party tool for integration testing, which produces multiple XML files in JUnit style (however, those files are **not** created by JUnit and I have no control over the testing procedure).
> 
> Is there a Maven plugin, which allows me to parse those files? Especially, I would like the build to fail, in case those XML files list a failure.
> 
> My exact problem has been described [here][1] some years ago, and the proposed solutions were:
> 
> 1. *"Write your own plugin to call your external test and report failures,
either by parsing the xml or some other approach"* -- potential solution, however I hope that some years later maybe there is something ready-to-use?
> 2. *"Adjust your external test tool so it returns "false" (1) when it has a
failure which Maven should pick up and understand to mean "failure
encountered" and it will fail the build"* -- unfortunately, I have no control over the external tool.


Usage
-----

### Through `pom.xml`

Add it to your `pom.xml` within the `<build>` section as follows and specify the `resultsDirectory` which contains the XML files with the test results. The plugin will also parse XML files within subdirectories.

```
  <build>
    <plugins>
      <plugin>
        <groupId>de.philippkatz.maven.plugins</groupId>
        <artifactId>test-parser-plugin</artifactId>
        <version>3.1</version>
        <configuration>
          <resultsDirectory>${project.build.directory}/testflow-reports</resultsDirectory>
        </configuration>
        <executions>
          <execution>
            <goals>
              <goal>testparser</goal>
            </goals>
            <phase>integration-test</phase>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
```

### Via command line

In case you want to use it stand alone without a Maven project, you can still execute the plugin directly on the command line. Pass the path to the results directory with the `-Dtestparser.resultsDirectory` parameter:

```
$ mvn de.philippkatz.maven.plugins:test-parser-plugin:3.1.0:testparser -Dtestparser.resultsDirectory=./testflow-reports
```

Contributing
------------

Pull requests are very welcome. Feel free to discuss bugs or new features by
opening a new [issue][2].


- - -

Copyright (c) 2017, 2018 Philipp Katz


  [1]: http://maven.40175.n5.nabble.com/How-to-parse-JUnit-report-xml-that-causes-build-to-pass-fail-td5433750.html
  [2]: http://stackoverflow.com/questions/33857858/parse-junit-result-xml-format-created-by-3rd-party-tool-with-maven
  [3]: https://maven.apache.org
  [4]: https://github.com/qqilihq/maven-test-parser-plugin/issues
