$(document).ready(function () {
    $("#content").on("submit", "#user-creation-form", function (event) {
        event.preventDefault(); // Предотвращаем стандартную отправку формы

        let formData = $(this).serialize(); // Собираем данные формы

        $.post("/api/admin/users", formData)
            .done(function () {
                // Загружаем обновленный список пользователей
                $.get("/api/admin/users", function (data) {
                    $("#content").fadeOut(200, function () {
                        $(this).empty();
                        $(this).append(generateAdminPanel(data));
                        $(this).fadeIn(200);
                    });
                }).fail(function () {
                    $("#content").text("Ошибка загрузки данных.");
                });
            })
            .fail(function (xhr) {
                alert("Ошибка при добавлении пользователя: " + xhr.responseText);
            });
    });
});
