$(document).ready(function(){
    $('#submit').click(function(){
       var employeeName  = $("#employeeName").val();
       var dateOfBirth = $("#employeeDateOfBirth").val();
       var phoneNumber = $("#employeeMobileNumber").val();
       var emailId = $("#employeeMail").val();
       var designation = $("#employeeDesignation").val();
       var aadhaarCardNumber = $("#aadhaarCardNumber").val();
       var panCardNumber = $("#panCardNumber").val();
       var address = $("#currentAddress").val();

//       console.log("employee id", employeeId);
//       console.log(dateOfBirth);
//       console.log(roleId);
//       console.log(projectId);
        var employeeDto = {
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
            data: JSON.stringify(employeeDto),
            url: "http://localhost:8080/trainer",
            contentType: "application/json; charset=UTF-8",
            success: function(data) {
//                console.log("hi");
                console.log(typeof data);
                alert(data.employeeName + "added Successfully");
            }
      });

    })
});