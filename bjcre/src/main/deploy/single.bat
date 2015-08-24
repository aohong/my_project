#!/bin/bash

#取当前目录
#SET BASE_PATH=`cd "$(dirname "$0")"; pwd`

#设置java运行参数
SET DEFAULT_JAVA_OPTS=" -server -Xmx1g -Xms256m  -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=11088,server=y,suspend=n"

#定义变量:
SET APP_PATH=..
SET CLASS_PATH="%CLASSPATH%;../config;../lib/*"
SET MAIN_CLASS=com.bjcre.server.WebStart 8080 /test ../config

		echo $APP_PATH

				java  -cp %CLASS_PATH% %MAIN_CLASS%
				echo "bjcre is started."
