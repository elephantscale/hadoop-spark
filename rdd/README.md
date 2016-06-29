# RDD

## Working Directory
rdd

## Depends On
[Hadoop setup](../hadoop-setup/README.md)

## Step 1 : Login to Sandbox
Follow instructions for your environment.

## Step 2 : Start Spark shell

```
    $    spark-shell
```

## Step 3 : Set the log level to WARN
Type this in Spark Shell
```
    sc.setLogLevel("WARN")
```

## Step 4 : Inspect the Shell UI
Go to http://localhost:4040 in the browser.


## Step 5 : Load Clickstream Data
Issue the following commands in Spark-shell

```
        val clickstream = sc.textFile("/user/root/clickstream/in/clickstream.csv")

        # count how many lines are there
        clickstream.count

        # print all lines
        clickstream.collect
```


## Step 6 : Apply Transformations
Let's find all traffic from 'facebook.com'.  
We can use a filter command for this.   
Try the following in Spark-shell

```
    # apply filter
    val fb = clickstream.filter(line => line.contains("facebook.com"))

    # check the Shell UI, is the above transformation executed yet? why (not) ?

    # count the FB traffic
    fb.count

    # print fB traffic
    fb.collect

```


## Step 7 : More Transformations
**Find the views / clicks ratio for facebook.com**

Let's load all data in `clickstream/in` directory.

```
    val clickstream = sc.textFile("/user/root/clickstream/in/")

    val fb = clickstream.filter(line => line.contains("facebook.com"))

    val fbViews = fb.filter(line => line.contains("viewed"))
    val fbClicks = fb.filter(line => line.contains("clicked"))

    # calculate the views / clicks ratio
    println ("FB views / clicks  = " + (fbViews.count.toFloat / fbClicks.count))
```

** => Inspect the Spark Shell UI (port 4040) ** 
