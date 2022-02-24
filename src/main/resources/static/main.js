let users;

function searchUser(id) {
    return users.find(user => user.id === id);
}

function userRole(user) {
    console.log(user)
    let userRole = '';
    let tableRoles = user.roles;
    for (let role of tableRoles) {
        userRole += role.role + ' ';
    }
    return userRole;
}

let roles;
roles = ${userRoles}
console.log(roles)

function editModal(id) {
    let user = searchUser(id);
    $("#uID").val(user.id);
    $("#uName").val(user.name);
    $("#uSurname").val(user.surname);
    $("#uAge").val(user.age);
    $("#uEmail").val(user.email);
    $("#uPassword").val(user.password);
    $("#uRoles").val();
}

function updateSubmit() {
    let form = $("#uForm");
    $.ajax('api/edit/' + $("#uID").val(), {
        method: 'PATCH',
        data: form.serialize(),
        success: function (response) {
                return response;
        }
    })
}

function deleteModal(id) {
    let user = searchUser(id);
    $("#dID").val(user.id);
    $("#dName").val(user.name);
    $("#dSurname").val(user.surname);
    $("#dAge").val(user.age);
    $("#dEmail").val(user.username);
    console.log(userRole(user));
    $("#dRoles").empty().append("<option>" + userRole(user)+ "</option>");
}

function deleteSubmit() {
    $.ajax({
        method: 'DELETE',
        url: "api/delete/" + $("#dID").val(),
        success: function () {
            users = users.filter(user => user.id !== $("#dID").val());
            updateTable();
        }
    })
}

function addSubmit() {
    let form = $("#newForm");
    $.ajax({
        type: 'POST',
        url: 'api/admin/create',
        data: form.serialize(),
        success: function (response) {
            $("#nav-users-tab").trigger("click");
            form.trigger("reset");
            users.push(response);
            updateTable();
        }
    })
}

function updateTable() {
    $("#tbodyID").empty();
    users.forEach(user => {
        $("#tbodyID").append("<tr>" +
            "<td>" + user.id + "</td>" +
            "<td>" + user.name + "</td>" +
            "<td>" + user.surname + "</td>" +
            "<td>" + user.age + "</td>" +
            "<td>" + user.username + "</td>" +
            "<td>" + userRole(user) + "</td>" +
            "<td><button class='btn btn-info' data-bs-toggle='modal' data-bs-target='#Um' onclick='editModal(" + user.id + ")' >Edit</button></td>" +
            "<td><button class='btn btn-danger' data-bs-toggle='modal' data-bs-target='#Dm' onclick='deleteModal(" + user.id + ")' >Delete</button></td>" +
            "</tr>");
    });
}

function go() {
            fetch("/api/admin").then(response => {
                response.json().
                then(allUsers => {
                    users = allUsers;
                    updateTable()
                });
            });
}
go();