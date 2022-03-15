## Apache Spark

- 빅데이터를 위한 통합 컴퓨팅 엔진과 라이브러리 집합.
- 빅데이터 애플리케이션 개발에 필요한 통합 플랫폼 제공.
- 데이터 읽기, SQL 처리, 머신러닝 그리고 스트림 처리, 데이터 분석 작업 연산을 일관성 있는 API 로 수행할 수 있다.
- 컴퓨팅 엔진으로만 사용.
    + 시스템의 데이터를 연산하는 역할만 수행.
    + 영구 저장소 역할은 하지 않음.
    + 하둡, 카산드라, 카프카, 아마존 S3, 애저 스토리지 등등의 저장소 지원.
- 코어 API 뿐만 아니라 오픈소스 프로젝트의 집합체.

#### Spark 의 자리.

Kafka, Flume, HDFS, Kinesis, Twitter -> Spark Streaming -> HDFS, Databases, Dashboards

카프카, 플럼, 키네시스, TCP 소켓 등 다양한 경로를 통해서 데이터를 입력 받고, map, reduce, window 등의 연산을 통해 데이터를 분석하여
최종적으로 파일시스템, 데이터베이스 등에 적재.

### 등장배경

- 2005년 까지 빠르게 성장하던 하드웨어 스펙(프로세서의 속도)이 점점 늦춰졌다.
- 클러스터 컴퓨팅이 엄청난 잠재력을 가지고 있었다.
- 기존의 맵리듀스 엔진을 사용하는 대규모 애플리케이션의 난이도와 효율성 문제.
- 배치 애플리케이션만 지원.
- 함수형 연산 관점에서 API를 정의.
- 스파크 생태계의 여러 신규 프로젝트는 스파크의 영역을 꾸준히 넓혀나가고 있다.
- HFDS DISK I/O를 기반으로 동작.
  + 실시간성 데이터베이스에 대한 니즈 충족이 불가능.
  + H/W들의 가격이 인하(메모리).
  + 인메모리상에서 동작.
  + 하둡 + 스파크 연계 형태로 자리 잡음.
  + 하둡의 YARN 위에 스파크를 얹고, 실시간성이 필요한 데이터는 스파크로 처리.

### 구조

- 스파크는 Driver 프로세스와 익스큐터 프로세스로 구성.
- 1개의 Spark Driver와 N개의 Executor가 존재.
- Executor는 Cluster Manager에 의하여 해당 스파크 어플리케이션에 할당.
- 해당 스파크 어플리케이션이 완전히 종료된 후 할당에서 해방.
- 서로 다른 스파크 어플리케이션 간의 직접적인 데이터 공유는 불가능.

#### Driver
- 한 개의 노드에서 실행되며, 스파크 전체의 main() 함수를 실행.
- 어플리케이션 내 정보의 유지 관리, 익스큐터의 실행 및 실행 분석, 배포 등의 역할.

#### Executor
- Spark Driver가 할당한 작업을 수행하여 결과를 반환.
- 블록매니저를 통해 cache하는 RDD를 저장.

### 실행흐름

1. 사용자가 Spark-submit을 통해 어플리케이션을 제줄.
2. Spark Driver가 main()을 실행하며, SparkContext를 생성.
3. SparkContext가 Cluster Manager와 연결된다.
4. Spark Driver가 Cluster Manager로부터 Executor 실행을 위한 리소스를 요청.
5. Spark Context는 작업 내용을 task 단위로 분할하여 Excutor에 보낸다.
6. 각 Executor는 작업을 수행하고, 결과를 저장

- 사용자가 프로그램을 실행하면, Spark Driver 내의 Spark Context가 Job을 task 단위로 쪼갠다. Cluste Manager로 부터 할당받은 Executor로 task를 넘긴다.


### 데이터 구조

#### RDD (Resilient Distributed Data)
- Resilient (회복력 있는) : 메모리 내부에서 데이터가 손실 시 유실된 파티션을 재연산해 복구할 수 있음.
- Distributed (분산된) : 스파크 클러스터를 통하여, 메모리에 분산되어 저장됨
- Data : 파일
- RDD는 불변의 특성을 가지는데 RDD는 수정이 불가능하기 때문에 새로운 RDD들이 생성.
- 