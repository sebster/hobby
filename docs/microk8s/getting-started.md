# Running microk8s on Ubuntu 20.04

Set up a basic microk8s install with the `dns` plugin enabled and the firewall on the Ubuntu machine working correctly.

## 1) Disable firewalld

```shell script
sudo systemctl stop firewalld.service
sudo systemctl disable firewalld.service
```

## 2) Install the Snap

Install the microk8s snap:

```shell script
sudo snap install microk8s --classic
```

## 3) Optional: Install and Configure `kubectl`

```shell script
sudo snap install kubectl
```

Get the config from your microk8s cluster:

```shell script
sudo microk8s config
```

Merge the config with your .kube/config:

```shell script
vi .kube/config
```

Test that it works:

```shell script
kubectl config use-context microk8s
kubectl get all
```

This should show output similar to this:

```
NAME                 TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.152.183.1   <none>        443/TCP   5m4s
```

## 4) Configure Uncomplicated Firewall

Install UFW if necessary:

```shell script
sudo apt-get install ufw
```

Set the default forward policy to accept in `/etc/default/ufw`:

```
DEFAULT_FORWARD_POLICY="ACCEPT"
```

Enable ip forwarding in `/etc/ufw/sysctl.conf`:

```
net.ipv4.ip_forward=1
```

Enable NAT on your external interface for the microk8s networks in `/etc/ufw/before.rules`.
Add the following to the end, after the COMMIT. Note: you will have COMMIT twice in the file
after this edit.

```
# NAT table rules
*nat
:POSTROUTING ACCEPT [0:0]

# Nat outgoing traffic. Change eth0 to your outbound interface.
-A POSTROUTING -s 10.0.0.0/16 -o eth0 -j MASQUERADE

# don't delete the 'COMMIT' line or these nat table rules won't
# be processed
COMMIT
```

Now enable ufw:

```shell script
sudo ufw enable
```

Next pass all pod traffic:

```shell script
sudo ufw allow in on cni0
sudo ufw allow out on cni0
```

Check your UFW status:

```shell script
ufw status
```

## 5) Configuring DNS

First enable the DNS plugin in microk8s:

```shell script
sudo microk8s enable dns
```

Update the upstream DNS servers if necessary:

```shell script
kubectl -n kube-system edit configmaps coredns
```

Install the dnsutils pod to test your DNS configuration:

```shell script
kubectl apply -f https://k8s.io/examples/admin/dns/dnsutils.yaml
```

Test your network connectivity:

```shell script
kubectl exec -ti dnsutils -- ping google.com.
```

Test your DNS lookups:

```shell script
kubectl exec -ti dnsutils -- dig google.com
```

## 6) Reboot

Reboot your machine and check that everything comes up as expected.

-   Check your UFW status: `sudo ufw status`
-   Check your dnsutils pod came back up: `kubectl get all`
-   Check your network connectivity: `kubectl exec -ti dnsutils -- ping google.com.`
-   Check your dns: `kubectl exec -ti dnsutils -- dig google.com`

## 7) Optional: Clean Up

You can delete the `dnsutils` pod from the default namespace:

```shell script
kubectl delete pod dnsutils
```