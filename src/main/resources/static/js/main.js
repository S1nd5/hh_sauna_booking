
function setCurrentValues() {
	let date = new Date();
	console.log(date.getUTCFullYear() + "-" + date.getUTCMonth() + "-" + date.getUTCDay());
	document.getElementById("date").value = date.getUTCFullYear() + "-" + (date.getUTCMonth()+1) + "-" + date.getDate();
	document.getElementById("time").value = date.getHours() + ":00";
	document.getElementById("submitBtn").addEventListener("click", function(event){
		//event.preventDefault()
	});
	console.log("Executed!");
}

function initAdding() {
  $('#datepicker').datepicker({
       uiLibrary: 'bootstrap4',
       format : 'yyyy-mm-dd'
   });
   $('#timepicker').timepicker({
       uiLibrary: 'bootstrap4',
       format : 'HH',
       showMeridian : false,
       showInputs: false
    });
}

function fillValues() {
  let datetime = document.getElementById("datetime").value;
  let split = datetime.split(" ");
  document.getElementById("datepicker").value = split[0];
  document.getElementById("timepicker").value = split[1].split(":")[0];
}

function fillHiddenDate() {
  let date = document.getElementById("datepicker");
  let time = document.getElementById("timepicker");
  let datetime = date.value + " " + time.value + ":00";
  document.getElementById("datetime").value = datetime;
}