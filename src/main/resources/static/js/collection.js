$(document).ready(function(){
	$(".goods-cz").on("click",function(){
		$(".goods-phone1").fadeOut();
		$(".goods-phone2").fadeOut();
		$(this).next().fadeToggle();
		$(this).next().next().fadeToggle();
	});
	//读取数据
    function GetSlideAngle(dx, dy) {
        return Math.atan2(dy, dx) * 180 / Math.PI;
    }
    function GetSlideDirection(startX, startY, endX, endY) {
        var dy = startY - endY;
        var dx = endX - startX;
        var result = 0;
        if(Math.abs(dx) < 2 && Math.abs(dy) < 2) {
            return result;
        }
        var angle = GetSlideAngle(dx, dy);
        if (angle >=0 && angle < 180) {
            result = 1;
        }else{
            result = 2;
        }
        return result;
    }
    var startX, startY;
    document.addEventListener('touchstart',function (ev) {
        startX = ev.touches[0].pageX;
        startY = ev.touches[0].pageY;
    }, false);
    document.addEventListener('touchend',function (ev) {
        var endX, endY;
        endX = ev.changedTouches[0].pageX;
        endY = ev.changedTouches[0].pageY;
        var direction = GetSlideDirection(startX, startY, endX, endY);
        switch(direction) {
            case 0:
                break;
            case 1:
                if( yesbottom() == 1){
                    $.ajax({
                        type: "GET",//数据发送的方式（post 或者 get）
                        url: "/collect/search",//要发送的后台地址
                        data: {
                            "page":1,
                        },//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
                        dataType:"JSON",
                        success: function (data) {//ajax请求成功后触发的方法
                            console.log(data);
                            var lis="<div>";
                                $('.allcoll').append(lis);
                        },
                        error : function (msg) {//ajax请求失败后触发的方法
                            alert("网络故障");
                        }
                    });
                }
                break;
            case 2:
                if($(window).scrollTop() <= 100){

                }
                break;
            default:
        }
    }, false);
    function yesbottom(){
        var bodyheights=$(document.body).height();
        var top=$(window).scrollTop();
        var pingheight=$(window).height();
        var bottom= bodyheights - top -pingheight;
        if(bottom <=10){
            return 1;
        }else {
            return 0;
        }
    }
    $(".goods-phone1").click(function () {
        var asd=$(this).parent().parent();
        var clicks=$(this).next().val();
        if (confirm("确定删除么？")){
            $.ajax({
                url:"/collect/deleteCollect",
                type:"post",
                data:{
                    "collectId":clicks
                },
                success:function () {
                    asd.slideUp();
                },
                error:function () {
                    alert("网络错误")
                }
            })
        }
    })
});