# Game name : The Great Dalmuti

#### *2016.01.04 ~ 2016.02.12*  

- Team Member & Role
	- 설 훈(본인) : 로그인 및 회원가입, 메인 화면, 대기실 구현
	- 박현수(팀장) : 서버 및 게임 기능 구현
	- 유재영 : DB구축 및 게임 기능 구현
	- 성민재 : 캐릭터 디자인 및 대기실 구현


#### *2017-07-14 ~ *  

- Type : 팀 프로젝트 -> 개인 프로젝트
- IDE : Spring Tool Suite
- JAVA : 1.8
- OS : OSX  
- Database : Oracle 11g

## 1. 프로젝트 목표.
- Thread > Multi Thread
- 기존 게임의 제한된 것을 리팩토링하는 것
- 사용자가 나갈 시 자체 로직을 만들어 AI가 진행 할 수 있게 만드는 것.(**)
- Docker Adapting


## 2. 문제점 및 해결방법 기록  
채팅기능과 간단한 스레드를 통해 여러 사람이 함께 즐길 수 있는 게임을 만들면서 문제점과 해결하는 과정을 기록하겠습니다.
	* - 구현 예정.


## 3. 실행방법
	* 실행 순서.	
	- 1. ChatServer를 실행한다.
	- 2. ChatClient를 실행한다.(2개)
	- 3. ChatClient에 Address(127.0.0.1) - 로컬환경, 혹은 ChatServer를 실행한 Address를 입력한다.
	- 4. 닉네임을 입력한다.
	- 5. 텍스트를 입력한다.
