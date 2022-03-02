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