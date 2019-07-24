<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<div class="page-header">
    <h1>专辑管理</h1>
</div>
<script>

    $(function(){
        $("#carouselTable").jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/album/queryAll",
            datatype:"json",
            colNames:["编号","专辑名称","专辑封面","章节数量","专辑得分","专辑作者","播音员","专辑简介","出版时间"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"cover",editable:true,edittype:"file",
                    formatter:function(cellvalue, options, rowObject){
                        return "<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/uploadCoverAlbum/"+cellvalue+"'/>";
                    }},
                {name:"count",editable:true},
                {name:"score",editable:true},
                {name:"author",editable:true},
                {name:"broadcast",editable:true},
                {name:"brief",editable:true},
                {name:"publishTime",editable:true,edittype:"date"}],
            pager:"carouselPager",
            rowNum:3,
            rowList:[3,5,7],
            viewrecords:true,
            autowidth:true,
            editurl:"${pageContext.request.contextPath}/album/edit",
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
                        url : "${pageContext.request.contextPath}/chapter/queryAll?idd="+row_id,
                        datatype : "json",
                        colNames:["编号","专辑编号","音频名称","音频大小","音频路径","长传时间","操作"],
                        colModel:[
                            {name:"id"},
                            {name:"albumId"},
                            {name:"title",editable:true,},
                            {name:"size"},
                            {name:"downPath",editable:true,edittype:"file"},
                            {name:"uploadTime",editable:true,edittype:"date"},
                            {formatter:function(cellvalue, options, rowObject){
                                    return "<a href='${pageContext.request.contextPath}/chapter/filedownload?fname="+rowObject.downPath+"' class='btn btn-primary' role=\"button\">下载</a>"
                                }}],
                        rowNum : 20,
                        pager : pager_id,
                        sortname : 'num',
                        sortorder : "asc",
                        multiselect:true,
                        height : '100%',
                        editurl:"${pageContext.request.contextPath}/chapter/edit?idd="+row_id
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id,{},{
                        //修改的部分
                        closeAfterEdit:true,
                        afterSubmit:function(response){
                            $.ajaxFileUpload({
                                url:"${pageContext.request.contextPath}/chapter/fileupload",
                                fileElementId:"downPath",
                                data:{"id":response.responseText},
                                type:"post",
                                success:function(){
                                    $("#" + subgrid_table_id).trigger("reloadGrid");
                                }
                            })
                            return "[true]";
                    }},{
                        //添加的部分
                        closeAfterAdd:true,
                        afterSubmit:function(response){
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/chapter/fileupload",
                            fileElementId:"downPath",
                            data:{"id":response.responseText},
                            type:"post",
                            success:function(){
                                $("#" + subgrid_table_id).trigger("reloadGrid");
                            }
                        })
                        return "[true]";
                    }
                })
            }
        }).jqGrid("navGrid","#carouselPager",{},{
            //修改的部分
            closeAfterEdit:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/album/fileupload",
                    fileElementId:"cover",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#carouselTable").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        },{
            //添加的部分
            closeAfterAdd:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/album/fileupload",
                    fileElementId:"cover",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#carouselTable").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        })

    })
</script>
<table id="carouselTable"></table>
<div id="carouselPager"></div>

