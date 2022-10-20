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
| web | 업무 조회 | 업무와 데이터 상태를 지정하면 리스트를 리턴 | /web/work/info | application/json;charset=utf-8 |
| web | 로그아웃 | oauth client auth 필요 | /oauth/token | application/x-www-form-urlencoded |
| web | 검색 | 검색어가 포함된 row를 리턴 | /web/search | application/json;charset=utf-8 |
| web | 데이터 추가 | 사용자가 데이터 추가 버튼을 통해 입력한 데이터를 DB에 저장 | /web/db/store | application/json;charset=utf-8 |
| web | 과제 개수 조회 | 과제별 분배 전, 임시 저장, 실측/조사불가, 완료 개수를 리턴 | /web/assignment/nums | application/json;charset=utf-8 |
| web | 업무 분배 | 업무를 회원에게 분해함 | /web/work/distribution | application/json;charset=utf-8 |
| web | 데이터정보 변경 | 입력된 데이터정보를 변경함 | /web/db/edit | application/json;charset=utf-8 |
| web | 유저 리스트 조회 | 해당 과제에 소속된 유저들의 리스트 리턴 | /web/assignment/user/list | application/json;charset=utf-8 |
| web | 메타데이터 조회 | work_id에 따른 메타데이터 리턴 | /web/metadata | application/json;charset=utf-8 |
| web | 업무 개수 조회 | 업무별 임시저장, 실측,완료, 총 개수 리턴 | /web/work/nums | application/json;charset=utf-8 |
| web | 관리자 업무 조회 | 업부를 지정하면 리스트를 리턴 | /web/manager/work/info | application/json;charset=utf-8 |
| web | user_id 조회 | user_email에 맞는 user_id를 리턴 | /web/email/to/id | application/json;charset=utf-8 |

## Codes
|column_name|	column_descript|	code_id|	code_name|
|:------:|:---:|:------:|:---:|
| user_type | 회원 유형 | 1 | 관리자 |
| user_type | 회원 유형 | 2 | 유저 |
| user_status | 가입 상태 | 0 | 탈퇴 |
| user_status | 가입 상태 | 1 | 정상 |
| data_status | 데이터상태 | 0 | 삭제 |
| data_status | 데이터상태 | 1 | 할당전 |
| data_status | 데이터상태 | 2 | 할 일 |
| data_status | 데이터상태 | 3 | 임시저장 |
| data_status | 데이터상태 | 4 | 실측필요 |
| data_status | 데이터상태 | 5 | 조사불가 |
| data_status | 데이터상태 | 6 | 완료 |
| meta_type | 메타타입 | 1 | input_text |
| meta_type | 메타타입 | 2 | select_box |
| meta_type | 메타타입 | 3 | check_box |
| meta_type | 메타타입 | 4 | radio_button |
| meta_type | 메타타입 | 5 | text_area |
| meta_type | 메타타입 | 6 | image_file |
| meta_type | 메타타입 | 7 | 주소 |
| assignment_id | 과제 분류 | 1 | 광진구 |
| assignment_id | 과제 분류 | 2 | 순천 |
| assignment_id | 과제 분류 | 3 | 광주 |
| assignment_id | 과제 분류 | 4 | 문정원 |
| work_id | 업무 분류  | 1 | 광진구 |
| work_id | 업무 분류  | 2 | 순천 |
| work_id | 업무 분류  | 3 | 광주 |
| work_id | 업무 분류  | 4 | OTT |
| work_id | 업무 분류  | 5 | 펫프랜드 |
| work_id | 업무 분류  | 6 | 베리어프리 |
| work_id | 업무 분류  | 7 | 트랜드 기반 문화시설 |
| work_id | 업무 분류  | 8 | 문화다양성 |
| work_id | 업무 분류  | 9 | 커뮤니티 |
| work_id | 업무 분류  | 10 | 액티비티 |
| work_id | 업무 분류  | 11 | 가족여가 |
