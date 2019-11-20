all: cs gui gui_multi

cs:
	javac -d classes src/server/EchoServerMultiThreaded.java src/server/ClientThread.java src/server/Sender.java

gui:
	javac -d classes src/chat/GUIClient.java src/chat/ChatClient.java

gui_multi:
	javac -d classes src/chat_multicast/GUIClient.java src/chat_multicast/ChatClient.java