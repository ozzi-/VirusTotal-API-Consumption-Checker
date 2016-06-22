# VirusTotal API Consumption Checker
This tool returns the current consumption of the specified VirusTotal API Keys.
![screenshot of tool](http://i.imgur.com/VPDR51j.png)


## Usage:
java -jar virusTotalApiConsumption.jar



## Configuration (put the files into the same directory as the .jar):
### apikeys.txt

Contains all the VirusTotal API Keys

Syntax: 1 key per line



### proxy.txt

If the file exists, and contains a valid config, the set proxy will be used

Syntax: hostname:port 