# ksuju 포트폴리오  https://bit.ly/4bVJyZA
## 업데이트중 -ing...

#### 리팩토링 예정
1. ERD 설계도 만들기
2. 프로젝트 구조 정리하기
3. DI 리팩토링 (필드주입 > 생성자주입)
4. 테스트 코드 만들기</br>

리팩토링 계획 : https://ksuju.tistory.com/44
   
## 👋 소개
회원가입, 게시판 등 기본적인 기능을 구현해보고 배포 경험을 쌓기 위한 웹 페이지 입니다.

## 🤯 기술 스택
- 백엔드 : JAVA, Spring
- 프론트엔드 : HTML, CSS, JavaScript, JSP, Tiles
- 데이터베이스 : MySQL, MyBatis
- 클라우드 : AWS(EC2, RDS)
- 서버 : Nginx
- etc : Github, SourceTree, FileZilla, MobaXterm

## 👀 구현 기능
### 1. 회원가입
      1-1. 이메일, 아이디 중복확인
      1-2. 아이디, 비밀번호 유효성 검사
      1-3. 가입한 아이디는 이메일인증 후 사용가능
<img src="https://github.com/user-attachments/assets/38d9b9af-cbbf-4665-ac12-1e5075516092" width="270" height="400"/>
<img src="https://github.com/user-attachments/assets/d5c52327-c852-4c9e-8c72-262e35b8b064" width="270" height="400"/>
<img src="https://github.com/user-attachments/assets/29859b60-a1d4-4a4f-8938-917121f8b477" width="270" height="400"/>
      
### 2. 비밀번호 재설정
      2-1. 이메일인증을 통한 비밀번호 재설정
      2-2. 이메일을 통해 db에 저장된 아이디 불러옴 > 사용자가 입력한 아이디와 일치여부 확인 후 성공해야 다음 단계 진행
      2-3. 이메일로 받은 링크를 클릭 시 인증확인 후 비밀번호 재설정 페이지로 이동
<img src="https://github.com/user-attachments/assets/ce6fc6bf-0b2a-4570-897c-d7c25470e71a" width="330" height="230"/>
<img src="https://github.com/user-attachments/assets/fe1c5d9a-0d5e-4c74-8336-c0aa1e6859ae" width="330" height="230"/>
<img src="https://github.com/user-attachments/assets/6af29c94-3c1c-4a02-8a53-2cf5eb45448f" width="330" height="230"/>

### 3. 게시판
      3-1. 게시글 작성, 삭제, 수정 기능
      3-2. 첨부파일 등록 가능 (최대 3개)
      3-3. 게시글에 대한 좋아요&싫어요 기능
      3-4. 특정 게시글이 가지고 있는 댓글, 첨부파일의 수, 좋아요&싫어요 여부를 게시판에서 표시
      3-5. 페이징 > 한 페이지에 10개의 게시물 출력
      3-6. 해당 게시판의 인기글 순위(좋아요순) top 5 출력
      3-7. 게시판에 들어가려면 아이디 인증 필요요
<img src="https://github.com/user-attachments/assets/04c1c5fc-4625-43ab-a372-c6aaa814813e" width="300" height="270"/>
<img src="https://github.com/user-attachments/assets/cca4a627-228a-43f8-9cd5-935251b69a8c" width="500" height="270"/>

### 4. 댓글
      4-1. 댓글 작성, 삭제, 수정 기능
      4-2. 댓글에 대한 좋아요&싫어요 기능
댓글작성<br/><br/>
<img src="https://github.com/user-attachments/assets/c1533354-1304-461e-b7d4-71010dbc4cff" width="400" height="230"/>
<img src="https://github.com/user-attachments/assets/5e1e4dd2-d840-453b-a4b8-bb45ad95a75a" width="400" height="230"/><br/>
댓글수정<br/><br/>
<img src="https://github.com/user-attachments/assets/99680a68-7a45-4c31-9d83-fe5e1dcf0ad0" width="400" height="230"/>
<img src="https://github.com/user-attachments/assets/b0ab52c5-e19c-43b8-82bd-886a5f89bc74" width="400" height="230"/>

### 5. 이력서 전송
      5-1. 이름(회사명), 이메일 주소를 입력 시 이력서 pdf 파일을 전송해 주는 기능

<img src="https://github.com/user-attachments/assets/8ce9f134-b14d-434b-ae34-38631eb25931" width="400" height="230"/>
<img src="https://github.com/user-attachments/assets/4efa5cff-4298-4b97-a893-868d8599d1e7" width="400" height="230"/>
<img src="https://github.com/user-attachments/assets/f45e8cef-14f0-4a2c-9fdf-81c737557889" width="400" height="230"/>

### 6. 오늘의 식사메뉴 추천
      6-1. 인덱스 페이지에 구현
      6-2. 메뉴 카테고리를 선택한 후 버튼을 클릭하면 추천 메뉴가 나옴 (기본은 '상관없음' 카테고리)

### 7. 아이디 찾기
	7-1. 이름, 이메일 주소 입력
 	7-2. DB에서 입력한 정보에 해당하는 유저아이디 조회 후 출력
  	7-3. NullPointerException 발생 시 에러메시지 출력
   
<img src="https://github.com/user-attachments/assets/dc082ab1-094e-4f8f-8f4d-8b578c18ef1e" width="400" height="230"/>
<img src="https://github.com/user-attachments/assets/120bb5cc-8ed5-41b8-a059-6ff45ca6094e" width="400" height="230"/>
<img src="https://github.com/user-attachments/assets/4b223b70-17bd-4dcc-8636-4ec766d621e3" width="400" height="230"/>


## 🧚‍♂ 수정사항 및 트러블슈팅

#### 2024-07-19
기존 :
해당 게시물에서 내가 좋아요 or 싫어요를 눌렀을 경우 상태 표시
변경 : 
기존 기능 삭제 후
게시물에 찍힌 전체 좋아요 숫자만 출력하도록 수정

리팩토링
- listPage 메서드 > 기능별 단위로 나눔
- SQL, listService > 첨부파일 수, 댓글 수, 좋아요 수 3개의 쿼리를 하나로 통합하여 성능개선

처음 포트폴리오 사이트에 접속했을 때 memberSeq가 null로잡혀서 발생하는 exception 처리완료


#### 2024-07-21

게시판에서 좋아요순 top 5 표시

#### 2024-07-21

이력서 오타수정, 이력서전송 > 이력서.pdf 재업로드

#### 2024-07-24

로그인기록 남기기

이메일인증 방식 변경
(기존 - 인증링크를 이용한 인증 > 변경 - 인증코드 직접 입력을 통한 인증)</br>
(기존 - 새롭게 회원가입할 경우 이메일인증을 완료해야 로그인 가능</br>> 변경 - 인증을 완료하지 않아도 로그인 가능하나 게시판 들어가려면 인증 필요)</br>

1. 이메일인증이 배포환경에서 안되는 에러발견
2. 로컬에서는 되는데 배포하면 안되는 이유를 못찾겠음 > 메일은 정상적으로 오는데 링크를 클릭하면 blank > 인증방식을 변경하기로 결정
3. 인증완료가 되지 않은 계정도 로그인 가능하게 변경
4. 인증이 되지 않은 계정으로 게시글 작성 시도하면 인증하도록 안내</br>
	3-1. 세션에서 로그인된 사용자의 아이디 가져옴</br>
	3-2. 아이디로 인증여부 비교</br>
	3-3. 인증된 계정이 아닐 경우 안내와 함께 인증페이지로</br>

#### 2024-07-26
1. README에 사진추가
2. 아이디 이메일인증 메일이 안날아옴</br>
	2-1. 회원가입시 member_auth 테이블에 새로운 데이터가 인서트되지 않음</br>
 	2-2. 회원가입시 member_auth 데이터 insert해주는 코드 작성해서 해결</br>

#### 2024-07-24
아이디찾기
1. 이름과 이메일 주소 입력
2. DB에서 이름, 이메일주소와 일치하는 유저아이디 찾아서 출력

Index 페이지 수정
1. 기술스택란 추가


#### 2024-08-20
게시판 클릭시 발생하던 NullPointerException 처리
1. 아이디 인증여부 확인에 들어가는 조건이 authYN.equals("N") 였음</br>
	1-1. 이 코드에서는 authYN이 NULL이면 equals메서드를 호출할 때 NullPointerException 발생함</br>
   	1-2. WHY??? authYN 변수가 NULL인 상태에서 equals("N")을 호출하려고 하기 때문!</br>
   	1-3. "N".equals(authYN) 으로 변경 > 'authYN'이 NULL일 경우에도 안전하게 eqauls 호출 가능

#### 2024-09-14
브라우저 캐시가 없는 비로그인(guest) 상태에서 게시판 진입에러<br/>
변경사항 블로그 URL : https://ksuju.tistory.com/54

