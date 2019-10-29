<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Karma">

	<title>Home_Mybatis</title>
	
	<script>
	function setClock() {
		var now = new Date() ;
		var t ='* Current Time: '
			+ now.getFullYear()+'년'+(now.getMonth()+1)+'월'+now.getDate()+'일'
		 	+'_'+now.getHours()+':'+now.getMinutes()+':'+now.getSeconds() ;
		document.getElementById("clock").innerHTML=t;
		setTimeout("setClock()",1000)  // 1/1000 초 단위
	}
	</script>
</head>
<body onload="setClock()">


<div class="w3-main w3-content w3-padding" style="max-width:1200px;height:1000px;margin-top:100px; align:center">

<h2> 찾아오시는 길 </h2>
<br>
<div id="map" style="width:50%;height:50%; height:500px; width:1200px; align:center"></div><br><br>
<script type="text/javascript" 
	    src="//dapi.kakao.com/v2/maps/sdk.js?appkey=aca5c562f0bb1e313c2110fa3ad55173&libraries=services">
</script>

<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
    	center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
    	level: 3 // 지도의 확대 레벨
		};  

//지도를 생성합니다    
var map = new daum.maps.Map(mapContainer, mapOption); 
//주소-좌표 변환 객체를 생성합니다
var geocoder = new daum.maps.services.Geocoder();

//주소로 좌표를 검색합니다
// 제주특별자치도 제주시 첨단로 242
var address = '경기 성남시 분당구 돌마로 46' ;
var description = 'Alba.exIT' ; // description: 설명,묘사

geocoder.addressSearch(address, function(result, status) { 
	// 정상적으로 검색이 완료됐으면
	if (status === daum.maps.services.Status.OK) { 
		
		var coords = new daum.maps.LatLng(result[0].y, result[0].x);  
		// 결과값으로 받은 위치를 마커로 표시합니다
		var marker = new daum.maps.Marker({ map: map, position: coords }); 
		// 인포윈도우로 장소에 대한 설명을 표시합니다 
		var infowindow = new daum.maps.InfoWindow({ 
			 content: '<div style="width:150px;text-align:center;padding:4px 0;">Alba.exIT</div>' }); 
			
		infowindow.open(map, marker); 
		// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다 
		map.setCenter(coords); 
	} // if
	}); // addressSearch

</script>


<div style="color:black;" align="center" >

<b>지 하 철</b> : 미금역 6번출구 50m거리<br>


<b>주 소</b> :성남시 분당구 구미동 7-2(돌마로 46) 5층<br>


<b>연 락 처</b> : 031- 000 -0000

</div>

</div>
</body>
</html>
