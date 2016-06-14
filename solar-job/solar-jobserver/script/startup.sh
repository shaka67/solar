#! /bin/sh

basepath=$(cd `dirname $0`; pwd)
java -Duser.timezone=Asia/Shanghai -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -server -Xmx${jvm.Xmx} -Xms${jvm.Xms} -Xmn${jvm.Xmn} -XX:PermSize=${jvm.permsize} -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -verbose:gc -Xloggc:/ouer/logs/gc/gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/ouer/data/heapdump.hprof -jar $basepath/../solar-jobserver-2.0.0.jar