# First we generate a base key pair, with a self signed certificate, to sign all other keys
openssl genrsa -out ../keys/client_base.key
openssl rsa -in ../keys/client_base.key -pubout > ../keys/public.key
openssl req -new -key ../keys/client_base.key -out ../keys/client_base.csr
openssl x509 -req -days 365 -in ../keys/client_base.csr -signkey ../keys/client_base.key -out ../keys/client_base.crt
echo 01 > ../keys/client_base.srl

# Generating key pairs for all clients
for var in client
do
	openssl genrsa -out ../keys/$var\_private_key.key
	openssl rsa -in ../keys/$var\_private_key.key -pubout > ../keys/$var\_public_key.key
	openssl req -new -key ../keys/$var\_private_key.key -out ../keys/$var.csr
	openssl x509 -req -days 365 -in ../keys/$var.csr -CA ../keys/client_base.crt -CAkey ../keys/client_base.key -out ../keys/$var.crt
	openssl rsa -in ../keys/$var\_private_key.key -text > ../keys/$var\_private_key.pem
	openssl pkcs8 -topk8 -inform PEM -outform DER -in ../keys/$var\_private_key.pem -out ../keys/$var\_private_key.der -nocrypt
	openssl rsa -in ../keys/$var\_private_key.pem -pubout -outform DER -out ../keys/$var\_public_key.der
done
