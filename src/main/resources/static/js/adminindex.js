$(document).ready(function(){
	$(".user").click(function(){
		$(".menu").fadeToggle();
	});
    $(".tologin").click(function () {
        $.ajax({
            type: "POST",
            url: "/user/adminCancellation",
            success:function () {
                location="/user/toLogin";
            }
        })
    });
});
