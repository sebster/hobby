# Using Istio with Strict mTLS

## 0) Prerequisites

Make sure that you have Istio set up and working with the `httpbin` service. See [Using Istio](using-istio.md) or
[Using Istio with HTTPS](using-istio-with-https.md).

## 1) Apply the Strict mTLS

To apply a strict mTLS policy:

```shell script
kubectl apply -f docs/microk8s/examples/istio-strict-mtls.yaml
```