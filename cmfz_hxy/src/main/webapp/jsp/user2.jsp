<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<div class="page-header">
    <h1 style="color: #2aabd2">用户管理</h1>
</div>
<script>
    function loadData() {
        var options = {
            url: actionPath + "!page"
        };
        loadPaginationData(options);
    }
    $(function(){
        user();
    })
    function user(){
        $.ajax({
            url:"${pageContext.request.contextPath}/user/exportUser",
            datatype:"json",
            type:"Post",
            success:function(){
            }
        })
        var actionPath = contextPath + "/alumni-import";
        loadData();
        //上传文件
          uploadFile({
                     subfix: ['xls'],
                     subfixTip: "请选择Excel的xls文件！",
                    successCall: function(data, status, a) {
                        $('[name=attachementPath]').val(data.fileName);
                       $.post(actionPath + "!importExcel", { "f_id": data.f_id }, function(data) {
                if (data.success) {
                                       alertify.alert(data.message);
                                       $("#myModal-import").modal("hide");
                                        loadData();
                                    } else {
                                       alertify.alert(data.message);
                                    }

                          }, "json");
                   }
             });
           //导入
            $("#btn-import").click(function() {
                    var html = template("importTpl");
                    $("#import-body").html(html);
                   $("#myModal-import").modal();
                });
           //确认导入
            $("#btn-sure").click(function() {
                var type = $("#indentity-type").val();
                    alertify.confirm("确定要全部导入吗？", function(e) {
                          if (!e) { return; } else {
                                   $.post("/alumni-import!saveReal?type=" + type, function(data) {
                                             alertify.alert("导入成功!", function() {
                                                  location.href = "/alumni!hrefPage";
                                                });
                                   }, "json");
                                }
                        });
                 });



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
                        return "<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/uploadImg/"+cellvalue+"'/>";
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
            success:function(){
                user();
            }
        })

    }
</script>
<div>
    <a href="${pageContext.request.contextPath}/user/exportUser" type="button" class='btn btn-primary'>导出</a>
    <input type="file" name="file" id="file">
    <input type="button" onclick="importUser()" value="一键导入">
</div>
<table id="carouselTable"></table>
<div id="carouselPager"></div>

