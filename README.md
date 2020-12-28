# 프로젝트 소개
- 제목 : 오늘 뭐 볼까?
- 구분 : 개인 프로젝트
- 개발환경 - 안드로이드 스튜디오
- 사용 언어 - Kotlin
- 내용  
영화 / TV프로그램 추천  
인기 순, 평점 순, 개봉예정 작품등 소개  
예고편 있을 시 예고편 제공
---
# 개요
![image1](https://github.com/jdsaeyqo/Project---MovieApp/blob/master/app/src/main/res/drawable/_movieapp_image1.jpg)


![image2](https://github.com/jdsaeyqo/Project---MovieApp/blob/master/app/src/main/res/drawable/_movieapp_image2.jpg)  

- RecyclerView
- Retrofit 사용하여 TMDb API 활용
- 영화 : 인기 순, 평점 순, 개봉 예정 작품  
- TV프로그램 : 인기 순, 평점 순, 현재 방영중인 작품

![image3](https://github.com/jdsaeyqo/Project---MovieApp/blob/master/app/src/main/res/drawable/_movieapp_image3.jpg)  

![image4](https://github.com/jdsaeyqo/Project---MovieApp/blob/master/app/src/main/res/drawable/_movieapp_image4.jpg)

- 작품 클릭 시 영화에 대한 소개와 포스터그림 제공  
- YouTubePlayer API 활용하여 예고편 있을 시 포스터 그림 대신 예고편 제공



#Library
~~~kotlin  
 //retrofit
   implementation com.squareup.retrofit2:retrofit:2.8.1
   implementation com.squareup.retrofit2:converter-gson:2.8.1  
// support
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    implementation 'androidx.cardview:cardview:1.0.0'      
// gson
    implementation 'com.google.code.gson:gson:2.8.6'  
// glide
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
~~~
