$(document).ready(function(){
    $(".allneeds").click(function(){
        if($(this).children("div.deleteneed").hasClass('none')){
            $(".deleteneed").removeClass('block').addClass('none');
            $(".allneeds").css("box-shadow","none");
            $(this).children("div.deleteneed").addClass('block').removeClass('none');
            $(this).css("box-shadow","0px 0px 15px #f68a45 inset");
        }else{
            $(this).children("div.deleteneed").addClass('none').removeClass('block');
            $(this).css("box-shadow","none");
        }
    })
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
    $('.deleteneed').on('click',function () {
        if(confirm("确定要进行删除吗？？")){
            $.ajax({
                type: "Post",//数据发送的方式（post 或者 get）
                url: "/admin/deleteMessage"  ,//要发送的后台地址
                data: {
                    messageId : $(this).siblings("input").val(),
                },//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
                dataType:"JSON",
                success: function (data) {
                    alert('删除成功');
                    // location.reload();
                },
                error: function (msg) {//ajax请求失败后触发的方法
                    alert("网络错误");//弹出错误信息
                }
            });
        }
    })
    $('.top-searchs').on('click',function () {
        $('.submess').click();
    })
})