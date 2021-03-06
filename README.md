Terracotta DB samples
=====================

What this document will help you accomplish:
--------------------------------------------

- Start a TerracottaDB cluster (one stripe or two stripes)

- Run sample Ehcache3 and Terracotta Store (TCStore) based applications, that connect to a Terracotta DB cluster.

Prerequisites:
--------------

- Download the `Terracotta DB 10.2 kit` from http://www.terracotta.org/downloads/ and unzip it / untar it.

- Have a Terracotta DB license file ready (you can get a trial license file from : http://www.terracotta.org/downloads/ )

- Run the following commands in `bash shell` on Un*x, or in the Windows command prompt (look for .bat files)

Set the environment
-------------------

- For Un*x, run in a bash shell :

   ```export JAVA_HOME="/location/to/valid/JDK8OrLater"```
   ```export TC_HOME="/path/to/extracted/TerracottaDBkit"```


- On Windows, run in a command prompt :

   ```set JAVA_HOME="C:\....\java\jdk1.x.y_zz"```
   ```set TC_HOME="C:\path\to\extracted\TerracottaDBkit"```

Make the license available to the cluster tool
----------------------------------------------

The cluster tool is responsible for configuring your cluster (how many stripes in your cluster, propagating the license, etc.)
By default, the cluster tool expects the license to be under TC_HOME/tools/cluster-tool/conf

For example :

   ```cp TerracottaDB102.xml /Users/ravi/amazing-tools/terracotta-db-10.2.0.0.1/tools/cluster-tool/conf/```


Run the sample applications
---------------------------

Clone this repo, and then you just need to cd into each directory to discover the sample and how start it.

