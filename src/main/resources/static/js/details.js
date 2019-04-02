$(document).ready(function () {
    $(".collect1").click(function () {
        var messageId =$(".inp").val();
        $.ajax({
            url:"/collect",
            type:"post",
            data:{
              "messageId":messageId
            },
            success:function () {
                location.reload();
            }
        })
    })
});