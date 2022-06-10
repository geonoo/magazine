# magazine
## Info
- 매거진 사이트 만들기
- 주특기 2주차 과제
- url : 
- Spring Boot, JPA(Hibernate), AWS EC2(배포 환경), AWS RDS(DB, MySQL Server)

## 데이터베이스 설계
### 개체 및 속성 추출
#### 사용자 (Users)
- 사용자 아이디
- 이메일
- 비밀번호
- 작성 날짜
- 수정 날짜

#### 게시물 (Boards)
- 게시물 아이디
- 사용자 아이디
- 제목
- 내용
- 조회수
- 작성 날짜
- 수정 날짜

#### 좋아요 (Favorites)
- 좋아요 아이디
- 사용자 아이디
- 게시물 아이디
- 상태(Y,N)
- 작성 날짜
- 수정 날짜

### 데이터 모델링
#### 사용자 (Users)
- USER_ID (PK, INT)
- EMAIL (VARCHAR)
- PASSWORD (VARCHAR)
- CREATED_DATE (DATETIME)
- MODIFIED_DATE (DATETIME)

#### 게시물 (Boards)
- BOARD_ID (PK, INT)
- USER_ID (FK, INT)
- TITLE (TEXT)
- BODY (TEXT)
- VIEW_COUNT (INT)
- CREATED_DATE (DATETIME)
- MODIFIED_DATE (DATETIME)

#### 좋아요 (Favorites)
- ID (PK, INT)
- USER_ID (FK, INT)
- BOARD_ID (FK, INT)
- STATUS (CHAR)
- CREATED_DATE (DATETIME)
- MODIFIED_DATE (DATETIME)

### ERD
![level2-erd](https://user-images.githubusercontent.com/39722357/172979859-86aefbf3-6614-461e-8bc9-67df2e4400e5.png)


## 필수
#### 프레임워크와 라이브러리의 차이점
#### 코드를 구현할때 예외처리를 위해 무엇을 했나요?
#### Restful이란?
#### 왜 Restful하게 짜야하나요?
#### Restful의 장/단점
#### Restful의 대안은?
#### Restful하게 짜기 위해 무엇을 고려했나요?

