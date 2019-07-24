<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<div class="page-header">
    <h1 style="color: #2aabd2">用户管理</h1>
</div>
<script>
    $(function(){
        user();
    })
    function user(){
        $("#carouselTable").jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/user/queryAllByPage",
            datatype:"json",
            colNames:["编号","电话","密码","上师","省","市","性别","个性签名","头像","状态","注册时间"],
            colModel:[
                {name:"id"},
                {name:"phone"},
                {name:"password"},
                {name:"dharmaName"},
                {name:"province"},
                {name:"city"},
                {name:"gender"},
                {name:"personalSign"},
                {name:"profile",
                    formatter:function(cellvalue, options, rowObject){
                        return "<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/uploadImg/1.jpg'/>";
                    }
                },
                {name:"status",editable:true,edittype:'select',editoptions: {value: "激活:激活; 冻结:冻结"}},
                {name:"registTime"}
            ],
            pager:"carouselPager",
            rowNum:3,
            rowList:[3,5,7],
            viewrecords:true,
            autowidth:true,
            editurl:"${pageContext.request.contextPath}/user/edit",
            height:"100%",
            multiselect:true,
            rownumbers:true,
        }).jqGrid("navGrid","#carouselPager",{
            del:false,
            add:false,
        },{

        },{

        })

    }
   function importUser() {
        console.log("111")
        var formData = new FormData();
        formData.append("file", document.getElementById("file").files[0]);
        $.ajax({
            url:"${pageContext.request.contextPath}/user/importUser",
            datatype:"json",
            type:"Post",
            data:formData,
            contentType: false,
            processData: false,
            success:function(response){
                console.log("123")
                user();
            }
        })
    }
</script>
<div>
    <a href="${pageContext.request.contextPath}/user/exportUser" type="button" class='btn btn-primary'>导出</a>
    <input type="file" name="file" id="file" style="display: inline;"  accept="application/vnd.ms-excel">
    <input type="button" onclick="importUser()" value="一键导入">
</div>
<table id="carouselTable"></table>
<div id="carouselPager"></div>

