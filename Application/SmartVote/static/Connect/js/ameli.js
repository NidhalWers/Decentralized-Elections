function AideUnique(idAide){
	var el = document.getElementById(idAide);
	
	if(el.style.display=='block'){
		el.style.display ='none';
	}
	else{
		el.style.display = 'block';
	}
}