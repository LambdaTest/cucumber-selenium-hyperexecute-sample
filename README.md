<img height="100" alt="hyperexecute_logo" src="https://user-images.githubusercontent.com/1688653/159473714-384e60ba-d830-435e-a33f-730df3c3ebc6.png">

HyperExecute is a smart test orchestration platform to run end-to-end Selenium tests at the fastest speed possible. HyperExecute lets you achieve an accelerated time to market by providing a test infrastructure that offers optimal speed, test orchestration, and detailed execution logs.

The overall experience helps teams test code and fix issues at a much faster pace. HyperExecute is configured using a YAML file. Instead of moving the Hub close to you, HyperExecute brings the test scripts close to the Hub!

* <b>HyperExecute HomePage</b>: https://www.lambdatest.com/hyperexecute
* <b>Lambdatest HomePage</b>: https://www.lambdatest.com
* <b>LambdaTest Support</b>: [support@lambdatest.com](mailto:support@lambdatest.com)

To know more about how HyperExecute does intelligent Test Orchestration, do check out [HyperExecute Getting Started Guide](https://www.lambdatest.com/support/docs/getting-started-with-hyperexecute/)

[<img alt="Try it now" width="200 px" align="center" src="images/Try it Now.svg" />](https://hyperexecute.lambdatest.com/?utm_source=github&utm_medium=repository&utm_content=java&utm_term=cucumber)

## Gitpod

Follow the below steps to run Gitpod button:

1. Click '**Open in Gitpod**' button (You will be redirected to Login/Signup page).
2. Login with Lambdatest credentials and it will be redirected to Gitpod editor in new tab and current tab will show hyperexecute dashboard.

[<img alt="Run in Gitpod" width="200 px" align="center" src="images/Gitpod.svg" />](https://hyperexecute.lambdatest.com/hyperexecute?type=gitpod&framework=Cucumber)
---
<!---If logged in, it will be redirected to Gitpod editor in new tab where current tab will show hyperexecute dashboard.

If not logged in, it will be redirected to Login/Signup page and simultaneously redirected to Gitpod editor in a new tab where current tab will show hyperexecute dashboard.

If not signed up, you need to sign up and simultaneously redirected to Gitpod in a new tab where current tab will show hyperexecute dashboard.--->



# How to run Selenium automation tests on HyperExecute (using Cucumber framework)

* [Pre-requisites](#pre-requisites)
   - [Download HyperExecute CLI](#download-hyperexecute-cli)
   - [Configure Environment Variables](#configure-environment-variables)

* [Auto-Split Execution with Cucumber](#auto-split-execution-with-cucumber)
   - [Core](#core)
   - [Pre Steps and Dependency Caching](#pre-steps-and-dependency-caching)
   - [Post Steps](#post-steps)
   - [Artifacts Management](#artifacts-management)
   - [Test Execution](#test-execution)

* [Matrix Execution with Cucumber](#matrix-execution-with-cucumber)
   - [Core](#core-1)
   - [Pre Steps and Dependency Caching](#pre-steps-and-dependency-caching-1)
   - [Post Steps](#post-steps-1)
   - [Artifacts Management](#artifacts-management-1)
   - [Test Execution](#test-execution-1)

* [Secrets Management](#secrets-management)
* [Navigation in Automation Dashboard](#navigation-in-automation-dashboard)

# Pre-requisites

Before using HyperExecute, you have to download HyperExecute CLI corresponding to the host OS. Along with it, you also need to export the environment variables *LT_USERNAME* and *LT_ACCESS_KEY* that are available in the [LambdaTest Profile](https://accounts.lambdatest.com/detail/profile) page.

## Download HyperExecute CLI

HyperExecute CLI is the CLI for interacting and running the tests on the HyperExecute Grid. The CLI provides a host of other useful features that accelerate test execution. In order to trigger tests using the CLI, you need to download the HyperExecute CLI binary corresponding to the platform (or OS) from where the tests are triggered:

Also, it is recommended to download the binary in the project's parent directory. Shown below is the location from where you can download the HyperExecute CLI binary:

* Mac: https://downloads.lambdatest.com/hyperexecute/darwin/hyperexecute
* Linux: https://downloads.lambdatest.com/hyperexecute/linux/hyperexecute
* Windows: https://downloads.lambdatest.com/hyperexecute/windows/hyperexecute.exe

## Configure Environment Variables

Before the tests are run, please set the environment variables LT_USERNAME & LT_ACCESS_KEY from the terminal. The account details are available on your [LambdaTest Profile](https://accounts.lambdatest.com/detail/profile) page.

For macOS:

```bash
export LT_USERNAME=LT_USERNAME
export LT_ACCESS_KEY=LT_ACCESS_KEY
```

For Linux:

```bash
export LT_USERNAME=LT_USERNAME
export LT_ACCESS_KEY=LT_ACCESS_KEY
```

For Windows:

```bash
set LT_USERNAME=LT_USERNAME
set LT_ACCESS_KEY=LT_ACCESS_KEY
```

## Auto-Split Execution with Cucumber

Auto-split execution mechanism lets you run tests at predefined concurrency and distribute the tests over the available infrastructure. Concurrency can be achieved at different levels - file, module, test suite, test, scenario, etc.

For more information about auto-split execution, check out the [Auto-Split Getting Started Guide](https://www.lambdatest.com/support/docs/getting-started-with-hyperexecute/#smart-auto-test-splitting)

### Core

Auto-split YAML file (*yaml/win/cucumber_hyperexecute_autosplit_sample.yaml*) in the repo contains the following configuration:

```yaml
globalTimeout: 150
testSuiteTimeout: 150
testSuiteStep: 150
```

Global timeout, testSuite timeout, and testSuite timeout are set to 150 minutes.
 
The *runson* key determines the platform (or operating system) on which the tests are executed. Here we have set the target OS as Windows.

```yaml
runson: win
```

Auto-split is set to true in the YAML file.

```yaml
 autosplit: true
```

*retryOnFailure* is set to true, instructing HyperExecute to retry failed command(s). The retry operation is carried out till the number of retries mentioned in *maxRetries* are exhausted or the command execution results in a *Pass*. In addition, the concurrency (i.e. number of parallel sessions) is set to 4.

```yaml
retryOnFailure: true
maxRetries: 5
concurrency: 4
```

### Pre Steps and Dependency Caching

Dependency caching is enabled in the YAML file to ensure that the package dependencies are not downloaded in subsequent runs. The first step is to set the Key used to cache directories. The directory *m2_cache_dir* is created in the project's root directory.

```yaml
env:
  CACHE_DIR: m2_cache_dir

cacheKey: '{{ checksum "pom.xml" }}'
cacheDirectories:
  - $CACHE_DIR
```

Steps (or commands) that must run before the test execution are listed in the *pre* run step. In the example, the Maven packages are downloaded in the *m2_cache_dir*. To prevent test execution at the *pre* stage, the *maven.test.skip* parameter is set to *true* so that only packages are downloaded and no test execution is performed.

```yaml
pre:
  - mvn -Dmaven.repo.local=$CACHE_DIR -Dmaven.test.skip=true clean install
```

### Post Steps

Steps (or commands) that need to run after the test execution are listed in the *post* step. In the example, we *cat* the contents of *yaml/win/cucumber_hyperexecute_autosplit_sample.yaml*

```yaml
post:
  - cat yaml/win/cucumber_hyperexecute_autosplit_sample.yaml
```

The *testDiscovery* directive contains the command that gives details of the mode of execution, along with detailing the command that is used for test execution. Here, we are fetching the list of class names that would be further passed in the *testRunnerCommand*

```yaml
testDiscovery:
  type: raw
  mode: static
  command: grep -nri '@' src/main/java/Features --include=\*.feature | sed 's/^.*://'
```

Running the above command on the terminal will give a list of scenarios present in the *feature* files:

* @BingSearch
* @ToDo
* @LambdaTestBlogSearch
* @SeleniumPlayground

The *testRunnerCommand* contains the command that is used for triggering the test. The output fetched from the *testDiscoverer* command acts as an input to the *testRunner* command.

```yaml
testRunnerCommand: mvn test `-Dmaven.repo.local=$CACHE_DIR `-Dcucumber.options="--tags $test"
```

### Artifacts Management

The *mergeArtifacts* directive (which is by default *false*) is set to *true* for merging the artifacts and combing artifacts generated under each task.

The *uploadArtefacts* directive informs HyperExecute to upload artifacts [files, reports, etc.] generated after task completion. In the example, *path* consists of a regex for parsing the directories that contain the XML and JSON reports (i.e. *target/surefire-reports/testng-results.xml* and *target/surefire-reports/testng-results.xml*) directory.

```yaml
mergeArtifacts: true

uploadArtefacts:
  - name: XmlReports
    path:
      - target/surefire-reports/testng-results.xml
  - name: JsonReports
    path:
      - target/cucumber-reports/CucumberTestReport.json
```

HyperExecute also facilitates the provision to download the artifacts on your local machine. To download the artifacts, click on Artifacts button corresponding to the associated TestID.

<img width="1425" alt="cucumber_autosplit_artefacts_1" src="https://user-images.githubusercontent.com/1688653/160453348-67a416d9-ec37-4467-9a51-1b20484f39f1.png">

Now, you can download the artifacts by clicking on the Download button as shown below:

<img width="1425" alt="cucumber_autosplit_artefacts_2" src="https://user-images.githubusercontent.com/1688653/160453362-aa371377-0d81-4140-8a7a-b6170310ac15.png">

### Test Execution

The CLI option *--config* is used for providing the custom HyperExecute YAML file (i.e. *yaml/win/cucumber_hyperexecute_autosplit_sample.yaml* for Windows and *yaml/linux/cucumber_hyperexecute_autosplit_sample.yaml* for Linux).

#### Execute Cucumber tests using Autosplit mechanism on Windows platform

Run the following command on the terminal to trigger Cucumber tests on the Windows platform. The *--download-artifacts* option is used to inform HyperExecute to download the artifacts for the job. The *--force-clean-artifacts* option force cleans any existing artifacts for the project.

```bash
./hyperexecute --config yaml/win/cucumber_hyperexecute_autosplit_sample.yaml --force-clean-artifacts --download-artifacts
```

#### Execute Cucumber tests using Autosplit mechanism on Linux platform

Run the following command on the terminal to trigger Cucumber tests on the Linux platform. The *--download-artifacts* option is used to inform HyperExecute to download the artifacts for the job. The *--force-clean-artifacts* option force cleans any existing artifacts for the project.

```bash
./hyperexecute --config yaml/linux/cucumber_hyperexecute_autosplit_sample.yaml --force-clean-artifacts --download-artifacts
```

Visit [HyperExecute Automation Dashboard](https://automation.lambdatest.com/hyperexecute) to check the status of execution

<img width="1414" alt="cucumber_autosplit_execution" src="https://user-images.githubusercontent.com/1688653/160453348-67a416d9-ec37-4467-9a51-1b20484f39f1.png">

Shown below is the execution screenshot when the YAML file is triggered from the terminal:

<img width="1412" alt="cucumber_autosplit_cli1_execution" src="https://user-images.githubusercontent.com/1688653/159763098-bbb5af76-bf4f-42f3-a4f0-fc1dda8347b3.png">

<img width="1408" alt="cucumber_autosplit_cli2_execution" src="https://user-images.githubusercontent.com/1688653/159763105-58c49a6b-68e2-4e1e-a413-b03dc23ad03d.png">

# Matrix Execution with Cucumber

Matrix-based test execution is used for running the same tests across different test (or input) combinations. The Matrix directive in HyperExecute YAML file is a *key:value* pair where value is an array of strings.

Also, the *key:value* pairs are opaque strings for HyperExecute. For more information about matrix multiplexing, check out the [Matrix Getting Started Guide](https://www.lambdatest.com/support/docs/getting-started-with-hyperexecute/#matrix-based-build-multiplexing)

### Core

In the current example, matrix YAML file (*yaml/win/cucumber_hyperexecute_matrix_sample.yaml*) in the repo contains the following configuration:

```yaml
globalTimeout: 150
testSuiteTimeout: 150
testSuiteStep: 150
```

Global timeout, testSuite timeout, and testSuite timeout are set to 150 minutes.
 
The target platform is set to Win. Please set the *[runson]* key to *[mac]* if the tests have to be executed on the macOS platform.

```yaml
runson: win
```

The *matrix* constitutes of the following entries - *cucumbertag*. The entries represent the scenario names in the feature files located in *src/main/java/Features*.

```yaml
matrix:
  cucumbertag: ["@SeleniumPlayground", "@ToDo",
                "@BingSearch", "@LambdaTestBlogSearch"]
```

The *testSuites* object contains a list of commands (that can be presented in an array). In the current YAML file, commands for executing the tests are put in an array (with a '-' preceding each item). The Maven command *mvn test* is used to run tests located in the current project. In the current project, parallel execution is achieved at the *class* level. The *maven.repo.local* parameter in Maven is used for overriding the location where the dependent Maven packages are downloaded. The *cucumber.options* parameter is used for filtering tests at the *scenario* level.

```yaml
testSuites:
  - mvn test `-Dmaven.repo.local=$CACHE_DIR `-Dcucumber.options="--tags $cucumbertag"
```

### Pre Steps and Dependency Caching

Dependency caching is enabled in the YAML file to ensure that the package dependencies are not downloaded in subsequent runs. The first step is to set the Key used to cache directories. The directory *m2_cache_dir* is created in the project's root directory.

```yaml
env:
  CACHE_DIR: m2_cache_dir

cacheKey: '{{ checksum "pom.xml" }}'
cacheDirectories:
  - $CACHE_DIR
```

Steps (or commands) that must run before the test execution are listed in the *pre* run step. In the example, the Maven packages are downloaded in the *m2_cache_dir*. To prevent test execution at the *pre* stage, the *maven.test.skip* parameter is set to *true* so that only packages are downloaded and no test execution is performed.

```yaml
pre:
  - mvn -Dmaven.repo.local=$CACHE_DIR -Dmaven.test.skip=true clean install
```

### Post Steps

Steps (or commands) that need to run after the test execution are listed in the *post* step. In the example, we *cat* the contents of *yaml/win/cucumber_hyperexecute_matrix_sample.yaml*

```yaml
post:
  - cat yaml/win/cucumber_hyperexecute_matrix_sample.yaml
```

### Artifacts Management

The *mergeArtifacts* directive (which is by default *false*) is set to *true* for merging the artifacts and combing artifacts generated under each task.

The *uploadArtefacts* directive informs HyperExecute to upload artifacts [files, reports, etc.] generated after task completion. In the example, *path* consists of a regex for parsing the directories that contain the XML and JSON reports (i.e. *target/surefire-reports/testng-results.xml* and *target/surefire-reports/testng-results.xml*) directory.

```yaml
mergeArtifacts: true

uploadArtefacts:
  - name: XmlReports
    path:
      - target/surefire-reports/testng-results.xml
  - name: JsonReports
    path:
      - target/cucumber-reports/CucumberTestReport.json
```

HyperExecute also facilitates the provision to download the artifacts on your local machine. To download the artifacts, click on Artifacts button corresponding to the associated TestID.

<img width="1425" alt="cucumber_matrix_artefacts_1" src="https://user-images.githubusercontent.com/1688653/160452786-e60e6ee2-b0e9-4248-81aa-ca07ecbfdd1f.png">

Now, you can download the artifacts by clicking on the Download button as shown below:

<img width="1425" alt="cucumber_matrix_artefacts_2" src="https://user-images.githubusercontent.com/1688653/160452801-88341e4e-ac9b-41f7-bd21-18f363ef16f5.png">

## Test Execution

The CLI option *--config* is used for providing the custom HyperExecute YAML file (i.e. *yaml/win/cucumber_hyperexecute_matrix_sample.yaml* for Windows and *yaml/linux/cucumber_hyperexecute_matrix_sample.yaml* for Linux).

#### Execute Cucumber tests using Matrix mechanism on Windows platform

Run the following command on the terminal to trigger Cucumber tests on the Windows platform. The *--download-artifacts* option is used to inform HyperExecute to download the artifacts for the job. The *--force-clean-artifacts* option force cleans any existing artifacts for the project.

```bash
./hyperexecute --config yaml/win/cucumber_hyperexecute_matrix_sample.yaml --force-clean-artifacts --download-artifacts
```

#### Execute Cucumber tests using Matrix mechanism on Linux platform

Run the following command on the terminal to trigger Cucumber tests on the Linux platform. The *--download-artifacts* option is used to inform HyperExecute to download the artifacts for the job. The *--force-clean-artifacts* option force cleans any existing artifacts for the project.

```bash
./hyperexecute --config yaml/linux/cucumber_hyperexecute_matrix_sample.yaml --force-clean-artifacts --download-artifacts
```

Visit [HyperExecute Automation Dashboard](https://automation.lambdatest.com/hyperexecute) to check the status of execution:

<img width="1414" alt="cucumber_matrix_execution" src="https://user-images.githubusercontent.com/1688653/160452786-e60e6ee2-b0e9-4248-81aa-ca07ecbfdd1f.png">

Shown below is the execution screenshot when the YAML file is triggered from the terminal:

<img width="1413" alt="cucumber_cli1_execution" src="https://user-images.githubusercontent.com/1688653/159763909-305b13b7-df13-43de-b115-09565387edce.png">

<img width="1101" alt="cucumber_cli2_execution" src="https://user-images.githubusercontent.com/1688653/159763911-61ba72e1-ed47-40c8-914a-2a8e3ab8db2a.png">

## Secrets Management

In case you want to use any secret keys in the YAML file, the same can be set by clicking on the *Secrets* button the dashboard.

<img width="703" alt="cucumber_secrets_key_1" src="https://user-images.githubusercontent.com/1688653/152540968-90e4e8bc-3eb4-4259-856b-5e513cbd19b5.png">

Now create a *secret* key that you can use in the HyperExecute YAML file.

<img width="359" alt="cucumber_management_1" src="https://user-images.githubusercontent.com/1688653/153250877-e58445d1-2735-409a-970d-14253991c69e.png">

All you need to do is create an environment variable that uses the secret key:

```yaml
env:
  PAT: ${{ .secrets.testKey }}
```

## Navigation in Automation Dashboard

HyperExecute lets you navigate from/to *Test Logs* in Automation Dashboard from/to *HyperExecute Logs*. You also get relevant get relevant Selenium test details like video, network log, commands, Exceptions & more in the Dashboard. Effortlessly navigate from the automation dashboard to HyperExecute logs (and vice-versa) to get more details of the test execution.

Shown below is the HyperExecute Automation dashboard which also lists the tests that were executed as a part of the test suite:

<img width="1429" alt="cucumber_hyperexecute_automation_dashboard" src="https://user-images.githubusercontent.com/1688653/160452786-e60e6ee2-b0e9-4248-81aa-ca07ecbfdd1f.png">

Here is a screenshot that lists the automation test that was executed on the HyperExecute grid:

<img width="1429" alt="cucumber_testing_automation_dashboard" src="https://user-images.githubusercontent.com/1688653/159763904-c5c6e1f2-394b-48b0-aadf-541a7c9dbeae.png">

## LambdaTest Community :busts_in_silhouette:

The [LambdaTest Community](https://community.lambdatest.com/) allows people to interact with tech enthusiasts. Connect, ask questions, and learn from tech-savvy people. Discuss best practises in web development, testing, and DevOps with professionals from across the globe.

## Documentation & Resources :books:
      
If you want to learn more about the LambdaTest's features, setup, and usage, visit the [LambdaTest documentation](https://www.lambdatest.com/support/docs/). You can also find in-depth tutorials around test automation, mobile app testing, responsive testing, manual testing on [LambdaTest Blog](https://www.lambdatest.com/blog/) and [LambdaTest Learning Hub](https://www.lambdatest.com/learning-hub/).     
      
 ## About LambdaTest

[LambdaTest](https://www.lambdatest.com) is a leading test execution and orchestration platform that is fast, reliable, scalable, and secure. It allows users to run both manual and automated testing of web and mobile apps across 3000+ different browsers, operating systems, and real device combinations. Using LambdaTest, businesses can ensure quicker developer feedback and hence achieve faster go to market. Over 500 enterprises and 1 Million + users across 130+ countries rely on LambdaTest for their testing needs.

[<img height="70" src="https://user-images.githubusercontent.com/70570645/169649126-ed61f6de-49b5-4593-80cf-3391ca40d665.PNG">](https://accounts.lambdatest.com/register)
      
## We are here to help you :headphones:

* Got a query? we are available 24x7 to help. [Contact Us](mailto:support@lambdatest.com)
* For more info, visit - https://www.lambdatest.com
