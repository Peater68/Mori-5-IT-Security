FROM openjdk:11-jdk

VOLUME /tmp
ADD target/*.jar app.jar
ADD src/main/java/com/mori5/itsecurity/cpp /usr/cpp
RUN apt-get update && apt-get install g++ -y && \
	cd /usr/cpp && \
	g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux com_mori5_itsecurity_cpp_CPPParserCaller.cpp -o com_mori5_itsecurity_cpp_CPPParserCaller.o && \
	g++ -shared -fPIC -o libparser.so com_mori5_itsecurity_cpp_CPPParserCaller.o -lc && \
	cp libparser.so /lib
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]