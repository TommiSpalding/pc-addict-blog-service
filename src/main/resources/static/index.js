var BrowserRouter = window.ReactRouterDOM.BrowserRouter
var Route = window.ReactRouterDOM.Route
var Link = window.ReactRouterDOM.Link
var HashRouter = window.ReactRouterDOM.HashRouter

function Blogpost(properties) {
    let pathname = `/blogPost/${properties.id}`
    let authorPathname = `/blogPostsByAuthor/${properties.authorName}`

    return <div className="card my-4">
        <img className="card-img-top" src="shiit.jpg" alt="Card image cap"/>
        <div className="card-body">
            <h2 className="card-title">{properties.title}</h2>
            <p className="card-text text-truncate">{properties.textBody}</p>
            <Link class="btn btn-primary" value={properties.id} to={pathname}>Read More &rarr;</Link>
        </div>
        <div className="card-footer text-muted">
            {new Date(Number(properties.timePosted)*1000).toDateString()} by <Link value={properties.authorName} to={authorPathname}>{properties.authorName}</Link>
        </div>
    </div>;
}

function FullBlogpost(properties) {
    let authorPathname = `/blogPostsByAuthor/${properties.authorName}`
    return <div>
        <div className="card my-4">
            <img className="card-img-top" src="shiit.jpg" alt="Card image cap"/>
            <div className="card-body">
                <h2 className="card-title">{properties.title}</h2>
                <p className="card-text pre-wrap">{properties.textBody}</p>
            </div>
            <div className="card-footer text-muted">
                {new Date(Number(properties.timePosted)*1000).toDateString()} by <Link value={properties.authorName} to={authorPathname}>{properties.authorName}</Link>
            </div>
        </div>
        <h4 className="text-white">Comments to this post</h4>
        <ManyComments array={properties.comments} parentId={properties.id} refresh={properties.refresh}/>
        <button type="button" className="btn btn-success" data-toggle="modal" data-target="#postCommentModal" onClick={prePost(properties.id, properties.refresh)}>Post a Comment!</button>
    </div>
}

function Comment(properties) {

    let item = "b" + properties.parentId + "c" + properties.realid;

    if(localStorage.getItem(item) == undefined || localStorage.getItem(item) == null || localStorage.getItem(item) == "" || localStorage.getItem(item) == 'no') {

        return <div className="card mb-3">
                <div className="card-body">
                    <p className="card-text float-left">{properties.textBody}</p>
                    <div className="float-right"><button className="btn btn-primary btn-sm" value={properties.id, properties.parentId} onClick={() => likeComment(properties.parentId, properties.id, item, true, properties.refresh) }>Like!</button></div>
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
                    <div className="float-right"><button className="btn btn-primary btn-sm" value={properties.id, properties.parentId} onClick={() => likeComment(properties.parentId, properties.id, item, false, properties.refresh) }>Dont!</button></div>
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

    if (typeof arr == 'undefined') return <div></div>

    for(let j = 0; j < arr.length; j++) {
        output.push(<Comment
        textBody={arr[j].textbody}
        timePosted={arr[j].timePosted}
        authorName={arr[j].author}
        likes={arr[j].likes}
        id={j}
        realid={arr[j].commentId}
        parentId={properties.parentId}
        refresh={properties.refresh}/>)
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

function likeComment(parentId, id, item, bool, refresh) {
    if(!bool)
        fetch(`http://localhost:8080/blogposts/${parentId}/comments/${id}/like`,{ method: 'post', body: 'no' }).then(() => { localStorage.setItem(item, 'no'); refresh(); });
    else
        fetch(`http://localhost:8080/blogposts/${parentId}/comments/${id}/like`,{ method: 'post', body: 'yes' }).then(() => {  localStorage.setItem(item, 'yes'); refresh(); });
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
    let path = `/blogPostsTitle/${title}`;
    ReactDOM.render(<Dummy/>,document.getElementById("safespace"));

}

function prePost(id, refresh) {

    document.thisIsNotGood = id;
    document.neitherIsThis = refresh
}

function postComment() {
    console.log(document.neitherIsThis)

    fetch('http://localhost:8080/blogposts/' + document.thisIsNotGood + '/comments', {

        method: 'POST',

        body: JSON.stringify({

            author: document.getElementById("commentAuthor").value,
            textbody: document.getElementById("commentTextBody").value,
            likes: 0
        }),

        headers: new Headers({ 'Content-Type': 'application/json'})

    }).then(() => { document.neitherIsThis() });
}


class App extends React.Component {

    render() {
        return (
            <div className="row">
                <div className="col-md-8">
                    <Route exact={true} path="/" component={AllBlogPosts}/>
                    <Route path="/blogPostsByAuthor/:authorName" component={BlogPostsByAuthorName}/>
                    <Route path="/blogPostsByTitle/:titleName" component={BlogPostsByTitle}/>
                    <Route path="/blogPost/:blogId" component={ShowFullBlogPost}/>
                    <Route path="/dummy" component={Dummy}/>
                </div>
                <div className="col-md-4">
                    <div className="card my-4">
                        <h5 className="card-header">Search in posts...</h5>
                        <div className="card-body">
                            <div className="input-group">
                                <input type="text" className="form-control" placeholder="titles, authors, textbody" id="searchInput"></input>
                                <span className="input-group-btn">
                          <Link className="btn btn-secondary" to="/dummy">Go!</Link>
                        </span>
                            </div>
                        </div>
                    </div>
                    <div className="card my-4">
                        <h5 className="card-header">Lets Follow The Rules</h5>
                        <div className="card-body">
                            <img className="card-img-top" src="mod.jpg"/>
                        </div>
                    </div>
                </div>
            </div>);
    }
}

class AllBlogPosts extends React.Component {
    constructor(props) {
        super(props);
        this.state = {'arr':[]}
    }

    componentDidMount() {
        fetch('http://localhost:8080/blogposts').then((response) => response.json()).then((arr) => {
            this.setState({'arr':arr});
        });
    }

    render() {
        return <ManyBlogposts array={this.state.arr}/>
    }
}

class BlogPostsByAuthorName extends React.Component {
    constructor(props) {
        super(props);
        this.state = {'arr':[]}
        this.authorName = props.match.params.authorName;
    }

    componentDidMount() {
        fetch(`http://localhost:8080/blogposts/searchAuthor?q=${this.authorName}`).then((response) => response.json()).then((arr) => {
            this.setState({'arr':arr});
        });
    }

    render() {
        return <ManyBlogposts array={this.state.arr}/>
    }
}

class BlogPostsByTitle extends React.Component {
    constructor(props) {
        super(props);
        this.state = {'arr':[]}
        this.titleName = props.match.params.titleName
    }

    componentDidMount() {
        fetch(`http://localhost:8080/blogposts/search?q=${this.titleName}`).then((response) => response.json()).then((arr) => {
            this.setState({'arr':arr});
        });
    }

    render() {
        return <ManyBlogposts array={this.state.arr}/>
    }
}

class Dummy extends React.Component {
    constructor(props) {
        super(props);
        let titleName = document.getElementById('searchInput').value;
        this.path = `/blogPostsByTitle/${titleName}`
    }

    componentDidMount() {
        this.props.history.replace(this.path)
    }

    render() {
        return <div></div>
    }
}

class ShowFullBlogPost extends React.Component {
    constructor(props) {
        super(props);
        this.state = {'post':{}}
        this.blogId = props.match.params.blogId;
    }

    componentDidMount() {
        fetch(`http://localhost:8080/blogposts/${Number(this.blogId)}`).then((response) => response.json()).then((post) => {
            this.setState({'post':post});
        });
    }

    refresh() {
        fetch(`http://localhost:8080/blogposts/${Number(this.blogId)}`).then((response) => response.json()).then((post) => {
            this.setState({'post':post});
        });
    }

    render() {
        return <FullBlogpost
               title={this.state.post.title}
               textBody={this.state.post.textBody}
               timePosted={this.state.post.timePosted}
               authorName={this.state.post.authorName}
               id={this.state.post.blogId}
               comments={this.state.post.comments}
               refresh={this.refresh.bind(this)}/>
    }
}

function fixLineBreaks(text) {
    if (typeof text == 'undefined') return ""
    return text.replace(/(?:\r\n|\r|\n)/g, '<br />');
}

ReactDOM.render(<HashRouter><App/></HashRouter>,document.getElementById("blogposts"));
