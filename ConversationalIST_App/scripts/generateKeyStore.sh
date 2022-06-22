# Delete existing key stores
rm ../keystores/*.cer
rm ../keystores/*.jks

keytool -genkey -alias client -keyalg RSA -keystore ../keystores/client_KeystoreFile.jks
keytool -export -alias client -file ../keystores/client_Certificate.cer -keystore ../keystores/client_KeystoreFile.jks