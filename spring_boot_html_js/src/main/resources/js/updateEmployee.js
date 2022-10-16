$(document).ready(function(){
    $('#submit').click(function(){
       var id = $("#employee").val();
       console.log("id", id);
        $.ajax({
            type: 'GET',
            url: "http://localhost:8088/employee/"+id,
            contentType: "application/json; charset=UTF-8",
            success: function(data) {
                console.log("hi");
                bindData(data);
                updatefunction(data);
                console.log(typeof data);
            }
      });

    })
    function updatefunction(data){
    $('#update').click(function(){
        var id = $("#employee").val();
        var employeeId = $("#employeeId").val();
        var employeeName  = $("#employeeName").val();
        var gender = $("#gender").val();
        var dateOfBirth = data.dateOfBirth;
        var phoneNumber = $("#phoneNumber").val();
        var emailId = $("#emailId").val();
        var designation = $("#designation").val();
        var experience = $("#experience").val();
        var dateOfJoining = data.dateOfJoining;
        var roleId = data.roleDto.id;
        var projectId = data.projectDto.id;
        console.log(employeeName);
        console.log(dateOfBirth);
        console.log(id);
        
        var employeeDto = {
             "id":id,
            "employeeId": employeeId,
            "employeeName": employeeName,
            "gender": gender,
            "dateOfBirth": dateOfBirth,
            "phoneNumber": phoneNumber,
            "emailId": emailId,
            "designation": designation,
            "dateOfJoining": dateOfJoining,
            "experience": experience,
            "projectDto": {
                "id" : projectId
        
            }, 
            "roleDto": {
                "id": roleId
            }   
     };
        
         $.ajax({
             type: 'PUT',
             dataType: "json",
             data: JSON.stringify(employeeDto),
             url: "http://localhost:8088/employee/",
             contentType: "application/json; charset=UTF-8",
             success: function(data) {
                 console.log("hi");
                 console.log(typeof data);
                 alert(data.employeeName + "Updated Successfully")
             }
       });

 
     })
    }
});

function bindData(data) {
    var employeeId = data.employeeId;
    $("#employeeId").val(employeeId);
    var employeeName = data.employeeName;
    $("#employeeName").val(employeeName);
    var gender = data.gender;
    $("#gender").val(gender);
    var age = data.age;
    $("#age").val(age);
    var phoneNumber = data.phoneNumber;
    $("#phoneNumber").val(phoneNumber);
    var emailId = data.emailId;
    $("#emailId").val(emailId);
    var designation = data.designation;
    $("#designation").val(designation);
    var experience = data.experience;
    $("#experience").val(experience);
    if (data.seatDto != null){
        var seat = data.seatDto.seatNumber;
        $("#seat").val(seat);
    }
    if (data.projectDto != null) {
    var project = data.projectDto.projectName;
    $("#project").val(project);
    }
    if (data.roleDto != null) {
    var role = data.roleDto.role;
    $("#role").val(role);
    }
}
