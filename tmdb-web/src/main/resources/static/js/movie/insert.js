$().ready(function() {
    $("#posterUrl").on("change", function() {
        console.log($(this)[0].files);
        
        var file = $(this)[0].files[0];
        
        if (file) {
            $("#fileName").text(file.name);
        } else {
            $("#fileName").text("선택된 파일 없음");
        }
    });

    $("#insertVO").on("submit", function (event) {
        event.preventDefault();
        $(this).find(".span__error").remove();
        
        
        var title = $("#title").val();
        if (!title) {
            var errorMessage = $("<span>");
            errorMessage.addClass("span__error");
            errorMessage.text("제목이 입력되지 않았습니다.");

            $("#span__title").after(errorMessage);
        }

        var language = $("#language").val();
        if (!language) {
            var errorMessage = $("<span>");
            errorMessage.addClass("span__error");
            errorMessage.text("언어가 입력되지 않았습니다.");

            $("#span__language").after(errorMessage);
        }

        var state = $("#state").val();
        if (!state) {
            var errorMessage = $("<span>");
            errorMessage.addClass("span__error");
            errorMessage.text("개봉 상태가 입력되지 않았습니다.");

            $("#span__state").after(errorMessage);
        }

        var synopsis = $("#synopsis").val();
        if (!synopsis) {
            var errorMessage = $("<span>");
            errorMessage.addClass("span__error");
            errorMessage.text("줄거리가 입력되지 않았습니다.");

            $("#span__synopsis").after(errorMessage);
        }

        if ($(".span__error").length === 0) {
            this.submit(); 
        }
    });

});