# magazine
## Info
- 매거진 사이트 만들기
- 주특기 2~3주차 과제
- 배포서버 : ~~http://13.125.145.83/~~
- Spring Boot, JPA(Hibernate), AWS EC2(배포 환경), AWS RDS(DB, MySQL Server)


## 데이터베이스
- [데이터베이스 설계](https://github.com/geonoo/magazine/wiki/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EC%84%A4%EA%B3%84)


## 필수
#### 프레임워크와 라이브러리의 차이점
- 라이브러리와 프레임워크의 차이는 제어 흐름에 대한 주도성이 누구에게/어디에 있는가에 따라 다르다고 한다.
- 프레임워크는 전체적인 흐름을 스스로가 쥐고 있으며 사용자는 그 안에서 필요한 코드를 짜 넣으면 된다.
- 라이브러리는 사용자가 전체적인 흐름을 만들며 라이브러리를 가져다 쓰는 것이라고 할 수 있다.
- 즉, 라이브러리는 가져다가 사용하고 호출하는 측에 주도성이 있고, 프레임워크는 프레임워크 안에 들어가서 사용한다는 느낌으로 접근하면 이해가 쉽다.

#### 코드를 구현할때 예외처리를 위해 무엇을 했나요?
- 예외가 발생하면 호출한 쪽에서 어떤 이유로 예외가 발생했는지에 대해서 반환해주고 싶었다.
- 구글링을 하던 중 @RestControllerAdvice를 알게되었다.
- @RestControllerAdvice는 스스로 이해한 바로는 예외를 처리하는 관점만 보고 처리할 수 있게 도와주는 어노테이션이라고 생각했다.
- @ExceptionHandler 등의 어노테이션이 적용된 메서드들을 AOP를 이용해 Controller에서 적용하기 위한 어노테이션이라고 한다.
- [사용한 부분](https://github.com/geonoo/magazine/blob/main/src/main/java/com/geonoo/magazine/exception/ApiException.java)

#### Restful이란?
- **REST?**
1. HTTP URI(Uniform Resource Identifier)를 통해 자원(Resource)을 명시하고,
2. HTTP Method(POST, GET, PUT, DELETE)를 통해
3. 해당 자원(URI)에 대한 CRUD Operation을 적용하는 것을 의미한다.
- **RestFul?**
- RESTful은 일반적으로 REST라는 아키텍처를 구현하는 웹 서비스를 나타내기 위해 사용되는 용어이다.
- ‘REST API’를 제공하는 웹 서비스를 ‘RESTful’하다고 할 수 있다고 합니다.
- 즉, REST 원리를 따르는 시스템은 RESTful이란 용어로 지칭된다.
- RESTful하게 API 설계 규칙도 있다고 한다. 아래 블로그를 참조하면 확인가능하다.

- [참조1](https://khj93.tistory.com/entry/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-REST-API%EB%9E%80-REST-RESTful%EC%9D%B4%EB%9E%80)
- [참조2](https://gmlwjd9405.github.io/2018/09/21/rest-and-restful.html)

#### 왜 Restful하게 설계해야할까?
- 결론은 이해하기 쉽고 사용하기 쉬운 REST API를 만드는 것이 목적이다.
- RESTful한 API를 구현하는 근본적인 목적이 성능 향상에 있는 것이 아니라 일관적인 API를 통한 이해도 및 호환성을 높이는 것이 주 목적이니, 성능이 중요한 상황에서는 굳이 RESTful한 API를 구현할 필요는 없다고 한다.

#### Restful의 장/단점
- **장점**
- 위에서 말 했듯이 API를 쉽게 사용하기 위한것이 바로 장점이다.
- 각자의 역할이 명확하게 분리되어 있다
- HTTP 프로토콜 서비스라는 기본적인 요구만 충족되면 다양한 플랫폼에서 원하는 서비스를 쉽고 빠르게 개발하고 배포할 수 있게 된다.
- **단점**
- 메소드 형태가 제한적 이라는 문제점을 가져오기도 한다.
- 관리의 어려움과 좋은(공식화 된) API 디자인 가이드가 존재하지 않는다. 결국 표준이 없다는 말이다.
- REST API는 간단한 서비스에는 문제가 없지만 서비스와 복잡해질수록 [Over-Fetching 과 Under-Fetching](https://ivvve.github.io/2019/07/24/server/graphql/over-under-fetching/)  문제가 발생할 수 있습니다.
- 비슷한 역할을 하지만 Endpoint가 다른 API가 많이 개발됩니다.

#### Restful의 대안은?
- REST API와 대비되는 facebook에서 개발한 쿼리 언어인 GraphQL로 많이 비교한다고 들었다.
- 하나의 Endpoint를 생성하고 클라이언트에게 필요한 데이터를 직접 쿼리를 통해 호출하게끔 하는 방식입니다.
- 예를 들어 Over-Fetching 같은 문제는 아래와 같은 쿼리로 필요한 데이터만 조회할 수 있습니다.
```
query {
	user(user_id: 1) {
		username
	}
}
```
- **GraphQL 장점**
- 하나의 Endpoint 위에서도 언급했듯이 GraphQL은 하나의 Endpoint를 갖습니다.
- IOS, Android 와 같은 다른 기종에 대해 별도의 API를 개발할 필요가 없습니다.
- **GraphQL 단점**
- 하나의 Endpoint를 사용하기 때문에 HTTP에 제공하는 캐싱 전략을 그대로 사용하는 것이 불가능합니다.
- GraphQL은 아직 파일 업로드에 대해서 구체적인 구현 방법이 정의되어 있지 않습니다. (따로 구현해야 한다고 함)
- **결론**
- REST API의 문제점을 극복하기 위해 등장했다고는 하나 아직까지는 REST API가 더 많이 쓰이고 있습니다.
- 둘의 장단점이 명확하니 상황에 맞춰 적절한 통신 규약을 선택하는게 중요합니다.

#### Restful하게 짜기 위해 무엇을 고려했나요?
- Over-Fetching되어서 나오는 부분을 최소화하려고 시도는 해보았다.
- response-entity로 End-Point 공통화를 해야겠다는 생각을 가지고있다. (현재 코드를 변경하면 프론트쪽도 변경되어서 따로 진행)
#### Entity 설계를 위해 무엇을 하였나요?
- [데이터베이스 설계](https://github.com/geonoo/magazine/wiki/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EC%84%A4%EA%B3%84)
- 유저와 게시물 (유저)oneToMany(게시물)
- 유저와 좋아요 (유저)oneToMany(좋아요)
- 게시물과 좋아요 (게시물)oneToMany(좋아요)

#### 연관관계에 근거하여 설명해주세요.
- 유저는 게시물을 여러개 작성할 수 있기 때문에 OneToMany로 해주었다.
- 좋아요는 여러개의 게시물을 좋아요할 수 있고, 여러명의 유저가 좋아요 할 수 있다. 그래서 N:M의 관계를 가지므로 좋아요 테이블을 두어서 유저와 게시물을 (유저,게시물)OneToMany(좋아요) 로 해주었다.

#### N+1 문제
- 만약 게시물 엔티티에서 좋아요 엔티티를 EAGER(즉시로딩)로 사용하고 'select * from 게시물 limit 100' 를 수행한다면 좋아요를 가져오기 위해 쿼리가 100+1개가 같이 날아간다.
- 위와 같은 문제를 n+1이라고는 하는데 그래서 LAZY(지연로딩)을 사용해서 어느정도 해결은 했다.
- 하지만 그렇다고 해서 해결된 것은 아니라고 한다. [참조](https://incheol-jung.gitbook.io/docs/q-and-a/spring/n+1)
- @EntityGraph를 이용하면 된다고 하는데 좀 더 공부가 필요한 것 같다.

#### Unit test와 Integration test는 무엇이고, 어떻게 하는지 알아보기
#### Integration test 시나리오 작성하기(개발자의 관점이 아닌 고객 관점)
#### 서비스 레이어 테스트 코드 커버율 70% 이상 만들어오기

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
- **JWT Token 방식의 인증, 인가를 선택한 이유**
- 웹 어플리케이션에서 세션을 관리할 때 자주 사용되는 쿠키는 단일 도메인 및 서브 도메인에서만 작동하도록 설계되어 있기 때문에 프론트와 협업하는 입장에서 여러 도메인에서 쿠키를 관리하는 것이 번거롭다.
- 세션 방식은 서버의 메모리나 데이터베이스에 저장하는데, 로그인 중인 사용자가 늘어날 경우에 메모리에 부하가 생기거나 데이터베이스에 무리를 줄 수 있다. (물론 연습 프로젝트이기에 고려 하지 않아도 되지만 나중을 위해서..?)


#### CORS 해결하기
- CORS란 브라우저에서는 보안적인 이유로 cross-origin HTTP 요청들을 제한한다고 합니다.
- 이 말은 백엔드에서 API를 만들어주면 다른 IP나 다른 포트에서 해당 API를 호출할 수 없게하는 보안정책 입니다.
- 그래서 프론트엔드 개발자와 협업하려면 cross-origin 요청을 하려면 백엔드 서버의 동의가 필요합니다.
- 해당 이슈 해결방법은 WebSecurityConfig.java 파일에서 CorsConfigurationSource를 bean으로 등록해서 사용하고 있습니다.
- 현재 프론트쪽에서 테스트하기 위해 *(ALL)로 열어놓음, 실제로는 프론트경로와 포트를 지정해줘야 한다.
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
 - 문제는 SecurityFilterChain의 authorizeRequests()를 이용하려고하니 같은 URL로 Method만 막는 방법을 찾지 못했습니다.
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
