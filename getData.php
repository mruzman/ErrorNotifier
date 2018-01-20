<?php
   require __DIR__ . '/db_connect.php';
   $baza = new Baza();
   $con= $baza->connect();
   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }
   $query = urldecode($_GET['q']);
   $query = str_replace('\"', '', $query);

   if((0 === strpos($query, 'select'))||(0 === strpos($query, 'SELECT'))){
        $result = $con->query($query);
        $polje=array();
		$polje['records'] = array();
		if(mysqli_num_rows($result) > 1){
			while($row = $result->fetch_assoc()){
				array_push($polje['records'], $row);
			}
			echo json_encode($polje);
		}else{
			while($row = $result->fetch_assoc()){
				echo json_encode($row);
			}
			
		}
   }else if((0 === strpos($query, 'insert'))||(0 === strpos($query, 'INSERT'))){
        $result = $con->query($query);
        if($result){ echo 1;}
        else{echo -1;}

   }else if((0 === strpos($query, 'update'))||(0 === strpos($query, 'UPDATE'))){
        $result = $con->query($query);
        if($result) {echo 1;}
        else {echo -1;}
   }else{
        $result = $con->query($query);
        echo $result;
   }
   mysqli_close($con);
?>