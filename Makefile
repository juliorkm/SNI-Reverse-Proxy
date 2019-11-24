
setup:
	@mvn package

run:
	@mvn exec:java -Dexec.mainClass="com.globo.snireverseproxy.SNIReverseProxy"