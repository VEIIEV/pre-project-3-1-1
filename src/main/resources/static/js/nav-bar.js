$(document).ready(function () {
    // Обработчик кликов по элементам навбара
    $("#navbar a").click(function (e) {
        e.preventDefault();
        let page = $(this).data("page");

        // Затеняем невыбранные элементы навбара
        $("#navbar a").removeClass("active");
        $(this).addClass("active");

        // определение url
        let url = null;
        if (page === 'admin-page') {
            url = '/api/admin/users'

        } else if (page === 'user-page') {
            url = '/api/users'
        }
        // Запрос на загрузку данных с сервера
        $.get(url, function (data) {
            $("#content").fadeOut(200, function () {
                $(this).empty();
                if (page === "admin-page") {
                    $(this).append(generateAdminPanel(data));
                } else if (page === "user-page") {
                    $(this).append(generateUserPage(data));
                }
                $(this).fadeIn(200);
            });
        }).fail(function () {
            $("#content").text("Ошибка загрузки данных.");
        });
    });

    // Загрузка страницы по умолчанию
    $("#navbar a[data-page='user']").trigger("click");
});

function generateAdminPanel(users) {
    let container = $('<div>');
    container.append(`
        <h2>Admin panel</h2>
        <div>
            <a href="#" class="btn">Users table</a>
            <a href="#" class="btn btn-primary">New User</a>
        </div>
        <h3>All users</h3>
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

    let tbody = container.find("tbody");
    $.each(users, function (index, user) {
        let authoryties = user.authorities.map(auth => auth.authority).join(", ")

        tbody.append(`
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${authoryties}</td>
                <td><button class="btn btn-edit">Edit</button></td>
                <td><button class="btn btn-delete">Delete</button></td>
            </tr>
        `);
    });

    return container;
}

function generateUserPage(userData) {
    let container = $('<div>');
    container.append(`
        <h2>User information-page</h2>
        <p>Добро пожаловать, ${userData.username}.</p>
        <div>
            <h4>About user</h4>
            <table className="table table-bordered mt-2"  border="1" width="100%">      

                 <thead>
                    <tr>
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Age</th>
                        <th>Email</th>
                    </tr>
                 </thead>
                 <tbody>
                 </tbody>
            </table>
        </div>
    `);

    let tbody = container.find("tbody");
    tbody.append(`
            <tr>
                <td>${userData.id}</td>
                <td>${userData.firstName ?? ''}</td>
                <td>${userData.lastName ?? ''}</td>
                <td>${userData.age}</td>
                <td>${userData.email}</td>
            </tr>
        `);
    return container;
}

//
// <div className="card p-3 mt-3">
//     <h4>About user</h4>
//     <table className="table table-bordered mt-2">
//         <thead className="table-light">
//         <tr>
//             <th>ID</th>
//             <th>First Name</th>
//             <th>Last Name</th>
//             <th>Age</th>
//             <th>Email</th>
//             <th>Role</th>
//         </tr>
//         </thead>
//         <tbody>
//         <tr>
//             <td>3</td>
//             <td>user</td>
//             <td>user</td>
//             <td>30</td>
//             <td>user@mail.ru</td>
//             <td>USER</td>
//         </tr>
//         </tbody>
//     </table>
// </div>