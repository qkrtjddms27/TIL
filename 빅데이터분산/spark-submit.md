## spark-submit

- spark-submit는 클러스터에서 애플리케이션(작업)을 시작을 위해 사용.
- 사용하기 위해서는 jar파일 형태로 만들어야함. (파이썬 제외)

### spark-submit 옵션
```shell
./bin/spark-submit \
  --class <main-class> \
  --master <master-url> \
  --depoly-mode <depoly-mode> \
  --conf <key>=<value> \
  ... # other options
  <application-jar> \
  [application-arguments]
```
- --class: 애플리케이션의 진입점 (entry point)
- --master : cluster의 master URL
- --deploy-mode : Worker 노드에 드라이버를 배포할지(cluster), 외부 클라이언트의 로컬에 배포할지(client) (기본값 : client)
- --conf : spark 설정 property (key = value format), value 에 공백이 포함 된 경우 "key=value" 처럼 따옴표로 묶는다.
- application-jar : 필요 jar 파일들을 포함하여 클러스터에 전달 해준다, 콤마로 URL들을 나열해야 한다.
