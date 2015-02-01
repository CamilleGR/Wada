#!/bin/bash

TWITTER4J_JARS=`ls -m /media/DATA/Logiciel/spark-1.2.0-bin-hadoop2.4/lib/twitter4j/*.jar | tr -d '\n'`
spark-shell -i conf.scala --jars /media/DATA/Logiciel/spark-1.2.0-bin-hadoop2.4/lib/spark-streaming-twitter_2.11-1.2.0.jar,$TWITTER4J_JARS
#Script pour lancer le sparkShell
