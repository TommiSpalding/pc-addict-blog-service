window.addEventListener('load', () => {

    document.write("<br>");
    let title = addElementFieldTo(document.body, "title", "input");
    document.write("<br>");
    let author = addElementFieldTo(document.body, "author", "input");
    document.write("<br>");
    let textBody = addElementFieldTo(document.body, "textBody", "textarea");
    document.write("<br>");

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
            headers: new Headers({ 'Content-Type': 'application/json'}) }).then((r) => { console.log(r); window.location.reload(false); }); });
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
    document.write("<br>");
    to.appendChild(element);

    return element;
}