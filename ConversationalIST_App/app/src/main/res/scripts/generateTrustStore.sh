# Delete existing key stores
rm ../truststores/*.cer
rm ../truststores/*.jks

keytool -genkey -alias client -keyalg RSA -keystore ../truststores/client_TruststoreFile.jks