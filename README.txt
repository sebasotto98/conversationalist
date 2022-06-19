after changes on the proto files and before running always do:
	- ensure proto files consistent in both modules
	- maven clean compile for the backend
	- gradle clean build for the app

when DB changes increment VERSION variable

!!! if nothing working do invalidate caches restart !!!

SCRIPT EXECUTION ORDER:

---------------------IN SERVER-----------------------
1) generateKeyStore.sh
2) generateTrustStore.sh
---------------------IN CLIENT-----------------------
3) generateKeyStore.sh
4) generateTrustStore.sh
---------------------IN SERVER-----------------------
5) ADD CLIENT CERT TO SERVER TRUSTSTORE FILE MANUALLY
6) addCertToTrustStore.sh
---------------------IN CLIENT-----------------------
7) ADD SERVER CERT TO CLIENT TRUSTSTORE FILE MANUALLY
8) addCertToTrustStore.sh