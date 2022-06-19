# Delete existing key stores
rm ../truststores/*.cer
rm ../truststores/*.jks

keytool -genkey -alias server -keyalg RSA -keystore ../truststores/server_TruststoreFile.jks
keytool -export -alias server -file ../truststores/server_Certificate.cer -keystore ../truststores/server_TruststoreFile.jks