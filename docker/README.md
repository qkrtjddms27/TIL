# docker

> 컨테이너 기반의 오픈소스 가상화 플랫폼

## 컨테이너
> 격리된 공간에서 프로세스가 동작하는 기술.

### 화물 컨테이너
배에 실는 네모난 화물 수송용 박스. 컨테이너 안에는 옷, 신발, 전자제품, 술, 과일등 다양한 화물을 넣을 수 있고 규격화되어 컨테이너선이나 
트레일러등 다양한 운송수단으로 쉽게 옮길 수 있습니다.

### 서버 컨테이너 
다양한 프로그램, 실행환경을 컨테이너로 추상화하고 동일한 인터페이스를 제공하여 배포 및 관리르 단순하게 해줌.

### docker의 가상화
전가상화, 반가상화, OS를 추가설치하는 가상화 방법에서 성능문제가 있었고 개선하기 위해 **프로세스 격리**하는 방식도입.

리눅스에서 이 방식을 리눅스 컨테이너라고 하고 단순히 프로세스를 격리시키기 때문에 가볍고 빠르게 동작합니다.
CPU나 메모리는 딱 프로세스가 필요한 만큼만 추가로 사용하므로 성능적으로 손실이 적다.

하나의 서버에 여러개의 컨테이너가 동작해도 독립적인 서버처럼 사용이 가능하다. 새로운 컨테이너를 걸리는 시간은 겨우 1-2초로 가상머신과 비교도
할 수 없이 빠름.

도커는 LXC(Linux container)기술 기반으로 가상화.

 **가상화** : 하나의 물리적 장치를 여러개의 물리적 장치인 것 처럼, 여러개의 물리적 장치를 하나의 물리적 장치처럼 사용하게 하는 것. 
## Image

> 컨테이너 실행에 필요한 파일과 설정값등을 포함하고 있는 것

이미지는 컨테이너를 실행하기 위한 모든 정보를 가지고 있기 때문에 더 이상 의존성 파일을 컴파일하고 이것저것 설치할 필요가 없다.

### Dockerfile
docker build에 사용되는 파일로 어떤 식으로 이미지를 만들지 정의해놓은 파일. DSL 로 만든다.
```dockerfile
# vertx/vertx3 debian version
FROM subicura/vertx3:3.3.1
MAINTAINER chungsub.kim@purpleworks.co.kr

ADD build/distributions/app-3.3.1.tar /
ADD config.template.json /app-3.3.1/bin/config.json
ADD docker/script/start.sh /usr/local/bin/
RUN ln -s /usr/local/bin/start.sh /start.sh

EXPOSE 8080
EXPOSE 7000

CMD ["start.sh"]
```

### Docker Hub
도커 이미지를 원격 저장소에 무료로 저장할 수 있게 해준다. push, pull 가능.

### Docker 실행

docker는 기본적으로 root권한이 필요하다. root가 아닌 사용자가 sudo없이 사용하려면 해당 사용자를 docker 그룹에 추가합니다.
```
sudo usermod -aG docker $USER # 현재 접속중인 사용자에게 권한주기
sudo usermod -aG docker your-user # your-user 사용자에게 권한주기
```

### 컨테이너 실행하기

```
docker run [OPTIONS] IMAGE[:TAG]
```

#### DockerHub
도커 커뮤니티 사이트로 docker server에 repo를 만들면 push, pull을 자유롭게 이용할 수 있다.

```shell
# docker push [repo/이름명:태그명]

docker push qkrtjddms27/backend:0.1.0
```

```shell
# docker pull [repo/이름명:태그명]

docker pull qkrtjddms27/backend:0.1.0
```

#### [OPTIONS]
- -d : detached mode 백그라운드 모드
- -p : 호스트와 컨테이너의 포트를 연결 (포워딩)
- -v : 호스트와 컨테이너의 디렉토리를 연결 (마운트)
- -e : 컨테이너 내에서 사용할 환경변수 설정
- -name : 컨테이너 이름 설정
- -rm : 프로세스 종료시 컨테이너 자동 제거
- -it : 터미널 입력을 위한 옵션
- -link : 컨테이너 연결 [컨테이너명:별칭]

### Docker Compose

- 실행 명령어를 정리 해놓은 파일.
- 한 번에 여러개의 도커 파일을 실행 할 수 있다. ex) openvidu
```shell
#frontend
docker run -d --rm --name backend -p 80:80 -p 443:443 -v /home/ubuntu/docker-volume/ssl:/var/www/html [repo/이름명:태그명]

#backend
docker run -d --rm --name frontend -p 8080:8080 -v /home/ubuntu/docker-volume/ssl:/root [repo/이름명:태그명]
```

```yaml
version: '3.2'

services: 
  frontend:
    image: frontend-react
    build:
      context: frontend/
      dockerfile: Dockerfile
    ports:
      - "80:80"
      - "443:443" 
    volumes:
      - /home/ubuntu/docker-volume/ssl:/var/www/html
    container_name: "frontend"
  
  backend:
    image: backend-spring
    build:
      context: backend/
      dockerfile: Dockerfile
    ports:
      - "8080:8080"  
    volumes:
      - /home/ubuntu/docker-volume/ssl:/root
    container_name: "backend"
```

