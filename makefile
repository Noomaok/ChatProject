all: cc cs

cs:
	javac -d classes src/server/EchoServerMultiThreaded.java src/server/ClientThread.java src/server/Sender.java

cc: 
	javac -d classes src/chat/ChatClient.java