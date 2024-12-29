# sync-wave 
일정 관리 시스템

## Features
- 사용자 관리
- 스케쥴, todo-List 일정 관리
- Slack 기반 일정 알림 서비스
- 모든 스케쥴 Overview 확인 기능 서비스

## Tech Stack
- Java 21
- Spring Boot 3.2.3
- JPA / Hibernate
- Gradle
- MySQL 8.0
- retrofit 2.7.2
- lombok

## Project Structure
<pre>
.
├── README.md
├── build
│   ├── libs
│   ├── resolvedMainClassName
│   └── tmp
├── build.gradle
├── folder-structure.txt
├── gradle
│   └── wrapper
├── gradlew
├── gradlew.bat
├── out
│   └── production
├── settings.gradle
├── sync-wave-batch
│   ├── build.gradle
│   ├── out
│   ├── src
│   └── sync-wave-batch.iml
├── sync-wave-common
│   ├── build
│   ├── build.gradle
│   ├── out
│   ├── src
│   └── sync-wave-common.iml
└── sync-wave-service
    ├── build
    ├── build.gradle
    ├── out
    ├── src
    └── sync-wave-service.iml

19 directories, 13 files

</pre>

## API Endpoints
- `POST /service/v1/user/signup`: 회원 가입
- `PATCH /service/v1/user/{userId}`: 회원 정보 수정
- `DELETE /service/v1/user/{userId}`: 회원 정보 삭제
- `GET /service/v1/user/list`: 회원 정보 목록 조회
- `GET /service/v1/user/{userId}`: 특정 회원 상세 정보 조회
- `POST /service/v1/auth/login`: 로그인
- `POST /service/v1/todos`: to-do 생성
- `GET /service/v1/todos/{todosId}`: to-do 상세 조회
- `GET /service/v1/todos`: to-do 목록 조회
- `PATCH /service/v1/todos`: to-do 정보 수정
- `DELETE /service/v1/todos/{todosId}`: to-do 정보 삭제
- `POST /service/v1/schedules`: 스케쥴 생성
- `GET /service/v1/schedules/{scheduleId}`: 스케쥴 상세 조회
- `GET /service/v1/schedules`: 스케쥴 목록 조회
- `DELETE /service/v1/schedules/{scheduleId}`: 스케쥴 정보 삭제
- `PATCH /service/v1/schedules/{scheduleId}`: 스케쥴 정보 수정
- `POST /service/v1/file/upload`: 파일 업로드
- `DELETE /service/v1/file/{fileSeq}`: 파일 삭제
- `GET /service/v1/dashboard`: 스케쥴, to-do 대시보드 조회

## Project Documentation

프로젝트의 자세한 내용은 아래 노션 페이지에서 확인할 수 있습니다:  
[🔗 프로젝트 노션 페이지](https://closed-roar-8b6.notion.site/SyncWave-10d08810873880bbba55ef782f590edb?pvs=4)
