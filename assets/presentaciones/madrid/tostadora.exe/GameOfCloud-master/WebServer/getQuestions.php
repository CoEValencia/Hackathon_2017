<?php
    $server = "localhost";
    $username = "root";
    $password = "";
    $db = "goc";

    $conexion = @mysqli_connect($server, $username, $password, $db);
    if(!$conexion){
        die("FALLO");
    }
    $conexion->set_charset("utf8");
    $query = "SELECT * FROM PREGUNTAS;";
    $result = $conexion->query($query);
    while($row = $result->fetch_array(MYSQLI_ASSOC)){	
		printf ("%s;%s;%s;%s;%s\n", $row["Pregunta"], $row["RespuestaBuena"], $row["Respuesta1"], $row["Respuesta2"], $row["Respuesta3"]);
    }

?>
