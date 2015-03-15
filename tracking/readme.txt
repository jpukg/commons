for building this project you need to add these properties in your settings.xml

<tracking.oracle.url> ...</tracking.oracle.url>
<tracking.oracle.username>track</tracking.oracle.username>
<tracking.oracle.password>track</tracking.oracle.password>

You need to create track user DBMS :
create user track identified by track;

commons user needs resource and connect privileges:
grant resource,connect to track;
