$().ready(function () {
  // 메인 이동
  $("#main-title").on("click", function () {
    window.location.href = "/order";
  });

  // 상세 이동
  $("li.menu").on("click", function () {
    window.location.href = "/order/detail/" + $(this).data("id");
  });

  // 삭제 클릭
  $("#remove").on("click", function () {
    $.get("/payment/remove", function (response) {
      if (response.status === "success") alert("삭제 완료!");
    });
  });

  /**
   * 모달
   */
  var seatIdList = [];
  // 좌석 클릭
  $("button.seat").on("click", function () {
    $("#seatId").val($(this).data("id"));
    $(".modal").show();
  });
  // 취소 클릭
  $(".cancle").on("click", function () {
    $(".modal").hide();
  });
  // 시간 클릭
  $(".time").on("click", function () {
    $(".time").removeClass("selected");
    $(this).addClass("selected");
    $("#reserveTime").val(Number($(this).data("time")));
    // 클릭하면 시간 변경 => 완료 활성화
    $(".complete").prop("disabled", false);
  });
  // 완료 클릭 => input hidden으로 추가
  $(".complete").on("click", function () {
    seatIdList.push($("#seatId").val());
    console.log(seatIdList);
    $("<div>")
      .addClass("seat-row")
      .append(
        $("<label>").text("선택 좌석: "),
        $("<input>")
          .attr("type", "text")
          .attr("name", "seatIdList")
          .val(seatIdList[seatIdList.length - 1])
          .prop("readonly", true),
        $("<button>").attr("type", "button").addClass("remove-seat").text("X")
      )
      .appendTo("#seatForm");

    $(".modal").hide();
  });
  // 선택 좌석 삭제
  $("#seatForm").on("click", ".remove-seat", function () {
    var row = $(this).closest(".seat-row");
    var seatId = row.find("input").val();

    // 배열에서도 제거
    seatIdList = seatIdList.filter((id) => id !== seatId);

    // 화면에서도 제거
    row.remove();
  });

  /**
   * detail 화면
   */
  $("#cancle").on("click", function () {
    window.location.href = "/order";
  });
});
