# magazine
## Info
- 매거진 사이트 만들기
- 주특기 2~3주차 과제
- 배포서버 : 
- Spring Boot, JPA(Hibernate), AWS EC2(배포 환경), AWS RDS(DB, MySQL Server)


## 데이터베이스
- [데이터베이스 설계](https://github.com/geonoo/magazine/wiki/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EC%84%A4%EA%B3%84)


## 필수
#### 프레임워크와 라이브러리의 차이점
#### 코드를 구현할때 예외처리를 위해 무엇을 했나요?
#### Restful이란?
#### 왜 Restful하게 짜야하나요?
#### Restful의 장/단점
#### Restful의 대안은?
#### Restful하게 짜기 위해 무엇을 고려했나요?
#### Entity 설계를 위해 무엇을 하였나요?
#### 연관관계에 근거하여 설명해주세요.

## 발생했던 문제들
#### WebSecurityConfigurerAdapter - deprecated
- Spring Boot에서 더이상 효율적이지 않거나 안전하지 않은 코드일 수 있어서 해당 부분을 수정해 주어야 했습니다.
- https://github.com/geonoo/magazine/issues/8
- SecurityFilterChain을 이용하면 된다고 합니다.

#### JWT 비밀키를 Class에 작성하여 노출되는데 어떻게 해결하지?
- git에 push 했을 때, 비밀키가 노출되서 보안 이슈가 생길 수 있다.
- https://github.com/geonoo/magazine/issues/10
- application.properties을 .gitignore에 적용시키고, application.properties해당 파일에서 꺼내서 사용 하였습니다.

#### Access Token, Refresh Token 적용기
- 상세내용은 아래 이슈 링크에 있습니다.
- https://github.com/geonoo/magazine/issues/9

