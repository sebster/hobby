# Using Istio with HTTPS

## 0) Prerequisites

Make sure you have Istio running. See [Using Istio](using-istio.md).

Also make sure you have `cert-manager` running, and have created a CA key pair and cluster issuer for the `httpbin`
service. See [Using an Ingress with HTTPS](using-an-ingress-with-https.md). Don't enable the NGINX Ingress Controller.

## 1) Create a Certificate for the Ingress Gateway

```shell script
kubectl apply -f docs/microk8s/examples/httpbin-gateway-certificate.yaml
```

## 2) Add HTTPS to the `httpbin` Gateway

```shell script
kubectl apply -f docs/microk8s/examples/httpbin-gateway-https.yaml
```

This should report:

```
gateway.networking.istio.io/httpbin-gateway configured
virtualservice.networking.istio.io/httpbin unchanged
```

Now you can test that the service works:
