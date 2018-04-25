window.addEventListener('load', () => {

    if(typeof(Storage) !== "undefined") {

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
    
            fetch('https://pc-addict-blog.herokuapp.com/blogposts', { 
    
                method: 'POST', 
                body: JSON.stringify({
    
                    title: title.value,
                    textBody: textBody.value,
                    authorName: author.value
                }),
                headers: {
                    'content-type': 'application/json',
                    'Authorization': 'Basic YWRtaW46dGFpa2F2aWl0dGE='
               }
               }).then((r) => { console.log(r); window.location.reload(false); });
        });
        
        document.body.appendChild(document.createElement('br'));
        document.body.appendChild(document.createElement('br'));
    
        createBlogpostTable(document.body);
        
    } else {

        let a = document.createElement('h1');
        a.innerHTML = "GET A MODERN BROWSER THAT SUPPORTS HTML5"

        document.body.appendChild(a);
    }
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

    fetch('https://pc-addict-blog.herokuapp.com/blogposts').then((response) => response.json()).then((arr) => {

        for(let j = 0; j < arr.length; j++) {

            let tr = tbl.insertRow();
    
            tr.insertCell().appendChild(document.createTextNode(j + 1));
            tr.insertCell().appendChild(document.createTextNode(arr[j].title));
            tr.insertCell().appendChild(document.createTextNode(arr[j].authorName));
            let b = tr.insertCell().appendChild(document.createElement('button'));
            b.innerHTML = 'DELET';
            b.addEventListener('click',() => { fetch('https://pc-addict-blog.herokuapp.com/blogposts/' + arr[j].id, { method: 'delete' }).then(() => { window.location.reload(false); }); });

            for(let l = 0; l < arr[j].comments.length; l++) {

                let arrr = arr[j].comments;

                let tr1 = tbl.insertRow();
        
                tr1.insertCell().appendChild(document.createTextNode(l + 1));
                tr1.insertCell().appendChild(document.createTextNode(arrr[l].author));
                tr1.insertCell().appendChild(document.createTextNode(arrr[l].likes));
                let b1 = tr1.insertCell().appendChild(document.createElement('button'));
                b1.innerHTML = 'DELET';
                b1.addEventListener('click',() => { fetch('https://pc-addict-blog.herokuapp.com/blogposts/' + arr[j].id + '/comments/' + l, { method: 'delete' }).then(() => { window.location.reload(false); }); });

                let b2 = tr1.insertCell().appendChild(document.createElement('button'));
                let item = "b" + arr[j].id + "c" + arrr[l].id;

                if(localStorage.getItem(item) == undefined || localStorage.getItem(item) == null || localStorage.getItem(item) == "" || localStorage.getItem(item) == 'no') {

                    b2.innerHTML = 'LIKE';
                    b2.addEventListener('click',() => { fetch('https://pc-addict-blog.herokuapp.com/blogposts/' + arr[j].id + '/comments/' + l + '/like', { method: 'post', body: 'yes' }).then(() => { window.location.reload(false); localStorage.setItem(item, 'yes'); }); });

                } else if(localStorage.getItem(item) == 'yes') {

                    b2.innerHTML = 'DONT';
                    b2.addEventListener('click',() => { fetch('https://pc-addict-blog.herokuapp.com/blogposts/' + arr[j].id + '/comments/' + l + '/like', { method: 'post', body: 'no' }).then(() => { window.location.reload(false); localStorage.setItem(item, 'no'); }); });

                }
            }
        }
    });

    e.appendChild(tbl);
}