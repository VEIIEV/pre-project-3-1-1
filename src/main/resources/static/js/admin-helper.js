// Функция для загрузки формы добавления нового пользователя
function loadNewUserForm() {
    let formHtml = `
        <form id="new-user-form">
            <label>Имя: <input type="text" id="name"></label><br>
            <label>Email: <input type="email" id="email"></label><br>
            <button type="submit">Создать</button>
        </form>
    `;
    $("#work-table").html(formHtml);

    // Обработчик отправки формы
    $("#new-user-form").submit(function (event) {
        event.preventDefault(); // Отмена стандартной отправки формы

        let userData = {
            name: $("#name").val(),
            email: $("#email").val()
        };

        $.ajax({
            url: "/api/users",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(userData),
            success: function () {
                alert("Пользователь создан!");
                loadUsersTable(); // Перезагрузить таблицу пользователей
            },
            error: function () {
                alert("Ошибка при создании пользователя!");
            }
        });
    });
}



function generateWorkTable(users) {
    let table = $("<div id='work-table'>");
    table.append(`
        <table border="1" width="100%">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Age</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    `);

    let tbody = table.find("tbody");
    $.each(users, function (index, user) {
        let authorities = user.authorities.map(auth => auth.authority).join(", ");

        tbody.append(`
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age ?? ''}</td>
                <td>${user.email}</td>
                <td>${authorities}</td>
                <td><button class="btn btn-edit">Edit</button></td>
                <td><button class="btn btn-delete">Delete</button></td>
            </tr>
        `);
    });

    return table;
}
