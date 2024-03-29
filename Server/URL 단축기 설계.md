# URL 단축기 설계

> 고전적인 시스템 설계 문제 가운데 하나인, tiny url 같은 URL 단축기를 설계하는 문제.

## 1단계, 문제 이해 및 설꼐 범위 확정

- URL 단축기가 어떻게 동작해야 하는지 예제를 보여주실 수 있을까요?
  + https://url/q=chatsystem&c=loggedin...이 입력으로 주어졌을때. https://tinyurl.com/y7ke-ocwj와 같은 단축 url을 제공해야 합니다.

- 트래픽 규모는 어느 정도일까요?
  + 매일 1억 개의 단축 URL을 만들어낼 수 있어야 합니다.

- 단축 URL의 길이는 어느 정도여야 하나요?
  + 짧으면 짧을 수록 좋습니다.
  
- 단축 URL에 포함될 문자에 제한이 있습니까?
  + 단축 URL에는 숫자와 영문자만 사용할 수 있습니다.

- 단축 URL을 시스템에서 지우거나 갱신할 수 있습니까?
  + 시스템을 단수화하기 위해 삭제나 갱신은 할 수 없다고 가정.
  
### 개략적인 추정
- 쓰기 연산: 매일 1억 개의 단축 URL 생성
- 초당 쓰기 연산: 1억 / 24 / 3600 = 1160
- 읽기 연산: 읽기 연산과 쓰기 연산 비율은 10:1이라고 가정.
- URL 단축 서비스를 10년간 운영한다고 가정하면 1억 * 365 * 10 = 3650억 개의 레코드를 보관
- 축약 전 URL의 평균 길이는 100이라고 가정
- 10년 동안 필요한 저장 용량은 3650억 * 100바이트 = 36.TB

## 2단계, 개략적 설계안 제시 및 동의 구하기
> API 엔드포인트, URL 리다이렉션, URL 단축 플로

### API 엔드포인트
> 클라이언트는 서버가 제공하는 API 엔드포인트를 통해 서버와 통신한다.

- URL 단축용 엔드포인트 : 새 단축 URL을 생성하고자 하는 클라이언트는 이 엔드포인트에 단축할 URL을 인자로 실어서 POST 요청을 보내야 한다.
- URL 리다리엑션용 엔드포인트 : 단축 URL 에 대해서 요청이 오면 원래 URl로 보내주기 위한 용도의 엔드포인트
  + 301 상태코드로 해당 요청의 값을 다른 주소로 보냄
  
### URL 단축을 위한
> 중요한 것은 긴 URL을 이 해시 값으로 대응시킬 해시 함수 fx를 찾는 일이 될 것이다.

- 입력으로 주어지는 긴 URL이 다른 값이면 해시 값도 달라야 한다.
- 계산된 해시 값은 원래 입력으로 주어졌던 긴 URL로 복원될 수 있어야 한다.

## 3단계, 상세 설계
> 데이터 모델, 해시 함수, URL 단축 및 리다이렉션에 관한 구체적인 설계안

### 데이터 모델
> 메모리에 모든 순서쌍을 저장하는 것은 값 비싸다. 그래서 순서쌍을 관계형 데이터베이스에 저장하는 방법으로 진행

### 해시 함수
> 해시 함수는 원래 URL을 단축 URL로 변환하는 데 쓰인다. 편의상 해시 함수가 계산하는 단축 URL값을 hashValue라고 지칭.


#### 해시 값 길이
- hashValue는 [0-9, a-z, A-Z]의 문자들로 구성된다.
  + 사용할 수 문자의 갯수는 10 + 26 + 26 = 62개다.
  + 62^n > 3650억인 n의 최소값을 찾아야 한다.
  + 추정에 따르면 이 시스템은 3650억 개의 URL을 만들어 낼 수 있어야 한다.
  
#### 해시 후 충돌 해소
> 이 방법을 쓰면 충돌은 해소할 수 있지만 단축 URL을 새성할 떄 한 번 이상 데이터베이스 질의를 해야 하므로 오버헤드가 크다. 
> 성능 개선은 볼륨 필터를 사용하면 성능을 높일 수 있다.
- 입력 loginURL
- 해시 함수를 사용하여 shrotURL 생성
- DB에 해당 shortURL이 있는지 확인
  + 이미 존재 한다면, loginURL 뒤에 사전에 정한 문자열 추가하고 다시 처음으로 돌아가 반복
  + 존재하지 않는다면, DB에 저장
- 종료

#### base-62 변환
> 진법 변환은 URL 단추기를 구현할 때 흔히 사용되는 접근법 중 하나다.
- 62진법은 수를 표현하기 위해 총 62개의 문자를 사용한다.
- [0-9, a-z, A-Z], 10 + 26 + 26

#### 62진법을 이용한 설계
- 입력으로 URL을 받는다.
- 데이터베이스에 해당 URL이 있는지 검사한다.
- 데이터베이스에 있다면 해당 URL에 단춘 URL을 만든 적이 있는 것이다. 따라서 데이터베이스에 해당 단축 URL을 가져와서 클라이언트에게 반환.
- 데이터베이스에 없는 경우에는 해당 URL은 새로 접수된 것이므로 유일한 ID를 생성한다. 이 ID는 데이터베이스의 기본 키로 사용한다.
- 62진법 변환을 적용, ID를 URL로 만든다.
- ID, 단축 URL,원래 URL로 새 데이터베이스 레코드를 만든 후 단축 URL을 클라이언트에 전달.

## 마무리
- URL 단축기의 API, 데이터 모델, 해시 함수, URL 단축 및 리다이렉션 절차 설계.
- 처리율 제한기는 엄청난 양의 URL 단축 요청이 밀려들 경우 무력화될 수 있다.
  + 설정을 바꿔주는 것이 좋다.
- 웹 계층은 무상태 계층이므로, 웹 서버를 자유로이 증설하거나 삭제할 수 있다.
- 데이터베이스를 다중화하거나 샤딩하여 규모 확장성을 달성할 수 있다.
- URL 단축기에 데이터 분석 솔루션을 통합해 두면 어떤 링크를 얼마나 많은 사용자가 클릭했는지, 언제 주로 클릭했는지 등 중요한 정보를 알아낼 수 있다.