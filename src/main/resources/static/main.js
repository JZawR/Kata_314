let users;
let roles;
getAdminPage()
getUserPage()

// Заполняем страницу пользователя
function getUserPage() {
    fetch("/api/user")
        .then(response => {
            response.json().then(user => {
                $("#id").text(user.id);
                $("#name").text(user.name);
                $("#surname").text(user.surname);
                $("#age").text(user.age);
                $("#email").text(user.email);
                $("#roles").text(getUserRoles(user));
            })
        })
}

// Функция поиска пользователя для модальных окон
function searchUser(id) {
    return users.find(user => user.id === id);
}

// Выгружаем роли
function getUserRoles(user) {
    let userRole = '';
    let tableRoles = user.roles;
    for (let role of tableRoles) {
        userRole += role.role + ' ';
    }
    return userRole;
}

// Заполняем окно обновления
function showUpdateWindow(id) {
    let user = searchUser(id);
    $("#uID").val(user.id);
    $("#uName").val(user.name);
    $("#uSurname").val(user.surname);
    $("#uAge").val(user.age);
    $("#uEmail").val(user.email);
    $("#uPassword").val(user.password);
    $("#uRoles").empty();
    roles.forEach(role => {
        $("#uRoles").append(
            "<option value=".concat(role.id,
                (user.roles.some(r => r.id === role.id) ? " selected" : ""),
                ">", role.role + "</option>")
        );
    });
}

// Заполняем окно удаления
function showDeleteWindow(id) {
    let user = searchUser(id);
    $("#dID").val(user.id);
    $("#dName").val(user.name);
    $("#dSurname").val(user.surname);
    $("#dAge").val(user.age);
    $("#dEmail").val(user.username);
    $("#dRoles").empty();
    roles.forEach(role => {
        $("#dRoles").append(
            "<option value=".concat(role.role, (user.roles.some(r => r.id === role.id) ? " selected" : ""),
                ">", role.role + "</option>")
        );
    });
}

// отправка на добавление
function addSubmit() {
    let form = $("#newForm");
    $.ajax({
        type: 'POST',
        url: 'api/admin/create',
        data: form.serialize(),
        success: function () {
            form.submit()
        }
    }).done(function(  ) {
        top.location.href = '/admin';
    })
}

// отправка на обновление
function updateSubmit() {
    let form = $("#UpdateForm");
    $.ajax('api/edit/' + $("#uID").val(), {
        type: 'PATCH',
        data: form.serialize(),
        success: function () {
            form.submit()
        }
    }).done(function(  ) {
        top.location.href = '/admin';
    })
}

// отправка на удаление
function deleteSubmit() {
    $.ajax({
        type: 'DELETE',
        url: "api/delete/" + $("#dID").val(),
        success: function () {
            users = users.filter(user => user.id !== $("#dID").val());
        }
    }).done(function(  ) {
        top.location.href = '/admin';
    })
}

// Заполенение таблицы администратора
function getAdminTable() {
    $("#tbodyID").empty();
    users.forEach(user => {
        $("#tbodyID").append("<tr>" +
            "<td>" + user.id + "</td>" +
            "<td>" + user.name + "</td>" +
            "<td>" + user.surname + "</td>" +
            "<td>" + user.age + "</td>" +
            "<td>" + user.username + "</td>" +
            "<td>" + getUserRoles(user) + "</td>" +
            "<td><button class='btn btn-info' data-bs-toggle='modal' data-bs-target='#UpdateWindow' onclick='showUpdateWindow(" + user.id + ")' style='color: white'>Edit</button></td>" +
            "<td><button class='btn btn-danger' data-bs-toggle='modal' data-bs-target='#DeleteWindow' onclick='showDeleteWindow(" + user.id + ")' >Delete</button></td>" +
            "</tr>");
    });
}

// Получение ролей и пользователей из JSON
function getAdminPage() {
    fetch("/api/roles").then(response => {
        response.json().then(allRoles => {
            roles = allRoles;
        })
    })
    fetch("/api/admin").then(response => {
        response.json().then(allUsers => {
            users = allUsers;
            getAdminTable()
        })
    })
}


