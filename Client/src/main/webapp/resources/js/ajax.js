$(document).ready(function(){
    $('#submit').click(function(){
        var formdata=$('#testForm').serialize();
        var div =  $('#result');
        div.empty();
        jQuery.ajax({
            url: "search",
            type: "POST",
            data: formdata,
            async: true,
            success: function(data) {

                data.forEach(function (item,i,obj) {
                    div.append('<div>' + item.thread + '<br>'+item.date + '<br>'+ item.text.replace(/</g,"&lt;") + '</div>'+ '<br><br>');

                })

            }
        });
    });
});