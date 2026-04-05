$().ready(function () {
  // ".add-file"을 클릭하면
  // 새로운 파일 인풋과 버튼을
  // ".attach-files" 아래에 추가한다.
  $(".attach-files").on("click", ".add-file", function () {
	//$(".add-file").on("click", function () {
	// 새로운 파일이 추가될 때 마다 기존의 "add-file"을 "del-file"로 변경하고
	// 텍스트는 "+" 에서 "-"로 변경한다.
	$(this)
	  .closest(".attach-files")
	  .children(".add-file")
	  .removeClass("add-file")
	  .addClass("del-file")
	  .text("-")
	  .off("click") // 할당되어있던 이벤트를 제거한다.
	  .on("click", function () {
		// 버튼 왼쪽에 있는 인풋 태그 삭제.
		$(this).prev().remove();
		// 버튼도 삭제.
		$(this).remove();
	  }); // 새로운 이벤트를 추가한다.

	var fileInput = $("<input />");
	fileInput.attr({
	  type: "file",
	  name: "attachFile",
	});

	var addButton = $("<button />");
	addButton.attr("type", "button").addClass("add-file").text("+");

	$(".attach-files").append(fileInput).append(addButton);
  });
  
	$("#writeVO").on("submit", function (event) {
		event.preventDefault();
		$(this).find(".validation-error").remove();
		
		var subject = $("#subject").val();
		if (subject.length <= 3) {
			var emailErrorMessage = $("<div>");
			emailErrorMessage.addClass("validation-error");
			emailErrorMessage.text("제목을 세 글자 이상 입력해주세요.");
			
			$("#subject").after(emailErrorMessage);
			
			if (!subject) {
				emailErrorMessage.text("제목을 입력해주세요.");
				
				$("#subject").after(emailErrorMessage);
			}
		}

		var email = $("#email").val();
		var emailPattern = /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z0-9]+$/;
		if (!emailPattern.test(email)) {
			var emailErrorMessage = $("<div>");
			emailErrorMessage.addClass("validation-error");
			emailErrorMessage.text("이메일 형식은 xxx@xxx.xxx입니다.");
			
			$("#email").after(emailErrorMessage);
			
			if (!email) {
				emailErrorMessage.text("이메일을 입력해주세요.");
				
				$("#email").after(emailErrorMessage);
			}
		}

		if ($(".validation-error").length === 0) {
			this.submit();
		}
	});
});
