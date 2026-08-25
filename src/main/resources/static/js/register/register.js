$().ready(function (){

    $("#confirmPassword").on('keyup', function(e){
        if($("#password").val() != $(this).val()){
            e.preventDefault();
            $("#passwordMsg").text('비밀번호가 일치하지 않습니다.');
        }else{
            $("#passwordMsg").text('');
        }
    });
});