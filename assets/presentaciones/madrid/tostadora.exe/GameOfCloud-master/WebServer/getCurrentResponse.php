<?php
    $server = "localhost";
    $username = "root";
    $password = "";
    $db = "goc";

    $conexion = @mysqli_connect($server, $username, $password, $db);
    if(!$conexion){
        die("FALLO");
    }

    $query = "SELECT * FROM STATUS;";
    $result = $conexion->query($query);
    $row = $result->fetch_array(MYSQLI_ASSOC);   
    printf ("%s%s\n", $row["CurrentResponse"], $row["CurrentStatus"]);
    if($row["CurrentStatus"] == 0){
        die();
    }

    $query = "UPDATE STATUS SET CURRENTSTATUS=0 WHERE 1;";
    $result = $conexion->query($query);

?>

