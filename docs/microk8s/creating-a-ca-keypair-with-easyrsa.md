# Create a CA Key Pair with EasyRSA

## 1) Install EasyRSA

```shell script
sudo apt install easy-rsa
```

## 2) Create an PKI directory

Make a CA directory:

```shell script
make-cadir my-ca
cd my-ca
```

Edit the `vars` file to set the defaults if you don't want to have to type them every time.

Next init the pki dir:

```shell script
./easyrsa init-pki
```

The output will be:

```
Note: using Easy-RSA configuration from: ./vars

init-pki complete; you may now create a CA or requests.
Your newly created PKI dir is: /home/sebster/Temp/easyrsa/test/pki
```

Then build the CA:

```shell script
./easyrsa build-ca nopass
```

We will use nopass so the CA key is not encrypted.

The output will be something like this:

```
Note: using Easy-RSA configuration from: ./vars

Using SSL: openssl OpenSSL 1.1.1f  31 Mar 2020
Generating RSA private key, 2048 bit long modulus (2 primes)
.............................+++++
..+++++
e is 65537 (0x010001)
You are about to be asked to enter information that will be incorporated
into your certificate request.
What you are about to enter is what is called a Distinguished Name or a DN.
There are quite a few fields but you can leave some blank
For some fields there will be a default value,
If you enter '.', the field will be left blank.
-----
Common Name (eg: your user, host, or server name) [Easy-RSA CA]:

CA creation complete and you may now import and sign cert requests.
Your new CA certificate file for publishing is at:
<HOME>/my-ca/pki/ca.crt
```
