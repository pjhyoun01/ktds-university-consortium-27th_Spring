$().ready(function () {var delay;
    $("#email").on("keyup", function () {
        var emailValue = $(this).val();
        $(".valid__error, .check__email").remove();

        clearTimeout(delay);
        if (emailValue) {
        delay = setTimeout(function () {
            var emailPattern = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/;
            if (emailPattern.test(emailValue)) {
                fetch("/members/regist/" + emailValue)
                    .then(function (fetchResult) {
                        return fetchResult.json();
                    })
                    .then(function (json) {
                        duplicateResult = $("<span>");
                        
                        if (!json.duplicate) {
                        duplicateResult.addClass("check__email");
                        duplicateResult.text("사용가능한 이메일 입니다");
                        } else {
                        duplicateResult.addClass("valid__error");
                        duplicateResult.text("사용중인 이메일 입니다");
                        }
                        $("label[for='email']").after(duplicateResult);
                    });
            }
        }, 300);
        }
    });

    $("#password, #confirm-password").on("keyup", function () {

        var confirmPassword = $("label[for='confirm-password']");
        confirmPassword.next().remove();

        if ($("#password").val() !== $("#confirm-password").val()) {
            confirmPassword.after($("<span>").addClass("valid__error").text("비밀번호가 일치하지 않습니다"));
        } else {
            confirmPassword.after($("<span>").addClass("valid__error").text("비밀번호가 일치합니다"));

        }
    });

    $("#view-password, #view-confirm-password").on("change", function () {
        if (this.checked) {
            $(this).prev().prev().attr("type", "text");
        } else {
            $(this).prev().prev().attr("type", "password");
        }
    });

    $("#registVO").on("submit", function (event) {
        event.preventDefault();
        $(this).find(".valid__error").remove();
        
        var email = $("#email").val();
        if (!email) {
            var validError = $("<span>");
            validError.addClass("valid__error");
            validError.text("이메일을 입력해주세요");
            $("label[for='email']").after(validError);
        } 
        
        var name = $("#name").val();
        if (!name) {
            var validError = $("<span>");
            validError.addClass("valid__error");
            validError.text("이름음 입력해주세요");
            $("label[for='name']").after(validError);
        }
        
        var password = $("#password").val();
        if (!password) {
            var validError = $("<span>");
            validError.addClass("valid__error");
            validError.text("비밀번호를 입력해주세요");
            $("label[for='password']").after(validError);
        }
        
        var confirmPassword = $("#confirm-password").val();
        if (!confirmPassword) {
            var validError = $("<span>");
            validError.addClass("valid__error");
            validError.text("확인 비밀번호 입력해주세요");
            $("label[for='confirm-password']").after(validError);
        }

        if ($(".valid__error").length === 0) {
            this.submit(); 
        }
    });
});