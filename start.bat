@echo off
chcp 65001

setlocal enabledelayedexpansion

:: 获取脚本所在目录
set "SCRIPT_DIR=%~dp0"

:: 指定配置文件路径
set "CONFIG_FILE=%SCRIPT_DIR%config.yml"

echo 正在启动 device 模拟器...
java -Dfile.encoding=UTF-8 -jar "%SCRIPT_DIR%target\device-1.0.jar" --spring.config.location="file:!CONFIG_FILE!"

pause
