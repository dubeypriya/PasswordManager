function cancel() {
    var url = "home.html";
    $(location).attr('href', url);
};
function generatePassword() {
    var pass = '';
    var str = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ' +
        'abcdefghijklmnopqrstuvwxyz0123456789@#$';

    for (i = 1; i <= 12; i++) {
        var char = Math.floor(Math.random()
            * str.length + 1);

        pass += str.charAt(char)
    }
    $("#generatedPassword").val(pass);
} 
function savePassword(){
    var websiteName = $("#websiteName").val(),
        generatedPassword = $("#generatedPassword").val();
    if(generatedPassword.length>20){
        alert("Password length can't exceed 20 characters");
        location.reload(true);
    }
    var data = {};
    data["website"] = websiteName;  
    data["password"]=  generatedPassword;
   addData(data);
  }
  function addData(data){
    console.log(data);
    $.ajax({
            type: "POST",
            url: "http://localhost:8080/createPassword",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            crossDomain: true,
            dataType: "json",
            success: function (data, status, jqXHR) {
                var url = "home.html";
            $(location).attr('href', url);
            },

            error: function (jqXHR, status) {
                // error handler
                console.log(jqXHR);
                alert('fail' + status.code);
            }
         });
         
   }