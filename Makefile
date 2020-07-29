VERSION=$(shell mvn help:evaluate -Dexpression=project.version -q -DforceStdout )

all: target/Robot-Overlord-$(VERSION)-with-dependencies.jar
	java \
		-Djava.library.path=. \
		-Djogl.disable.openglarbcontext=1 \
		-jar target/Robot-Overlord-$(VERSION)-with-dependencies.jar


target/classes/com/marginallyclever/robotOverlord/RobotOverlord.class:
	mvn compile

target/Robot-Overlord-$(VERSION)-with-dependencies.jar: target/classes/com/marginallyclever/robotOverlord/RobotOverlord.class
	mvn package

