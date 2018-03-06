<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="${request.contextPath}/resources/index/public/js/store.min.js"></script>
	</head>

	<body style="background-color: #efefef;">

		<script>
			if(store.get('layout') == undefined) {
                window.location.href = "horizontalLayout"
			} else {
                window.location.href = "verticalLayout"
			}
		</script>
	</body>

</html>