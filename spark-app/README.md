# Running a Spark Application

## Working Directory
spark-app

## Depends On
[Hadoop setup](../hadoop-setup/README.md)

## Step 1 : Login to Sandbox
Follow instructions for your environment.

## Step 2 : Setup Dev Tools
We will need SBT to build our Spark application.  
To see if SBT is installed, try this
```
    $   sbt -help
```

If no SBT is installed, no worries.  SBT is easy to install following these steps.
```bash

    $   cd

    # if this following URL doesn't work, get the most recent 
    # download link from : http://www.scala-sbt.org/

    $   wget https://dl.bintray.com/sbt/native-packages/sbt/0.13.11/sbt-0.13.11.tgz

    $   tar xvf sbt-0.13.11.tgz

    $   ~/sbt/bin/sbt -help
```

First time you run SBT, it will download a bunch of dependencies.  This will take a couple of minutes.  Go get some coffee ! :-)


## Step 3 : Spark Application Project
The folder  `hadoop-spark/spark-app` has Spark application setup as SBT project.  

**Inspect file : [build.sbt](build.sbt) on the top level project.** 

**Inspect code : [src/main/scala/x/Clickstream.scala](src/main/scala/x/Clickstream.scala)**

## Step 4 : Build Spark Application
From the `spark-app` folder

```bash

    $   sbt package
    #   if running for the first time, go get some coffee :-)
```


## Step 5 : Testing Spark Application in 'local' mode
Before submitting the application to the cluster, let's test it locally to make sure it works

```
    $    spark-submit --master local[*] \
         --driver-memory 512m --executor-memory 512m \
        
         --class 'x.Clickstream'  target/scala-2.10/testapp_2.10-1.0.jar \
         ../data/clickstream/clickstream.json
```

Arguments (must have):
* --master local[*]: Run in local mode using all CPU cores (*)
* --class 'x.Clickstream' : name of the class to execute
* 'target/scala-2.10/testapp_2.10-1.0.jar' : location of jar file
* input location : point to the data.  Here we are using local file in 'data/clickstream/clickstream.json'

Arguments (optional):
* --driver-memory 512m  : memory to be used by client application
* --executor-memory 512m : memory used by Spark executors


## Step 6 : Submit Spark Application to YARN cluster
Here is the command to submit the Spark application

```
    $    spark-submit --master local[*] \
         --driver-memory 512m --executor-memory 512m \
         --num-executors 2 --executor-cores 1  \
         --class 'x.Clickstream'  target/scala-2.10/testapp_2.10-1.0.jar \
         /user/root/clickstream/in-json/clickstream.json
```



Must have arguments:
* --master : we are submitting to YARN in 'client' mode.  Another option is `--master yarn-cluster`
* --class 'x.Clickstream' : name of the class to execute
* 'target/scala-2.10/testapp_2.10-1.0.jar' : location of jar file
* input location : point to the data.  Here we are using data in HDFS '/user/root/clickstream/in-json/clickstream.json'

Optional arguments:
* --driver-memory 512m  : memory to be used by client application
* --executor-memory 512m : memory used by Spark executors
* --num-execuctors 2 : how many executors to use
* --executor-cores 1 : use only 1 CPU core

Since we are running on a virtual machine, we are keeping our resource usage low by specifying low memory usage (512M) and only using one CPU core.


## Step 7 : Inspect UIs
* Resource Manager UI : to see how the application is running
* Spark Application UI : to see application progress

## Step 8 : Minimize Logging
Are the logs too much and distracting from program output?

#### Option 1 :  Redirect Logs
To redirect logs, add  '> logs' at the end of the command.  Now all the logs will be sent to file called 'logs', and we can see our program output clearly**

```
    $    spark-submit --master yarn-client \
         --driver-memory 512m --executor-memory 512m \
         --num-executors 2 --executor-cores 1  \
         --class 'x.Clickstream'  target/scala-2.10/testapp_2.10-1.0.jar \
         /user/root/clickstream/in-json/clickstream.json   2> logs

```

#### Option 2 :  Disable logging
Use  log4j directives.
We have a `logging/log4j.properties` file.  Inspect this file

```
    $    cat   logging/log4j.properties
```


The file has following contents
```
    # Set everything to be logged to the console
    log4j.rootCategory=WARN, console
    log4j.appender.console=org.apache.log4j.ConsoleAppender
    log4j.appender.console.target=System.err
    log4j.appender.console.layout=org.apache.log4j.PatternLayout
    log4j.appender.console.layout.ConversionPattern=%d{yy/MM/dd HH:mm:ss} %p %c{1}: %m%n
    
    # Settings to quiet third party logs that are too verbose
    log4j.logger.org.eclipse.jetty=WARN
    log4j.logger.org.apache.spark.repl.SparkIMain$exprTyper=INFO
    log4j.logger.org.apache.spark.repl.SparkILoop$SparkILoopInterpreter=INFO
```



```
    $    spark-submit --master yarn-client \
         --driver-class-path logging/ \
         --driver-memory 512m --executor-memory 512m \
         --num-executors 2 --executor-cores 1  \
         --class 'x.Clickstream'  target/scala-2.10/testapp_2.10-1.0.jar \
         /user/root/clickstream/in-json/clickstream.json   2> logs
```

## Step 9 : Load All Data
Change the input to `/user/root/clickstream/in-json` to load all JSON files.

```
    $    spark-submit --master yarn-client \
         --driver-memory 512m --executor-memory 512m \
         --num-executors 2 --executor-cores 1  \
         --class 'x.Clickstream'  target/scala-2.10/testapp_2.10-1.0.jar \
         /user/root/clickstream/in-json/   2> logs

```
