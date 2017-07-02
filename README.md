[![Run Status](https://api.shippable.com/projects/58d7cb2ca8fea50500f12d34/badge?branch=master)](https://app.shippable.com/github/qqilihq/maven-test-parser-plugin)

This [Maven][3] plugin solves the following issue which I described in [this][2] Stack Overflow question:

> I'm running a Maven build workflow which involves running a 3rd party tool for integration testing, which produces multiple XML files in JUnit style (however, those files are **not** created by JUnit and I have no control over the testing procedure).

> Is there a Maven plugin, which allows me to parse those files? Especially, I would like the build to fail, in case those XML files list a failure.

> My exact problem has been described [here][1] some years ago, and the proposed solutions were:

> 1. *"Write your own plugin to call your external test and report failures,
either by parsing the xml or some other approach"* -- potential solution, however I hope that some years later maybe there is something ready-to-use?
> 2. *"Adjust your external test tool so it returns "false" (1) when it has a
failure which Maven should pick up and understand to mean "failure
encountered" and it will fail the build"* -- unfortunately, I have no control over the external tool.

  [1]: http://maven.40175.n5.nabble.com/How-to-parse-JUnit-report-xml-that-causes-build-to-pass-fail-td5433750.html
  [2]: http://stackoverflow.com/questions/33857858/parse-junit-result-xml-format-created-by-3rd-party-tool-with-maven
  [3]: https://maven.apache.org
