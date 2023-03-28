cd framework/classes
jar cf fw.jar .
move fw.jar ../../test-framework/WEB-INF/lib 
cd ../../
cd ./test-framework/
jar cf test-framework.war .
move test-framework.war ..
cd ..
del E:\Logiciel\apache-tomcat-10.0.27\webapps\test-framework.war
del E:\Logiciel\apache-tomcat-10.0.27\webapps\test-framework
move test-framework.war E:\Logiciel\apache-tomcat-10.0.27\webapps\
startup.bat