# Running the `httpbin` Service

To deploy `httpbin` in the default namespace:

```shell script
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.6/samples/httpbin/httpbin.yaml
```

This should return:
```
serviceaccount/httpbin created
service/httpbin created
deployment.apps/httpbin created
```

After this check what cluster IP the service is on:

```shell script
kubectl get all
```

This should return something like:

```
NAME                           READY   STATUS    RESTARTS   AGE
pod/dnsutils                   1/1     Running   21         42h
pod/httpbin-66cdbdb6c5-gmxfq   1/1     Running   0          56s

NAME                 TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
service/httpbin      ClusterIP   10.152.183.54   <none>        8000/TCP   56s
service/kubernetes   ClusterIP   10.152.183.1    <none>        443/TCP    42h

NAME                      READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/httpbin   1/1     1            1           56s

NAME                                 DESIRED   CURRENT   READY   AGE
replicaset.apps/httpbin-66cdbdb6c5   1         1         1       56s
```

Now it should be possible to access the service. Using httpie:

```shell script
http 10.152.183.54:8000/status/200
```

This should return a 200 response:

```http request
HTTP/1.1 200 OK
Access-Control-Allow-Credentials: true
Access-Control-Allow-Origin: *
Connection: keep-alive
Content-Length: 0
Content-Type: text/html; charset=utf-8
Date: Mon, 25 May 2020 06:24:29 GMT
Server: gunicorn/19.9.0
```
