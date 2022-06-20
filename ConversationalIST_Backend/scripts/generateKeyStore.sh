# Delete existing key stores
rm ../keystores/*.cer
rm ../keystores/*.jks

keytool -genkey -alias server -keyalg RSA -keystore ../keystores/server_KeystoreFile.jks
keytool -export -alias server -file ../keystores/server_Certificate.cer -keystore ../keystores/server_KeystoreFile.jks