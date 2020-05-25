# Using MetalLB Load Balancer

We will use MetaLB load balancer with an nginx ingress controller.

The "external" IP range will be local to the host, as this is a purely "development" setup. It will not be reachable from
the outside world.

## 0) Prerequisites

Make sure you have the `httpbin` service installed in the default namespace. See [Running a Service](running-a-service.md).

## 1) Enable the MetaLB Plugin

```shell script
sudo microk8s enable metallb
```

You need to enter an address range for the load balancer to use. The output should be like this:

```
Enabling MetalLB
Enter the IP address range (e.g., 10.64.140.43-10.64.140.49): 10.64.10.64.140.100-10.64.140.200
Applying registry manifest
namespace/metallb-system created
podsecuritypolicy.policy/speaker created
serviceaccount/controller created
serviceaccount/speaker created
clusterrole.rbac.authorization.k8s.io/metallb-system:controller created
clusterrole.rbac.authorization.k8s.io/metallb-system:speaker created
role.rbac.authorization.k8s.io/config-watcher created
clusterrolebinding.rbac.authorization.k8s.io/metallb-system:controller created
clusterrolebinding.rbac.authorization.k8s.io/metallb-system:speaker created
rolebinding.rbac.authorization.k8s.io/config-watcher created
daemonset.apps/speaker created
deployment.apps/controller created
configmap/config created
MetalLB is enabled
```

You can check that everything went well using:

```shell script
kubectl -n metallb-system get all
```

## 2) Disable the Ingress Plugin

If the nginx ingress controller is installed using the microk8s plugin, it will use the host network IP and a `DaemonSets.apps`
resource instead of a deployment. This is not compatible with the load balancer. We will disable the `ingress` plugin and
install it manually, after which we will reconfigure it to use the load balancer. 

First disable the `ingress` plugin:

```shell script
sudo microk8s disable ingress 
```

## 3) Install the `nginx` Ingress Controller

Install the ingress controller as follows:

```shell script
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-0.32.0/deploy/static/provider/baremetal/deploy.yaml
```

Now we the ingress service and deployments should be runnning:

```shell script
kubectl -n ingress-nginx get all
```

This will look like this:
```
NAME                                            READY   STATUS      RESTARTS   AGE
pod/ingress-nginx-admission-create-9kgkc        0/1     Completed   0          40m
pod/ingress-nginx-admission-patch-6b6bj         0/1     Completed   1          40m
pod/ingress-nginx-controller-69fb496d7d-fftjp   1/1     Running     0          40m

NAME                                         TYPE           CLUSTER-IP       EXTERNAL-IP     PORT(S)                      AGE
service/ingress-nginx-controller             LoadBalancer   10.152.183.234   <none>          80:32104/TCP,443:30934/TCP   40m
service/ingress-nginx-controller-admission   ClusterIP      10.152.183.134   <none>          443/TCP                      40m

NAME                                       READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/ingress-nginx-controller   1/1     1            1           40m

NAME                                                  DESIRED   CURRENT   READY   AGE
replicaset.apps/ingress-nginx-controller-69fb496d7d   1         1         1       40m

NAME                                       COMPLETIONS   DURATION   AGE
job.batch/ingress-nginx-admission-create   1/1           15s        40m
job.batch/ingress-nginx-admission-patch    1/1           16s        40m
```

Note that the ingress controller service does not yet have an external IP. To give it an external IP we need to change
the service type to LoadBalancer instead of NodePort:

```shell script
kubectl -n ingress-nginx edit service ingress-nginx-controller
```

Change the line `type: NodePort` to `type: LoadBalancer`.

After applying, use `kubectl -n ingress-nginx get all` again to see the new external IP:

```shell script
NAME                                            READY   STATUS      RESTARTS   AGE
pod/ingress-nginx-admission-create-9kgkc        0/1     Completed   0          43m
pod/ingress-nginx-admission-patch-6b6bj         0/1     Completed   1          43m
pod/ingress-nginx-controller-69fb496d7d-fftjp   1/1     Running     0          43m

NAME                                         TYPE           CLUSTER-IP       EXTERNAL-IP     PORT(S)                      AGE
service/ingress-nginx-controller             LoadBalancer   10.152.183.234   10.64.140.100   80:32104/TCP,443:30934/TCP   43m
service/ingress-nginx-controller-admission   ClusterIP      10.152.183.134   <none>          443/TCP                      43m

NAME                                       READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/ingress-nginx-controller   1/1     1            1           43m

NAME                                                  DESIRED   CURRENT   READY   AGE
replicaset.apps/ingress-nginx-controller-69fb496d7d   1         1         1       43m

NAME                                       COMPLETIONS   DURATION   AGE
job.batch/ingress-nginx-admission-create   1/1           15s        43m
job.batch/ingress-nginx-admission-patch    1/1           16s        43m
```

## 4) Enable the `httpbin` Ingress

You can add the external IP of the ingress controller to the `/etc/hosts` file with the correct host (`httpbin.microk8s`).
Next deploy the `httpbin` ingress resource:

```shell script
kubectl apply -f docs/microk8s/examples/httpbin-ingress.yaml
```

After this you should be able to verify that the load balancer works:

```shell script
http http://httpbin.microk8s/status/200
```

This should return:
```http request
HTTP/1.1 200 OK
Access-Control-Allow-Credentials: true
Access-Control-Allow-Origin: *
Connection: keep-alive
Content-Length: 0
Content-Type: text/html; charset=utf-8
Date: Mon, 25 May 2020 20:04:32 GMT
Server: nginx/1.17.10
```

You can also run a secure ingress with https. See [Creating an Ingress with HTTPS](creating-an-ingress-with-https.md).