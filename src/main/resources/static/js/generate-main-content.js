function generateAdminPanel(users) {
    let container = $('<div>');
    container.append(`
        <h2>Admin panel</h2>
        <div>
            <a href="#" data-page="create-user-page">New User</a>
            <a href="#" data-page="users-table-page">Users table</a>
        </div>
        <h3>All users</h3>
    `);

    container.append(generateWorkTable(users));

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
