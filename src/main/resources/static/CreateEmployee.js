$(document).ready(function(){
    $('#submit').click(function(){
    console.log("hi");
       var employeeName  = $("#employeeName").val();
       var dateOfBirth = $("#employeeDateOfBirth").val();
       var phoneNumber = $("#employeeMobileNumber").val();
       var emailId = $("#employeeMail").val();
       var designation = $("#employeeDesignation").val();
       var aadhaarCardNumber = $("#aadhaarCardNumber").val();
       var panCardNumber = $("#panCardNumber").val();
       var address = $("#currentAddress").val();
       var trainerDto = {
                "employeeName": employeeName,
                "employeeDateOfBirth": dateOfBirth,
                "employeeMobileNumber": phoneNumber,
                "employeeMail": emailId,
                "employeeDesignation": designation,
                "aadhaarCardNumber": aadhaarCardNumber,
                "panCardNumber": panCardNumber,
                "currentAddress": address
       };
       $.ajax({
            type: 'POST',
            dataType: "json",
            data: JSON.stringify(trainerDto),
            url: "http://localhost:8080/employee_portal/trainer",
            contentType: "application/json; charset=UTF-8",
            success: function(data) {
                console.log(typeof data);
                alert(data.employeeName + "added Successfully");
            }
      });

    })
});