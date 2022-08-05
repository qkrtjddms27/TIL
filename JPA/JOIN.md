# JOIN
두 개 이상의 테이블을 연결해서 데이터를 검색하는 방법, 연결하기 위해서는 적어도 하나의 컬럼을 공유하고 있어야함.

## INNER JOIN
- 내부조인, 교집합
- 공통적인 부부만 SELECT
```
SELECT A.ID, A.ENAME, B.KNAME
FROM A INNER JOIN B
ON A.ID = B.ID 
```

## LEFT JOIN
- 조인기준으로 왼쪽에 있는 테이블이 모두 SELECT 됨.

```
SELECT A.ID, A.ENAME, A.KNAME
FROM A LEFT OUTER JOIN B
ON A.ID = B.ID;
```

## RIGHT JOIN
- 조인기준 오른쪽에 있는거 모두 SELECT 됨.

```
SELECT A.ID, A.ENAME A.ENAME, A.KNAME
FROM A RIGHT OUTER JOIN B
ON A.ID = B.ID;
```

## OUTER JOIN
- A테이블, B테이블이 갖고 있는 모든 것을 SELECT

```
SELECT A.ID, A.ENAME, A.KNAME
FROM A FULL OUTER JOIN B
ON A.ID = B.ID
```