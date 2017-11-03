<?php
   require __DIR__ . '/db_connect.php';
   $baza = new Baza();
   $con= $baza->connect();
   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }
   $query = urldecode($_GET['q']);
   $query = str_replace('\"', '', $query);
   $result = $con->query($query);

   $polje=array();
   while($row = $result->fetch_assoc()){
    echo json_encode($row);
   }
   mysqli_close($con);
?>