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

### 실행흐름

1. 사용자가 Spark-submit을 통해 어플리케이션을 제줄.
2. Spark Driver가 main()을 실행하며, SparkContext를 생성.
3. SparkContext가 Cluster Manager와 연결된다.
4. Spark Driver가 Cluster Manager로부터 Executor 실행을 위한 리소스를 요청.
5. Spark Context는 작업 내용을 task 단위로 분할하여 Excutor에 보낸다.
6. 각 Executor는 작업을 수행하고, 결과를 저장

- 사용자가 프로그램을 실행하면, Spark Driver 내의 Spark Context가 Job을 task 단위로 쪼갠다. Cluste Manager로 부터 할당받은 Executor로 task를 넘긴다.


### RDD (Resilient Distributed Data)
- Resilient (회복력 있는) : 메모리 내부에서 데이터가 손실 시 유실된 파티션을 재연산해 복구할 수 있음.
- Distributed (분산된) : 스파크 클러스터를 통하여, 메모리에 분산되어 저장됨
- Data : 파일
- RDD는 불변의 특성을 가지는데 RDD는 수정이 불가능하기 때문에 새로운 RDD들이 생성.

### Spark Architecture
- Cluster의 리소스를 관리하는 Cluster Manager와 그 위에서 동작하는 사용자 프로그램인 Spark Application으로 구분.
- Spark Cluster Manager로는 Spark에 built-in된 기본 모듈 Spark Standalone과 Hadoop에서 사용되는 Yarn이 있다.
- Standalone은 Standalen Cluster Manager는 Master와 Worker로 구성됨.
- Spark Master WebUI 또는 Rest API를 통해 연결이 정상적인지 쉽게 확인할 수 있다.
- Cluster가 구성되면, Spark Application을 실행할 준비가 완료됨.

### SparkContext
- Spark Cluster와 커뮤니케이션을 담당.
- SparkContext를 통해 Application에서 요구하는 리소스를 요청.
- Application은 1개 이상의 Job을 실행시키고, Job은 여러 개의 Task로 나누어서 Executor에게 요청하고 결과를 받으면서 Cluster 컴퓨팅 작업을 수행.
- Driver Program은 Spark Context를 생성.
- Worker에 충분한 리소스가 없다면 오류가 발생.
- Spark Context가 자원을 해제하면서 모든 Executor 프로세스가 종료.
- 실행중인 환경이라면 생성된 Spark Context에 Job을 요청하는 것.

- 특정동작에 대해서 DAG(Directed Acycli Graph)의 형태를 가짐
- RDD 관련 정보가 유실되었을 경우, 그래프를 복기하여 다시 계산, 자동 복구 가능. (Fault-tolerant)


#### 지연 연산
- Lazy Evaluation(지연 연산) 즉시 실행하지 않는 것.
  + Action 연산자를 만나기 전까지는, Transformation 연산자가 아무리 쌓여도 처리하지 않습니다.
  + 장점 : 간단한 operation들에 대한 성능적 이슈를 고려하지 않아도 된다.
- 위 코드를 실행해도 결과는 출력되지 않는다.
  + 추상적인 트랜스포메이션만 지정한 상태이기 때문에.

#### 좁은 의존성
- 각 입력 파티션이 하나의 출력 파티션에만 영향을 준다.

#### 넓은 의존성
- 넓은 의존성을 가진 트랜스포메이션은 하나의 입력 파티션이 여러 출력 파티션에 영향을 준다.
  + 셔플

### DataFrame
- 행과 열로 구성된 데이터 분산 컬렉션
- 관계형 데이터 베이스 구조.

#### RDD의 단점
- RDD는 메모리나 디스크에 저장 공간이 충분치 않으면 제대로 동작하지 않는다. (?)
- RDD는 스키마 개념이 없다.
- RDD는 기본적으로 직렬화와 Garbage Collection을 사용.
  + 오버헤드로 이어짐.
- RDD는 내장된 최적화 엔진이 없다.

#### DataFrame의 특징
- 구조화된 데이터 구조 : DataFrame은 구조화된 데이터를 다루기 쉽게 하기 위해 만들어진 데이터 구조.
- GC 오버헤드 감소 : RDD는 데이터를 메모리에 저장하지만, DataFrame은 데이터를 오프-힙 영역에 저장.
- 직렬화 오버헤드 감소 : DataFrame은 오프-힙 메모리를 사용한 직렬화를 통하여 오버헤드를 크게 감소.
- Flexibility & Scalability : DataFrame은 CSV, 카산드라 등 다양한 형태의 데이터를 직접 지원.
- 스파크 기반의 SQL이 가능해짐

#### Dataset
- DataSet은 구조적 API의 기본 데이터 타입.
- 도메인별 특정 개체를 효과적으로 지원하기 위해 '인코더'라 부르는 특수한 개념이 필요.
  + 인코더는 도메인별 특정 객체 T를 스파크의 내부 데이터 타입으로 매핑하는 시스템

### 구조

- 스파크는 Driver 프로세스와 익스큐터 프로세스로 구성.
- 1개의 Spark Driver와 N개의 Executor가 존재.
- Executor는 Cluster Manager에 의하여 해당 스파크 어플리케이션에 할당.
- 해당 스파크 어플리케이션이 완전히 종료된 후 할당에서 해방.
- 서로 다른 스파크 어플리케이션 간의 직접적인 데이터 공유는 불가능.
- 드라이버와 익스큐터는 단순한 프로세스이므로 같은 머신이나 서로 다른 머신에서 실행할 수 있다.
- 스파크는 사용 가능한 자원을 파악하기 위해 클러스터 매니저를 사용.

#### Driver
- 한 개의 노드에서 실행되며, 스파크 전체의 main() 함수를 실행.
- 어플리케이션 내 정보의 유지 관리, 익스큐터의 실행 및 실행 분석, 배포 등의 역할.

#### Executor
- Spark Driver가 할당한 작업을 수행하여 결과를 반환.
- 블록매니저를 통해 cache하는 RDD를 저장.

### Transformation
- 스파크의 핵심 데이터 구조는 불변성.
- 변경하려면 원하는 변경 방법을 스파크에 알려줘야 함.
- 해당 변경을 트랜스포메이션이라고 부름.
```scala
val divisBy2 = myRange.where("number % 2 = 0")
```

#### Transformation 연산자
- map() : 각 요소에 함수를 적용.
- filter() : 조건에 통과한 값만 리턴.
- distinct() : 값 중 중복을 제거한다.
- union() : 두 데이터를 합친다.
- intersection() : 두 Data에 모두 있는 데이터만을 반환한다.

### Action
- 실제 수행하는 작업
- 리턴 값은 데이터 또는 실행 결과
- 트랜스포메이션을 사용해 논리적 실행 계획을 세울 수 있다.
```scala
divisBy2.count() 
```
#### Action 연산자
- collect() : RDD의 모든 데이터를 리턴한다.
- count() : RDD의 값 개수를 리턴한다.
- top(num) : 상위 num 갯수만큼 리턴한다.
- takeOrdered(num) : Ordering 기준으로 num 갯수만큼 리턴한다.
- reduce(func) : RDD의 값들을 병렬로 병합 연산한다.
