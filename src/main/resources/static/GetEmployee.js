$(document).ready(function(){
    
$.ajax({
        type: 'GET',
        cache: false,
        url: "http://localhost:8080/employee_portal/trainers",
        contentType: "application/json; charset=UTF-8",
        success: function(data) {
        console.log($('#displayArea'))
            console.log("hi");
            console.log(data);
            if (Object.keys(data).length > 0) {
                for (var index in data) {
                $('#displayArea').append($('<tr>')
                              .append('<td>' + data[index].trainerId +'</td>')
                              .append('<td>' + data[index].employeeName +'</td>')
                              .append('<td>' + data[index].designation +'</td>')
                              .append('<td>' + data[index].mobileNumber +'</td>')
                              .append('<td>' + data[index].employeeMail +'</td>')
                              .append('<td>' + data[index].currentAddress +'</td>'))
                }
            } else {
                alert("No data for this id")
            }
            console.log(typeof data);
        }
  });


    $('#submit').click(function(){
       var id = $("#employee").val();
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/employee_portal/trainer_get/"+id,
            contentType: "application/json; charset=UTF-8",
            success: function(data) {
                if (data.trainerId > 0) {
                        $('#displayArea').empty().append($('<tr>')
                                                          .append('<td>' + data.trainerId +'</td>')
                                                          .append('<td>' + data.employeeName +'</td>')
                                                          .append('<td>' + data.designation +'</td>')
                                                          .append('<td>' + data.mobileNumber +'</td>')
                                                          .append('<td>' + data.employeeMail +'</td>')
                                                          .append('<td>' + data.currentAddress +'</td>')
                        )
                } else {
                    alert("No data for this id");
                }
                console.log(typeof data);

            }
      });

    })
    });


