build/flight-kiosk: src/* | include/*
	g++ -o $@ -Iinclude $^ -g