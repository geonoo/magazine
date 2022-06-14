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
- 닉네임은 `최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성하기(이메일로 변경) o.k
- 비밀번호는 `최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패`로 만들기 o.k
- 비밀번호 확인은 비밀번호와 정확하게 일치하기 o.k
- [유효성](https://github.com/geonoo/magazine/blob/main/src/main/java/com/geonoo/magazine/dto/UsersDto.java)

#### 로그인
- 로그인 버튼을 누른 경우 닉네임과 비밀번호가 데이터베이스에 등록됐는지 확인한 뒤, 하나라도 맞지 않는 정보가 있다면 "닉네임 또는 패스워드를 확인해주세요"라는 메세지를 프론트엔드에서 띄워줄 수 있도록 예외처리 하기 o.k
- [Service](https://github.com/geonoo/magazine/blob/main/src/main/java/com/geonoo/magazine/service/UsersService.java)
- [ApiException](https://github.com/geonoo/magazine/blob/main/src/main/java/com/geonoo/magazine/exception/ApiException.java)

#### 로그인 검사
- 로그인 하지 않은 사용자도, 게시글 목록 조회는 가능하도록 하기 o.k
- 로그인하지 않은 사용자가 좋아요 버튼을 눌렀을 경우, "로그인이 필요합니다." 라는 메세지를 프론트엔드에서 띄워줄 수 있도록 예외처리 하기 o.k
- 로그인 한 사용자가 로그인 페이지 또는 회원가입 페이지에 접속한 경우 "이미 로그인이 되어있습니다."라는 메세지로 예외처리하기
- 인증 인가를 어떤 개념(Token/Session)을 채택 했는지, 그 이유에 대해서 설명하기
- [게시판 컨트롤러](https://github.com/geonoo/magazine/blob/main/src/main/java/com/geonoo/magazine/controller/BoardsController.java)
- JWT Token 방식의 인증, 인가를 선택했다 이유는...

#### CORS 해결하기
- CORS란 브라우저에서는 보안적인 이유로 cross-origin HTTP 요청들을 제한한다고 합니다.
- 이 말은 백엔드에서 API를 만들어주면 다른 IP나 다른 포트에서 해당 API를 호출할 수 없게하는 보안정책 입니다.
- 그래서 프론트엔드 개발자와 협업하려면 cross-origin 요청을 하려면 백엔드 서버의 동의가 필요합니다.
- 해당 이슈 해결방법은 WebSecurityConfig.java 파일에서 CorsConfigurationSource를 bean으로 등록해서 사용하고 있습니다.
- [CORS](https://github.com/geonoo/magazine/blob/main/src/main/java/com/geonoo/magazine/security/WebSecurityConfig.java)

#### 생성일 순 정렬
- [관련 이슈](https://github.com/geonoo/magazine/issues/25)


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
 - Spring Data JPA에서는 Delete와 Update 함수가 없다.
 - Update시에 자바영역에서만 Update해주고 실제 데이터베이스에는 반영이 안되었기 때문에 @Transactional 해당 어노테이션을 붙여주고 해결하였다.

#### @AuthenticationPrincipal
 - 로그인한 사용자만 접근할 수 있는 API를 만들고 싶었다.
 - Restful하게 하기 위해서 API URL를 맞춰주어야 했습니다.
 - 문제는 SecurityFilterChain의 authorizeRequests()를 이용하려고하니 같은 URL로 Method만 변경된 경우가 있었습니다.
 - 그래서 메소드 파라미터에 @AuthenticationPrincipal를 추가해서 현재 로그인한 사용자를 가져와서 확인헀다.


#### Many To One, One To Many ;;
- 연관관계에 대한 감을 잘 잡지 못하고 하루종일 구글링 -> 코드 -> 구글링 -> 코드...를 반복하고 있다.
- 현재 게시판 하나를 호출 했을 때 아래와 같이 나온다.
```
{
        "id": 1,
        "createdDate": "2022-06-14T01:57:50.374745",
        "modifiedDate": "2022-06-14T01:57:50.374745",
        "board_id": 2,
        "title": "제목",
        "body": "내용",
        "img_url": null,
        "viewCount": 5,
        "template": 1
    }
```
- 내가원하는 코드는 아래 처럼 favorites가 담겼으면 한다.
```
{
        "id": 1,
        "createdDate": "2022-06-14T01:57:50.374745",
        "modifiedDate": "2022-06-14T01:57:50.374745",
        "board_id": 2,
        "title": "제목",
        "body": "내용",
        "favorites" : [
             {
              "user_id" : "1",
              "board_id" : "2"
             },
             {
              "user_id" : "3",
              "board_id" : "2"
             }
        ]
        "img_url": null,
        "viewCount": 5,
        "template": 1
    }
```
- 연관 관계 설계부터 잘 이해를 못하고 구글링해서 코드만 계속 바꾸고 있으니 답을 얻기가 어렵워서 강의를 다시 보고있다.
- [잭슨 – 양방향 관계](https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion)
- [참조](https://blog.devgenius.io/three-jackson-annotations-which-all-spring-boot-developers-should-know-1b6304dda19)
```
@JsonIgnore
@OneToMany(mappedBy = "boards", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Favorites> favoritesList = new ArrayList<>();
```
- 현재 이런식으로되어 있고 @JsonIgnore 해당 어노테이션을 지우면 아래 처럼 직렬화 문제라고 알려주고는 있다.
- com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer....
- https://github.com/geonoo/magazine/issues/23
```
{
    "createdDate": "2022-06-14T13:03:52.877356",
    "modifiedDate": "2022-06-14T13:03:52.877356",
    "board_id": 2,
    "title": "제목",
    "body": "내용",
    "img_url": null,
    "viewCount": 114,
    "template": 1,
    "favoritesList": [
        {
            "id": 3,
            "users": {
                "createdDate": "2022-06-14T13:03:35.60846",
                "modifiedDate": "2022-06-14T13:03:35.60846",
                "user_id": 1,
                "email": "aaa@aa.com",
                "password": "{bcrypt}$2a$10$27JZWAfiiPrPrZfGztivPupgWkcJfrthKSRg5qSywe8wjbyd3vLUq",
                "nick": "cc",
                "roles": [
                    "ROLE_USER"
                ],
                "hibernateLazyInitializer": {}
            }
        }
    ]
}
```
- 위 처럼 해결은 했으나, 왜 되었는지는 정확히 이해가 안됌
1. application.properties에 spring.jackson.serialization.fail-on-empty-beans=false 해당코드를 넣었다.
2. 그리고 나오지 안아도 될 객체들을 @JsonIgnore 해당 코드로 막아주었다.
