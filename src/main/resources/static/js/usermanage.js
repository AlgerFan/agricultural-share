//搜索出现与消失
$('.user-search').on('click', function() {
	$('.top-sech').css('width', '85%');
	$('.top-sech').css('height', '30px');
	$('.searchback').removeClass('none').addClass('block');
})
$('.searchback').on('click', function() {
	$('.top-sech').css('width', '0px');
	$('.top-sech').css('height', '0px');
	$('.searchback').removeClass('block').addClass('none');
})
// //修改头像
// function inforpho(){
// 		$('#fileds').click();
// 		$('#fileds').change(function() {
// 			//获取input file的files文件数组;
// 			//$('#filed')获取的是jQuery对象，.get(0)转为原生对象;
// 			//这边默认只能选一个，但是存放形式仍然是数组，所以取第一个元素使用[0];
// 			var file = $('#fileds').get(0).files[0];
// 			//创建用来读取此文件的对象
// 			var reader = new FileReader();
// 			//使用该对象读取file文件
// 			reader.readAsDataURL(file);
// 			//读取文件成功后执行的方法函数
// 			reader.onload = function(e) {
// 				//读取成功后返回的一个参数e，整个的一个进度事件
// 				//选择所要显示图片的img，要赋值给img的src就是e中target下result里面
// 				//的base64编码格式的地址
// 				$('#imgshow').get(0).src = e.target.result;
// 			}
// 		})
// }
//打开修改
$('.manage-sign').on('click', function(){
	$('.modi').css('left','0%');
	$('#imgshow').attr('src',$(this).find('.userimg').attr('src'));
	$('.userName').val($(this).find('.username').text());
    $('.userPhone').val($(this).find('.userphone').text());
    $('.userPass').val($(this).find('.userpass').val());
    $('.userId').val($(this).find('.userid').val());
    var options=$('option');
    for (var i=0;i<options.length;i++){
        if ($(this).find('.userrole').val() == $(options[i]).val()) {
            $(options[i]).attr("selected",true);
        }else {
            $(options[i]).attr("selected",false);
        }
	}
	if($(this).find('.userrole').val() == 1){
        $('.list-right').css('display','none');
    }else {
        $('.list-right').css('display','block');
    }
}) 
$('.header-title').on('click', function(){
	$('.modi').css('left','100%');
}) 
//点击修改资料
$('.list-left').on('click',function () {
    if(confirm("确定要进行修改吗？？")){
        $.ajax({
            type: "Post",//数据发送的方式（post 或者 get）
            url: "/admin/user/update",//要发送的后台地址
            data: {
                userId : $('.userId').val(),
                userName:$('.userName').val(),
                userPassword:$('.userPass').val(),
                userPhone:$('.userPhone').val(),
                userRole:$('.userRole').val(),
            },//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
            dataType:"JSON",
            success: function (data) {
                console.log(data.msg);
                $('.yesgen').text(data.msg);
                if(data.msg == "修改成功"){
                    $('.yesgen').css({"width":"40%","height":"40px"});
                    yesaddblock();
                    setTimeout(function () {
                        yesaddnone();
                        location.reload();
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

})
//修改资料的提示信息
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
//查找用户
// function finduser() {
//    $('.upfind').click()
// }
$('.top-searchs').click(function () {
    $('.upfind').click();
})

//删除用户
function deluser() {
    if(confirm("确定要进行删除吗？？")){
        $.ajax({
            type: "Post",//数据发送的方式（post 或者 get）
            url: "/admin/user/deleteUser"  ,//要发送的后台地址
            data: {
                userId : $('.userId').val(),
            },//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
            dataType:"JSON",
            success: function (data) {
                console.log(data.msg);
                $('.yesgen').text(data.msg);
                yesaddblock();
                // if(data.msg == "修改成功"){
                //     $('.yesgen').css({"width":"40%","height":"40px"});
                //     yesaddblock();
                //     setTimeout(function () {
                //         yesaddnone();
                //         location.reload();
                //     },1000);
                // }else if(data.msg == "用户名或密码或手机号不符合要求") {
                //     $('.yesgen').css({"width":"50%","height":"80px"});
                //     yesaddblock();
                //     setTimeout(function () {
                //         yesaddnone();
                //     },2000);
                // }else {
                //     $('.yesgen').css({"width":"40%","height":"40px"});
                //     yesaddblock();
                //     setTimeout(function () {
                //         yesaddnone();
                //     },2000);
                // }
            },
            error: function (msg) {//ajax请求失败后触发的方法
                alert("网络错误");//弹出错误信息
            }
        });
    }
}
$('.list-right').on('click',function () {
    deluser();
})