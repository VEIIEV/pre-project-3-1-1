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

    // Обработчик кликов на кнопки "Users table" и "New User"
    $(document).ready(function () {
        $("#content").on("click", "a[data-page]", function (e) {
            e.preventDefault();
            let page = $(this).data("page");

            if (page === "create-user-page") {
                $("#work-table").load("templates/user-form.html")
            } else if (page === "users-table-page") {
                $.get("/api/admin/users", function (data) {
                    $("#work-table").html(generateWorkTable(data));
                }).fail(function () {
                    console.error("Ошибка загрузки списка пользователей");
                });
            }
        });
    });
});




