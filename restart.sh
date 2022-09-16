#!/bin/sh
echo "restart"
pid=`jps -ef | grep elc-$1.jar | grep -v grep | awk 'print $2'`
if [ -n "$pid" ]
then
    kill -9 $pid
fi
cd /apps
source /etc/profile
java -version
pwd
nohup java -jar elc-$1 > $1.log 2>&1 &


#启动该脚本
#nohup sh /apps/restart.sh name