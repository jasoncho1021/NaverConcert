# 공연 예매 웹 애플리케이션

* 조회

![concerto](https://user-images.githubusercontent.com/12610035/191224556-24396510-3bab-4746-9015-e51874b33fec.gif)

* 예매, 로그인, 취소

![concerto2](https://user-images.githubusercontent.com/12610035/191225697-b194c1be-1f0a-4046-8e84-63618906401a.gif)

* 리뷰작성

![concertor](https://user-images.githubusercontent.com/12610035/191228508-ef539adc-c1be-4c47-b130-2fdea317f73f.gif)

## 요구사항 및 구조
https://docs.google.com/presentation/d/1i2IC1yIH5ACFCvCH4EMVv_3Zw2oltRvHK94amyNEKbs/edit#slide=id.p22

* Layerd Architecture 를 적용하여 Spring 설정 파일을 Presentation Layer 와 Service, Data Access Layer 로 나눠 관리
* CSR(Client Side Rendering) 방식 활용
* Session 변수와 Interceptor 를 사용하여 로그인 상태 유지 및 페이지 접근 권한 검증 구현  
* 정규표현 식을 사용하여 사용자 입력에 대한 검증 구현
* formData 와 MultipartFile 을 활용한 이미지 업로드 구현
* SpringSecurity를 이용한 인증,인가 구현
* 테스트 코드와 AOP를 사용하여 DB에 직접 값을 넣어보며 테스트 진행
 

## 사용기술 
* MySQL,innodb version : 5.7.32  
* Java version : 1.8.0_161  
* Spring version: 4.3.14.RELEASE  
* OS version : Ubuntu 16.04 LTS  

## 추가학습내용 정리 
https://github.com/jasoncho1021/NaverConcert/wiki

## Swagger
http://49.236.147.192:9090/swagger-ui.html#/

## ERD
![rsvmodel](https://user-images.githubusercontent.com/12610035/191214350-b8580484-3ce9-4726-a210-dc61df648d6e.png)


## [추가] 업로드기능 구현화면
* iframe을 사용하여 기존 서비스 UI 미리보기 가능
* 사진 여러장 업로드시 캐러셀 동작
* Naver 지도 연동하여 지도 표시 및 지번주소, 도로명 주소 검색  
  
![Screenshot from 2022-09-20 17-34-30](https://user-images.githubusercontent.com/12610035/191210034-542241e9-6aca-4463-91bd-df01b2f2212e.png)
![Screenshot from 2022-09-20 17-35-56](https://user-images.githubusercontent.com/12610035/191210044-d147d731-f0b9-42f1-a5d8-df6a6c8a2510.png)

