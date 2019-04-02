var bulin1=false;
var bulin2=false;
$(".phonenum").blur(function(){
    var phone = /^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\d{8}$/;
    if(!phone.test($(".phonenum").val())){
        $(".pointer").text("手机号格式不正确！");
    }else {
        $(".pointer").text("");
        bulin1=true;
    }
});
$(".login").click(function () {
    if (bulin1==true){
        var userphone=$(".phonenum").val();
        var userpassword=$(".userpassword").val();
        $.ajax({
            type: "POST",
            url: "/user/login",
            data: {
                "userPhone":userphone,
                "userPassword":userpassword
            },
            async: false,
            success:function (data) {
                if(data.msg == "用户登录成功") {
                    location="/index";
                }
                if(data.msg == "管理员登录成功") {
                    location="/adminindex";
                }
                if(data.msg == "账号或密码错误") {
                    $('.loginError').html("<i class='fa fa-times'></i> 账号或密码错误");
                }
            },
            error:function (data) {
                alert(data)
            }
        });
    }else {
        $(".pointer").text("该账号不存在");
    }
});