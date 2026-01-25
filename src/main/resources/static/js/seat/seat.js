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
  // TODO : 동시 예약 금지

  // 결제 완료 => 현재부터 시간 서버로 보내서 디비에 저장

  // 결제하기 클릭
  $("#payment").on("click", function () {
    // 결제하기 버튼 클릭 (좌석 리스트)

    //$("form").submit();

    //alert("!");
    IMP.init("imp26301316"); // 발급받은 가맹점 식별코드

    $.get("/payment", function (response) {
      console.log(response);
      console.log(response.orderId);
      console.log(response.amount);

      // TODO : 주문 id

      IMP.request_pay(
        {
          pg: "html5_inicis",
          pay_method: "card",
          //merchant_uid: "order_" + new Date().getTime(),
          merchant_uid: response.orderId, //"order_" + new Date().getTime(),//merchantUid, // “서비스 예약 슬롯” PK
          // 주문 고유 번호 => 요청시마다 바뀌어야 함
          name: "음료",
          amount: response.amount,
        },
        function (response) {
          // 결제 종료 시 호출되는 콜백 함수
          // response.imp_uid 값으로 결제 단건조회 API를 호출하여 결제 결과를 확인하고,
          // 결제 결과를 처리하는 로직을 작성
          var impUid = response.imp_uid;
          if (response.success == true) {
            alert("결제 완료");
          } else {
            alert("결제 실패");
          }
          // 서버에 결제 완료 저장

          $.ajax({
            url: "/payment/valid",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
              orderId: merchant_uid,
              ordrDt: resOrdrDt,
              ordrStts: resStatus,
              resSuccess: resSuccess,
              pymnt: "P001", // 카드
              ttltyAmnt: resAmount,
              resErrorCd: resErrorCd,
              impUid: impUid,
            }),
            //dataType: "json",
            success: function (response) {
              // alert("결제가 완료되었습니다. 예약 확정!");
              // 결제 취소해도 여기로 온다!
              console.log("서버 응답 완료");
              console.log("~~ : ", $(".title").data("success"));
              if (resSuccess == true && resStatus === "paid") {
                window.location.href = "/payment/success";
                // 서버에서 redirect 안 먹음
              } else {
                window.location.href = "/payment/fail";
              }
            },
            error: function (xhr, status, err) {
              console.error("서버 에러:", xhr.status, xhr.responseText);
              alert("서버 요청 실패");
              window.location.href = "/payment/fail";
            },
          });
        }
      );
    });
  });

  /**
   * detail 화면
   */
  $("#cancle").on("click", function () {
    window.location.href = "/order";
  });
});
