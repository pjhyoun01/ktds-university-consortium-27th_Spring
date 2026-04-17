$().ready(function () {
    $(".page-navigator").find("a").on("click", function () {
        var pageNo = $(this).data("page-no");
        var listSize = $("#list-size").val();
        var searchType = $("#search-type").val();
        var searchKeyword = $("#search-keyword").val();

        location.href = "/?pageNo=" + pageNo
            + "&listSize=" + listSize
            + "&searchType=" + searchType
            + "&searchKeyword=" + searchKeyword;
    });


    $("#list-size").on("change", function () {
        // location.href = "/?pageNo=0&listSize=" + $(this).val();
        $(".search-button").trigger("click");
    });

    $(".search-button").on("click", function () {
        // /?pageNo=0&listSize=#list-size값&searchType=#search-type값&searchKeyword=#search-keyword값
        var pageNo = 0;
        var listSize = $("#list-size").val();
        var searchType = $("#search-type").val();
        var searchKeyword = $("#search-keyword").val();

        location.href = "/?pageNo=" + pageNo
            + "&listSize=" + listSize
            + "&searchType=" + searchType
            + "&searchKeyword=" + searchKeyword;
    });
})