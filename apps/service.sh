!/bin/bash

#变量值，修改为对应模块的对应值
APP_NAME="demo-service-0.0.1-SNAPSHOT.jar"
APP_HOME="/c/app/demo"
APP_MEM="-Xms4096m -Xmx4096m"
APP_CONFIG="${APP_HOME}/config"
APP_CMD="java -jar ${APP_MEM} -Dextend.path=${APP_CONFIG} -Dlogging.config=${APP_CONFIG}/logback-spring.xml ${APP_NAME}"
LOG_NAME="${APP_HOME}/logs/demo/service/service-info.log"
SCRIPT_NAME="service.sh"

echo "app 名字：$APP_NAME"
echo "app 路径：$APP_HOME"
echo "app 内存：$APP_MEM"
echo "app 配置路径：$APP_CONFIG"
echo "app 日志名字: $LOG_NAME"

function startApp {
    APP_PID=$(ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}')
    if [[ ! "$APP_PID" ]];then
        cd $APP_HOME
        echo "$APP_NAME startup in"
        echo ${APP_CMD}
        nohup ${APP_CMD} > /dev/null 2>$1 &
        echo "$APP_NAME started successfully"
    else
        echo "$APP_NAME is running, pid is: $APP_PID"
    fi;
}

function stopApp {
    APP_PID=$(ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}')
    if [[ ! "$APP_PID" ]];then
        echo "$APP_NAME not start"
    else
        echo "$APP_NAME shutdown in"
        kill -9 $APP_PID
        echo "$APP_NAME stopped successfully"
    fi;
}

function statusApp {
    APP_PID=$(ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}')
    if [[ ! "$APP_PID" ]];then
        echo "$APP_NAME not start"
    else
        echo "$APP_NAME is running, pid is: $APP_PID"
    fi;
}

function restartApp {
    stopApp
    startApp
}

function appLogs {
    printSeparete
    echo "look up $APP_NAME logs, enter Ctrl+c exit"
    printSeparete
    tail -f $LOG_NAME
}

function printSeparete {
    echo "------------------------------------------------>"
}



function userHelp {
    echo "输入错误，请根据提升输入!!!
    sh $SCRIPT_NAME start : 启动$APP_NAME
    sh $SCRIPT_NAME stop: 停止$APP_NAME
    sh $SCRIPT_NAME status: 查看$APP_NAME状态
    sh $SCRIPT_NAME restart: 重启$APP_NAME
    sh $SCRIPT_NAME logs: 查看$APP_NAME日志"
}

if [[ $1 == "start" ]];then
    printSeparete 
    startApp
    printSeparete 
elif [[ $1 == "stop" ]];then
    printSeparete 
    stopApp
    printSeparete 
elif [[ $1 == "status" ]];then
    printSeparete 
    stopApp
    printSeparete 
elif [[ $1 == "restart" ]];then
    printSeparete 
    stopApp
    printSeparete 
elif [[ $1 == "logs" ]];then
    appLogs
else
    printSeparete 
    userHelp
    printSeparete 
fi;