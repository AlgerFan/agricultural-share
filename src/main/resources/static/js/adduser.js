//上传头像
// function photoup() {
// 	$('#imgshow').removeClass('none').addClass('block');
// 	$('.spanimg').addClass('none').removeClass('block');
// 	addphoto();
// }
//更改头像；
// function addphoto(){
// 	$('#fileds').click();
// 	$('#fileds').change(function() {
// 		//获取input file的files文件数组;
// 		//$('#filed')获取的是jQuery对象，.get(0)转为原生对象;
// 		//这边默认只能选一个，但是存放形式仍然是数组，所以取第一个元素使用[0];
// 		var file = $('#fileds').get(0).files[0];
// 		//创建用来读取此文件的对象
// 		var reader = new FileReader();
// 		//使用该对象读取file文件
// 		reader.readAsDataURL(file);
// 		//读取文件成功后执行的方法函数
// 		reader.onload = function(e) {
// 			//读取成功后返回的一个参数e，整个的一个进度事件
// 			//选择所要显示图片的img，要赋值给img的src就是e中target下result里面
// 			//的base64编码格式的地址
// 			$('#imgshow').get(0).src = e.target.result;
// 		}
// 	})
// }


//用户手机号进行判断
var phone=0;
$('input[name="userPhone"]').blur(function () {
    if (!(/^1(3|4|5|7|8)\d{9}$/.test($(this).val()))){
        $('.nump').text('手机号格式错误');
        phone=0;
    }else {
        $.ajax({
            type: "Post",//数据发送的方式（post 或者 get）
            url: "/user/checkUserPhone",//要发送的后台地址
            data: {
                userPhone:$(this).val(),
            },//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
            dataType:"JSON",
            success: function (data) {
                if(data.msg == "该手机号可以使用"){
                    $('.nump').text("");
                    phone=1;
                }else if(data.msg == "该手机号已经存在"){
                    $('.nump').text(data.msg);
                    phone =0;
                }else {
                    $('.nump').text(data.msg);
                    phone=0;
                }
            },
            error: function (msg) {//ajax请求失败后触发的方法
                alert("网络错误");//弹出错误信息
            }
        });
    }
})
$('input[name="userPhone"]').focus(function () {
    $('.nump').text('');
})
//用户昵称判断
var name=0;
$('input[name="userName"]').blur(function () {
    $.ajax({
        type: "Post",//数据发送的方式（post 或者 get）
        url: "/user/checkUserName",//要发送的后台地址
        data: {
            userName:$(this).val(),
        },//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
        dataType:"JSON",
        success: function (data) {
            console.log(data.msg);
            if(data.msg == "该用户名可以使用"){
                $('.namep').text(" ");
                name=1;
            }else if(data.msg == "该用户名已经存在"){
                $('.namep').text(data.msg);
                name =0;
            }else {
                $('.namep').text(data.msg);
                name=0;
            }
        },
        error: function (msg) {//ajax请求失败后触发的方法
            alert("网络错误");//弹出错误信息
        }
    });
})
$('input[name="userName"]').focus(function () {
    $('.namep').text('');
})
//用户密码判断
function passw(){
    if($('.userPass').val() != $('.passPassWord').val()){
        $('.passp').text('密码不一致');
        $('.passP').text('密码不一致');
        return false;
    }else {
        $('.passp').text('');
        $('.passP').text('');
        return true;
    }
}
$('input[name="userPassword"]').blur(function () {
    passw();
})
$('input[name="userPassword"]').focus(function () {
    $('.passp').text('');
    $('.passP').text('');
})
$('input[name="userPassWord"]').focus(function () {
    $('.passp').text('');
    $('.passP').text('');
})
//确认添加用户
function check() {
    if (phone == 0){
        $('.nump').text("请重新输入手机号");
    }else if( passw() == false ){
        $('.nump').text("请重新输入密码");
        $('.numP').text("请重新输入密码");
    }else if(name == 0){
        $('.namep').text("请重新输入昵称");
    }else {
        $.ajax({
            type: "Post",//数据发送的方式（post 或者 get）
            url: "/admin/user/addUser",//要发送的后台地址
            data: {
                userPassword : $('.userPass').val(),
                userName:$('.userName').val(),
                userPhone:$('.userPhone').val(),
            },//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
            dataType:"JSON",
            success: function (data) {
                console.log(data.msg);
                $('.yesgen').text(data.msg);
                if(data.msg == "添加成功"){
                    $('.yesgen').css({"width":"40%","height":"40px"});
                    yesaddblock();
                    setTimeout(function () {
                        yesaddnone();
                        location='/admin/user/adminUserList';
                    },1000);
                }else if(data.msg == "用户名或密码或手机号不符合要求") {
                    $('.yesgen').css({"width":"50%","height":"80px"});
                    yesaddblock();
                    setTimeout(function () {
                        yesaddnone();
                    },2000);
                }else {
                    $('.yesgen').css({"width":"40%","height":"40px"});
                    yesaddblock();
                    setTimeout(function () {
                        yesaddnone();
                    },2000);
                }

            },
            error: function (msg) {//ajax请求失败后触发的方法
                alert("网络错误");//弹出错误信息
            }
        });
    }

}
$('.login').click(function () {
    check();
})

//添加用户的提示信息
function yesaddblock() {
    $('.yesgen').removeClass('none').addClass('block');
    $('.yesgenback').removeClass('none').addClass('block');
}
function yesaddnone() {
    $('.yesgen').removeClass('block').addClass('none');
    $('.yesgenback').removeClass('block').addClass('none');
}
$('yesgenback').click(function () {
    yesaddnone();
});