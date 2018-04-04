function Blogpost(properties) {
    return <div className="card mb-4">
        <img className="card-img-top" src="http://placehold.it/750x300" alt="Card image cap"/>
        <div className="card-body">
            <h2 className="card-title">{properties.title}</h2>
            <p className="card-text">{properties.textBody}</p>
            <a href="#" class="btn btn-primary">Read More &rarr;</a>
        </div>
        <div className="card-footer text-muted">
            {new Date(Number(properties.timePosted)*1000).toDateString()} by <a href="#" value={properties.authorName} onClick={() => blogpostsByAuthorName(properties.authorName) }>{properties.authorName}</a>
        </div>
    </div>;
}

function ManyBlogposts(properties) {
    let arr = properties.array
    let output = []


    for(let j = 0; j < arr.length; j++) {
        output.push(<Blogpost title={arr[j].title} textBody={arr[j].textBody} timePosted={arr[j].timePosted} authorName={arr[j].authorName}/>)
    }

    return <div>{output}</div>
}

function allBlogposts() {
    fetch('http://localhost:8080/blogposts').then((response) => response.json()).then((arr) => {
        ReactDOM.render(<ManyBlogposts array={arr}/>,document.getElementById("blogposts"));
    });
}

function blogpostsByAuthorName(authorName) {
    fetch(`http://localhost:8080/blogposts/searchAuthor?q=${authorName}`).then((response) => response.json()).then((arr) => {
        ReactDOM.render(<ManyBlogposts array={arr}/>,document.getElementById("blogposts"));
    });
}

function blogpostsByTitle(titleName) {
    console.log('paska paska paska')
    fetch(`http://localhost:8080/blogposts/search?q=${titleName}`).then((response) => response.json()).then((arr) => {
        ReactDOM.render(<ManyBlogposts array={arr}/>,document.getElementById("blogposts"));
    });
}

function searchTitle() {
    console.log('vittu vittu vittu')
    let title = document.getElementById('searchInput').value;
    blogpostsByTitle(title);
}

allBlogposts();

