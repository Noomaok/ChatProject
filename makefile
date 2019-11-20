all: cs gui

cs:
	javac -d classes src/server/EchoServerMultiThreaded.java src/server/ClientThread.java src/server/Sender.java

gui:
	javac -d classes src/chat/GUIClient.java src/chat/ChatClient.java