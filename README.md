StartUpLab
===================
## 참여인원
|맡은 업무|이름|
|:------:|:---:|
|Back-End|[신동준](https://github.com/dongjun0128)|
|Back-End|[유병찬](https://github.com/SweetDdang)|
|Front-End|사수봉|
|Front-End|이은서|
|Front-End|이규빈|
|Front-End|임수찬|

## 개발 환경
- Visual Studio Code
- 사용 스택
  - Java 17.0.3
  - Spring Boot 2.5.8
  - Mysql 8
  - lombok
  - vue
  
## Server
- web, api
  - [49.50.164.147](http://49.50.164.147)
  - ubuntu 18.04
- Database
  - 115.85.181.186
  - ubuntu 18.04, Mysql 8

## Api List
|Type|API명|설명|endpoint|Content Type|
|:------:|:---:|:------:|:---:|:------:|
|common | 로그인 |	oauth client auth 필요|	/oauth/token|	application/x-www-form-urlencoded|
| common | 토큰갱신 | oauth client auth 필요 | /oauth/token| application/x-www-form-urlencoded|
| common | 코드리스트 | 코드타입별리스트 |/common/code/list | application/json;charset=utf-8|
| common | 회원가입 |  | /common/user/join| application/json;charset=utf-8|
| common | 회원정보 변경 |  | /common/user/edit| application/json;charset=utf-8|
| common | 회원조회 | user_id, user_email 검색 | /common/user/info| application/json;charset=utf-8|
| common | 파일업로드 | 파일을 업로드 하여 Object Storage에 저장 | /common/file/upload| multipart/form-data|
| common | 토큰정보 조회 | access token의 user  정보 | /common/token/info| application/json;charset=utf-8|
