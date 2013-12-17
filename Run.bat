@javaw -version 1>nul 2>&1 && (
	@set JAVA=
	goto RUN
) || (
	@for /f %%j in ("javaw.exe") do @set JAVA=%%~dp$PATH:j
	goto RUN
)
:RUN
start %JAVA%javaw -cp .\mysql-connector.jar;.\poi-3.9-20121203.jar;.\build\classes\ auto.Auto
exit