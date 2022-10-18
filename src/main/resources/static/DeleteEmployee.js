$(document).ready(function(){
    $('#submit').click(function(){
       var id = $("#employee").val();
       console.log("id", id);
        $.ajax({
            type: 'DELETE',
            url: "http://localhost:8091/employee/"+id,
            contentType: "application/json; charset=UTF-8",
            success: function(data) {
                console.log(typeof data);
                if (data == true) {
                    alert("Employee Deleted Successfully");
                } else {
                    alert("Try Again Sometime");
                }
            }
      });

    })
});