<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="百度地图,百度地图API，百度地图自定义工具，百度地图所见即所得工具" />
    <meta name="description" content="百度地图API自定义地图，帮助用户在可视化操作下生成百度地图" />
    <title>百度地图API自定义地图</title>
    <!--引用百度地图API-->
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=M8Ej0Vd6nGlizfrHp8iidee4GqNWMe2a"></script>
</head>

<body>
<!--百度地图容器-->
<input type="text" name="searchValue" id="searchValue" />
<input type="text" name="result" id="result" />
<input type="button" value="查询" onclick="searchPOI()">
<div style="width:700px;height:550px;border:#ccc solid 1px;font-size:12px" id="map"></div>
</body>
<script type="text/javascript">
    //创建和初始化地图函数：
    function initMap(){
        createMap();//创建地图
        setMapEvent();//设置地图事件
        addMapControl();//向地图添加控件
        addMapOverlay();//向地图添加覆盖物
    }
    function createMap(){
        map = new BMap.Map("map");
        //map.centerAndZoom(new BMap.Point(120.083041,30.291631),15);
        map.centerAndZoom("杭州", 12);
    }
    function setMapEvent(){
        map.enableScrollWheelZoom();
        map.enableKeyboard();
        map.enableDragging();
        map.enableDoubleClickZoom()
    }
    function addClickHandler(target,window){
        target.addEventListener("click",function(){
            target.openInfoWindow(window);
        });
    }
    function addMapOverlay(){
        var markers = [
            {content:"我的备注",title:"我的标记",imageOffset: {width:0,height:3},position:{lat:30.288388,lng:120.092096}}
        ];
        for(var index = 0; index < markers.length; index++ ){
            var point = new BMap.Point(markers[index].position.lng,markers[index].position.lat);
            var marker = new BMap.Marker(point,{icon:new BMap.Icon("http://api.map.baidu.com/lbsapi/createmap/images/icon.png",new BMap.Size(20,25),{
                imageOffset: new BMap.Size(markers[index].imageOffset.width,markers[index].imageOffset.height)
            })});
            var label = new BMap.Label(markers[index].title,{offset: new BMap.Size(25,5)});
            var opts = {
                width: 200,
                title: markers[index].title,
                enableMessage: false
            };
            var infoWindow = new BMap.InfoWindow(markers[index].content,opts);
            marker.setLabel(label);
            addClickHandler(marker,infoWindow);
            map.addOverlay(marker);
        };
    }
    //向地图添加控件
    function addMapControl(){
        var scaleControl = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
        scaleControl.setUnit(BMAP_UNIT_IMPERIAL);
        map.addControl(scaleControl);
        var navControl = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
        map.addControl(navControl);
        var overviewControl = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:true});
        map.addControl(overviewControl);
    }
    var map,myGeo;
    initMap();
    searchPOI();

    function searchPOI(){
        var houseMsgs = "${houseMsgs}";
        alert(houseMsgs[2]);
        var users = JSON.stringify(houseMsgs);
        alert(users);
        for( var j = 0;j < users.length;j++){
            console.log(users[j]);
        }
        myGeo = new BMap.Geocoder();
        for(var i = 0;i < locations.length;i++){
            var address = locations[i];
            geocodeSearch(address);
        }
    }
    function geocodeSearch(address){
        myGeo.getPoint(address, function(point){
            if (point) {
                var locpoint = new BMap.Point(point.lng, point.lat);
                addMarker(locpoint,new BMap.Label(address,{offset:new BMap.Size(20,-10)}));
            }
        }, "杭州市");
    }
    //创建标注
    function addMarker(point,label){
        var marker = new BMap.Marker(point);
        map.addOverlay(marker);
        marker.setLabel(label);
    }
</script>
</html>