function Blogpost(properties) {
    return <div className="card mb-4">
        <img className="card-img-top" src="http://placehold.it/750x300" alt="Card image cap"/>
        <div className="card-body">
            <h2 className="card-title">{properties.title}</h2>
            <p className="card-text">{properties.textBody}</p>
            <a href="#" class="btn btn-primary" value={properties.id} onClick={() => showFullBlogpost(properties.id)}>Read More &rarr;</a>
        </div>
        <div className="card-footer text-muted">
            {new Date(Number(properties.timePosted)*1000).toDateString()} by <a href="#" value={properties.authorName} onClick={() => blogpostsByAuthorName(properties.authorName) }>{properties.authorName}</a>
        </div>
    </div>;
}

function FullBlogpost(properties) {
    return <div>
        <div className="card mb-4">
            <img className="card-img-top" src="http://placehold.it/750x300" alt="Card image cap"/>
            <div className="card-body">
                <h2 className="card-title">{properties.title}</h2>
                <p className="card-text">{properties.textBody}</p>
            </div>
            <div className="card-footer text-muted">
                {new Date(Number(properties.timePosted)*1000).toDateString()} by <a href="#" value={properties.authorName} onClick={() => blogpostsByAuthorName(properties.authorName) }>{properties.authorName}</a>
            </div>
        </div>
        <h4>Comments to this post</h4>
        <ManyComments array={properties.comments} parent={properties.id}/>
        <a href="#" className="btn btn-success" id="postCommentButton">Post a Comment!</a>
    </div>
}

function Comment(properties) {
    return <div className="card mb-3">
       <div className="card-body">
           <p className="card-text">{properties.textBody}</p>
       </div>
       <a href="#" className="btn btn-primary float-right">Like *HEART*</a>
       <div className="card-footer text-muted">
           <div className="float-left">
           {new Date(Number(properties.timePosted)*1000).toDateString()} by {properties.authorName}
           </div>
           <div className="float-right">Likes: {properties.likes}</div>
           <div className="clearfix"></div>
       </div>
    </div>;
}

function ManyComments(properties) {
    let arr = properties.array
    let output = []

    for(let j = 0; j < arr.length; j++) {
        output.push(<Comment
        textBody={arr[j].textbody}
        timePosted={arr[j].timePosted}
        authorName={arr[j].author}
        likes={arr[j].likes}
        id={arr[j].id}
        parent={properties.parent}/>)
    }

    return <div>{output}</div>
}

function ManyBlogposts(properties) {
    let arr = properties.array
    let output = []

    for(let j = 0; j < arr.length; j++) {
        output.push(<Blogpost
        title={arr[j].title}
        textBody={arr[j].textBody}
        timePosted={arr[j].timePosted}
        authorName={arr[j].authorName}
        id={arr[j].id}/>)
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
    fetch(`http://localhost:8080/blogposts/search?q=${titleName}`).then((response) => response.json()).then((arr) => {
        ReactDOM.render(<ManyBlogposts array={arr}/>,document.getElementById("blogposts"));
    });
}

function showFullBlogpost(id) {
    fetch(`http://localhost:8080/blogposts/${Number(id)}`).then((response) => response.json()).then((post) => {
        ReactDOM.render(<FullBlogpost
        title={post.title}
        textBody={post.textBody}
        timePosted={post.timePosted}
        authorName={post.authorName}
        id={post.id} comments={post.comments}/>,document.getElementById("blogposts"));
    });
}

function searchTitle() {
    let title = document.getElementById('searchInput').value;
    blogpostsByTitle(title);
}

allBlogposts();

