// $('.login').on('click',function () {
//     $.ajax({
//         type: "Post",//数据发送的方式（post 或者 get）
//         url: "/user/registered",//要发送的后台地址
//         data: {
//             userName:$('.userName').val(),
//             userPhone:$('.phonenum').val(),
//             userPassword:$('.userPassword').val(),
//         },//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
//         dataType:"JSON",
//         success: function (data) {
//             console.log(data.msg);
//         },
//         error: function (msg) {//ajax请求失败后触发的方法
//             alert("网络错误");//弹出错误信息
//         }
//     });
// })
$('.login').on('click',function () {
    $('.usersub').click();
});
//用户手机号进行判断
var phone=0;
$('input[name="userPhone"]').change(function () {
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
                console.log(data.msg);
                console.log($('.nump').text());
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
                console.log(phone)
            },
            error: function (msg) {//ajax请求失败后触发的方法
                alert("网络错误");//弹出错误信息
            }
        });
    }
})
//用户昵称判断
var name=0;
$('input[name="userName"]').change(function () {
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
                $('namep').text(data.msg);
                name=0;
            }
            console.log(name);
        },
        error: function (msg) {//ajax请求失败后触发的方法
            alert("网络错误");//弹出错误信息
        }
    });
})
//用户密码判断
function passw(){
    if($('.passWord').val() != $('.passWords').val()){
        $('.passp').text('密码不一致');
        return false;
    }else {
        $('.passp').text('');
        return true;
    }
}
$('input[name="userPassword"]').change(function () {
   passw();
})
function check() {
    if (phone==0){
        $('.nump').text('请重新填写手机号');
        return false;
    }else if(passw() != true){
        $('.passp').text('请重新填写密码');
        return false;
    }else if(name == 0){
        $('.namep').text('请重新填写昵称');
        return false;
    }else {
        return true;
    }
}