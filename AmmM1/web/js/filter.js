/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// evento ready del document
$(document).ready(function ()
{
   // aggangio dell'event handler
   $("#filtro").keyup(function()
    {  
        // preleva il valore inserito
        var q = $("#filtro").val();
       
        // richiesta ajax
        $.ajax(
        {
            url: "filter.json",
            data:{
              cmd: "search",
              q: q
            },
            dataType: 'json',
            success : function(data, state){
                updateTabella(data);
            },
            error : function(data, state){
            }
        });
        
       // Funzione che viene richiamata in caso di successo
        function updateTabella(listaArticoli)
        {
            //Svuoto la tabella e cancello un possibile messaggio precedente
            $("tr:not(.intestazione)").remove();
            $("#none").remove();
            
            if(listaArticoli.length != 0)
            {
                // Per ogni risultato aggiungo una riga
                for(var articolo in listaArticoli)
                {
                    var newRow = $('<tr></tr>');
                    //inserisco i campi nella riga
                    newRow.append('<td>' + listaArticoli[articolo].nomeArticolo + '</td>');
                    newRow.append('<td>'
                            + '<img src="' + listaArticoli[articolo].urlImmagine
                            + '" title="' + listaArticoli[articolo].nomeArticolo
                            + '" alt="Foto ' + listaArticoli[articolo].nomeArticolo
                            + '" width="100" height="120">'
                            + '</td>');
                    newRow.append('<td>' + listaArticoli[articolo].quantita + '</td>');
                    newRow.append('<td>' + listaArticoli[articolo].prezzo + ' €</td>');
                    newRow.append('<td><a href="cliente.html?idArticolo=' + listaArticoli[articolo].id
                            + '" class="cart" >Aggiungi al carrello</a></td>');
                    //Aggiungo la riga appena creata
                    $("table").append(newRow);
                }

            }else{
                // Avviso in caso non venga trovato nessun articolo
                $("table").after('<p class="error">'
                        + 'Nessun oggetto corrispondente</p>');
            }
            
        }
    }); 
});

