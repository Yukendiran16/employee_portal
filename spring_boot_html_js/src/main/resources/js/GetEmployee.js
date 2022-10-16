$(document).ready(function(){
    
    $.ajax({
        type: 'GET',
        cache: false,
        url: "http://localhost:8088/employee_portal/trainer_get",
        contentType: "application/json; charset=UTF-8",
        success: function(data) {
            console.log("hi");
            console.log(data);
            if (Object.keys(data).length > 0) {
                for (var employee in data) {
                    $('#displayArea').append(
                        "<tr>
                            <td>" + data[employee].employeeId +"</td> \
                            <td>" + data[employee].employeeName + "</td> \
                            <td>" + data[employee].gender + "</td> \
                            <td>" + data[employee].phoneNumber + "</td> \
                            <td>" + data[employee].emailId + "</td> \
                            <td>" + data[employee].designation + "</td> \
                            <td>  <button type='button' onclick="+'viewOperation('+ data[employee].id+')'+" class='btn btn-info btn-sm' data-toggle='modal' data-target='#myModal'> View</button>  </td> \
                            <td>  <button type='button' onclick="+'deleteOperation('+data[employee].id+')'+" class='btn btn-danger btn-sm'>Delete</button> </td> \
                            <td>  <button type='button' onclick="+'updateOperation('+data[employee].id+')'+" class='btn btn-primary btn-sm'>Update</button>  </td> </tr>"
                    );
                }
            } else {
                alert("No data for this id");
            }
        
            //location.reload(true);
            //bindData(data);
            console.log(typeof data);
           // alert(data.projectDto.id + " was valid user");
        }
  });


    $('#submit').click(function(){
       var id = $("#employee").val();
       console.log("id", id);
        $.ajax({
            type: 'GET',
            url: "http://localhost:8088/employee/"+id,
            contentType: "application/json; charset=UTF-8",
            success: function(data) {
                console.log("hi");
                if (data.id > 0) {
                        $('#displayArea').empty().append(
                            "<tr> \
                                <td>" + data.employeeId +"</td> \
                                <td>" + data.employeeName + "</td> \
                                <td>" + data.gender + "</td> \
                                <td>" + data.phoneNumber + "</td> \
                                <td>" + data.emailId + "</td> \
                                <td>" + data.designation + "</td> \
                                <td>  <button type='button' onclick="+'viewOperation('+ data.id+')'+" class='btn btn-info btn-sm' data-toggle='modal' data-target='#myModal'>View</button>  </td> \
                                <td>  <button type='button' onclick="+'deleteOperation('+data.id+')'+"  class='btn btn-danger btn-sm'>Delete</button> </td> \
                                <td>  <button type='button' onclick="+'updateOperation('+data.id+')'+" class='btn btn-primary btn-sm'>Update</button>  </td> </tr>"
                        );
                } else {
                    alert("No data for this id");
                }
            
                //location.reload(true);
                //bindData(data);
                console.log(typeof data);
               // alert(data.projectDto.id + " was valid user");
            }
      });

    })

    $('#addEmployee').click(function(){
        var employeeId = $("#emp_id").val();
        var employeeName  = $("#emp_name").val();
        var gender = $("#emp_gender").val();
        var dateOfBirth = $("#emp_dob").val();
        var phoneNumber = $("#emp_phone").val();
        var emailId = $("#emp_email").val();
        var designation = $("#emp_designation").val();
        var experience = $("#emp_exp").val();
        var dateOfJoining = $("#emp_doj").val();
        var roleId = $("#emp_role_id").val();
        var projectId = $("#emp_project_id").val();
 
        console.log("employee id", employeeId);
        console.log(dateOfBirth);
        console.log(roleId);
        console.log(projectId);
        var employeeDto =  {

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
             "id":roleId
         }   
     };
                
         $.ajax({
             type: 'POST',
             dataType: "json",
             data: JSON.stringify(employeeDto),
             url: "http://localhost:8088/employee",
             contentType: "application/json; charset=UTF-8",
             success: function(data) {
                 console.log("hi");
                 console.log(typeof data);
                 
                 alert(data.employeeName + "added Successfully");
             }
       });
 
     })
});

function deleteOperation(id) {

    $.ajax({
        type: 'DELETE',
        cache: false,
        url: "http://localhost:8088/employee/"+id,
        contentType: "application/json; charset=UTF-8",
        success: function(data) {
            if (data) {
                alert("Deleted Successfully");
            }
           location.reload(true);
        }
  });

}


function viewOperation(id) {
    $.ajax({
        type: 'GET',
        url: "http://localhost:8088/employee/"+id,
        contentType: "application/json; charset=UTF-8",
        success: function(data) {
            if (data.id > 0) {
                console.log("it's working");
                $('#employee_name').text(data.employeeName);
            }
            else {
                alert("No details found");
            }
           //location.reload(true);
        }
  });
}

