## 하둡

### HDFS
- File은 block 단위로 분할.
- block은 64MB, 128MB 크기
- 180MB는 64MB, 64MB, 64MB 3개의 블럭으로 나눠 들어가게 됨.
- Block들은 여러 machine에 복제되어 Data node에 저장.
- Name Node로 불리는 master node는 어떤 block들이 file로 구성하고 있고, 어느 위치에 저장되어 있는지 meta data로 관리

#### HDFS에서 파일을 읽는 과정
1. Client가 namenode에게 파일을 요청.
2. Namenode는 파일이 있는 Datanode 위치를 Client에게 알림.
3. Client가 Datanode와 직접 통신하면서 데이터를 전송.

#### HDFS 특징
- 일부 데이터 노드가 장애를 일으키면 복제본을 사용하여 유지
- 파일의 생성, 삭제, append 연산만 가능
- 대용량의 파일을 블록단위로 분산하여 저자하기 때문에 물리적 한계가 없다.
- metadata는 메모리에서 관리되므로 전체 파일 개수 등의 metadata 자원은 Namenode의 메모리의 크기에 제한을 받는다.

### 맵리듀스 프레임워크(MapReduce)
- 여러 대의 머신에 분산하여 저장.
- 각 파일을 여러 개의 순차적인 블록으로 분할하여 저장.
- Falut Tolerance 를 위해서 여러 개로 복사되어 중복 저장.
- 레코드, 튜플 - (KEY, VALUE)

### 맵 리듀스 페이즈(MapReduce Phase)
- 맵-페이즈
- 셔플링-페이즈
- 리듀스-페이즈

#### 맵-페이즈
- 분산 호출되어 수행된 후 맵 함수에서 출력한 결과들이 (KEY-VALUE)로 표현
- 제일 먼저 사용되는 함수
- 병렬 분산으로 호출
- (KEY, VALUE)쌍 형태로 결과를 출력
- 같은 KEY를 가진 (KEY, VALUE) 쌍은 같은 머신으로 보내짐.
#### 셔플링-페이즈
- (KEY, VALUE) 쌍을 정렬 후 (KEY, VALUE-LIST)의 형태로 만듬
- 맵-페이즈가 끝나면 시작
#### 리듀스-페이즈
- 셔플링-페이즈가 끝나면 시작.
- VALUE-LIST에 있는 값들을 다 합한 값을 VALUE로 만듬. (압축)

인버티드 인덱스
각 단어 마다 그 단어가 나타나는 문서 아이디 doc와 그 문서에서 나오는 위치를 doc:position 형태로 만듬

```
Doc1 : IMF, Finacial economics Crisis
Doc2 : IMF, Finacial Crisis
Doc3 : Harry Economics

IMF -> Doc1:1, Doc2:1
Potter -> Doc4:17, Doc5:7
```
## Partitioner Class
- Map 함수의 출력인 (KEY, VALUE) 쌍이 KEY에 의해서 어느 Reducer로 보내질 것인지를 정해지는 이러한 결정을 정의하는 class.
- 하둡의 기본 타입은 Hash 함수가 Default로 제공되고 있어서 KEY에 대한 해시 값에 따라 어느 Reducer으로 보낼지를 결정.


### 하둡의 기본 타입
- Text
- IntWritable
- LongWritable
- FloatWritable
- DoubleWritable

### Hadoop HA
Namenode가 정상적으로 동작하지 않을 경우 모든 클라이언트가 HDFS에 접근할 수 없다. 이러한 문제점을 보완하기 위해 네임노드의 이중화 필요성 대두, 체크포인트 주기 조절, edit log 수시 백업.

- Namenode HA 네임 노드의 이중화 shared edits : edit log 를 여러 서버에 복제 저장. journalnode는 최소 3개 이상, 홀수.
- ZKFC(zookeeper failover controller) : Namenode 상태 모니터링, active Namenode에 장애 발생시 standby Namenode를 active Namenode로 전환
- HDFS federation : 기존의 HDFS가 단일 Namenode에 기능이 집중되는 것을 막기 위해 제안된 시스템.
- HDFS snapshot : 스냅샷을 이용해 언제든지 복원 가능한 시점으로 HDFS를 복원할 수 있다.

----

## Hadoop EcoSystem

### Zookeeper

분산 환경에서 서버들간에 상호 조정이 필요한 다양한 서비스를 제공하는 시스템.
1. 하나의 서버에만 서비스가 집중되지 않도록 서비스를 알맞게 분산하여 동시에 처리하게 해줌
2. 하나의 서버에서 처리한 결과를 다른 서버드로가도 동기화 -> 데이터 안전성 보장
3. 운영 서버에서 문제가 발생해 서비스를 제공할 수 없는 경우, 다른 대기중인 서버를 운영 서버로 바꾸 서비스가 중지없이 제공되게 해줌
4. 분산 환경을 구성하는 서버들의 환경설정을 통합적으로 관리.

### Ooozie

- 하둡의 작업을 관리하는 워크플로우 및 코디네이터 시스템
- 자바 서블릿 컨테이너에서 실행되는 자바 웹어플리케이션 서버로, MapReduce 작업이나 Pig 작업 같은 특화된 액션들로 구성된 워크플로우를 제어함.

### HBase

- HDFS의 칼럼 기반 데이터베이스
- 구글의 BigTable 논문을 기반으로 개발도니 것으로, 실시간 랜덤 조회 및 업데이트가 가능하며, 각각의 프로세스들은 개인의 데이터를 비동기적으로 업데이트 할 수 있다.

### Pig
- 복잡한 MapReduce 프로그래밍을 대체할 Pig Latin이라는 자체 언어를 제공.
- MapReduce API를 매우 단수화시키고 SQL과 유사한 형태로 설계됨.

### Hive
- 자바를 잘 모르는 데이터 분석가들도 쉽게 하둡 데이터를 분석할 수 있게 도와주는 HiveQL 쿼리 제공.
- HiveQL은 내부적으로 MapReduce 잡으로 변환되어 실행.

### Mahout
- 하둡 기반 데이터 마이닝 알고리즘을 구현한 오픈소스
    + **데이터 마이닝** : 대구모로 저장된 데이터 안에서 체계적의고 자동적으로 통계적 규칙이나 패턴을 분석하여 가치있는 정보를 추철하는 과정.
- 현재 분류, 클러스터링, 추천 및 협업 필터링, 패턴 마이닝, 회귀 분석, 차원 리덕션, 진화 알고리즘 등을 지원.

### HCatalog
- 하둡으로 생성한 데이터를 위한 테이블 및 스토리지 관리 서비스
- HCatalog의 가장 큰 장점은 하둡 에코 시스템들간의 상호 운용성 향상
- Hive에서 생성한 테이블이나 데이터 모델을 Pig나 MapReduce에서 손쉽게 이용가능.

### Chukwa
- 분산 환경에서 생성되는 데이터를 HDFS에 안정적으로 저장시키는 플랫폼
- 분산된 각 서버에서 에이전트를 실행하고, 콜렉터가 에이전트로부터 데이터를 받아 HDFS에 저장.
- 콜렉터는 100개의 에이전트당 하나씩 구동, 데이터 중복 제거 등의 작업은 MapReduce로 처리

### Flume
- Chukwa 처럼 분산된 서버에 에이전트가 설치되고, 에이전트로부터 데이터를 전달받는 콜렉터로 수정
- 전체 데이터의 흐름을 관리하는 마스터 서버가 있어서, 데이터를 어디서 수집하고, 어떤 방식으로 전송, 어디에 저장할 지를 동적으로 변경 가능.

### Scribe
- 페이스북에서 개발한 데이터 수집 플랫폼이며, Chukwa와는 다르게 데이터를 중앙 집중 서버로 전송하는 방식이다.
- HDFS에 저장하기 위해서는 JNI를 이용해야 한다.

### Sqoop
- **대용량 데이터 전송 솔루션**
- HDFS, RDBMS, DW, NoSQL등 다양한 저장소에 대용량 데이터를 신속하게 전송할 수 있는 방법을 제공.

### Hiho
- **대용량 데이터 전송 솔루션**
- 하둡에서 데이터를 가져오기 위한 SQL을 지정할 수 있으며, JDBCff 인터페이스를 지원

### Impala
- 하둡 기반의 실시간 SQL 질의 시스템
- 맵리듀스를 사용하지 않고, 자체 개발한 엔진을 사용해 빠른 성능을 보여준다.
