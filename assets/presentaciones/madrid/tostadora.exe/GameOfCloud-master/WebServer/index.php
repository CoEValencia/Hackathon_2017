<!--ESTE ARCHIVO SUSTITUYE AL PARSER DE JSON (Hemos publicado un panel que simplifica el proceso de: cojer cordenadas enviadas por node-red, procesarlas, seleccionar el target mas cercano, y seleccionarlo.)-->
<html>
	<head>
		<script type="text/javascript">
			function click1(){
				window.location.href = 'pos1.php';
			}

			function click2(){
				window.location.href = 'pos2.php';
			}

			function click3(){
				window.location.href = 'pos3.php';
			}

			function click4(){
				window.location.href = 'pos4.php';
			}

			function click5(){
				window.location.href = 'pos5.php';
			}
		</script>
	</head>
	<body>
		<p>ESTE ARCHIVO SUSTITUYE AL PARSER DE JSON (Hemos publicado un panel que simplifica el proceso de: cojer cordenadas enviadas por node-red, procesarlas, seleccionar el target mas cercano, y seleccionarlo.)</p>
		<table style="width: 100%">
			<tr>
				<th><button style="width:100%; height:250px; background: red;" onclick="click1()">1</button></th>
				<th><button style="width:100%; height:250px; background: green;" onclick="click2()">2</button></th>
			</tr>
			<tr>
				<th><button style="width:100%; height:250px; background: blue;" onclick="click3()">3</button></th>
				<th><button style="width:100%; height:250px; background: yellow;" onclick="click4()">4</button></th>
			</tr>
			<center>
				<tr>
					<button style="width:100%; height:150px;" onclick="click5()">Pulsar</button>
				</tr>
			</center>
		</table>
	</body>
</html>