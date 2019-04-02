function photoup() {
	$('#fileds').click();
	$('#fileds').change(function() {
		//获取input file的files文件数组;
		//$('#filed')获取的是jQuery对象，.get(0)转为原生对象;
		//这边默认只能选一个，但是存放形式仍然是数组，所以取第一个元素使用[0];
		var file = $('#fileds').get(0).files[0];
		//创建用来读取此文件的对象
		var reader = new FileReader();
		//使用该对象读取file文件
		reader.readAsDataURL(file);
		//读取文件成功后执行的方法函数
		reader.onload = function(e) {
			//读取成功后返回的一个参数e，整个的一个进度事件
			//选择所要显示图片的img，要赋值给img的src就是e中target下result里面
			//的base64编码格式的地址
			$('#imgshow').get(0).src = e.target.result;
		}
	})
}
//打开和关闭密码修改
function password(){
	$('.pass').css('left','0%');
}
function closepassword(){
	$('.pass').css('left','100%');
}
//确认修改的密码
function yespass(){
   newpass();
	if ( bb == 1){
	    $('.finish').removeClass('none').addClass('block');
        setTimeout(function () {
            location="/user/toLogin";
        },2000);

    }
}
$('.footer-finish').on('click',function () {
	$('.phosub').click();
})

function pass() {
    $.ajax({
        type: "Post",//数据发送的方式（post 或者 get）
        url: "/user/updatePsw",//要发送的后台地址
        data: {
            userPassword : $('.oldpass').val(),
            newUserPassword:$('.newpass').val(),
        },//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
        dataType:"JSON",
        success: function (data) {
            console.log(data.msg);
            if(data.msg == "旧密码不能为空"){
                $('.passone').text(data.msg);

            }else if(data.msg == "验证密码错误，请重新输入"){
                $('.passone').text(data.msg);

            }else if(data.msg == "新密码不能为空"){
                $('.passthr').text(data.msg);

            }else if(data.msg == "新密码设置过短，请重新输入"){
                $('.passtwo').text(data.msg);

            }else if(data.msg == "新密码不能与旧密码相同，请重新输入"){
                $('.passtwo').text(data.msg);

            }else {
                yespass();
            }
        },
        error: function (msg) {//ajax请求失败后触发的方法
            alert("网络错误");//弹出错误信息
        }
    });
}
var bb=0;
function newpass() {
    if ($('.newp').val() != $('.newpass').val()){
        $('.passthr').text('密码不一致');
         bb=0;
    }else{
         bb=1;
    }
}
function clearp() {
    $('.passone').text("");
    $('.passtwo').text("");
    $('.passthr').text("");
}