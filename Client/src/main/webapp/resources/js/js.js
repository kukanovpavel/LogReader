
$(document).ready(function(){

$('#clear').click(function() {

    document.getElementById("testForm").reset();
    $('#result').empty();
    $('#date').empty();
});

    $('#addDate').click(function(){

        $('#date').append('<div><input  type="text" name="fromDate"/>to <input  type="text" name="toDate"/><br><br></div>');
    });

    $('#delete').click(function(){

        $(this).parent('div').remove();
    });



});





