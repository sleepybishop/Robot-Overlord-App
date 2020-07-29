
all: target/Robot-Overlord-1.6.2-with-dependencies.jar
	java \
		-Djava.library.path=. \
		-Djogl.disable.openglarbcontext=1 \
		-jar target/Robot-Overlord-1.6.2-with-dependencies.jar


target/classes/com/marginallyclever/robotOverlord/RobotOverlord.class:
	mvn compile

target/Robot-Overlord-1.6.2-with-dependencies.jar: target/classes/com/marginallyclever/robotOverlord/RobotOverlord.class
	mvn package

