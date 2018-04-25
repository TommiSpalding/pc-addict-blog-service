function Blogpost(properties) {
    return <div className="card mb-4">
        <img className="card-img-top" src="shiit.jpg" alt="Card image cap"/>
        <div className="card-body">
            <h2 className="card-title">{properties.title}</h2>
            <p className="card-text text-truncate">{properties.textBody}</p>
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
            <img className="card-img-top" src="shiit.jpg" alt="Card image cap"/>
            <div className="card-body">
                <h2 className="card-title">{properties.title}</h2>
                <p className="card-text">{properties.textBody}</p>
            </div>
            <div className="card-footer text-muted">
                {new Date(Number(properties.timePosted)*1000).toDateString()} by <a href="#" value={properties.authorName} onClick={() => blogpostsByAuthorName(properties.authorName) }>{properties.authorName}</a>
            </div>
        </div>
        <h4>Comments to this post</h4>
        <ManyComments array={properties.comments} parentId={properties.id}/>
        <button type="button" className="btn btn-success" data-toggle="modal" data-target="#postCommentModal" onClick={prePost(properties.id)}>Post a Comment!</button>
    </div>
}

function Comment(properties) {

    let item = "b" + properties.parentId + "c" + properties.realid;

    if(localStorage.getItem(item) == undefined || localStorage.getItem(item) == null || localStorage.getItem(item) == "" || localStorage.getItem(item) == 'no') {

        return <div className="card mb-3">
                <div className="card-body">
                    <p className="card-text float-left">{properties.textBody}</p>
                    <div className="float-right"><button className="btn btn-primary btn-sm" value={properties.id, properties.parentId} onClick={() => likeComment(properties.parentId, properties.id, item, true) }>Like!</button></div>
                </div>


                <div className="card-footer text-muted">
                    <div className="float-left">
                    {new Date(Number(properties.timePosted)*1000).toDateString()} by {properties.authorName}
                    </div>
                    <div className="float-right">Likes: {properties.likes}</div>
                    <div className="clearfix"></div>
                </div>
        </div>;

    } else if(localStorage.getItem(item) == 'yes') {

        return <div className="card mb-3">
                <div className="card-body">
                    <p className="card-text float-left">{properties.textBody}</p>
                    <div className="float-right"><button className="btn btn-primary btn-sm" value={properties.id, properties.parentId} onClick={() => likeComment(properties.parentId, properties.id, item, false) }>Dont!</button></div>
                </div>


                <div className="card-footer text-muted">
                    <div className="float-left">
                    {new Date(Number(properties.timePosted)*1000).toDateString()} by {properties.authorName}
                    </div>
                    <div className="float-right">Likes: {properties.likes}</div>
                    <div className="clearfix"></div>
                </div>
        </div>;
    }
}

function ManyComments(properties) {
    let arr = properties.array
    let output = []

    for(let j = 0; j < arr.length; j++) {
        console.log(arr[j])
        output.push(<Comment
        textBody={arr[j].textbody}
        timePosted={arr[j].timePosted}
        authorName={arr[j].author}
        likes={arr[j].likes}
        id={j}
        realid={arr[j].commentId}
        parentId={properties.parentId}/>)
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
        id={arr[j].blogId}/>)
    }

    return <div>{output}</div>
}

function likeComment(parentId, id, item, bool) {
    console.log(parentId)
    console.log(id)

    if(!bool)
        fetch(`http://localhost:8080/blogposts/${parentId}/comments/${id}/like`,{ method: 'post', body: 'no' }).then(() => { showFullBlogpost(parentId); localStorage.setItem(item, 'no'); });
    else
        fetch(`http://localhost:8080/blogposts/${parentId}/comments/${id}/like`,{ method: 'post', body: 'yes' }).then(() => { showFullBlogpost(parentId); localStorage.setItem(item, 'yes'); });
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
        id={post.blogId} comments={post.comments}/>,document.getElementById("blogposts"));
    });
}

function searchTitle() {
    let title = document.getElementById('searchInput').value;
    blogpostsByTitle(title);
}

function prePost(id) {

    document.thisIsNotGood = id;
}

function postComment() {

    fetch('http://localhost:8080/blogposts/' + document.thisIsNotGood + '/comments', {

        method: 'POST',

        body: JSON.stringify({

            author: document.getElementById("commentAuthor").value,
            textbody: document.getElementById("commentTextBody").value,
            likes: 0
        }),

        headers: new Headers({ 'Content-Type': 'application/json'})

    }).then(() => { showFullBlogpost(document.thisIsNotGood) });
}

allBlogposts();

