$('.allx').on('click',function () {
	$('.black').removeClass('none').addClass('block');
	$('.bottom').css('bottom','0%');
	$('.ids').val($(this).siblings("input").val());
    $('.mesid').val($(this).siblings("div").text())
})
function closeback() {
    $('.black').removeClass('block').addClass('none');
    $('.bottom').css('bottom','-100%');
}
$('.black').on('click',function () {
    closeback();
})
$('.quxiao').click(function () {
    closeback();
})
//删除足迹
$('.deletes').on('click',function () {
    $.ajax({
        type: "Post",//数据发送的方式（post 或者 get）
        url: "/browse/deleteBrowse"  ,//要发送的后台地址
        data: {
            browseId :$('.ids').val() ,
        },//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
        dataType:"JSON",
        success: function (data) {
            location.reload();
        },
        error: function (msg) {//ajax请求失败后触发的方法
            alert("网络错误");//弹出错误信息
        }
    });
})
//添加收藏
$('.collon').on('click',function () {
    $.ajax({
        type: "Post",//数据发送的方式（post 或者 get）
        url: "/browse/removeCollect"  ,//要发送的后台地址
        data: {
            messageId:$('.mesid').val(),
            browseId :$('.ids').val() ,
        },//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
        dataType:"JSON",
        success: function (data) {
            console.log(data.msg);
            location.reload();
        },
        error: function (msg) {//ajax请求失败后触发的方法
            alert("网络错误");//弹出错误信息
        }
    });
})
//退出登录
$(".Icon").click(function () {
    $.ajax({
        type: "POST",
        url: "/user/cancellation",
        success:function () {
            location="/index";
        }
    })
});
