<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>システムエラー</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<link rel="stylesheet" href="/static/css/bootstrap.min.css">
<link rel="stylesheet" href="/static/css/customize.css">
<link rel="shortcut icon" type="image/x-icon" href="/static/favicon.ico" />
<script type="text/javascript" src="/static/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/static/layer/layer.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
	<div>
		<nav class="navbar navbar-dark bg-dark fixed-top" role="navigation">
			<div class="container-fluid">
				<a class="navbar-brand" href="#" style="font-size: 24px;">システムエラー</a>
			</div>
		</nav>
	</div>
	<div class="container" style="margin-top: 60px;">
		<div class="text-center">
			<h2>システム情報</h2>
			<c:if test="${request.getAttribute('exception')} != null">
				<h6 th:text="${requestScope.exception.message}"></h6>
			</c:if>
			<button id="backbutton"
				style="width: 300px; margin: 0px auto 0px auto;"
				class="btn btn-lg btn-warning btn-block">戻る</button>
		</div>
	</div>
	<script type="text/javascript">
		$("#backbutton").on('click', function() {
			window.history.back();
		});
	</script>
</body>
</html>