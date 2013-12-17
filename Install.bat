@java -version 1>nul 2>&1 && (
	@set JAVA=
	goto RUN
) || (
	@for /f %%j in ("java.exe") do @set JAVA=%%~dp$PATH:j
	goto RUN
)
:RUN
start %JAVA%java -Dfile.encoding=UTF-8 -cp .\mysql-connector.jar;.\poi-3.9-20121203.jar;.\build\classes\ installer.Installer
exit