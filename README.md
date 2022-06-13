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

## 요구사항
#### 회원가입
- 닉네임은 `최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성하기
- 비밀번호는 `최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패`로 만들기
- 비밀번호 확인은 비밀번호와 정확하게 일치하기

#### 로그인
- 로그인 버튼을 누른 경우 닉네임과 비밀번호가 데이터베이스에 등록됐는지 확인한 뒤, 하나라도 맞지 않는 정보가 있다면 "닉네임 또는 패스워드를 확인해주세요"라는 메세지를 프론트엔드에서 띄워줄 수 있도록 예외처리 하기

#### 로그인 검사
- 로그인 하지 않은 사용자도, 게시글 목록 조회는 가능하도록 하기
- 로그인하지 않은 사용자가 좋아요 버튼을 눌렀을 경우, "로그인이 필요합니다." 라는 메세지를 프론트엔드에서 띄워줄 수 있도록 예외처리 하기
- 로그인 한 사용자가 로그인 페이지 또는 회원가입 페이지에 접속한 경우 "이미 로그인이 되어있습니다."라는 메세지로 예외처리하기
- 인증 인가를 어떤 개념(Token/Session)을 채택 했는지, 그 이유에 대해서 설명하기

#### CORS 해결하기
- CORS란 무엇이며, 어떤 상황에서 일어나는지 / 어떻게 해결하는지 알아보고, 프로젝트에 적용하기

#### 좋아요 순 정렬
- 정렬 기준 중 하나를 선택해주세요!
- 생성일 순
- 좋아요 순
- view 순


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
- 다른 기능부터 우선순위가 되어서 주석처리하게 되었습니다. (06/13)

#### Executing an update/delete query; nested exception is javax.persistence.Transaction

#### Many To One, One To Many ;;
- 연관관계에 대한 감을 잘 잡지 못하고 하루종일 구글링 -> 코드 -> 구글링 -> 코드...

#### @AuthenticationPrincipal

#### @JsonIgnore, @JsonProperty, @JsonAlias 
 - [참조](https://blog.devgenius.io/three-jackson-annotations-which-all-spring-boot-developers-should-know-1b6304dda19)





