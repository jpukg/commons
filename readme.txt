In order to run this project you need to add below properties in your settings.xml file.
change them to appropriate value :)

<commons.oracle.url>jdbc:oracle:thin:@localhost:1521:XE</commons.oracle.url>
<commons.oracle.username>commons</commons.oracle.username>
<commons.oracle.password>commons</commons.oracle.password>

You need to create commons user DBMS :
create user commons identified by commons;

commons user needs resource and connect privileges:
grant resource,connect to commons;