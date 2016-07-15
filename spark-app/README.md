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


## Step 5 : Submit Spark Application
Here is the command to submit the Spark application

```
    $    spark-submit --master yarn-client \
         --driver-memory 512m --executor-memory 512m \
         --num-executors 2 --executor-cores 1  \
         --class 'x.Clickstream'  target/scala-2.10/testapp_2.10-1.0.jar \
         /user/root/clickstream/in-json/clickstream.json
```

Here is what these arguments mean.  

Must have arguments:
* --master : we are submitting to YARN in 'client' mode.  Another option is `--master yarn-cluster`
* --class 'x.Clickstream' : name of the class to execute
* 'target/scala-2.10/testapp_2.10-1.0.jar' : location of jar file
* input location : point to the data.  Here we are using '/user/root/clickstream/in-json/clickstream.json'

Optional arguments:
* --driver-memory 512m  : memory to be used by client application
* --executor-memory 512m : memory used by Spark executors
* --num-execuctors 2 : how many executors to use
* --executor-cores 1 : use only 1 CPU core

Since we are running on a virtual machine, we are keeping our resource usage low by specifying low memory usage (512M) and only using one CPU core.


## Step 6 : Inspect UIs
* Resource Manager UI : to see how the application is running
* Spark Application UI : to see application progress

## Step 7 : Minimize Logging
Are the logs too much and distracting from program output?

To redirect logs, add  '> logs' at the end of the command.  Now all the logs will be sent to file called 'logs', and we can see our program output clearly**

```
    $    spark-submit --master yarn-client \
         --driver-memory 512m --executor-memory 512m \
         --num-executors 2 --executor-cores 1  \
         --class 'x.Clickstream'  target/scala-2.10/testapp_2.10-1.0.jar \
         /user/root/clickstream/in-json/clickstream.json   2> logs

```


## Step 8 : Load All Data
Change the input to `/user/root/clickstream/in-json` to load all JSON files.

```
    $    spark-submit --master yarn-client \
         --driver-memory 512m --executor-memory 512m \
         --num-executors 2 --executor-cores 1  \
         --class 'x.Clickstream'  target/scala-2.10/testapp_2.10-1.0.jar \
         /user/root/clickstream/in-json/   2> logs

```
