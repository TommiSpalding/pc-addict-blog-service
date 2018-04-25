window.addEventListener('load', () => {

    if(typeof(Storage) !== "undefined") {

        let root = document.getElementById('root-container');

        let form = document.createElement('form');
        form.setAttribute('class', 'form-group');

        let title = addElementFieldTo(form, "title", "input", "ititle");
        title.setAttribute('class', 'form-control');
        title.setAttribute('placeholder', 'enter title');

        let author = addElementFieldTo(form, "author", "input", "iauthor");
        author.setAttribute('class', 'form-control');
        author.setAttribute('placeholder', 'enter author');

        let textBody = addElementFieldTo(form, "textBody", "textarea", "itext");
        textBody.setAttribute('class', 'form-control');
        textBody.setAttribute('rows', '10');
        textBody.setAttribute('placeholder', 'enter blog');

        textBody.style.height = "500px";

        root.appendChild(form);
        
        let b = root.appendChild(document.createElement('button'));
        b.innerHTML = 'POST';
        b.setAttribute('class','btn btn-success btn-lg btn-block');
        b.setAttribute('style','margin-bottom:20px')
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
    
        createBlogpostTable(root);
        
    } else {

        let a = document.createElement('h1');
        a.innerHTML = "GET A MODERN BROWSER THAT SUPPORTS HTML5"

        document.body.appendChild(a);
    }
});

function addElementFieldTo(to, title, e, id) {

    let element = document.createElement(e);

    //let label = document.createElement("Label");
    //label.setAttribute("for",id)
    //label.innerHTML = title;

    element.setAttribute("type", "text");
    element.setAttribute("id", id);
    element.setAttribute("name", title);

    //to.appendChild(label);
    to.appendChild(element);

    return element;
}

function createBlogpostTable(e) {

    let tbl  = document.createElement('table');

    tbl.setAttribute('class','table table-striped');
    tbl.setAttribute('id','table1');

    let tr = tbl.insertRow();
    tr.insertCell().appendChild(document.createTextNode('#'));
    tr.insertCell().appendChild(document.createTextNode('title'));
    tr.insertCell().appendChild(document.createTextNode('author'));
    tr.insertCell().appendChild(document.createTextNode('act'));

    fetch('https://pc-addict-blog.herokuapp.com/blogposts').then((response) => response.json()).then((arr) => {

        for(let j = 0; j < arr.length; j++) {

            let tr = tbl.insertRow();
    
            tr.insertCell().appendChild(document.createTextNode(arr[j].blogId));
            tr.insertCell().appendChild(document.createTextNode(arr[j].title));
            tr.insertCell().appendChild(document.createTextNode(arr[j].authorName));
            let b = tr.insertCell().appendChild(document.createElement('button'));
            b.setAttribute('class','btn btn-danger');
            b.innerHTML = 'DELETE';
            b.addEventListener('click',() => { fetch('https://pc-addict-blog.herokuapp.com/blogposts/' + arr[j].blogId, { method: 'delete', headers: { 'content-type': 'application/json','Authorization': 'Basic YWRtaW46dGFpa2F2aWl0dGE=' } }).then(() => { window.location.reload(false); }); });

            let b2 = tr.insertCell().appendChild(document.createElement('button'));
            b2.setAttribute('class','btn btn-primary');
            b2.setAttribute('data-toggle','modal');
            b2.setAttribute('data-target','#modifyPostModal');
            b2.innerHTML = 'MODIFY';
            b2.addEventListener('click', () => { preModify(arr[j].blogId); });

            for(let l = 0; l < arr[j].comments.length; l++) {

                let arrr = arr[j].comments;

                let tr1 = tbl.insertRow();

                let javascriptisfun = l;
        
                tr1.insertCell().appendChild(document.createTextNode('c' + javascriptisfun));
                tr1.insertCell().appendChild(document.createTextNode(arrr[l].author));
                tr1.insertCell().appendChild(document.createTextNode(arrr[l].likes));
                let b1 = tr1.insertCell().appendChild(document.createElement('button'));
                b1.setAttribute('class','btn btn-danger');
                b1.innerHTML = 'DELETE';
                b1.addEventListener('click',() => { fetch('https://pc-addict-blog.herokuapp.com/blogposts/' + arr[j].blogId + '/comments/' + l, { method: 'delete', headers: { 'content-type': 'application/json','Authorization': 'Basic YWRtaW46dGFpa2F2aWl0dGE=' } }).then(() => { window.location.reload(false); }); });
            }
        }
    });

    e.appendChild(tbl);
}

function preModify(id) {

    fetch('https://pc-addict-blog.herokuapp.com/blogposts/' + id).then((response) => response.json()).then((arr) => {

        document.getElementById('modifyTitle').setAttribute('value', arr.title);
        document.getElementById('modifyAuthor').setAttribute('value', arr.authorName);
        document.getElementById('modifyTextBody').setAttribute('value', arr.textBody);
    }

    document.thisIsNotGood = id;
}

function modifyPost() {

    console.log(document.thisIsNotGood);

    fetch('https://pc-addict-blog.herokuapp.com/blogposts/' + document.thisIsNotGood, {

        method: 'POST',

        body: JSON.stringify({

            title: document.getElementById("modifyTitle").value,
            authorName: document.getElementById("modifyAuthor").value,
            textBody: document.getElementById("modifyTextBody").value,
        }),

        headers: {
            'content-type': 'application/json',
            'Authorization': 'Basic YWRtaW46dGFpa2F2aWl0dGE='
        }

    }).then(() => { window.location.reload(false); });
}