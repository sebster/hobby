# Creating an Ingress with HTTPS

We will create a secure ingress (HTTPS). The certificate management will be done with `cert-manager`. In this example we will
be using our own CA to issue certificates.

## 0) Prerequisites

Make sure you have the `httpbin` service installed in the default namespace. See [Running a Service](running-a-service.md).

If you don't have a CA key pair, create one first. See [Creating a CA key pair with EasyRSA](creating-a-ca-keypair-with-easyrsa.md).

## 1) Install cert-manager

Install cert-manager:

```shell script
kubectl apply -f https://github.com/jetstack/cert-manager/releases/download/v0.15.0/cert-manager.yaml
```

Check that it's running:

```shell script
kubectl -n cert-manager get pods --watch
```

## 2) Create a CA Secret

We will create a secret containing the CA signing key and certificate. We will install the the secret in the
`cert-manager` namespace, and use a `ClusterIssuer` resource to issue certificates. This will allow for one issuer for
the entire cluster. If you want issuers per namespace, you need to install a CA signing key and certificate per namespace
and install an `Issuer` resource per namespace instead of a single `ClusterIssuer` resource.

Adapt the key pair properties in the `ca-keypair.yaml` file with your `ca.key` and `ca.crt`
files. It should contain the base64 encoded contents of the PEM files:

```shell script
echo `base64 -w0 ca.crt`
echo `base64 -w0 ca.key`
```

After updating the `ca-keypair.yaml` file, you can import the keypair like this:

```shell script
kubectl apply -f docs/microk8s/examples/ca-keypair.yaml
```

It should report:

```
secret/ca-keypair created
```

## 3) Create the ClusterIssuer Resource

Next we create the `ClusterIssuer` resource:

```shell script
kubectl apply -f docs/microk8s/examples/ca-issuer.yaml
```

It should report:

```
clusterissuer.cert-manager.io/ca-issuer created
```

To check that the cluster issuer is working as expected run:

```shell script
kubectl get clusterissuers.cert-manager.io  -o wide
```

It should report:

```
NAME        READY   STATUS                AGE
ca-issuer   True    Signing CA verified   116s
```

## 4) Create a Certificate Resource

In cert-manager, the Certificate resource represents a human readable definition of a
certificate request that is to be honored by an issuer which is to be kept up-to-date.

To create a certificate resource:

```shell script
kubectl apply -f docs/microk8s/examples/ingress-certificate.yaml
```

It should report:

```
certificate.cert-manager.io/ingress-certificate created
```

To check if the certificate was generated issue:

```shell script
kubectl describe certificate ingress-certificate
```

You should see something like this:

```
...
Status:
  Conditions:
    Last Transition Time:  2020-05-25T12:58:24Z
    Message:               Certificate is up to date and has not expired
    Reason:                Ready
    Status:                True
    Type:                  Ready
  Not After:               2020-05-26T12:58:24Z
Events:
  Type    Reason        Age   From          Message
  ----    ------        ----  ----          -------
  Normal  GeneratedKey  67s   cert-manager  Generated a new private key
  Normal  Requested     67s   cert-manager  Created new CertificateRequest resource "ingress-certificate-2636079526"
  Normal  Issued        67s   cert-manager  Certificate issued successfully
```

## 5) Create the Ingress

Finally we want to create the ingress:

```shell script
kubectl apply -f examples/httpbin-ingress-secure.yaml
```

This should report:

```
ingress.extensions/httpbin-ingress configured
```

Map the `httpbin.microk8s` to your external host IP (or to 127.0.0.1) in `/etc/hosts`. You can now test that it works with httpie:

```shell script
http --verify ca.crt https://httpbin.microk8s/status/200
```

Here ca.crt is the path to the CA certificate file.

You should get something like this:

```http request
HTTP/1.1 200 OK
Access-Control-Allow-Credentials: true
Access-Control-Allow-Origin: *
Connection: keep-alive
Content-Length: 0
Content-Type: text/html; charset=utf-8
Date: Mon, 25 May 2020 14:42:22 GMT
Server: openresty/1.15.8.1
Strict-Transport-Security: max-age=15724800; includeSubDomains
```

If you want you can import the CA certificate (ca.crt) into your browser, which will then accept the any certificates issued by
the cluster.
