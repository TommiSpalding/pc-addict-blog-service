window.addEventListener('load', () => {

    document.body.appendChild(document.createElement('br'));
    let title = addElementFieldTo(document.body, "title", "input");
    document.body.appendChild(document.createElement('br'));
    let author = addElementFieldTo(document.body, "author", "input");
    document.body.appendChild(document.createElement('br'));
    let textBody = addElementFieldTo(document.body, "textBody", "textarea");
    document.body.appendChild(document.createElement('br'));

    textBody.style.height = "500px";
    
    let b = document.body.appendChild(document.createElement('button'));
    b.innerHTML = 'POST';
    b.addEventListener('click',() => {

        fetch('http://localhost:8080/blogposts', { 

            method: 'POST', 
            body: JSON.stringify({

                title: title.value,
                textBody: textBody.value,
                authorName: author.value
            }),
            headers: new Headers({ 'Content-Type': 'application/json'}) }).then((r) => { console.log(r); window.location.reload(false); }); 
    });
    
    document.body.appendChild(document.createElement('br'));
    document.body.appendChild(document.createElement('br'));

    createBlogpostTable(document.body);
});

function addElementFieldTo(to, title, e) {

    let element = document.createElement(e);

    let label = document.createElement("Label");
    label.innerHTML = title;     

    element.setAttribute("type", "text");
    element.setAttribute("value", title + " here");
    element.setAttribute("name", title);
    element.setAttribute("style", "width:200px");

    label.setAttribute("style", "font-weight:normal");

    to.appendChild(label);
    document.body.appendChild(document.createElement('br'));
    to.appendChild(element);

    return element;
}

function createBlogpostTable(e) {

    let tbl  = document.createElement('table');

    tbl.style.border = '1px solid black';

    tbl.setAttribute('id','table1');

    let tr = tbl.insertRow();
    tr.insertCell().appendChild(document.createTextNode('#'));
    tr.insertCell().appendChild(document.createTextNode('title'));
    tr.insertCell().appendChild(document.createTextNode('author'));
    tr.insertCell().appendChild(document.createTextNode('act'));

    fetch('http://localhost:8080/blogposts').then((response) => response.json()).then((arr) => {

        for(let j = 0; j < arr.length; j++) {

            let tr = tbl.insertRow();
    
            tr.insertCell().appendChild(document.createTextNode(j + 1));
            tr.insertCell().appendChild(document.createTextNode(arr[j].title));
            tr.insertCell().appendChild(document.createTextNode(arr[j].authorName));
            let b = tr.insertCell().appendChild(document.createElement('button'));
            b.innerHTML = 'DELET';
            b.addEventListener('click',() => { fetch('http://localhost:8080/blogposts/' + arr[j].id, { method: 'delete' }).then(() => { window.location.reload(false); }); });
        }
    });

    e.appendChild(tbl);
}