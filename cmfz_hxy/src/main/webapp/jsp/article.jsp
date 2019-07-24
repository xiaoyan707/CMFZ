<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<div class="page-header">
    <h1 style="background-origin: border-box">上师管理</h1>
</div>
<script charset="utf-8" src="../kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="../kindeditor/lang/zh-CN.js"></script>
<script type="text/javascript">
    $(function(){
        a();
    })
    var editt;
    function queryArticle(id) {
        $("#articleForm tr:not(:first)").empty();
      $.post("${pageContext.request.contextPath}/article/queryOne",{id:id}, function(arr){
                $("#title").val(arr.title);
                editt.html(arr.content)
                $("#publishTime").val(arr.publishTime);

        }, "json")
    }
    function uploadArt(id) {
        $("#guruId").val(id);
        console.log($("#guruId").val(id))
    }

    function addArticle() {
        $("#articleForm tr:not(:first)").empty();
        $.ajax({
            url:"${pageContext.request.contextPath}/article/addArticle",
            type:"post",
            datatype:"json",
            data:$("#articleForm").serialize(),
            success:function(){

            }
        })
    }

    function updateStatus(rowObject){
        $.ajax({
            type:"Post",
            url:"${pageContext.request.contextPath}/guru/updateStatus",
            dataType:"json",
            data:{article:rowObject},
            success:function(){
            }

        })
        a();
    }
    function a(){
        editt= KindEditor.create('#editor_id',{
            width : '1000px',
            height:'600px',
            uploadJson:'${pageContext.request.contextPath}/article/upload', //文本编辑中图片的上传
            fileManagerJson:'${pageContext.request.contextPath}/article/showAll',//图片空间的路径展示所有图片
            allowFileManager:true,//允许远程访问
            filePostName:'file',//后台接收文件的名字
            afterBlur:function(){
                this.sync();
            }
        });

        $("#carouselTable").jqGrid({
            styleUI:"Bootstrap",
            url : "${pageContext.request.contextPath}/guru/queryAll",
            datatype : "json",
            colNames:["编号","姓名","头像","性别","状态","操作"],
            colModel:[
                {name:"id"},
                {name:"name",editable:true},
                {name:"profile",editable:true,edittype:"file",
                    formatter:function(cellvalue, options, rowObject){
                        return "<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/uploadProfile/"+cellvalue+"'/>";
                    }
                },
                {name:"sex",editable:true},
                {name:"status",edittype:"select",editable:true,editoptions: {value: "激活:激活; 冻结:冻结"},},
                {name:"id",formatter:function(cellvalue, options, rowObject){
                        return "<a href='#' class='btn btn-primary' role=\"button\" onclick='updateStatus(\""+rowObject+"\")'>冻结</a> <button type='button' class='btn btn-primary btn-lg' data-toggle='modal' data-target='#myModal' onclick='uploadArt(\""+rowObject.id+"\")' >文章上传</button>";

                    },

                },
            ],
            pager:"carouselPager",
            rowNum:3,
            rowList:[3,5,7],
            viewrecords:true,
            autowidth:true,
            editurl:"${pageContext.request.contextPath}/guru/edit",
            height:"100%",
            multiselect:true,
            rownumbers:true,
            subGrid : true,
            subGridRowExpanded : function(subgrid_id, row_id) {

                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id
                    + "' class='scroll'></table><div id='"
                    + pager_id + "' class='scroll'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        styleUI:"Bootstrap",
                        url:"${pageContext.request.contextPath}/article/queryAll?idd="+row_id,
                        datatype:"json",
                        colNames:["编号","作者编号","标题","出版时间","操作"],
                        colModel:[
                            {name:"id"},
                            {name:"guruId"},
                            {name:"title",editable:true},
                            {name:"publishTime",editable:true},
                            {name:"id",formatter:function(cellvalue, options, rowObject){
                                    return "<button type='button' class='btn btn-primary btn-lg' data-toggle='modal' data-target='#myModal' onclick='queryArticle(\""+rowObject.id+"\")'>预览</button>";
                            },},
                            ],
                        rowNum : 3,
                        pager : pager_id,
                        sortname : 'num',
                        sortorder : "asc",
                        multiselect:true,
                        height : '100%',
                        editurl:"${pageContext.request.contextPath}/article/edit?idd="+row_id
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id,{
                        add:false,
                    },{
                        //修改的部分
                        closeAfterEdit:true,
                        },{
                        //添加的部分
                        closeAfterAdd:true,

                })
            }
        }).jqGrid("navGrid","#carouselPager",{},{
            //修改的部分
            closeAfterEdit:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/guru/upload",
                    fileElementId:"profile",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#carouselTable").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }

        },{
            closeAfterAdd:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/guru/upload",
                    fileElementId:"profile",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#carouselTable").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }

        })

    }

</script>
<table id="carouselTable"></table>
<div id="carouselPager"></div>



<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width:1100px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <form action="javascript:void(0);" id="articleForm">
                <div class="modal-body">
                    <input type="hidden" name="guruId" value="" id="guruId">
                    <input type="text" name="title" id="title">
                    <input type="date" name="publishTime" id="publishTime">
                    <textarea id="editor_id" name="content" style="width:700px;height:300px;">
                </textarea>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="addArticle()">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>

