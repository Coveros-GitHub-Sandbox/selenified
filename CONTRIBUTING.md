# How To Contribute
Thank you for your interest in contributing to Selenified. This is an open source project, and we encourage
contributions from the from the community. After all, those using it, probably know what it needs the most.

## Testing
We have a multitude of unit and integration tests. Any new framework feature should have either a unit or 
integration test written against it. Ensure both positive and negative branches are explored. While we aren't
aiming for 100% coverage, we currently have over 95% framework coverage from UT and IT tests.
All Unit Tests need to pass, and all Integration tests should pass for all browsers. Examine the Jenkinsfile to
see how tests are executed.

## Submitting Changes
Please send a [GitHub Pull Request to Selenified](https://github.com/Coveros/selenified/pull/new/develop) with a 
clear list of what you've done (read more about [pull requests](http://help.github.com/pull-requests/)). Please 
follow our coding conventions (below) and make sure all of your commits are atomic (one feature per commit).

Always write a clear log message for your commits. One-line messages are fine for small changes, but bigger 
changes should look like this:

    $ git commit -m "A brief summary of the commit
    > 
    > A paragraph describing what changed and its impact."

A copy of the Jenkinsfile will automatically be run, executing unit tests, performing static analysis, running 
integration tests, and determining coverage. This information will be reported back to GitHub via checks, and all
must pass for the pull request to be approved. Once the code is approved into `develop`, a quarterly release will be
made to maven-central, promoting code from `develop` to `master`. 

## Coding Conventions
Start reading our code and you'll get the hang of it. We optimize for readability, and use the IntelliJ defaults:

  * We indent using four spaces (soft tabs)
  * We ALWAYS put spaces after list items and method parameters (`[1, 2, 3]`, not `[1,2,3]`), around operators 
  (`x += 1`, not `x+=1`), and around hash arrows
  * We want to be inclusive of all browsers, and so methods should be written in a generic fashion to enable cross 
  browser functionality, including HtmlUnit
  * We include Javadocs for all new methods, and comments in code when you think it's necessary
  * We try to be explicit whenever possible, not chaining method calls, not redefining variables, etc
  * This is open source software. Consider the people who will read your code, and make it look nice for them. 
  It's sort of like driving a car: Perhaps you love doing donuts when you're alone, but with passengers the goal is 
  to make the ride as smooth as possible.
  
Thanks,
The Selenified Team