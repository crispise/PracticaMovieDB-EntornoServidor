var datos = JSON.parse(jsonInfo);

$(function() {
    $("#tags").autocomplete({
        source: function(request, response) {
            // Filtro personalizado: buscar por letras que comienzan con la entrada del usuario
            var resultados = $.grep(datos, function(item) {
                return item.toLowerCase().startsWith(request.term.toLowerCase()); // Filtra por lo que comienza con las letras ingresadas
            });

            // Mostrar los resultados filtrados
            response(resultados);
        }
    });
});

