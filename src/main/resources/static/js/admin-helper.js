function generateWorkTable(users) {
    let table = $("<div id='work-table'>");
    table.append('<h3>All users</h3>')
    table.append(`
        <table class="table table-striped table-bordered">
            <thead class="table-light">
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
                <td><button class="btn btn-primary btn-sm btn-edit-${user.id}">Edit</button></td>
                <td><button class="btn btn-danger btn-sm btn-delete-${user.id}">Delete</button></td>
            </tr>
        `);
    });

    return table;
}



