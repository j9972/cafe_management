# cafe management system1

## Package Explain

### rest 
-  define the path the end point of this api <br>

### DAO 
- change data type for using in controller<br>

### utils 
- for bills' pdf file copy & print (not essential but additional)<br>

### constants 
- for const variable ( I can use const variable directly without package )<br>

### wrapper 
- auto type conversion<br>

### JWT 
- checking(valid, expire time etc) token<br>

## EndPoints

### 회원가입 페이지에서 회원가입 데이터 전송

Post /user/signup

ex) http://localhost:4200/user/signup

### 로그인 페이지에서 유저(관리자포함) 데이터 전송

Post /user/login

ex) http://localhost:4200/user/login

### admin 입장에서 모든 일반 유저 데이터 가져오기

Get /user/get

ex) http://localhost:4200/user/get

### admin data 수정

Post /user/update

ex) http://localhost:4200/user/update

### 토큰 확인 

Get /user/checkToken

ex) http://localhost:4200/user/checkToken

### 비번 변경

Post /user/changePassword

ex) http://localhost:4200/user/changePassword

### 비번 찾기

Post /user/forgotPassword

ex) http://localhost:4200/user/forgotPassword

### 카테고리 추가

Post /category/add

ex) http://localhost:4200/category/add

### 카테고리 목록 가져오기

Post /category/changePassword

ex) http://localhost:4200/category/get

### 카테고리 목록 수정하기

Post /category/update

ex) http://localhost:4200/category/update

### 상품 추가하기

Post /product/add

ex) http://localhost:4200/product/add

### 상품 모든 목록 가져오기

Get /product/get

ex) http://localhost:4200/product/get

### 상품 수정하기

Post /product/update

ex) http://localhost:4200/product/update

### 상품 삭제하기

Post /product/delete/{id}

ex) http://localhost:4200/product/delete/{id}

### 상품 상태 수정하기

Post /product/updateStatus

ex) http://localhost:4200/product/updateStatus

### 상품 카테고리 가져오기

Get /product/getByCategory/{id}

ex) http://localhost:4200/product/getByCategory/{id}

### 상품 가져오기

Get /product/getById/{id}

ex) http://localhost:4200/product/getById/{id}

### 영수증 만들기

Post /bill/generateReport

ex) http://localhost:4200/bill/generateReport

### 영수증 불러오기

Get /bill/getBills

ex) http://localhost:4200/bill/getBills

### pdf file (Bill) 만들기 

Post /bill/getBills

ex) http://localhost:4200/bill/getBills

### 영수증 삭제하기

Post /bill/delete/{id}

ex) http://localhost:4200/bill/delete/{id}

### 대시보드 - 간략하게 전체 내용 보여주기

Get /dashboard/details

ex) http://localhost:4200/dashboard/details


## Annotation 추가 설명 및 일반 설명
1. Jpa = @NamedQuery ( 정적 쿼리 ) </br>
   - application 로딩 시점에 JPQL 문법 체크후 미리 파싱해두기 ( 빠른오류 확인 가능, 재사용성 좋음 -> 조회시 최적화 )

1-1. @NamedQuery는 Entity에 작성, @Query는 repository에 ( 내 파일에서는 Dao 파일에 작성하면 된다)
   - @NamedQuery(name = "Bill.getAllBills", query = "select b from Bill b order by b.id desc")
   - @Query("select b from Bill b order by b.id desc") </br>
    List<Bill> getAllBills();


2. @Data ( Entity 부분에서 사용 )</br>
   - 기본 생성자 ( @NoArgus )</br>
   - 파라미터 모든걸 받는 생성자 ( @AllArgus )</br>
   - getter, setter 메소드 만들어 줘야 한다</br>


3. Gson 사용 이유
   - 자바 객체 <-> Json 구조를 띄는 직렬화된 데이터 변환</br>


4. getString(), toString()
   - getString() : 열에 있는 데이터를 string 형으로 받아온다
   - toString() : 객체가 가지고 있는 정보나 값들을 문자열로 만들어 리턴하는 메소드