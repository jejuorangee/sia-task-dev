# sia-task-dev
## aws S3에 저장된 영상을 COG로 변환 및 메타데이터 DB 저장 후 부여 받은 bucket에 업로드


## 프로젝트 설정
설정 설명
- 이 프로젝트는 GDAL java 바인딩과 네이티브 라이브러리를 사용하는데 시스템 내에 VC++ 런타임과 JDK 설치 경로 내의 동일한 이름을 가진 DLL이 충돌하는 문제가 있습니다.
  
- 충돌 대상  
C:\Program Files\Java\jdk-17\bin\msvcp140.dll
C:\Program Files\Java\jdk-17\bin\vcruntime140.dll
- 문제 증상
EXCEPTION_ACCESS_VIOLATION
 Problematic frame:
 C  [msvcp140.dll+0x132a8]
- GDAL이 로드하려는 msvcp140.dll이 JDK 내 DLL과 충돌하여
의존 라이브러리 연결에 실패하는 현상이 발생 합니다.

## 임시 해결 방법   
1. C:\Program Files\Java\jdk-17\bin 경로로 이동    
2. msvcp140.dll → msvcp140.dll.old   
3. vcruntime140.dll → vcruntime140.old
4. 이후 installDist로 실행된 task.bat 스크립트를 실행

## 이런 방식을 채택한 이유
- 저는 현재 GDAL + Java 연동 구조에 대한 충분한 이해가 부족한 상태였습니다.
- 문제 발생 당시 데드라인 내 구현 완성이 필요했기 때문에 근본적인 충돌 원인을 완전히 해결하지 못하고 우회 방식을 택하게 되었습니다.

## 개선 방향
- PATH를 통한 DLL 우선순위 명확화
- JDK와 DLL 충돌을 막기 위한 별도 실행 환경 구성
ex) Docker 또는 JDK custom build

# 아키텍처
- Java 17 / Spring Boot 3.4.5
- GDAL native 라이브러리 사용
- Dotenv
- 인메모리 H2 DB
- installDist + shadowJar 방식 빌드

## 라이브러리 사용 이유
- GDAL native 라이브러리
  -  COG(Cloud Optimized GeoTIFF)이라는 포맷은 처음 접하는 개념이었고 검색하던 중 가장 먼저 확인된 도구가 GDAL이었습니다. 실제로 널리 사용되고 있다고 생각했고 단순 변환을 넘어 많은 기능을 통해 추후 다양한 데이터를 필요로 할 상황을 고려 했을 때 확장성 있는 선택이라고 생각했습니다.
- Dotenv 라이브러리
  - 시크릿 키나 버킷이름, 경로 등 민감하거나 환경마다 다른  값들을 코드에서 분리할 수 있어 유지보수성이 향상됩니다.
  - 민감한 설정값들을 git에 올리지 않기 위해 사용했습니다.
   - .env 파일만 교체하면 설정을 쉽게 바꿀 수 있어 CI/CD나 배포 자동화에도 유리합니다.

# API 서비스 구성
- 파일 다운로드 -- S3 버킷에서 파일을 로컬에 다운로드 합니다.
- COG 변환 및 메타데이터 추출 -- GDAL을 통해 COG 변환 후 메타데이터를 추출 합니다.
- DB 저장 -- 추출된 메타데이터를 H2 DB에 저장합니다.
- S3 업로드 -- 배정받은 버킷 경로에 COG 파일을 업로드 합니다.
- 데이터 조회 -- DB에 저장된 파일 목록 및 정보를 조회합니다.

# 인프라 구성
- 백엔드 -- Java 17, Spring Boot 3.4.5
- DB -- H2(in-memory, JPA)
- 파일 변환 -- GDAL native dll 사용
- 빌드 방법 -- Gradle installDist + ShadowJar
- 외부 저장소 -- AWS S3

# 사용 방법
- 빌드 및 실행
  - 빌드 --  ./gradlew clean installDist
  - 실행 -- cd build/install/task/bin  ./task bat

# 구글 드라이브
https://drive.google.com/file/d/1P5dFbbtu_7NokdjLdaEc2SzmFdbbNAxb/view?usp=drive_link

# 예상 면접 질문
- GDAL과 Java 연동 시 DLL 충돌 문제를 겪었다고 했는데 이 문제를 루트 원인까지 해결하지 않고 우회하신 이유와 당시 고민했던 구체적인 다른 해결 방법은 무엇인가요?

- installDist와 shadowJar를 함께 사용했는데 이 두 방식의 차이와 병행 사용의 장점을 설명해 주세요.

- Dotenv를 사용했는데 Spring Boot에는 기본적으로 application.properties나 application.yml이 있습니다. 굳이 dotenv를 사용한 이유는 무엇인가요?

# 노션
https://www.notion.so/sia-1de8aad9af6280a59a6ded073602771f?pvs=4
