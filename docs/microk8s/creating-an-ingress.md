# Using an Ingress on microk8s

## 0) Prerequisites

Make sure you are unning the `httpbin` service. See [Running a Service](running-a-service.md).

## 1) Enable the microk8s `ingress` Plugin

Enable the microk8s ingress plugin:

```shell script
sudo microk8s enable ingress
```

You will get the following output:

```
Enabling Ingress
namespace/ingress created
serviceaccount/nginx-ingress-microk8s-serviceaccount created
clusterrole.rbac.authorization.k8s.io/nginx-ingress-microk8s-clusterrole created
role.rbac.authorization.k8s.io/nginx-ingress-microk8s-role created
clusterrolebinding.rbac.authorization.k8s.io/nginx-ingress-microk8s created
rolebinding.rbac.authorization.k8s.io/nginx-ingress-microk8s created
configmap/nginx-load-balancer-microk8s-conf created
configmap/nginx-ingress-tcp-microk8s-conf created
configmap/nginx-ingress-udp-microk8s-conf created
daemonset.apps/nginx-ingress-microk8s-controller created
Ingress is enabled
```

Check that it is running:

```shell script
kubectl -n ingress get all
```

## 2) Create the Ingress

Next create the ingress:

```shell script
kubectl apply -f examples/httpbin-ingress.yaml
```

This should report the following:

```
ingress.extensions/httpbin-ingress configured
```

## Appendix: Troubleshooting

If `kubectl -n ingress get all` gives output similar to this, you can check to the logs to see what's going on.

```
NAME                                          READY   STATUS             RESTARTS   AGE
pod/nginx-ingress-microk8s-controller-srs8q   0/1     CrashLoopBackOff   8          18m

NAME                                               DESIRED   CURRENT   READY   UP-TO-DATE   AVAILABLE   NODE SELECTOR   AGE
daemonset.apps/nginx-ingress-microk8s-controller   1         1         0       1            0           <none>          18m
```

To check the logs:

```shell script
kubectl -n ingress logs nginx-ingress-microk8s-controller-srs8q
```

Potentially the port is already being used:

```
-------------------------------------------------------------------------------
NGINX Ingress controller
  Release:       0.25.1
  Build:         git-5179893a9
  Repository:    https://github.com/kubernetes/ingress-nginx/
  nginx version: openresty/1.15.8.1

-------------------------------------------------------------------------------

F0525 08:41:53.928635       6 main.go:63] port 80 is already in use. Please check the flag --http-port
```

In this case disable whatever is conflicting. Another option is to use the `metallb` load balancer, which we will
investigate in another document.
