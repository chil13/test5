<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test</title>
</head>
<body>

	<div id="timer">00:00:000</div>
	<br>

	<c:out value="${question}"></c:out>
	<br>
	<br>

	<button id="start">Start</button>
	<button id="stop">Stop</button>
	<!-- 	<button id="reset">reset</button> -->
	<a id="download">保存</a>
	<br>
	<br>

	<form action=RetryRegister method="post">
		<input type="hidden" name="q_id" value="${q_id}">
		<button type="submit">
			<div class="button-small color-green">【もう1度リスト】へ登録</div>
		</button>
	</form>

	<br>


	<form action="Test" method="post">
		<a href="mmMenuServlet">MENUに戻る</a>
		<button type="submit">
			<div class="button-small color-green">次の質問へ</div>
		</button>
	</form>

	<script>
		//recording function
		// for html
		const downloadLink = document.getElementById('download');
		const startButton = document.getElementById('start');
		const stopButton = document.getElementById('stop');
		const fileName = document.getElementById('fileName');
		const wav = ".wav";
		console.log(fileName);
		console.log(fileName + wav);

		// for audio
		let audio_sample_rate = null;
		let scriptProcessor = null;
		let audioContext = null;

		// audio data
		let audioData = [];
		let bufferSize = 1024;

		let saveAudio = function() {
			downloadLink.href = exportWAV(audioData);
			downloadLink.download = "就活一問一答.wav";
			downloadLink.click();

			audioContext.close().then(function() {
				stopButton.setAttribute('disabled', 'disabled');
			});
		}

		// export WAV from audio float data
		let exportWAV = function(audioData) {

			let encodeWAV = function(samples, sampleRate) {
				let buffer = new ArrayBuffer(44 + samples.length * 2);
				let view = new DataView(buffer);

				let writeString = function(view, offset, string) {
					for (let i = 0; i < string.length; i++) {
						view.setUint8(offset + i, string.charCodeAt(i));
					}
				};

				let floatTo16BitPCM = function(output, offset, input) {
					for (let i = 0; i < input.length; i++, offset += 2) {
						let s = Math.max(-1, Math.min(1, input[i]));
						output.setInt16(offset,
								s < 0 ? s * 0x8000 : s * 0x7FFF, true);
					}
				};

				writeString(view, 0, 'RIFF'); // RIFFヘッダ
				view.setUint32(4, 32 + samples.length * 2, true); // これ以降のファイルサイズ
				writeString(view, 8, 'WAVE'); // WAVEヘッダ
				writeString(view, 12, 'fmt '); // fmtチャンク
				view.setUint32(16, 16, true); // fmtチャンクのバイト数
				view.setUint16(20, 1, true); // フォーマットID
				view.setUint16(22, 1, true); // チャンネル数
				view.setUint32(24, sampleRate, true); // サンプリングレート
				view.setUint32(28, sampleRate * 2, true); // データ速度
				view.setUint16(32, 2, true); // ブロックサイズ
				view.setUint16(34, 16, true); // サンプルあたりのビット数
				writeString(view, 36, 'data'); // dataチャンク
				view.setUint32(40, samples.length * 2, true); // 波形データのバイト数
				floatTo16BitPCM(view, 44, samples); // 波形データ

				return view;
			};

			let mergeBuffers = function(audioData) {
				let sampleLength = 0;
				for (let i = 0; i < audioData.length; i++) {
					sampleLength += audioData[i].length;
				}
				let samples = new Float32Array(sampleLength);
				let sampleIdx = 0;
				for (let i = 0; i < audioData.length; i++) {
					for (let j = 0; j < audioData[i].length; j++) {
						samples[sampleIdx] = audioData[i][j];
						sampleIdx++;
					}
				}
				return samples;
			};

			let dataview = encodeWAV(mergeBuffers(audioData), audio_sample_rate);
			let audioBlob = new Blob([ dataview ], {
				type : 'audio/wav'
			});
			console.log(dataview);

			let myURL = window.URL || window.webkitURL;
			let url = myURL.createObjectURL(audioBlob);
			return url;
		};

		// stop button
		stopButton.addEventListener('click', function() {
			saveAudio();
			console.log('saved wav');
		});

		// save audio data
		var onAudioProcess = function(e) {
			var input = e.inputBuffer.getChannelData(0);
			var bufferData = new Float32Array(bufferSize);
			for (var i = 0; i < bufferSize; i++) {
				bufferData[i] = input[i];
			}

			audioData.push(bufferData);
		};

		// getusermedia
		let handleSuccess = function(stream) {
			audioContext = new AudioContext();
			audio_sample_rate = audioContext.sampleRate;
			console.log(audio_sample_rate);
			scriptProcessor = audioContext.createScriptProcessor(bufferSize, 1,
					1);
			var mediastreamsource = audioContext
					.createMediaStreamSource(stream);
			mediastreamsource.connect(scriptProcessor);
			scriptProcessor.onaudioprocess = onAudioProcess;
			scriptProcessor.connect(audioContext.destination);

			console.log('record start?');

			// when time passed without pushing the stop button
			setTimeout(function() {
				console.log("10 sec");
				if (stopButton.disabled == false) {
					saveAudio();
					console.log("saved audio");
				}
			}, 10000);
		};

		// getUserMedia
		startButton.addEventListener('click', function() {
			navigator.mediaDevices.getUserMedia({
				audio : true,
				video : false
			}).then(handleSuccess);
		});

		(function() {
			'use strict';

			//htmlのidからデータを取得
			//取得したデータを変数に代入

			var timer = document.getElementById('timer');
			var start = document.getElementById('start');
			var stop = document.getElementById('stop');
			var reset = document.getElementById('reset');

			//クリック時の時間を保持するための変数定義
			var startTime;

			//経過時刻を更新するための変数。 初めはだから0で初期化
			var elapsedTime = 0;

			//タイマーを止めるにはclearTimeoutを使う必要があり、そのためにはclearTimeoutの引数に渡すためのタイマーのidが必要
			var timerId;

			//タイマーをストップ -> 再開させたら0になってしまうのを避けるための変数。
			var timeToadd = 0;

			//ミリ秒の表示ではなく、分とか秒に直すための関数, 他のところからも呼び出すので別関数として作る
			//計算方法として135200ミリ秒経過したとしてそれを分とか秒に直すと -> 02:15:200
			function updateTimetText() {

				//m(分) = 135200 / 60000ミリ秒で割った数の商　-> 2分
				var m = Math.floor(elapsedTime / 60000);

				//s(秒) = 135200 % 60000ミリ秒で / 1000 (ミリ秒なので1000で割ってやる) -> 15秒
				var s = Math.floor(elapsedTime % 60000 / 1000);

				//ms(ミリ秒) = 135200ミリ秒を % 1000ミリ秒で割った数の余り
				var ms = elapsedTime % 1000;

				//HTML 上で表示の際の桁数を固定する　例）3 => 03　、 12 -> 012
				//javascriptでは文字列数列を連結すると文字列になる
				//文字列の末尾2桁を表示したいのでsliceで負の値(-2)引数で渡してやる。
				m = ('0' + m).slice(-2);
				s = ('0' + s).slice(-2);
				ms = ('0' + ms).slice(-3);

				//HTMLのid　timer部分に表示させる　
				timer.textContent = m + ':' + s + ':' + ms;
			}

			//再帰的に使える用の関数
			function countUp() {

				//timerId変数はsetTimeoutの返り値になるので代入する
				timerId = setTimeout(function() {

					//経過時刻は現在時刻をミリ秒で示すDate.now()からstartを押した時の時刻(startTime)を引く
					elapsedTime = Date.now() - startTime + timeToadd;
					updateTimetText()

					//countUp関数自身を呼ぶことで10ミリ秒毎に以下の計算を始める
					countUp();

					//1秒以下の時間を表示するために10ミリ秒後に始めるよう宣言
				}, 10);
			}

			//startボタンにクリック時のイベントを追加(タイマースタートイベント)
			start.addEventListener('click', function() {

				//在時刻を示すDate.nowを代入
				startTime = Date.now();

				//再帰的に使えるように関数を作る
				countUp();
			});

			//stopボタンにクリック時のイベントを追加(タイマーストップイベント)
			stop.addEventListener('click', function() {

				//タイマーを止めるにはclearTimeoutを使う必要があり、そのためにはclearTimeoutの引数に渡すためのタイマーのidが必要
				clearTimeout(timerId);

				//タイマーに表示される時間elapsedTimeが現在時刻かたスタートボタンを押した時刻を引いたものなので、
				//タイマーを再開させたら0になってしまう。elapsedTime = Date.now - startTime
				//それを回避するためには過去のスタート時間からストップ時間までの経過時間を足してあげなければならない。elapsedTime = Date.now - startTime + timeToadd (timeToadd = ストップを押した時刻(Date.now)から直近のスタート時刻(startTime)を引く)
				timeToadd += Date.now() - startTime;
			});
			/*
			 //resetボタンにクリック時のイベントを追加(タイマーリセットイベント)
			 reset.addEventListener('click', function() {

			 //経過時刻を更新するための変数elapsedTimeを0にしてあげつつ、updateTimetTextで0になったタイムを表示。
			 elapsedTime = 0;

			 //リセット時に0に初期化したいのでリセットを押した際に0を代入してあげる
			 timeToadd = 0;

			 //updateTimetTextで0になったタイムを表示
			 updateTimetText();

			 });
			 */

		})();
	</script>

</body>


</html>