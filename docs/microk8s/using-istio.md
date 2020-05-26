# Using Istio

We will use Istio, together with MetalLB and cert-manager to deploy a service with HTTPS. We will use Istio ingress gateway
to expose the service.

## 0) Prequisites

Make sure you have MetalLB load balancer running. See [Using MetalLB Load Balancer](using-metallb-loadbalancer.md).

## 1) Remove the Ingress Controller

If you have the NGINX IngressController installed, delete the ingress-nginx namespace:

```shell script
kubectl delete namespaces ingress-nginx
```

## 2) Download and Install Istio

Go to the directory where you want to install Istio, e.g., `$HOME/Programs`. Then install Istio:

```shell script
curl -L https://istio.io/downloadIstio | sh -
```

This will create an istio-<version> subdirectory with Istio (1.6 a the time of writing).

Now use `istioctl` to install the `demo` profile of Istio:

```shell script
istioctl install --set profile=demo
```

Check that everything is working:

```shell script
kubectl -n istio-system get all
```

## 3) Enable Sidecar Injection on the `default` Namespace

To enable automatic sidecar injection on a namespace just label it with `istio-injection=enabled`:

```shell script
kubectl label namespace default istio-injection=enabled
```

To see the namespaces with the value of the `istio-injection` label:

```shell script
kubectl get namespace -L istio-injection
```

Because sidecars are injected at pod creation time, we need to recreate the `httpbin` pod:

```shell script
kubectl delete pod -l app=httpbin
```

To see that the sidecar is working, use `kubectl get all`. The `httpbin` pod should have 2/2 instead of 1/1 in the
`READY` column:

```
AME                           READY   STATUS    RESTARTS   AGE
pod/dnsutils                   1/1     Running   46         2d19h
pod/httpbin-66cdbdb6c5-4sjjb   2/2     Running   0          37s

NAME                 TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
service/httpbin      ClusterIP   10.152.183.54   <none>        8000/TCP   25h
service/kubernetes   ClusterIP   10.152.183.1    <none>        443/TCP    2d19h

NAME                      READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/httpbin   1/1     1            1           25h

NAME                                 DESIRED   CURRENT   READY   AGE
replicaset.apps/httpbin-66cdbdb6c5   1         1         1       25h
```

You can test that the service works with the sidecar by doing a request:

```shell script
http 10.152.183.54:8000/status/200
```

This should return something like:

```http request
HTTP/1.1 200 OK
access-control-allow-credentials: true
access-control-allow-origin: *
content-length: 0
content-type: text/html; charset=utf-8
date: Tue, 26 May 2020 07:27:41 GMT
server: istio-envoy
x-envoy-decorator-operation: httpbin.default.svc.cluster.local:8000/*
x-envoy-upstream-service-time: 1
```

You can see the `server: istio-envoy` header which shows that the envoy proxy of the sidecar proxied this request.

## 4) Create an Istio Virtual Service

We will create a virtual service and an istio gateway for the `httpbin` service:

```shell script
kubectl apply -f docs/microk8s/examples/httpbin-gateway.yaml
```

It should report:

```
gateway.networking.istio.io/httpbin-gateway created
virtualservice.networking.istio.io/httpbin created
```

Now you can access the service on the load balancer IP using the Istio ingress gateway and virtual service:

```shell script
http httpbin.microk8s/status/200
```

It should report:

```http request
HTTP/1.1 200 OK
access-control-allow-credentials: true
access-control-allow-origin: *
content-length: 0
content-type: text/html; charset=utf-8
date: Tue, 26 May 2020 07:59:08 GMT
server: istio-envoy
x-envoy-upstream-service-time: 6
```
