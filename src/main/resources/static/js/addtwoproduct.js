$(document).ready(function () {
   $(".btn").click(function () {
       var parentName=$(".ipt1").val();
       var categoryName=$(".ipt2").val();
       var unitPrice=$(".ipt3").val();
       $.ajax({
           url:"/admin/category/addTwoCategory",
           type:"POST",
           data:{
               "categoryName": categoryName,
               "parentName":parentName,
               "unitPrice":unitPrice
           },
           success:function () {
               location="/admin/category/productCategoryList";
           }
       })
   })
});