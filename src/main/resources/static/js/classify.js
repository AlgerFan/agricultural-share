$(document).ready(function() {
	$('.user-search').on('click', function() {
		$('.top-sech').css('width', '85%');
		$('.top-sech').css('height', '30px');
		$('.searchback').removeClass('none').addClass('block');
	});
	$('.searchback').on('click', function() {
		$('.top-sech').css('width', '0px');
		$('.top-sech').css('height', '0px');
		$('.searchback').removeClass('block').addClass('none');
	});
	$(".user-add").click(function(){
		$(".add0").fadeToggle();
		$(".add1").fadeToggle();
		$(".add2").fadeToggle();
	});
    $(".top-searchs").click(function () {
        $(".search").click();
    });
	$(".del").click(function(){
        if(confirm("确定要进行删除吗？？")) {
            $.ajax({
                type: "Post",
                url: "/admin/category/deleteCategory",
                data: {
                    userId: $(this).prev().val()
                },
                dataType: "JSON",
                success: function (data) {
                    alert(data.msg);
                },
                error: function () {
                    alert("网络错误");
                }
            });
        }
    })
});