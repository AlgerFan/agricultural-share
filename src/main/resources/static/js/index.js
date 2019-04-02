$(document).ready(function(){
	$(".fortab").click(function(){
	var number=$(".fortab").index(this);
	$(this).addClass("checked");
	$(this).siblings().removeClass("checked");
	$(".tablelist:eq("+number+")").show();
	$(".tablelist:eq("+number+")").siblings().hide();
	});
	var top=0;
	var num=document.getElementsByClassName("new").length;
	setInterval(function(){
		top-=20;
		if(top==(num-1)*(-20)){
			document.getElementsByClassName("today-text-long")[0].style.transition="0s";
			document.getElementsByClassName("today-text-long")[0].style.top="0px";
			top=0;
		}else{
			document.getElementsByClassName("today-text-long")[0].style.transition="1s";
			document.getElementsByClassName("today-text-long")[0].style.top=top+"px";
		}
	},3500)
//	setInterval(function(){
//		var allimgs=document.getElementsByClassName("img");
//		var i=0;
//		var time=setInterval(function(){
//			allimgs[i].style.width="5.3%";
//				i+=1;
//			if(i==20){clearInterval(time)}
//			},70)
//		setTimeout(function(){
//			var allimgs=document.getElementsByClassName("img");
//			var i=0;
//			var time=setInterval(function(){
//				allimgs[i].style.width="0px";
//					i+=1;
//				if(i==20){clearInterval(time)}
//				},70)
//		},4000)
//	},8000)
	//上下变化
	setInterval(function(){
		var allimgs=document.getElementsByClassName("img");
		var i=0;
		var time=setInterval(function(){
			allimgs[i].style.height="100px";
				i+=1;
			if(i==20){clearInterval(time)}
			},70);
		setTimeout(function(){
			var allimgs=document.getElementsByClassName("img");
			var i=0;
			var time=setInterval(function(){
				allimgs[i].style.height="0px";
					i+=1;
				if(i==20){clearInterval(time)}
				},70)
		},5000)
	},10000);
    $(".Icon").click(function () {
        $.ajax({
            type: "POST",
            url: "/user/cancellation",
            success:function () {
                location="/index";
            }
        })
    });
    $(".jilu").click(function () {
		alert("暂未开通该服务")
    })
    $(".touser").click(function () {
        $(".tolist").click();
    })
});
