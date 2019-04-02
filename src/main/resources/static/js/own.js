$(document).ready(function(){
	$(".select").click(function(){
		$(".allse").css("height","auto");
		$(".allse").css("animation","myfirst 3s");
		$(".allse").css("box-shadow","0px 7px 5px -8px #5E5E5E;");
	});
	$(".select1").click(function(){
		$(".allse").css("height","auto");
		$(".allse").css("animation","myfirst 3s");
        $(".allse").css("box-shadow","0px 7px 5px -8px #5E5E5E;");
		$(".select").hide();
	});
	$(".alls2").click(function(){
		var kind=$(this).text();
		$(".kind").val(kind);
		$(".kind").val(kind);
		$(".allse").css('height','0');
		$(".allse").css("animation","none");
		$(".select").show();
	});
	$(".Icon").click(function () {
        $.ajax({
            type: "POST",
            url: "/user/cancellation",
            success:function () {
                location="/index";
            }
        })
    });
   $(".select").click(function () {
       $.ajax({
           type: "Post",
           url: "/productCategoryList",
           dataType: "JSON",
           success: function (data) {
                alert(data)
           },
           error: function () {

           }
       })
   });
    $(".userName").blur(function(){
        var username = /^[\u4E00-\u9FA5A-Za-z]+$/;
        if(!username.test($(".userName").val())){
            $(".remind").text("姓名格式不正确！");
            $(".remind").css("animation","myfirst5 2s");
            setTimeout(function(){
                $(".remind").css("animation","none");
            },2001)
        }
    });
    $(".userphone").blur(function(){
        var username = /^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\d{8}$/;
        if(!username.test($(".userphone").val())){
            $(".remind").text("手机号格式不正确！");
            $(".remind").css("animation","myfirst5 2s");
            setTimeout(function(){
                $(".remind").css("animation","none");
            },2001)
        }
    });
});