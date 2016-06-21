-- hive table for clickstream

CREATE EXTERNAL TABLE clickstream (
    ts BIGINT,
    ip STRING,
    user STRING,
    action STRING,
    domain STRING,
    campaign STRING,
    cost INT,
    session STRING)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
stored as textfile
LOCATION '/user/root/clickstream/in'  ;