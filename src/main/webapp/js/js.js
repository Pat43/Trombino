/**
 * 
 */
function MaxLengthTextarea(objettextarea,maxlength){
	if (objettextarea.value.length > maxlength) {
		objettextarea.value = objettextarea.value.substring(0, maxlength);
		alert("Votre texte ne doit pas d\351passer "+maxlength+" caract\350res!")
	}
}

//XXX : Serait mieux si le JS utilisait les listes Java (compliqué)
function refreshFilieres(filieres){
	var select=document.getElementById('department');
	var value = select.options[select.selectedIndex].text;
	var tab0 =[]; 
	var tab1 =[ "", "ILC", "I2RV", "LEIM", "R&D", "ISI (Apprentissage)"];
	var tab2 =[ "", "BEE", "PE", "RCS", "TSEE", "GE (Apprentissage)"];
	var tab3 =[ "", "ICP", "INP", "LOI", "QPI", "Apprentissage"];
	var tab4 =[ "", "CDP", "CIM", "CSM", "MOM", "THE"];
	var tab5 =[ "", "DIC", "EcIM", "EIC"];
	var tab;
	if(value=="")tab=tab0.slice();
	if(value=="Info")tab=tab1.slice();
	if(value=="EE")tab=tab2.slice();
	if(value=="IMSI")tab=tab3.slice();
	if(value=="MC")tab=tab4.slice();
	if(value=="EDIM")tab=tab5.slice();
	var fil1 = document.getElementById('filiere');
	fil1.options.length=0;
	var fil2 = document.getElementById('filiere2');
	fil2.options.length=0;
	for(var i=0; i < tab.length; i++){
		var opt=document.createElement('option');
		opt.textContent = tab[i];
		opt.value = tab[i]; fil1.appendChild(opt);}
	for(var i=0; i < tab.length; i++){
		var opt=document.createElement('option'); 
		opt.textContent = tab[i];
		opt.value = tab[i];
		fil2.appendChild(opt);
		} 
	}