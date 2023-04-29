function newToDo() {
    var authorname = document.getElementById('author').value;
    var titlename = document.getElementById('title').value;
    var urlEnc = encodeURI('addbook?author=' +authorname +'&'+'title='+titlename )
    $.ajax({
       url: urlEnc
    }).done(function (response) {
        location.href = "listMyStuff.jsp";
    });
}

function loadToDo() {
    $.ajax({
        url: 'listbook'
    }).done(function (response) {
      //  printOnDiv(response.listFromBackend);
          display(response.listFromBackend);
    });
}

 function deleteBook() {
     var authorname = document.getElementById('delBook').value;
     var urlEnc = encodeURI('deletebook?author=' +authorname)
    $.ajax({
        url: urlEnc,
    }).done(function (response) {
        location.href = "listMyStuff.jsp";
    });
}
function deleteAll() {
    $.ajax({
        url: 'deleteall',
    }).done(function (response) {
        location.href = "listMyStuff.jsp";
    });
}
function display (lista) {
    var randuri = "";
    lista.forEach(function (obiect) {
        randuri += "<tr>" +
            "<td>" + obiect.authorname +"</td>" +
            "<td>" + obiect.titlename + "</td>" +
            "</tr>";
    });
    $("#obiect").html(randuri);
}

function search(myText) {
    $.ajax("listbook", {
        cache: false,
        dataType: "json",
        data: {
            // order: ordinea,
            search: myText
        }
    }).done(function (response) {
        display(response.listFromBackend);
    });
}


function printOnDiv(listFromBackend) {
    var listHtml = '';

    var list = document.getElementById('listOfToDo');

    for (var i = 0; i < listFromBackend.length; i++) {
        var elemC = listFromBackend[i];
        var el = '<li>'+elemC.authorname+' '+elemC.titlename+' </li>';
        listHtml=listHtml+el;
    }
    list.innerHTML = '<ol>'+listHtml+'</ol>';
}
function openBook() {
    var authorname = document.getElementById('openbook').value;
    var urlEnc = encodeURI('openbook?author=' +authorname)
    $.ajax({
        url: urlEnc,
    }).done(function (response) {
        location.href = "listMyStuff.jsp";
    });
}


