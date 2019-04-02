//选择需求  左右进行切换
$('.needs-left p:first').addClass('active');
$('.types').on('click',function(){
	var all = $('.types');
	for(var i = 0; i < all.length; i++) {
		$(all[i]).removeClass('active');
	}
	$(this).addClass('active');


})
//需求详情显现出来
function addblock() {
	$('.needback').removeClass('none').addClass('block');
	$('.need').css('left', '0%');

}
$('.needs-left p').on('click',function () {
    $('.needs-x').html(" ");
    $.ajax({
        type: "Post",//数据发送的方式（post 或者 get）
        url: "/admin/category/findTwoCategory",//要发送的后台地址
        data: {
            parentName:$(this).find("span").text(),
        },//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
        dataType:"JSON",
        success: function (data) {
            var datas=data.data;
            for (var i=0;i<datas.length;i++){
                var p="<p class=\""+"needp\""+">"+datas[i].categoryName + "</p>"
                $('.needs-x').append(p);
            }
        },
        error: function (msg) {//ajax请求失败后触发的方法
            alert("网络错误");//弹出错误信息
        }
    });
})
$('.needs-left p:first').click();
function addnone() {
	$('.needback').removeClass('block').addClass('none');
	$('.need').css('left', '100%');
}
//选中服务类型后   将其表达到界面

function set() {
    $('.needp').on('click', function() {
        $('.typetext').text($(this).text());
        $('.sonType').val($(this).text());
        addnone();
    })
}
setInterval(function(){set()},0);
//点击显现出市县详情地址
function siteone() {
	$('.site-s-o').css('display', 'inline');
}

function sitetwo() {
	$('.site-s-t').css('display', 'inline');
}

function sitethree() {
	$('.site-input').css('display', 'inline');
}
//最后的地址集合
function allsite() {
	var str = $('.site-s-z').val() + " " + $('.site-s-o').val() + " " + $('.site-s-t').val() + " " + $('.site-input').val();
	console.log(str);
	$('.addresses').val(str);
}
//发送需求
$('.issue-up').on('click',function () {
    allsite();
    $('.sub').click();
})
function AutoScroll(obj) {
	$(obj).find("ul:first").animate({
			marginTop: "35px"
		},
		1000,
		function() {
			$(this).css({
				marginTop: "0"
			}).find("li:first").appendTo(this);
		}
	);
}
$(document).ready(function() {
    setInterval('AutoScroll(".mainright")', 1800)
});