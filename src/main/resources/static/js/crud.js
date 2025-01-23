var datos = JSON.parse(jsonInfo);



$(function() {

    $("#tags").autocomplete({
        source: function(request, response) {
            var resultados = $.grep(datos, function(item) {
                return item.toLowerCase().startsWith(request.term.toLowerCase()); // Filtra por lo que comienza con las letras ingresadas
            });
            response(resultados);
        }
    });
    $("#tags2").autocomplete({
        source: function(request, response) {
            if (typeof jsonInfo2 !== 'undefined' && jsonInfo2 !== null) {
                var datos2 = JSON.parse(jsonInfo2);
                console.log("Datos2:", datos2);
            } else {
                console.log("jsonInfo2 no está definido correctamente.");
            }
            var resultados = $.grep(datos2, function(item) {
                return item.toLowerCase().startsWith(request.term.toLowerCase()); // Filtra por lo que comienza con las letras ingresadas
            });
            response(resultados);
        }
    });
});




