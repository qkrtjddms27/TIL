# kubectl
> 쿠버네티스 클러스터를 관리하는 동작 대부분은 kubctl이라는 커맨드라인 인터페이스로 실행할 수 있다. kubctl에서 지원하는 명령은 다음처럼 구분할 수 있다.

- 쿠버네티스 자원드르이 생성, 업데이트, 삭제.(create, update, delete)
- 디버그, 모니터링, 트러블 슈팅(log, exec, top, attach, ...)
- 클러스터 관리(cordon, top, drain, taint ...)

---
## 설치
- Kubespray, Kubeadm 등은 마스터 노드에 kubectl이 설치되어 있다.
- 마스터 노드에 직접 접근해 클러스트 관리자 권한으로 kubectl 고나련 명령들을 사용할 수 있다.
-  이 방식으로는 여러 사용자의 권한을 제어할 수 없다는 문제가 있다.
  + 클러스터 사용자 각각은 클러스터 외부에 kubectl을 설치하고 인증 정보를 설정해야 한다.
- **도커 데스크톱에는 이미 kubectl이 설치되어 있음.**

## 기본 사용법
``` 
kubectl [command] [TYPE] [NAME] [flags]
```

- command : 자원에 실행하려는 동작,  create, get, delete 등
- TYPE : 자원 타입, pod, service, ingress 등
- NAME : 자원 이름.
- FLAG : 부가적으로 설정할 옵션을 선택.

--- 

```
$ kubectl get pods
```

- NAME : 파드 이름.
- READY : 숫자/숫자 형태로 파드의 준비 상태를 표시
  + 0/1이면 현재 파드는 생성되었으나 사용할 준비가 되지 않았다는 뜻.
  + 1/1이면 파드가 생성되었고 사용할 준비가 끝났다는 뜻.
- STATUS : 현재의 파드 상태를 나타냄.
  + Running : 파드가 실행되었다는 뜻.
  + Terminating : 컨테이너 접속 중.
  + ContainerCreating : 컨테이너 생성 중
- RESTARTS : 해당 파드가 몇 번 재시작했는지를 표시.
- AGE : 파드를 생성한 후 얼마나 시간이 지났는지 나타냄.

---
```
$ kubectl get servies
```
- NAME : 서비스의 이름을 표시
  + echoserver라는 이름의 서비스가 있는 것을 확인할 수 있습니다.
- TYPE : 서비스 타입을 뜨샇빈다.
- CLUSTER-IP : 현재 클러스터 안에서 사용되는 IP
- EXTERNAL-IP : 클러스터 외부에서 접속할 떄 사용하는 IP로 현재는 별도로 설정하지 않았으므로 <none>입니다.
- PORT(S) : 해당 서비스에 접속하는 포트
- AGE : 자원을 생성한 후 얼마나 시간이 지났는지 나타냄.

---

```
$ kubectl port-forward svc/echoserver 8080:8080 
```
- 에코 서버에 접근할 수 있도록 로컬 컴퓨터로 포트포워딩하는 명령어.

---

```
$ kubectl delete pod echoserver
$ kubectl delete service echoserver
```

## kubeconfig 환경 변수
- $HOME/.kube/config 파일에서 클러스터, 인증, 컨텍스트 정보를 읽어 들임
- kubectl -kubeconfig=[설정 파일 이름] get pods
  + kubectl -kubeconfig=AWSconfig get pods
  + kubectl -kubeconfig=GCPconfig get pods

### 클라우드 서비스의 쿠버넽니스 도구용 kubeconfig 설정
- 클라우드 서비스 각각은 kubeconfig를 설정하는 도구들을 제공. 클라우드 서비스별 kubeconfig 다르다.
- 각각 문서를 찾아봐야한다.

### 자동완성
- 배시, Z 셀에서의 자동 완성을 공식적으로 지원.
- $kubectl completion --help 명령을 실행하면 자동 완성 기능을 설정하는 방법을소개하고 있다.