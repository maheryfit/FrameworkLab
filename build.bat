cd framework/classes
jar cf fw.jar .
move fw.jar ../../test-framework/lib 

@REM Ajouter dernièrement avec le sprint - 13
cd ../
cd ./lib
xcopy *  E:\Github\FrameworkLab\test-framework\WEB-INF\ /E /I /Y
cd ../../
cd ./test-framework/
xcopy * E:\Github\FrameworkLab\repository-send-webapp\WEB-INF\ /E /I /Y
cd E:\Github\FrameworkLab\repository-send-webapp\
jar cf test-framework.war .
move test-framework.war ..
cd ..
del E:\Logiciel\apache-tomcat-10.0.27\webapps\test-framework.war
del E:\Logiciel\apache-tomcat-10.0.27\webapps\test-framework
move test-framework.war E:\Logiciel\apache-tomcat-10.0.27\webapps\
startup.bat