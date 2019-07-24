<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<!doctype html>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport"
		  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="../boot/css/bootstrap.min.css">
	<link rel="stylesheet" href="../jqgrid/extend/css/trirand/ui.jqgrid-bootstrap.css">
	<link rel="stylesheet" type="text/css"
		  href="${pageContext.request.contextPath}/ems1.0/css/style.css" />
	<script src="../boot/js/jquery-3.3.1.min.js"></script>
	<script src="../boot/js/bootstrap.3.3.7.min.js"></script>
	<script src="../jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script src="../jqgrid/extend/js/trirand/jquery.jqGrid.min.js"></script>
	<script src="../boot/js/ajaxfileupload.js"></script>

	  <script>
		  function formSubmit() {
			  $.ajax({
				  url:"${pageContext.request.contextPath}/admin/login",
				  datatype:"json",
				  type:"post",
				  data:$("#login").serialize(),
				  success:function(data){
					  if (data.code == '200'){
						  location.href="${pageContext.request.contextPath}/jsp/main.jsp"
					  }else{
						  $("#message").html(data.message)
					  }
				  }
			  })
		  }
	  </script>
	</head>

	<body>
		<div id="wrap">
			<div id="top_content">
					<div id="header">
						<div id="rightheader">
						</div>
						<div id="topheader">
							<h1 id="title">
								<a href="#">持明法洲后台登录</a>
							</h1>
						</div>
						<div id="navigation">
						</div>
					</div>
				<div id="content">
					<p id="whereami">
					</p>
					<h1>
						login
					</h1>
					<span style="color: red" id="message"></span>
					<form id="login">
						<table cellpadding="0" cellspacing="0" border="0"
							class="form_table">
							<tr>
								<td valign="middle" align="right">
									username:
								</td>
								<td valign="middle" align="left">
									<input type="text" class="inputgri" name="username" />
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									password:
								</td>
								<td valign="middle" align="left">
									<input type="password" class="inputgri" name="password" />
								</td>
							</tr>
						</table>
						<p>
							<button class="btn btn-default" onclick="formSubmit()" >Submit</button>
						</p>
					</form>
				</div>
			</div>
			<div id="footer">
				<div id="footer_bg">
					ABC@126.com
				</div>
			</div>
		</div>
		
	</body>
</html>
