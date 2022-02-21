let users, roles;

function updateTable() {
    $("#tbodyID").empty();
    users.forEach(user => {
        let userRole = '';
        let tableRoles = user.roles;
        for (let role of tableRoles) {
            userRole += role.role + ' ';
        }
        $("#tbodyID").append("<tr>" +
            "<td>" + user.id + "</td>" +
            "<td>" + user.name + "</td>" +
            "<td>" + user.surname + "</td>" +
            "<td>" + user.age + "</td>" +
            "<td>" + user.username + "</td>" +
            "<td>" + userRole + "</td>" +
            "<td><button class='btn btn-info' data-bs-toggle='modal' data-bs-target='#Um' onclick='editModal(" + user.id + ")' style='color: white'>Edit</button></td>" +
            "<td><button class='btn btn-danger' data-bs-toggle='modal' data-bs-target='#Dm' onclick='deleteModal(" + user.id + ")' style='color: white'>Delete</button></td>" +
            "</tr>");
    });
}

function searchUser(id) {
    return users.find(user => user.id === id);
}

function editModal(id) {
    let user = searchUser(id);
    $("#uID").val(user.id);
    $("#uName").val(user.name);
    $("#uSurname").val(user.surname);
    $("#uAge").val(user.age);
    $("#uEmail").val(user.email);
    $("#uPassword").val("");
    $("#uRoles").val(roles);
}

function editSubmit() {
    let form = $("#Um");
    $.ajax({
        type: form.attr("method"),
        url: form.attr("action"),
        data: form.serialize(),
        success: function (response) {
            users = users.map(user => {
                if (user.id === $("#uID").val()) {
                    user = response;
                }
                return user;
            });
            updateTable();
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
    $("#dRoles").val(user.role)
}

function deleteSubmit() {
    let form = $("#dForm");
    $.ajax({
        type: form.attr("DELETE"),
        url: form.attr("api/delete/{id}") + $("#dID").val(),
        data: form.serialize(),
        success: function () {
            users = users.filter(user => user.id !== $("#dID").val());
            updateTable();
        }
    })
}

function addSubmit() {
    let form = $("#addForm");
    $.ajax({
        type: form.attr("method"),
        url: form.attr("action"),
        data: form.serialize(),
        success: function (response) {
            $("#all-tab").trigger("click");
            form.trigger("reset");
            users.push(response);
            updateTable();
        }
    })
}



function pullData() {
            fetch("/api/admin").then(response => {
                response.json().then(allUsers => {
                    users = allUsers;
                    updateTable();
                });
            });
}
pullData();