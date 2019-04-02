$(document).ready(function () {
    $(".btn").click(function () {
        var categoryName=$(".ipt1").val();
        var unitPrice=$(".ipt2").val();
        var categoryShow=$(".ipt3").val();
        $.ajax({
            url:"/admin/category/addOneCategory",
            type:"POST",
            data:{
                "categoryName":categoryName,
                "unitPrice":unitPrice,
                "categoryShow":categoryShow
            },
            success:function () {
                location="/admin/category/productCategoryList";
            }
        })
    });
});