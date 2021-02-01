<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<button id="start">Start</button>

	<script type="text/javascript">
		var start = new Date();
		const startButton = document.getElementById('start');

		// 初期化
		var hour = 0;
		var min = 0;
		var sec = 0;
		var now = 0;
		var datet = 0;

		startButton.addEventListener('click', function disp() {

			now = new Date();

			datet = parseInt((now.getTime() - start.getTime()) / 1000);

			hour = parseInt(datet / 3600);
			min = parseInt((datet / 60) % 60);
			sec = datet % 60;

			// 数値が1桁の場合、頭に0を付けて2桁で表示する指定
			if (hour < 10) {
				hour = "0" + hour;
			}
			if (min < 10) {
				min = "0" + min;
			}
			if (sec < 10) {
				sec = "0" + sec;
			}

			// フォーマットを指定（不要な行を削除する）
			var timer1 = hour + ':' + min + ':' + sec; // パターン1
			var timer2 = hour + '時間' + min + '分' + sec + '秒'; // パターン2

			// テキストフィールドにデータを渡す処理（不要な行を削除する）
			document.form1.field1.value = timer1; // パターン1
			document.form1.field2.value = timer2; // パターン2

			setTimeout("disp()", 1000);
		});
	
		</script>
		
</head>
<body onLoad="disp()">

	<form action="#" name="form1">
		<input type="text" name="field1" size="8"> パターン1<br> <input
			type="text" name="field2" size="18"> パターン2
	</form>

</body>
</html>