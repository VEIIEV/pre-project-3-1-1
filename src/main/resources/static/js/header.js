$(document).ready(function () {
    // Запрос на получение текущего пользователя
    $.get("/api/users/min", function (userDetailsDTO) {
        let rolesText = userDetailsDTO.authorities.length > 0
            ? " with roles: " + userDetailsDTO.authorities.join(", ")
            : " without roles";
        $("#username").text(userDetailsDTO.username + rolesText);
    }).fail(function () {
        $("#username").text("Гость");
        $("#logout").hide();
    });

    // Обработчик выхода
    $("#logout").click(function () {
        $.post("/logout", function () {
            window.location.href = "/"; // После выхода перенаправляем на главную
        });
    });


    $(".nav-link").click(function (e) {
        e.preventDefault();
        $(".nav-link").removeClass("active");
        $(this).addClass("active");
    });
});

