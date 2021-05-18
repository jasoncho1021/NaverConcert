# NaverConcert
 공연 예매 웹 애플리케이션

# 요구사항
https://docs.google.com/presentation/d/1i2IC1yIH5ACFCvCH4EMVv_3Zw2oltRvHK94amyNEKbs/edit#slide=id.p22

# 세부구현사항  
> MySQL,innodb version : 5.7.32  
> Java version : 1.8.0_161  
> Spring version: 4.3.14.RELEASE  
> OS version : Ubuntu 16.04 LTS  

* Layerd Architecture 를 적용하여 Spring 설정 파일을 Presentation Layer 와 Service, Data Access Layer 로 나눠 관리
* CSR(Client Side Rendering) 방식 활용
* Session 변수와 Interceptor 를 사용하여 로그인 상태 유지 및 페이지 접근 권한 검증 구현

# Swagger
http://49.236.147.192:9090/swagger-ui.html#/

# ERD
![image](https://user-images.githubusercontent.com/12610035/103641702-7d8c4c00-4f95-11eb-9247-00b9ec5e6e39.png)
