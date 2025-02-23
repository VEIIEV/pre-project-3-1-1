$(document).ready(function () {
    handleUserFormSubmission("#user-creation-form", "#content", "/api/admin/users", "/api/admin/users");
    handleUserModal("[class*='btn-edit']", "/api/admin/users/{id}", "#modal", ".close");
    handleDeleteUser();
});


function handleUserFormSubmission(formSelector, containerSelector, apiUrl, reloadUrl) {
    $(document).on("submit", formSelector, function (event) {
        event.preventDefault(); // Предотвращаем стандартную отправку формы

        let formData = $(this).serialize(); // Собираем данные формы

        $.post(apiUrl, formData)
            .done(function () {
                // Загружаем обновленный список пользователей
                $.get(reloadUrl, function (data) {
                    $(containerSelector).fadeOut(200, function () {
                        $(this).empty();
                        $(this).append(generateAdminPanel(data));
                        $(this).fadeIn(200);
                    });
                }).fail(function () {
                    $(containerSelector).text("Ошибка загрузки данных.");
                });
            })
            .fail(function (xhr) {
                alert("Ошибка при добавлении пользователя: " + xhr.responseText);
            });
    });
}




//     handleUserModal("[class*='btn-edit']", "/api/admin/users/{id}", "#modal", ".close");
function handleUserModal(buttonSelector, apiUrlTemplate, modalSelector, closeSelector) {
    $(document).on("click", buttonSelector, function () {

        let userId = $(this).attr("class").match(/btn-edit-(\d+)/)[1];


        $.get("/templates/model-user-form.html", function (html) {
            $('body').append(html); // Вставляем HTML в модальное окно

            // Теперь можно заполнять модальное окно данными
            $.get(apiUrlTemplate.replace("{id}", userId), function (user) {
                console.log("Загруженные данные пользователя:", user);
                // Заполняем модальное окно полученными данными
                $(`${modalSelector}-firstName`).val(user.firstName);
                $(`${modalSelector}-lastName`).val(user.lastName);
                $(`${modalSelector}-email`).val(user.email);
                $(`${modalSelector}-username`).val(user.username);
                $(`${modalSelector}-password`).val(user.password);

                // Получаем список ролей пользователя
                let userRoles = user.authorities // ["USER", "ADMIN"]

                // Доступные роли в системе (можно загружать из API)
                let availableRoles = ["USER", "ADMIN"];

                // Генерируем чекбоксы и отмечаем нужные
                generateRoleCheckboxes("#modal", availableRoles, userRoles);

                // Показываем модальное окно
                let modal = new bootstrap.Modal(document.getElementById("modal"));
                modal.show();
            }).fail(function (xhr) {
                alert("Ошибка при получение пользователя: " + xhr.responseText);
            });
        }).fail(function (xhr) {
            alert("Ошибка при получение пользователя: " + xhr.responseText);
        });

        $(document).on("submit", '#modal-user-update-form', function (event) {
            event.preventDefault(); // Предотвращаем стандартную отправку формы

            let containerSelector = '#content'
            let formData = $(this).serialize(); // Собираем данные формы

            $.ajax({
                url: '/api/admin/users/' + userId,
                type: 'PUT',
                data: formData,
                success: function () {
                    // Загружаем обновленный список пользователей
                    $.get('/api/admin/users', function (data) {
                        $(containerSelector).fadeOut(200, function () {
                            $(this).empty();
                            $(this).append(generateAdminPanel(data));
                            $(this).fadeIn(200);
                        });
                    }).fail(function () {
                        $(containerSelector).text("Ошибка загрузки данных.");
                    });
                },
                error: function (xhr) {
                    alert("Ошибка при добавлении пользователя: " + xhr.responseText);
                }
            });

        });
    });

    // Закрытие модального окна
    $(document).on("click", closeSelector, function () {
        $("#modal").fadeOut(200, function () {
            $(this).remove(); // Полностью удаляем из DOM
        });
    });

    // Закрытие при клике вне окна
    $(document).on("click", function (event) {
        if ($(event.target).is(modalSelector)) {
            $("#modal").fadeOut(200, function () {
                $(this).remove(); // Полностью удаляем из DOM
            });
        }
    });
}

function generateRoleCheckboxes(modalSelector, availableRoles, userRoles) {
    let rolesList = $(`${modalSelector} #modalRoles`);
    rolesList.empty(); // Очищаем старые роли

    availableRoles.forEach(role => {
        let isChecked = userRoles.includes(role) ? "checked" : "";
        let checkbox = `
            <label>
                <input type="checkbox" name="authorities" value="${role}" ${isChecked} >
                ${role}
            </label><br>`;
        rolesList.append(checkbox);
    });
}


function handleDeleteUser() {
    $(document).on("click", "[class*='btn-delete']", function () {

        let userId = $(this).attr("class").match(/btn-delete-(\d+)/)[1];

        $.ajax({
            url: '/api/admin/users/' + userId,
            type: 'DELETE',
            success: function () {
                // Загружаем обновленный список пользователей
                $.get('/api/admin/users', function (data) {
                    $('#content').fadeOut(200, function () {
                        $(this).empty();
                        $(this).append(generateAdminPanel(data));
                        $(this).fadeIn(200);
                    });
                }).fail(function () {
                    $(containerSelector).text("Ошибка загрузки данных.");
                });
            },
            error: function (xhr) {
                alert("Ошибка при удалении пользователя: " + xhr.responseText);
            }
        });

    });
}